<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<h2>${message}</h2>
	<h4>Server date time is : ${date} </h4>
	
	<form action="/login" method="post">
		<table>
			<tr>
				<td>Username:</td><td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td><td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login"></td>
			</tr>
		</table>
	</form>
</body>
</html>