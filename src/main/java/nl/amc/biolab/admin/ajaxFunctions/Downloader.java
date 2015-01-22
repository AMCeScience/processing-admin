package nl.amc.biolab.admin.ajaxFunctions;

import nl.amc.biolab.admin.ajaxHandlers.AjaxInterface;
import nl.amc.biolab.admin.constants.VarConfig;
import nl.amc.biolab.datamodel.objects.Project;

public class Downloader extends AjaxInterface {
	public Downloader() {
		
	}
	
	@Override
	protected void _run() {
		_getResult();
	}

	private void _getResult() {
		Project project = _getPersistence().get.project(Long.parseLong(_getSearchTermEntry("project_id")));
		
		// Output the new status to the ajax request
		_getJSONObj().add("redirect", VarConfig.getOutputWebdavPath(project.getValueByName("folder_name")));
	}
}