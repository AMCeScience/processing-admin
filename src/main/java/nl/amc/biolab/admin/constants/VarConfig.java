package nl.amc.biolab.admin.constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import crappy.logger.Logger;

/**
 * Configuration file reader class, handles reading the configuration file and extracting specific configuration items from it
 *
 * @author Allard van Altena
 */
public class VarConfig extends Logger {
	private final String FILE_PATH = "guse/apache-tomcat-6.0.36/webapps/admin_page_files/config.json";
	private JSONObject OBJ;
    
    public VarConfig config;
    
    /**
     * Constructor which reads the configuration file, also exposes the config variable to all extending classes
     */
    public VarConfig() {
    	JSONParser parser = new JSONParser();
    	
    	try {
    		_setJSON((JSONObject) parser.parse(new FileReader(FILE_PATH)));
    	} catch(FileNotFoundException e) {
    		log(e.toString());
    	} catch (IOException e) {
    		log(e.toString());
		} catch (ParseException e) {
			log(e.toString());
		}
    	
    	config = this;
    }
    
    /**
     * Sets the JSON object class variable
     * @param obj JSON object to set
     */
    private void _setJSON(JSONObject obj) {
    	OBJ = obj;
    }
    
    /**
     * Gets the JSON object class variable
     * @return JSON object
     */
    private JSONObject _getJSON() {
    	return OBJ;
    }
    
    /**
     * Get configuration item with certain name in String.
     * @param name Name of configuration item we are looking for
     * @return String of configuration item belonging to the input 'name'
     * @throws Exception Throws exception when name does not exist in file
     */
    public String getItem(String name) throws Exception {
    	if (_getJSON().containsKey(name)) {
    		return _getItem(name);
    	} else {
    		throw new Exception("key does not exist in configuration file.");
    	}
    }
    
    /**
     * Use this function to add extra functions to the VarConfig class.
     * @param name Name of configuration item we are looking for
     * @return String of configuration item belonging to the input 'name'
     */
    private String _getItem(String name) {
    	return _getJSON().get(name).toString();
    }
    
    /**
     * Get boolean whether if the site is in development mode
     * @return Boolean whether if the site is in development mode
     */
    public boolean getIsDev() {
    	if (_getItem("is_dev").equals("true")) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Get items per page configuration item
     * @return Integer of items per page
     */
    public int getItemsPerPage() {
    	return Integer.parseInt(_getItem("items_per_page"));
    }
    
    /**
     * Get formatted database connection url
     * @return Formatted database connection url
     */
    public String getDbConnectionUrl() {
    	return "jdbc:mysql://" + _getItem("db_url") + ":" + _getItem("db_port") + "/" + _getItem("db_scheme") + "?user=" + _getItem("db_user") + "&password=" + _getItem("db_password");
    }
    
    /**
     * Get processing manager WSDL url
     * @return Processing manager WSDL url
     */
    public String getProcessingWSDL() {
        return _getItem("processing_wsdl");
    }
}
