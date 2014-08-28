package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.nsg.pm.ProcessingManagerClient;

/**
 * Calls the processing manager abort function and returns ajax object
 *
 * @author Allard van Altena
 */
public class SubmissionCancel extends AjaxInterface {
    public SubmissionCancel() {}
    
    @Override
    protected void _run() {
    	_cancelSubmission();
    }
    
    private void _cancelSubmission() {        
        ProcessingManagerClient client = new ProcessingManagerClient(config.getProcessingWSDL());
        
        // Get params
        Long submissionId = new Long(_getSearchTermEntry("submission_id"));
        String message = _getSearchTermEntry("message");

        log("cancelling submission...");
        
        // Call client
        client.abort(submissionId, message);
        
        log("done");
        
        // Get some info from database
        String newStatus = _getPersistence().get.submission(submissionId).getStatus();
        
        log("New status after cancel: " + newStatus);
        
        // Output
        _getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
        _getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
    }
}
