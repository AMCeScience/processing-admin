package nl.amc.biolab.admin.output.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import nl.amc.biolab.admin.output.tools.ProjectOutput;
import nl.amc.biolab.datamodel.objects.DataElement;
import nl.amc.biolab.datamodel.objects.Processing;
import nl.amc.biolab.datamodel.objects.Project;
import nl.amc.biolab.datamodel.objects.Submission;
import nl.amc.biolab.datamodel.objects.SubmissionIO;
import nl.amc.biolab.datamodel.objects.Value;

/**
 * Takes the nsgdm objects and creates object with more complete data
 *
 * @author Allard
 */
public class LocalProject {
    // Project items
    private Long ID = null;
    private String NAME = "";
    private String DESCRIPTION = "";
    private String OWNER = null;
    private String APPLICATION = "";
    private String OVERALL_STATUS = "";
    private HashMap<String, Object> PROJECT_OUTPUT = null;
    private String UPDATE_DATE = "";
    
    // Processing items
    private Long PROCESSING_ID = null;
    private Date DATE = null;
    
    // Submission items
    private ArrayList<HashMap<String, Object>> SUBMISSIONS = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> SUBMISSIONIOS = new ArrayList<HashMap<String, Object>>();
    
    private boolean FULLREPORT = false;
    
    /**
     * Constructor takes Project and Processing as input and initiates the data formatting
     * @param project Project class from nsgdm
     * @param processing Processing class from nsgdm
     * @param full_report Whether or not if we want to get the Project and Processing classes with additionally the Submission, SubmissionIO, and DataElement class
     */
    public LocalProject(Project project, Processing processing, boolean full_report) {
    	_setFullReport(full_report);
    	
    	_initProject(project, processing);
    }
    
    /**
     * Sets the class variables for Project, Processing, Submission, SubmissionIO, and DataElement 
     * @param project Project class from nsgdm
     * @param processing Processing class from nsgdm
     */
    private void _initProject(Project project, Processing processing) {
        _setID(project.getDbId());
        _setName(project.getName());
        _setDescription(project.getDescription());
        
        _setProcessingID(processing.getDbId());
        _setProjectStatus(processing.getStatus());
        _setDateStarted(processing.getDate());
        _setApplication(processing.getApplication().getName());
        _setOwner(processing.getUser().getFirstName() + " " + processing.getUser().getLastName());
        
        if (_getFullReport()) {
        	List<Date> dates = new ArrayList<Date>();
        	
	        // Get submissions for this project + processing
	        for(Submission sub : processing.getSubmissions()) {
	        	dates.add(sub.getLastStatus().getTimestamp());
	        	
	        	// Clear IO variable
	        	_resetSubmissionIOs();
	        	
	        	// Get submissionIOs
	        	for (SubmissionIO subIO : sub.getSubmissionIOs()) {
	        		// Set IOs
	        		_setSubmissionIO(subIO);
	            }
	        	
	        	// Set submission
	        	_setSubmission(sub);
	        }
	        
	        if (dates.size() > 0) {
	        	_setUpdateDate(Collections.max(dates));
	        }
	        
	        ProjectOutput output = new ProjectOutput();
        	
	        // Check if output exists
        	if (output.outputExists(project.getValueByName("folder_name"))) {
        		
        		// If output exists init the output map
        		if (output.initOutput(project.getValueByName("folder_name"))) {
        			_setProjectOutput(output.getMap());
        		}
        	}
        }
    }
         
    /**
     * Outputs the class variables as LinkedHashMap formatted for use at the client side
     * @return LinkedHashMap of project data
     */
    public LinkedHashMap<String, Object> getProjectMap() {
        LinkedHashMap<String, Object> project = new LinkedHashMap<String, Object>();
        
        project.put("project_id", _getID());
        project.put("project_name", _getName());
        project.put("description", _getDescription());
        project.put("user", _getOwner());
        project.put("application", _getApplication());
        
        project.put("processing_id", _getProcessingID());
        project.put("overall_status", _getProjectStatus());
        project.put("date_started", _getDateStarted().toString());
        
        project.put("last_update", _getUpdateDate());
        
        if (_getFullReport()) {
		    project.put("submissions", _getSubmissions());
		    
		    if (_getProjectOutput() != null) {
		    	project.put("output", _getProjectOutput());
		    }
        }
        
        return project;
    }
    
