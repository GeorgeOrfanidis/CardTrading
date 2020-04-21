<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="UserSession" class="beans.SessionBean" scope="session" /> 
<!-- create a new instance of the class SessionBean. The object's name is UserSession and its scope is session-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%= UserSession.getUserName() %>'s Card Collection</title>
<link rel="stylesheet" type="text/css" href="style/cardTradingStyle.css">
</head>
<body>
	<fieldset>
		<h1><%= UserSession.getUserName() %>'s  Current Card Collection</h1>
		<h3 class="herror"><%= UserSession.getMessage() %></h3>
		<div>
			<!--  <table border="1">
			<tr>
			   <th>Card</th>
			   <th>Card Number</th>
			   <th>Value</th>
			   <th>Owned Amount</th>
			   <th>Total Value</th>
			   <th>Action</th>
			   <th>Quantity</th>
			   <th></th>
		  	</tr> -->
			<%= UserSession.getCardCollectionForm() %>
			<!--  </table>-->
		</div>
		<div>
			<h1>Available Cards to Buy</h1>
				<table border="1">
				      <tr>
				        <th>Card</th>
				        <th>Card Number</th>
				        <th>Cost</th>
				        <th>Quantity</th>
				        <th></th>
				      </tr>
					  <%= UserSession.getCardsForSaleForm() %>
				</table>
		</div>
	</fieldset>
	<a href="LogoutControllerServlet"><button class="logout" type="button">Logout</button></a>

</body>
</html>
<% UserSession.setMessage("");  %>