package dockingadmin.crappy.logger;

import java.util.Date;

/**
 * Simple logger function
 * 
 * @author Allard van Altena
 */
public class Logger {
	private String PORTLET = "Admin Portlet";
    public Logger log;
    
    /**
     * Exposes the log variable to all the classes
     */
    public Logger() {
        log = this;
    }
    
    /**
     * Outputs a formatted message with the portlet name and the current datetime
     * @param message Message we want to output into the logs
     */    
    public void log(Object message) {
        Date date = new Date();
        
        System.out.println(PORTLET + ", " + date.toString() + " MSG: " + message);
    }
}
