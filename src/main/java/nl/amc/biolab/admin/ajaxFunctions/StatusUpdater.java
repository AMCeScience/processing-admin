package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.constants.VarConfig;
import nl.amc.biolab.exceptions.PersistenceException;
import nl.amc.biolab.nsg.pm.ProcessingManagerClient;
import nl.amc.biolab.persistencemanager.PersistenceManagerPlugin;

/**
 * Calls the processingmanager update status function and returns the new status
 *
 * @author Allard van Altena
 */
public class StatusUpdater extends VarConfig {
    public StatusUpdater() {}
    
    public String updateStatus(Long processId) {        
        ProcessingManagerClient client = new ProcessingManagerClient(config.getProcessingWSDL());
        
        log("updating status...");
        
        // Update the status through the processingmanager webservice
        client.updateStatus(processId);
        
        log("done");
        
        // Open a session
        PersistenceManagerPlugin db = new PersistenceManagerPlugin();
        String newStatus = "";
        
        try {
			db.init();

	        // Get the updated status from the database
	        newStatus = db.get.processing(processId).getSubmissions().iterator().next().getLastStatus().getValue();
		} catch (PersistenceException e) {
			log(e.getMessage());
		} finally {
			db.shutdown();
		}
        
        return newStatus;
    }
}
