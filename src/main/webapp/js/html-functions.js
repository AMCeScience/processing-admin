var header_div = "header_div_id_";
var data_div = "data_div_id_";

function update_panel(project_id, processing_id) {
	log("update panel called");
	
	function draw_panel(response) {
		var project = response.projects[0];
		var scope = '.' + data_div + project.project_id + project.processing_id;
		
		var html = build_inside_project_html(project);
		var header = $('.' + header_div + project.project_id + project.processing_id);
		
		$(header).removeClass("red");
		$(header).removeClass("orange");
		$(header).removeClass("yellow");
		$(header).removeClass("light-blue");
		$(header).removeClass("green");
		
		$(header).addClass(get_header_color(project.overall_status));
		$('.third', header).text(project.overall_status);
		
		$('.project_content', '.' + data_div + project.project_id + project.processing_id).html(html);

		if ($('.graph', '.' + data_div + project.project_id + project.processing_id).length > 0 && project.output !== undefined && project.output.graph !== undefined) {
            graph('.graph_' + project.project_id, project.output.graph);
        }
		
        // Init the project buttons
        init_project_buttons();
	}
	
	var ajax_data = {
		"project_id": project_id,
		"processing_id": processing_id
	}
	
	do_search(ajax_data, draw_panel);
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
	
	if (check_status(status, "aborted") || check_status(status, "failed")) {
		return "orange";
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

function buildNoProjectsHtml() {
	return '<span class="no_projects">No Projects</span>';
}