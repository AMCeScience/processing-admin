package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.nsg.pm.ProcessingManagerClient;

/**
 * Calls the processing manager resume function and returns json object
 *
 * @author Allard van Altena
 */
public class SubmissionResume extends AjaxInterface {
    public SubmissionResume() {}
    
    @Override
    protected void _run() {
        _resumeSubmission();
    }
    
    private void _resumeSubmission() {
        ProcessingManagerClient client = new ProcessingManagerClient(config.getProcessingWSDL());
        
        // Get params
        Long submissionId = new Long(_getSearchTermEntry("submission_id"));
        
        // Call client
        client.resume(submissionId);

        // Get some info from database
        String newStatus = _getPersistence().getSubmission(submissionId).getStatus();
        
        log("New status after resume: " + newStatus);
        
        // Output
        _getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
        _getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
    }
}