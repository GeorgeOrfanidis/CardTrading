<%@page import="utilities.createForm"%>
<jsp:useBean id="UserSession" class="beans.SessionBean" scope="session" /> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction Details</title>
<link rel="stylesheet" type="text/css" href="style/cardTradingStyle.css">
</head>
<body>
<h1>Transaction Details</h1>
<form action="TransactionControllerServlet">
		<table border="10">
	      <tr>
	        <th>Card</th>
	        <th>Card Number</th>
	        <th>Buy/Sell</th>
	        <th>Quantity</th>
	        <th>Card Price</th>
	        <th>Total Value</th>
	      </tr>
	      <%= UserSession.getTransactionDetailsTable()%>
	      <tr>
	        <th colspan="6" align="left">Bank Account Information</th>
	      </tr>
	      <tr>
	        <td>Account Holder Name</td>
	        <td colspan="5">
	          		<input type="text" name="accountName"/>
	        </td>
	      </tr>
	      <tr>
	        <td>Routing Number</td>
	        <td colspan="5\">
	          		<input type="text" name="routingNumber"/>
	        </td>
	      </tr>
		</table>  
		<br>
		<input type="submit" name="confirmTransaction" value="Confirm Transaction"/> 
		<a href="CardTradingServlet"><button type="button" name="cancel">Cancel</button></a>
</form> 
<br>
<br>
<a href="LogoutControllerServlet"><button class="logout" type="button">Logout</button></a>
</body>
</html>
 <% UserSession.cleanTransactionDetails();%>
