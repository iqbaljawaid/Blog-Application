$(document).ready(function() {
	$('#searchForm').submit( function(e) {
		e.preventDefault();
		var title = $('#title').val();
		$.ajax({
			url: '/search-index',
			type: 'GET',
			data: { title: title },
			success: function(data) {
				$("#card-data").remove();
				$("#filteredCardDiv").html(data);
			},
			error: function(error) {
				console.log(error);
			}
		});
	});
});