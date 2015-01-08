function project_html(project_data) {
	var header_class = get_header_color(project_data.overall_status);
	
    return html =
        "<h3 class='header " + header_class + " " + header_div + project_data.project_id + project_data.processing_id + "'>\
        	<span class='header-cols first'>" + project_data.date_started + "</span>\
        	| <span class='header-cols second'>" + project_data.project_name + "</span>\
    		| <span class='header-cols third'>" + project_data.overall_status + "</span>\
		</h3>\
        <div class='project_data_div " + data_div + project_data.project_id + project_data.processing_id + "' data-project=''>\
            <div class='accordion_content'>\
                <div class='project_content'>\
            		<div class='spinner'></div>\
        		</div>\
            </div>\
        </div>";
}

function build_inside_project_html(project_data) {
	log(project_data);
	var count = 0;
	
	var project_html = "<h2>Description</h2>\
    <span>" + project_data.description + "</span>\
    <div class='input_wrapper'>\
        <h2>Input</h2>";

        $.each(project_data.submissions[0].submissionIO, function(key, value) {
        	var data_element = value.data_element;
            var class_name = "input_details";
            
            // Skip output elements
            if (value.type === 'Output') {
            	return true;
            }
            
            if (count === 0) {
                class_name = "input_details first";
            }
            
            project_html += "<div class='" + class_name + "'>\
                <a href='" + data_element.uri.replace("http://localhost/webdav-curl", "") + "'>Name: " + data_element.name + "</a>";

            if (data_element.ligand_count !== null && data_element.ligand_count !== undefined) {
                project_html += "<span>Ligand count: " + data_element.ligand_count + "</span>";
            }

            project_html += "<span>Format: " + data_element.format + "</span>\
                </div>";
            
            count++;
        });
    
    project_html += "</div><div class='clear'></div>";

    // Job is in progress
    if (project_data.overall_status.indexOf("In Progress") > -1
			|| project_data.overall_status.indexOf("In Preparation") > -1
			|| project_data.overall_status.indexOf("On Hold") > -1
			|| project_data.overall_status.indexOf("Resuming")) {
        project_html +=
        "<div class='in_process_items_wrapper'>\
            <h2>Status</h2>\
            <span class='project_status_disp'>" + project_data.submissions[0].status + "</span>";
        	
    	// Remove from UI for now
    	//TODO check if this will be put back into the UI or this is a cronjob function
		//project_html += "<input class='button update' type='button' value='Update'/>";
        
        project_html += "</div>";
        
        if (project_data.output != undefined && project_data.overall_status.indexOf("Done") < 0) {
        	project_html += "<input class='button partial-result' type='button' value='Get Partial Results'/>";
        } else {
        	project_html += "No partial data available.";
        }
        
        project_html += "<span>Last update: " + project_data.last_update + "</span>"
    }
    
    // Job is done (but failed)
    if (project_data.overall_status.indexOf("Aborted") > -1 || project_data.overall_status.indexOf("Failed") > -1) {
		project_html += "<input class='button details' data-submission_id='" + project_data.submissions[0].submission_id + "' type='button' value='Details'/>";
	}

    // Job has output
    if (project_data.output != undefined) {
        project_html +=
        "<div class='outcomes_items_wrapper'>\
            <h2>Output</h2>\
            <div class='graph graph_" + project_data.project_id + "'></div>\
            \
            <span class='bold' style='display:none;'>Select % of data to download</span>\
            <div class='download_slider' style='display:none;'></div>\
            \
            <input class='download_input' name='download_input' type='text' style='display:none;' value='100'/>\
            <label class='download_input' style='display:none;' for='download_input'>%</label>\
            \
            <!--span>Selected number of compounds: <span class='compound_disp'>" + project_data.compound_count + "</span></span-->\
            \
            <input name='max_compounds' type='hidden' value='" + project_data.compound_count + "'/>";
            
        if (project_data.overall_status.indexOf("Done") > -1) {
        	project_html += "<input class='download button' type='button' value='Download Data'/>";

            // If no provenance exists for this project do not display it
            if (project_data.provenance_count !== undefined && project_data.provenance_count * 1 !== 0) {
                project_html +=
                "<span>This experiment is linked to " + project_data.provenance_count + " others</span>\
                <input class='button' type='button' value='View Provenance'/>";
            }
        }
        
        project_html += "</div>";
    }
    
    return project_html;
}

function buildLibraryHtml(ajax_data) {
    log("buildLibraryHtml called");
    
    log(ajax_data);
    
    var html = [];
    
    $.each(ajax_data, function(key, files) {
        var folder_name = key;
       
        html.push("<li class='lib_" + folder_name + " library'>\
                <input name='library_check' type='checkbox' value='1'/>\
                " + folder_name + "</li>");
    });
    
    return html;
}