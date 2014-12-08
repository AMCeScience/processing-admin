package nl.amc.biolab.admin.ajaxFunctions;

import java.io.IOException;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.admin.constants.VarConfig;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import dockingadmin.crappy.logger.Logger;

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
    	String output = "";
		
		Logger.log("resuming submission...", Logger.debug);
		
		HttpClient client = HttpClients.createDefault();
		HttpPatch httpPatch = new HttpPatch(VarConfig.getProcessingResource() + "/" + _getSearchTermEntry("processing_id"));
		
		try {
			HttpResponse response = client.execute(httpPatch);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				output = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Logger.log(output, Logger.debug);

		Logger.log("done", Logger.debug);
		
        // Output
		_getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
		_getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
		_getJSONObj().add("response", output);
    }
}
