<jsp:useBean id="UserSession" class="beans.SessionBean" scope="session" /> 
<!-- create a new instance of the class SessionBean. The object's name is UserSession and its scope is session-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" type="text/css" href="style/cardTradingStyle.css">
</head>
<body>
<form action = "LoginControllerServlet" method="post">
	<table>
		<tr> 
			<th colspan="2">Card Trading Post</th>
		</tr>
		<tr>
			
			<td colspan="2" class="error"><%= UserSession.getMessage() %></td>
		</tr>
		<tr>
			<td>Username:</td>
			<td> <input type="text" name="username"></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit">Login</button>
		    </td>
		</tr> 
		<tr>
			<td colspan="2">
				<button type="submit" formaction="registration.jsp">Register</button>
		    </td>
		</tr> 
	
	</table>
</form>
</body>
</html>
<% UserSession.setMessage("");  %>