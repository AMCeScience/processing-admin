package nl.amc.biolab.admin.constants;

import nl.amc.biolab.config.exceptions.ReaderException;
import nl.amc.biolab.config.manager.ConfigurationManager;
import dockingadmin.crappy.logger.Logger;

/**
 * Configuration file reader class, handles reading the configuration file and extracting specific configuration items from it
 *
 * @author Allard van Altena
 */
public class VarConfig extends Logger {
	private final String file_path = "guse/apache-tomcat-6.0.36/webapps/config.json";
	private final String app_name = "admin-page";
	private ConfigurationManager config_file;
    
    public VarConfig config;
    
    /**
     * Constructor which reads the configuration file, also exposes the config variable to all extending classes
     */
    public VarConfig() {
    	try {
			this.config_file = new ConfigurationManager(this.file_path);
		} catch (ReaderException e) {
			log(e.getMessage());
		}
    	
    	config = this;
    }
    
    /**
     * Get configuration item with certain name in String.
     * @param name Name of configuration item we are looking for
     * @return String of configuration item belonging to the input 'name'
     * @throws Exception Throws exception when name does not exist in file
     */
    public String getItem(String name) {
    	try {
    		return this.config_file.read.getStringItem(this.app_name, name);
    	} catch(ReaderException e) {
    		log(e.getMessage());
    	}
    	
    	return null;
    }
    
    /**
     * Get boolean whether if the site is in development mode
     * @return Boolean whether if the site is in development mode
     */
    public boolean getIsDev() {
    	try {
			return this.config_file.read.getBooleanItem("is_dev");
		} catch (ReaderException e) {
			log(e.getMessage());
		}
    	
    	return false;
    }
    
    /**
     * Get items per page configuration item
     * @return Integer of items per page
     */
    public int getItemsPerPage() {
    	try {
    		return this.config_file.read.getIntegerItem(this.app_name, "items_per_page");
    	} catch(ReaderException e) {
    		log(e.getMessage());
    	}
    	
    	return 0;
    }
    
    /**
     * Get processing manager WSDL url
     * @return Processing manager WSDL url
     */
    public String getProcessingWSDL() {
        return getItem("processing_wsdl");
    }
}
