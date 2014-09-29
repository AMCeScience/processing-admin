package nl.amc.biolab.persistencemanager;

import java.math.BigInteger;
import java.util.List;

import nl.amc.biolab.datamodel.manager.PersistenceManager;
import nl.amc.biolab.datamodel.objects.Processing;
import nl.amc.biolab.datamodel.objects.Project;

/**
 * Plugin for the PersistenceManager, adds functions specifically for the admin portlet
 * 
 * @author Allard van Altena
 */
public class PersistenceManagerPlugin extends PersistenceManager {
	/**
	 * Searches the database with the provided sql, the sql should contain a join of Project and Processing for this to work
	 * @param sql Sql by which we search for the projects, should contain a join of Project and Processing tables
	 * @return One project in a list of arrays, array position 0 contains the Project, array position 1 contains the Processing
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getSingleProjectBySQL(String sql) {
		List<Object[]> projects = session.createSQLQuery(sql).addEntity("p", Project.class).addEntity("po", Processing.class).setMaxResults(1).list();
		
		return projects;
	}

	/**
	 * Searches the database with the provided sql, the sql should contain a join of Project and Processing for this to work
	 * @param sql Sql by which we search for the projects, should contain a join of Project and Processing tables
	 * @return One or more projects in a list of arrays, array position 0 contains the Project, array position 1 contains the Processing
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getProjectsBySQL(String sql) {
		List<Object[]> projects = session.createSQLQuery(sql).addEntity("p", Project.class).addEntity("po", Processing.class).list();
		
		return projects;
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