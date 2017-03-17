function sendEmail() {
	$.ajax({
		cache : true,
		type : 'POST',
		url : 'utils/sendEmail',
		data : $('#sendEmail').serialize(),
		async : false,
		error : function(data) {
			alert('Send email fail.'+data);
		},
		success : function(data) {
			alert('Send email success.'+data);
		}
	});
}
