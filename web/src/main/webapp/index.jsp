<html>
<head>
<meta charset="UTF-8" />
<script type="text/javascript" src="js/homePage.js"></script>
<script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
<title>HomePage</title>
</head>
<body>
	<h1>HomePage</h1>
	<fieldset>
		<legend>Handler</legend>
		<input type="button" value="Repository" onclick="postHandler('handler/repository')" /> 
		<input type="button" value="Order" onclick="postHandler('handler/order')" />
		<input type="button" value="Cluster" onclick="postHandler('handler/cluster')" />
		<input type="button" value="Configure" onclick="postHandler('handler/configure')" />
	</fieldset>
</body>
</html>
