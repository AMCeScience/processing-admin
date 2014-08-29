package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.nsg.pm.ProcessingManagerClient;

/**
 * Calls the processing manager resumeAll function and returns ajax object
 *
 * @author Allard van Altena
 */
public class SubmissionResumeAll extends AjaxInterface {
    public SubmissionResumeAll() {}
    
    @Override
    protected void _run() {
        _resumeAllSubmissions();
    }
    
    private void _resumeAllSubmissions() {
        ProcessingManagerClient client = new ProcessingManagerClient(config.getProcessingWSDL());
        
        // Get params
        Long processingId = new Long(_getSearchTermEntry("processing_id"));
        
        // Call client
        client.resumeAll(processingId);
        
        // Get some info from database
        String newStatus = _getPersistence().get.processing(processingId).getStatus();
        
        // Output
        _getJSONObj().add("new_status", newStatus);
        _getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
        _getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
    }
}
