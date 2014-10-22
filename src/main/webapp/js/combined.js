var search_scope = '.search_form';

function project_display() {
    // Bind search inputs to search function
    init_search(search_scope, after_search);
    
    // Trigger search
    trigger_search(search_scope);
}

function after_search(ajax_data) {
	log("in ajaxContinue");
	
    if (ajax_data.no_projects) {
        if ($('.accordion').hasClass('ui-accordion')) {
            $('.accordion').accordion('destroy');
        }
    
        $('.accordion').html(buildNoProjectsHtml());
        
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
        		
        		log(data);
        		
        		if (data && !data.loaded) {
        			log('loading');
        			
            		data.loaded = true;
            		
            		$(ui.newPanel[0]).data('project', data);
            		
            		// Draw the panel html
            		update_panel(data.project_id, data.processing_id);
        		}
        	}
        });
    }
}