var header_div = "header_div_id_";
var data_div = "data_div_id_";

function admin_overview() {
	var search_scope = '.search_form';
	
    function ajax_continue(ajax_data) {
    	log("in ajaxContinue");
    	
        if (ajax_data.no_projects) {
            if ($('.accordion').hasClass('ui-accordion')) {
                $('.accordion').accordion('destroy');
            }
        
            $('.accordion').html('<span class="no_projects">No Projects</span>');
            
            return;
        }

        $('.spinner').remove();
        
        build_project_html('.accordion', ajax_data);

    	if (ajax_data.pages > 1) {
    		build_pagination_html('.accordion', 'pagination', ajax_data);
    		init_pagination(search_scope);
    	}

        // Init the accordion        
        if ($('.accordion').hasClass('ui-accordion')) {
            $('.accordion').accordion('refresh');
        } else {
            $('.accordion').accordion({
            	collapsible: true,
            	heightStyle: "content",
            	active: false,
            	activate: function(e, ui) {
            		var data = $(ui.newPanel[0]).data('project');
            		
            		log('loading');
            		log(data);
            		
            		if (data && !data.loaded) {
            			log('loading');
            			
	            		data.loaded = true;
	            		
	            		$(ui.newPanel[0]).data('project', data);
	            		
	            		var update_status = false;
	            		
	            		if (data.overall_status.indexOf("In Progress") > -1
    						|| data.overall_status.indexOf("In Preparation") > -1
    						|| data.overall_status.indexOf("On Hold") > -1) {
	            			update_status = true;
	            		}
	            		
	            		// Draw the panel html
	            		update_panel(data.project_id, data.processing_id, update_status);
            		}
            	}
            });
        }
    }
    
    // Bind search inputs to search function
    init_search(search_scope, ajax_continue);
    
    // Trigger search
    trigger_search(search_scope);
}

function update_panel(project_id, processing_id, update_status) {
	log("update panel called");
	
	function draw_panel(response) {
		$.each(response.projects, function(key, project) {
			var html = build_inside_project_html(project);
			var header = $('.' + header_div + project.project_id + project.processing_id);
			
			$(header).removeClass("red");
			$(header).removeClass("yellow");
			$(header).removeClass("light-blue");
			$(header).removeClass("green");
			
			$(header).addClass(get_header_color(project.overall_status));
			$('.second', header).text(project.overall_status);
			
			$('.project_content', '.' + data_div + project.project_id + project.processing_id).html(html);
		});

        // Init the project buttons
        init_project_buttons();
	}
	
	var ajax_data = {
		"project_id": project_id,
		"processing_id": processing_id
	}
	
	if (update_status !== undefined && update_status === true) {
		//TODO: Disabled for now until processing manager can handle status updates simultaneously
		//do_search_and_update(ajax_data, draw_panel);
		do_search(ajax_data, draw_panel);
	} else {
		do_search(ajax_data, draw_panel);
	}
}

function build_project_html(div, ajax_data) {
    log("buildProjectHtml called");

    log(ajax_data);

    $.each(ajax_data.projects, function(key, project) {
        $(div).append(project_html(project));
        
        $('.' + data_div + project.project_id + project.processing_id, div).data('project', project);
    });

    return;
}

function check_status(status, checkStatus) {
	if (status.toLowerCase().indexOf(checkStatus) > -1) {
		return true;
	}
	
	return false;
}

function get_header_color(status) {
	if (check_status(status, "on hold")) {
		return "red";
	}
	
	if (check_status(status, "aborted")) {
		return "red";
	}
	
	if (check_status(status, "in preparation")) {
		return "yellow";	
	}
	
	if (check_status(status, "in progress")) {
		return "light-blue";
	}
	
	if (check_status(status, "done")) {
		return "green";
	}
	
	return "red";
}

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
				if (project_data.overall_status.indexOf("In Progress") > -1
					|| project_data.overall_status.indexOf("In Preparation") > -1
					|| project_data.overall_status.indexOf("On Hold") > -1
					|| project_data.overall_status.indexOf("Resuming") > -1) {
					project_html += "<input class='button update' type='button' value='Refresh'/>";
				}
				
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
	                		<ul>";
	            
		        			$.each(submission.submissionIO, function(key, subIO) {
		        				project_html += 
		        					"<li>\
		        						<span>" + subIO.data_element.name + "</span>\
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