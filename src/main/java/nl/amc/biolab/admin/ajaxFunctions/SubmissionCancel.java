package nl.amc.biolab.admin.ajaxFunctions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.admin.constants.VarConfig;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import dockingadmin.crappy.logger.Logger;

/**
 * Calls the processing manager abort function and returns ajax object
 *
 * @author Allard van Altena
 */
public class SubmissionCancel extends AjaxInterface {
    public SubmissionCancel() {}
    
    @Override
    protected void _run() {
    	_cancelSubmission();
    }
    
    private void _cancelSubmission() {
    	JSONArray output = new JSONArray();

        Logger.log("cancelling submission...", Logger.debug);
		
        HttpClient client = HttpClients.createDefault();
        
		try {
			HttpDelete httpDelete = new HttpDelete(VarConfig.getProcessingResource() + "/submissions/" + _getSearchTermEntry("submission_id") + "?reason=" + URLEncoder.encode(_getSearchTermEntry("message"), "UTF-8"));
			
			try {
				HttpResponse response = client.execute(httpDelete);
				
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						output = (JSONArray) new JSONParser().parse(EntityUtils.toString(response.getEntity()));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (org.json.simple.parser.ParseException e) {
						e.printStackTrace();
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
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
