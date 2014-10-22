package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.constants.VarConfig;
import nl.amc.biolab.exceptions.PersistenceException;
import nl.amc.biolab.persistencemanager.PersistenceManagerPlugin;
import dockingadmin.crappy.logger.Logger;

/**
 * Calls the processingmanager update status function and returns the new status
 *
 * @author Allard van Altena
 */
public class StatusUpdater extends VarConfig {
    public StatusUpdater() {}
    
    public String updateStatus(Long processId) {        
//        ProcessingManagerClient client = new ProcessingManagerClient(VarConfig.getProcessingWSDL());
        
        Logger.log("updating status...", Logger.debug);
        
        // Update the status through the processingmanager webservice
//        client.updateStatus(processId);
        
        Logger.log("done", Logger.debug);
        
        // Open a session
        PersistenceManagerPlugin db = new PersistenceManagerPlugin();
        String newStatus = "";
        
        try {
			db.init();

	        // Get the updated status from the database
	        newStatus = db.get.processing(processId).getSubmissions().iterator().next().getLastStatus().getValue();
		} catch (PersistenceException e) {
			Logger.log(e.getMessage(), Logger.exception);
		} finally {
			db.shutdown();
		}
        
        return newStatus;
    }
}
