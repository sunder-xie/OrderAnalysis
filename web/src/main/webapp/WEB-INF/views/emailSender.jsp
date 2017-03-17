<html>
<head>
<meta charset="UTF-8"/>
<script type="text/javascript" src="js/email.js" ></script>
<script type="text/javascript" src="js/jquery-3.2.0.min.js" ></script>
<title>Send Email</title>
</head>
<body>
	<form id="sendEmail" action="utils/sendEmail" method="POST">
		<table>
			<tr>
				<td>Recipients:</td>
				<td><input type="text" id="recipients" name="recipients"
					placeholder="Please input recipients" /></td>
			</tr>
			<tr>
				<td>Title:</td>
				<td><input type="text" id="title" name="title"
					placeholder="Please input email title" /></td>
			</tr>
			<tr>
				<td>Content:</td>
				<td><textarea rows="4" cols="80" id="content" name="content"></textarea></td>
			</tr>
			<tr><td><input type="button" value="send" onclick="sendEmail()"/></td></tr>
		</table>
	</form>
</body>
</html>