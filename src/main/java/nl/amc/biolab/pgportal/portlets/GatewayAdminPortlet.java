package nl.amc.biolab.pgportal.portlets;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import nl.amc.biolab.admin.ajaxHandlers.AjaxDispatcher;
import crappy.logger.Logger;

/**
 * @author Allard van Altena
 */
public class GatewayAdminPortlet extends GenericPortlet {
    private final String PROJECT_DISPLAY_PAGE = "overview";
    private Logger LOG = new Logger();

    public GatewayAdminPortlet() {}

    /**
     * Handles view changes, called by the handleGoToPage method and when loading the page
     * @param request Handled by porlet http://docs.liferay.com/portlet-api/2.0/javadocs/javax/portlet/RenderRequest.html
     * @param response Handled by portlet http://docs.liferay.com/portlet-api/2.0/javadocs/javax/portlet/RenderResponse.html
     */
    @Override
    public void doView(RenderRequest request, RenderResponse response) throws PortletException {
        try {
            String nextJSP = (String) request.getParameter("nextJSP");
            
            // If no page is set load the new job page
            if (nextJSP == null) {
        		nextJSP = PROJECT_DISPLAY_PAGE;
            }

            // Load new JSP page
            PortletRequestDispatcher dispatcher;
            dispatcher = getPortletContext().getRequestDispatcher("/jsp/".concat(nextJSP).concat(".jsp"));
            dispatcher.include(request, response);
        } catch (IOException e) {
            LOG.log(e);
        }
    }
    
    /**
     * Handles ajax requests, resourceURLs from the .jsp end up here
     * @param request Handled by portlet http://docs.liferay.com/portlet-api/2.0/javadocs/javax/portlet/ActionRequest.html
     * @param response Handled by portlet http://docs.liferay.com/portlet-api/2.0/javadocs/javax/portlet/ActionResponse.html
     */
    @Override
    public void serveResource(ResourceRequest ajaxParameters, ResourceResponse response) throws PortletException {
    	LOG.log("serveResource");
    	
        AjaxDispatcher dispatch = new AjaxDispatcher();
        
        // Init ajax
        dispatch.dispatch(ajaxParameters, response);
        
        // Get and write JSON response
        dispatch.response();
    }
}
