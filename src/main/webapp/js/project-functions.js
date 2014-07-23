function init_project_buttons() {
	$('.details').click(function() {
		function ajax_continue(response) {
        	$('.dialog-message').text(response.message + " " + response.description);
        	
        	$('.dialog-div').dialog({
        		modal: true,
        		buttons: {
        			Ok: function() {
        				$(this).dialog('close');
        			}
        		}
        	});
        }
		
		var project_data = $(this).closest('.project_data_div').data('project');
		
		var ajax_data = {
			project_id: project_data.project_id,
			processing_id: project_data.processing_id,
			submission_id: $(this).data('submission_id')
		}
		
		get_details(ajax_data, ajax_continue);
	})
	
	// Update/Refresh button
    $('.update').click(function() {
    	function ajax_continue(response) {
        	update_panel(response.project_id, response.processing_id, true);
        	
            return;
        }
        
        var project_data = $(this).closest('.project_data_div').data('project');
        var submission_id = $(this).data('submission_id');

        $('.project_content', '.' + data_div + project_data.project_id + project_data.processing_id).html('<div class="spinner"></div>');
        
        var ajax_data = {
    		project_id: project_data.project_id,
    		processing_id: project_data.processing_id,
    		submission_id: submission_id
        };
        
        update_status(ajax_data, ajax_continue);
    });
    
    // Cancel button
    $('.cancel').click(function() {
        function ajax_continue(response) {
        	update_panel(response.project_id, response.processing_id, true);
        	
            return;
        }
        
        var project_data = $(this).closest('.project_data_div').data('project');
        var submission_id = $(this).data('submission_id');
        
        $('.dialog-message').html('<p>Provide a cancellation reason</p><input type="text" class="cancel-reason"/>');
        
        $('.dialog-div').dialog({
    		modal: true,
    		width: 500,
    		buttons: {
    			'Submit': function() {
    				var cancel_reason = $('.cancel-reason').val()
    				
    				if (cancel_reason.length < 1) {
    					return;
    				}
    				
    		        $('.project_content', '.' + data_div + project_data.project_id + project_data.processing_id).html('<div class="spinner"></div>');
    		        
    		        var ajax_data = {
    		    		project_id: project_data.project_id,
    		    		processing_id: project_data.processing_id,
    		    		submission_id: submission_id,
    		    		message: cancel_reason
    		        };
    		        
    		        cancel_submission(ajax_data, ajax_continue);
    		        
    		        $(this).dialog('close');
    			},
    			Cancel: function() {
    				$(this).dialog('close');
    			}
    		}
        });
    });
    
    // Resume button
    $('.resume').click(function() {
        function ajax_continue(response) {
        	update_panel(response.project_id, response.processing_id, true);
        	
            return;
        }
        
        var project_data = $(this).closest('.project_data_div').data('project');
        var submission_id = $(this).data('submission_id');

        $('.project_content', '.' + data_div + project_data.project_id + project_data.processing_id).html('<div class="spinner"></div>');
        
        var ajax_data = {
    		project_id: project_data.project_id,
    		processing_id: project_data.processing_id,
    		submission_id: submission_id
        };
        
        resume_submission(ajax_data, ajax_continue);
    });
    
    // Resume All button
    $('.resume-all').click(function() {
        function ajax_continue(response) {
        	update_panel(response.project_id, response.processing_id, true);
        	
            return;
        }
        
        var project_data = $(this).closest('.project_data_div').data('project');

        $('.project_content', '.' + data_div + project_data.project_id + project_data.processing_id).html('<div class="spinner"></div>');
        
        var ajax_data = {
    		project_id: project_data.project_id,
            processing_id: project_data.processing_id
        };
        
        resume_all_submissions(ajax_data, ajax_continue);
    });
}