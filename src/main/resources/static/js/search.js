$(document).ready(function() {
	$('#searchForm').submit( function(e) {
		e.preventDefault();
		var title = $('#title').val();
		$.ajax({
			url: '/search-title',
			type: 'GET',
			data: { title: title },
			success: function(data) {
				$("#table-data").remove();
				$("#filteredTblDiv").html(data);
			},
			error: function(error) {
				console.log(error);
			}
		});
	});
});