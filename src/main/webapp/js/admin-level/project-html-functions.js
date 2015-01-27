function project_html(project_data) {
	var header_class = get_header_color(project_data.overall_status);
	
    return html =
        "<h3 class='header " + header_class + " " + header_div + project_data.project_id + project_data.processing_id + "'>\
        	<span class='header-cols first'>" + project_data.date_started + "</span>\
    		| <span class='header-cols second'>" + project_data.project_name + "</span>\
        	| <span class='header-cols third'>" + project_data.overall_status + "</span>\
			| <span class='header-cols fourth'>" + project_data.application + "</span>\
			| <span class='header-cols fifth'>" + project_data.user + "</span>\
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
	
	var project_html = 
		"<h2>" + project_data.project_name + "</h2>\
			<div class='top-buttons'>";
				if (project_data.overall_status.indexOf("On Hold") > -1) {
					project_html += "<input class='button resume-all' type='button' value='Resume All'/>";
				}
			project_html += 
			"</div>\
	        <table class='submissions'>";

	        $.each(project_data.submissions, function(key, submission) {
	            project_html += 
	            	"<tr class='submission'>\
	            		<td class='status'>\
	                		<span>" + submission.status + "</span>\
                		</td>\
                		<td class='submissionIO'>\
	                		<ul class='data-list'>";
	            
		        			$.each(submission.submissionIO, function(key, subIO) {
		        				var subIO_tooltip = "submission_id: " + submission.submission_id + "<br/>";
		        				
		        				$.each(subIO.data_element, function(key, val) {
		        					// Insert tooltip line with linebreak
	        						subIO_tooltip += key + ": " + val + "<br/>";
		        				});
		        				
		        				project_html += 
		        					"<li class='data-item'>\
		        						<a class='portlet-tooltip' data-content='" + subIO_tooltip + "'>" + subIO.data_element.name + "</a>\
		        					</li>";
		        			});
	            			
		        			project_html += 
	            			"</ul>\
		                </td>";
		                
	        			project_html += 
	                	"<td class='buttons'>\
	                		<input class='button details' type='button' data-submission_id='" + submission.submission_id + "' value='Details'/>";
		                
		                if (submission.status.indexOf("On Hold") > -1 || submission.status.indexOf("In Progress") > -1) {
		                	project_html += "<input class='button resume' type='button' data-submission_id='" + submission.submission_id + "' value='Resume'/>";
		                }
		                
                		if (submission.status.indexOf("Aborted") === -1 && submission.status.indexOf("Done") === -1) {
                			project_html += "<input class='button cancel' type='button' data-submission_id='" + submission.submission_id + "' value='Cancel'/>";
                		}
                		
	                	project_html += "</td>";
	                	
            	project_html += 
            		"</tr>";
	        });
	    
    project_html += "</table>";
    
    return project_html;
}