<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8"/>
<title>Send Email</title>
</head>
<body>
	<form action="utils/sendEmail" method="POST">
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
			<tr><td><input type="submit" value="send"/></td></tr>
		</table>
	</form>
</body>
</html>