package nl.amc.biolab.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nl.amc.biolab.config.exceptions.ReaderException;
import nl.amc.biolab.config.manager.ConfigurationManager;
import nl.amc.biolab.datamodel.manager.HibernateUtil;
import dockingadmin.crappy.logger.Logger;

public class HibernateListener implements ServletContextListener {
	private final String file_path = System.getProperty("catalina.base") + "/conf/config.json";

    public void contextInitialized(ServletContextEvent event) {
        try {
        	System.out.println("################################### HERE");
            // Initialise configuration manager for this portlet
			new ConfigurationManager(file_path);
			
			System.out.println("################################### HERE 2");
	        // Initialise logger for this portlet
	        new Logger();
	        
	        System.out.println("################################### HERE 3");
	    	// Create database sessionfactory
	        HibernateUtil.getSessionFactory();
		} catch (ReaderException e) {
			e.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent event) {
    	HibernateUtil.getSessionFactory().close();
    }
}