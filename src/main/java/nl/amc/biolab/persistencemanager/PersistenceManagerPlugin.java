package nl.amc.biolab.persistencemanager;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.amc.biolab.admin.constants.VarConfig;
import nl.amc.biolab.datamodel.manager.PersistenceManager;
import nl.amc.biolab.datamodel.objects.Application;
import nl.amc.biolab.datamodel.objects.DataElement;
import nl.amc.biolab.datamodel.objects.Processing;
import nl.amc.biolab.datamodel.objects.Project;
import nl.amc.biolab.datamodel.objects.Submission;
import nl.amc.biolab.datamodel.objects.SubmissionIO;

/**
 * Plugin for the PersistenceManager, adds functions specifically for the admin portlet
 * 
 * @author Allard van Altena
 */
public class PersistenceManagerPlugin extends PersistenceManager {
	Connection connect = null;
	
	/**
	 * Searches the database with the provided sql, the sql should contain a join of Project and Processing for this to work
	 * @param sql Sql by which we search for the projects, should contain a join of Project and Processing tables
	 * @return One project in a list of arrays, array position 0 contains the Project, array position 1 contains the Processing
	 */
	public List<Object[]> getSingleProjectBySQL(String sql) {
		return _executeProjectSQL(sql, true);
	}

	/**
	 * Searches the database with the provided sql, the sql should contain a join of Project and Processing for this to work
	 * @param sql Sql by which we search for the projects, should contain a join of Project and Processing tables
	 * @return One or more projects in a list of arrays, array position 0 contains the Project, array position 1 contains the Processing
	 */	
	public List<Object[]> getProjectsBySQL(String sql) {
		return _executeProjectSQL(sql, false);
	}
	
	/**
	 * Searches database with the provided sql, sets the Project, Processing, Submission, SubmissionIO, and DataElement objects
	 * @param sql Sql by which we search for projects, should contain a join of Project and Processing tables
	 * @param single_project Switch for getting additional data for project (i.e. Submission, SubmissionIO, and DataElement), this lowers the loading time when refreshing the browser window
	 * @return One or more projects in a list of arrays, array position 0 contains the Project, array position 1 contains the Processing
	 */
	private List<Object[]> _executeProjectSQL(String sql, boolean single_project) {
		List<Object[]> projects = new ArrayList<Object[]>();
		VarConfig config = new VarConfig();
		
		try {
			connect = DriverManager.getConnection(config.getDbConnectionUrl());
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				Object[] holder = new Object[2];
				
				Project project = new Project();
				project.setDbId(resultSet.getLong("ProjectID"));
				project.setXnatID(resultSet.getString("XnatID"));
				project.setName(resultSet.getString("ProjectName"));
				project.setDescription(resultSet.getString("ProjectDescription"));
				project.setOwner(resultSet.getString("UserName"));
				//project.setApplications();
				//project.setDataElements();
				
				Processing processing = new Processing();
				processing.setDbId(resultSet.getLong("ProcessingID"));
				processing.setName(resultSet.getString("ProcessingName"));
				processing.setDescription(resultSet.getString("ProcessingDescription"));
				processing.setDate(resultSet.getDate("ProcessingDate"));
				processing.setStatus(resultSet.getString("ProcessingStatus"));
				processing.setLastUpdate(resultSet.getDate("ProcessingLastUpdate"));
				
				Application app = new Application();
				app.setDbId(resultSet.getLong("ApplicationID"));
				app.setName(resultSet.getString("Name"));
				
				processing.setApplication(app);
				
				if (single_project) {
					processing.setSubmissions(_getSubmissions(resultSet.getLong("ProcessingID")));
				}
				
				holder[0] = project;
				holder[1] = processing;
				
				projects.add(holder);
			}
			
			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("Error in project");
			e.printStackTrace();
		}
		
		return projects;
	}
	
	/**
	 * Gets submissions for specific processing id, function is used when searching for a single project
	 * @param processingId Processing id of project we want the Submissions, SubmissionIOs, and DataElements for.
	 * @return Collection of Submissions which contain SubmissionIOs and DataElements as well
	 */
	private Collection<Submission> _getSubmissions(Long processingId) {
		ArrayList<Submission> submissions = new ArrayList<Submission>();
		
		try {
			Statement statement = connect.createStatement();
			
			String sql = "SELECT s.* FROM Submission as s WHERE s.ProcessingID = " + processingId;
			ResultSet submissionSet = statement.executeQuery(sql);
			
			while (submissionSet.next()) {
				ArrayList<SubmissionIO> subIOs = new ArrayList<SubmissionIO>();
				
				Submission sub = new Submission();
				sub.setDbId(submissionSet.getLong("SubmissionID"));
				sub.setName(submissionSet.getString("Name"));
				sub.setStatus(submissionSet.getString("Status"));
				sub.setResults(submissionSet.getBoolean("Results"));
				
				String sqlIO = "SELECT sio.*, de.*, sio.Type as SubIOType, de.Type as DataType "
						+ "FROM SubmissionIO as sio "
						+ "JOIN DataElement as de ON sio.DataID = de.DataID "
						+ "WHERE sio.SubmissionID = " + submissionSet.getLong("SubmissionID");
				
				Statement statementIO = connect.createStatement();
				ResultSet subIOSet = statementIO.executeQuery(sqlIO);
				
				while (subIOSet.next()) {
					SubmissionIO subIO = new SubmissionIO();
					subIO.setDbId(subIOSet.getLong("dbId"));
					subIO.setType(subIOSet.getString("SubIOType"));
					
					DataElement data = new DataElement();
					data.setDbId(subIOSet.getLong("DataID"));
					data.setName(subIOSet.getString("Name"));
					data.setScanID(subIOSet.getString("ScanID"));
					data.setURI(subIOSet.getString("URI"));
					data.setSubject(subIOSet.getString("Subject"));
					data.setType(subIOSet.getString("DataType"));
					data.setFormat(subIOSet.getString("Format"));
					data.setDate(subIOSet.getDate("Date"));
					data.setSize(subIOSet.getInt("Size"));
					
					subIO.setDataElement(data);
					
					subIOs.add(subIO);
				}
				
				subIOSet.close();
				statementIO.close();
				
				sub.setSubmissionIOs(subIOs);
				
				submissions.add(sub);
			}
			
			submissionSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error in submission");
			System.out.println(e);
		}
		
		return submissions;
	}
    
	/**
	 * Counts the amount of projects in the database by the provided sql
	 * @param sql Sql by which we search for projects, should contain a join of Project and Processing tables 
	 * @return Count of projects as an integer
	 */
    public int countProjectsBySQL(String sql) {
        return ((BigInteger) session.createSQLQuery(sql).uniqueResult()).intValue();
    }
}