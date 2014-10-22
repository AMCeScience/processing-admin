package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import dockingadmin.crappy.logger.Logger;

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
//        ProcessingManagerClient client = new ProcessingManagerClient(VarConfig.getProcessingWSDL());
        
        // Get params
        Long submissionId = new Long(_getSearchTermEntry("submission_id"));
//        String message = _getSearchTermEntry("message");

        Logger.log("cancelling submission...", Logger.debug);
        
        // Call client
//        client.abort(submissionId, message);
        
        Logger.log("done", Logger.debug);
        
        // Get some info from database
        String newStatus = _getPersistence().get.submission(submissionId).getLastStatus().getValue();
        
        Logger.log("New status after cancel: " + newStatus, Logger.debug);
        
        // Output
        _getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
        _getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
    }
}
