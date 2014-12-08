package nl.amc.biolab.admin.constants;

import nl.amc.biolab.config.exceptions.ReaderException;
import nl.amc.biolab.config.manager.ConfigurationManager;
import dockingadmin.crappy.logger.Logger;

/**
 * Configuration file reader class, handles reading the configuration file and extracting specific configuration items from it
 *
 * @author Allard van Altena
 */
public class VarConfig {
	private static final String app_name = "admin-page";
    
    /**
     * Get configuration item with certain name in String.
     * @param name Name of configuration item we are looking for
     * @return String of configuration item belonging to the input 'name'
     * @throws Exception Throws exception when name does not exist in file
     */
    public static String getItem(String name) {
    	try {
    		return ConfigurationManager.read.getStringItem(VarConfig.app_name, name);
    	} catch(ReaderException e) {
    		Logger.log(e.getMessage(), Logger.exception);
    	}
    	
    	return null;
    }
    
    /**
     * Get boolean whether if the site is in development mode
     * @return Boolean whether if the site is in development mode
     */
    public static boolean getIsDev() {
    	try {
			return ConfigurationManager.read.getBooleanItem("is_dev");
		} catch (ReaderException e) {
			Logger.log(e.getMessage(), Logger.exception);
		}
    	
    	return false;
    }
    
    /**
     * Get items per page configuration item
     * @return Integer of items per page
     */
    public static int getItemsPerPage() {
    	try {
    		return ConfigurationManager.read.getIntegerItem(VarConfig.app_name, "items_per_page");
    	} catch(ReaderException e) {
    		Logger.log(e.getMessage(), Logger.exception);
    	}
    	
    	return 0;
    }
    
    public static String getProcessingResource() {
        return getItem("processing_url");
    }
}
