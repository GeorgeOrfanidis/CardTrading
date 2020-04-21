package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.SessionBean;

/**
 * Servlet implementation class RegistrationControllerServlet
 */
@WebServlet("/RegistrationControllerServlet")
public class RegistrationControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);//should i delete false here since see prompt 
		SessionBean sessionInfo = (SessionBean) session.getAttribute("UserSession");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmPassword");
		
		if (password.isEmpty() || username.isEmpty() || confirmedPassword.isEmpty())
		{
			sessionInfo.setMessage("****Don't leave empty!****");
			response.sendRedirect("registration.jsp");
			return; 
			
		}
		if (model.User_Login_Register_Info.isUsernameInDB(username))
		{
			sessionInfo.setMessage("****Username not available!****");
			//sessionInfo.setUserName(username);// why should we do this see promt 
			response.sendRedirect("registration.jsp");
			return;
			
		}
		else 
		{
			if (username.matches(confirmedPassword)==true)
			{
				model.User_Login_Register_Info.addUser(username, confirmedPassword);
				String primaryKey=model.User_Login_Register_Info.getUserInfoPrimaryKey(username);
				sessionInfo.setUserPrimaryKey(primaryKey);
				sessionInfo.setUserName(username);
				//System.out.println("inititalize or not "+ model.Available_Cards_Info.isAvailable_CardsEmpty());
				if(model.Available_Cards_Info.isAvailable_CardsEmpty())
				{
					model.Available_Cards_Info.initializeAvailableCards();
				}
				
				response.sendRedirect("CardTradingServlet");
			}
			else
			{
				sessionInfo.setMessage("****Passwords don't match!****");
				response.sendRedirect("registration.jsp");
			}
				
		 }
		
	}
		

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}


}
