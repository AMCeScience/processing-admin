function do_search(dataArr, success_func) {
    log('doSearch function running');
    
    ajax_call(dataArr, success_func, $('body').data('ajax-urls').searchUrl, "POST", false);
}

function update_status(dataArr, success_func) {
    log('updateStatus function running');

    ajax_call(dataArr, success_func, $('body').data('ajax-urls').updateStatusUrl, "POST", false);
}

function cancel_submission(dataArr, success_func) {
    log('cancelSubmission function running');

    ajax_call(dataArr, success_func, $('body').data('ajax-urls').cancelUrl, "POST", false);
}

function resume_submission(dataArr, success_func) {
    log('resumeSubmission function running');

    ajax_call(dataArr, success_func, $('body').data('ajax-urls').resumeUrl, "POST", false);
}

function resume_all_submissions(dataArr, success_func) {
    log('resumeAllSubmissions function running');

    ajax_call(dataArr, success_func, $('body').data('ajax-urls').resumeAllUrl, "POST", false);
}

function get_details(dataArr, success_func) {
    log('getDetails function running');

    ajax_call(dataArr, success_func, $('body').data('ajax-urls').getDetailsUrl, "GET", false);
}

function ajax_call(dataIn, success_func, urlIn, typeIn, cacheIn) {
    $.ajax({
        type: typeIn,
        dataType: "json",
        url: urlIn,
        cache: cacheIn,
        data: dataIn,
        success: function(data) {
            log("success");
            log("success", arguments);
            log("data", typeof data, data); // Verify the response

            if (data.error !== undefined && data.error !== null && data.error === true) {
            	log("error occured printing data: ");
            	log(data);
            } else {
            	success_func(data);
            }
        }
    });
}