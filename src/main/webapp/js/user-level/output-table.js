function table(div, dataIn) {
	var table_html = "<table class='ligands-table'>" +
				"<tr class='header-row'>" +
					"<td>Ligand Name</td>" +
					"<td>Binding Energy</td>" +
				"</tr>";
	
	var sorted_data = sort_data(dataIn);
	
	$.each(sorted_data, function(key, arr) {
		table_html += "<tr>" +
					"<td><a href='http://zinc.docking.org/substance/" + arr[0] + "' target='_blank'>" + arr[0] + "</a></td>" +
					"<td>" + arr[1] + "</td>" +
				"</tr>";
	});
	
	table_html += "</table>";
	
	$(div).html(table_html);
	
	$('.view-table').on('click', function() {
		$(div).dialog({
			width: 600,
			height: 300,
			draggable: true,
			resizable: true,
			closeOnEscape: true,
			buttons: {
				Close: function() {
					$(this).dialog('close');
				}
			}
		});
		
		$(div).mouseenter(function(){
			$('body').addClass('modal-open');
		}).mouseleave(function(){
			$('body').removeClass('modal-open');
		});
	});
}

function sort_data(dataIn) {
	var sortable = [];
	
	for (var item in dataIn) {
		sortable.push([item, parseFloat(dataIn[item])]);
	}
	
	return sortable.sort(function(a, b) {
		return a[1] - b[1];
	});
}