package nl.amc.biolab.admin.ajaxFunctions;

import java.io.IOException;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.admin.constants.VarConfig;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import dockingadmin.crappy.logger.Logger;

/**
 * Calls the processingmanager update status function and returns the new status
 *
 * @author Allard van Altena
 */
public class StatusUpdater extends AjaxInterface {
	public StatusUpdater() {
	}

	@Override
	protected void _run() {
		_updateStatus();
	}

	private void _updateStatus() {
		JSONArray output = new JSONArray();
		
		Logger.log("updating status...", Logger.debug);
		
		HttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(VarConfig.getProcessingResource() + "/" + _getSearchTermEntry("processing_id"));
		
		try {
			HttpResponse response = client.execute(httpGet);
			
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
		
		Logger.log(output, Logger.debug);

		Logger.log("done", Logger.debug);
		
        // Output
		_getJSONObj().add("project_id", _getSearchTermEntry("project_id"));
		_getJSONObj().add("processing_id", _getSearchTermEntry("processing_id"));
		_getJSONObj().add("response", output);
	}
}
