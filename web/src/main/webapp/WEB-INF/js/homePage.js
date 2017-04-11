function postHandler(uri) {
	$.ajax({
		cache : true,
		type : 'POST',
		url : uri,
		async : true,
		error : function(data) {
			alert('Fail.');
		},
		success : function(data) {
			alert('Success.');
		}
	});
}