    /**
     * Get the data map for DataElement objects
     * @param dataEl DataElement from nsgdm
     * @return HashMap of DataElement data
     */
    private HashMap<String, Object> _getDataElementMap(DataElement dataEl) {
    	HashMap<String, Object> dataMap = new HashMap<String, Object>();
    	
    	// Get all stored keys and put them in the output
    	if (dataEl.getAllValues() != null) {
    		Iterator<Value> iter = dataEl.getAllValues().iterator();
    		
    		while(iter.hasNext()) {
    			Value this_val = iter.next();
    			
    			dataMap.put(this_val.getKey().getName(), this_val.getValue());
    		}
    	}

    	dataMap.put("database id", dataEl.getDbId());
    	dataMap.put("name", dataEl.getName());
    	dataMap.put("uri", dataEl.getURI());
    	dataMap.put("type", dataEl.getType());
    	dataMap.put("format", dataEl.getFormat());
    	dataMap.put("date", (dataEl.getDate() != null ? dataEl.getDate().toString() : ""));
    	dataMap.put("size", dataEl.getSize());
    	
    	return dataMap;
    }
    
    /* ###################################################################### */
    /* # 				CLASS VARIABLE GETTERS AND SETTERS 					# */
    /* ###################################################################### */
    
    private Long _getID() {
        return ID;
    }
    
    private void _setID(Long id) {
        this.ID = id;
    }

    private Long _getProcessingID() {
        return PROCESSING_ID;
    }
    
    private void _setProcessingID(Long id) {
        this.PROCESSING_ID = id;
    }
    
    private String _getApplication() {
    	return APPLICATION;
    }
    
    private void _setApplication(String app) {
		APPLICATION += app.toString();
    }
    
    private String _getName() {
        return NAME;
    }

    private void _setName(String name) {
        this.NAME = name;
    }

    private Date _getDateStarted() {
        return DATE;
    }

    private void _setDateStarted(Date date) {
        this.DATE = date;
    }

    private String _getDescription() {
        return DESCRIPTION;
    }

    private void _setDescription(String description) {
        this.DESCRIPTION = description;
    }
    
    private String _getOwner() {
        return OWNER;
    }

    private void _setOwner(String owner) {
        this.OWNER = owner;
    }
    
    private String _getProjectStatus() {
    	return OVERALL_STATUS;
    }
    
    private void _setProjectStatus(String status) {
    	this.OVERALL_STATUS += " " + status;
    }
    
    private ArrayList<HashMap<String, Object>> _getSubmissions() {
    	return SUBMISSIONS;
    }
    
    private void _setSubmissionIO(SubmissionIO subIO) {
    	HashMap<String, Object> IOMap = new HashMap<String, Object>();
    	
    	IOMap.put("type", subIO.getType());
    	IOMap.put("data_element", _getDataElementMap(subIO.getDataElement()));
    	
    	SUBMISSIONIOS.add(IOMap);
    }
    
    private void _resetSubmissionIOs() {
    	SUBMISSIONIOS = new ArrayList<HashMap<String, Object>>();
    }
    
    private ArrayList<HashMap<String, Object>> _getSubmissionIOs() {
    	return SUBMISSIONIOS;
    }
    
    private void _setSubmission(Submission submission) {
    	HashMap<String, Object> local = new HashMap<String, Object>();
    	
    	local.put("submission_id", submission.getDbId());
    	local.put("status", submission.getLastStatus().getValue());
    	local.put("submission", submission.getName());
    	local.put("submissionIO", _getSubmissionIOs());
    	
    	SUBMISSIONS.add(local);
    }
    
    private void _setProjectOutput(HashMap<String, Object> output) {
    	PROJECT_OUTPUT = output;
    }
    
    private HashMap<String, Object> _getProjectOutput() {
    	return PROJECT_OUTPUT;
    }
    
    private void _setUpdateDate(Date date) {
    	UPDATE_DATE = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
    }
    
    private String _getUpdateDate() {
    	return UPDATE_DATE;
    }
    
    private void _setFullReport(boolean report) {
    	FULLREPORT = report;
    }
    
    private boolean _getFullReport() {
    	return FULLREPORT;
    }
}
