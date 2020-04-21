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
 * Servlet implementation class LoginControllerServlet
 */
@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		response.setContentType("text/html");
		
		HttpSession session = request.getSession(false);
		SessionBean sessionInfo = (SessionBean) session.getAttribute("UserSession");
		
		//get username and password 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username.isEmpty())
		{
			sessionInfo.setMessage("****Specify a Username to proceed!****");
			response.sendRedirect("login.jsp");
			return;
			
		}
		if (password.isEmpty())
		{
			sessionInfo.setMessage("****Give your password to proceed!****");
			response.sendRedirect("login.jsp");
			return; 
			
		}
		if (model.User_Login_Register_Info.isUsernameAndPasswordInDB(username, password))
		{
			sessionInfo.setUserName(username);
			String primaryKey=model.User_Login_Register_Info.getUserInfoPrimaryKey(username);
			sessionInfo.setUserPrimaryKey(primaryKey);
			response.sendRedirect("CardTradingServlet");
			return;
		}
		else
		{
			sessionInfo.setMessage("****Invalid Login!****");
			response.sendRedirect("login.jsp");
			return;
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}


}
