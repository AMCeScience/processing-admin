function build_pagination_html(placement, el, pagination_data) {
	var pages = pagination_data.pages * 1;
	var page = pagination_data.page * 1;
	var html = "<div class='" + el + "'>";
	
	for (var count = 1; count <= pages; count++) {
		var selected = "";
		
		if (count === page) {
			selected = "selected";
		}
		
		html += "<a href='#' class='page-click " + selected + "' data-page_nr='" + count + "'>" + count + "</a>";
	}
	
	html += "</div>";
	
	$(html).insertAfter(placement);
}

function init_pagination(scope) {
	$('.page-click').click(function(e) {
		e.preventDefault();
		
		var clicked_page = $(this).data("page_nr");
		
		log("page_change to: " + clicked_page);
		
		$(scope).data("page", clicked_page).trigger('changeData');
		
		return false;
	});
}