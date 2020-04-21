package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.SessionBean;

/**
 * Servlet implementation class CardTradingServlet
 */
@WebServlet("/CardTradingServlet")
public class CardTradingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		//obtain the session object 
		HttpSession session = request.getSession(false);//should i delete false here since see prompt 
		SessionBean sessionInfo = (SessionBean) session.getAttribute("UserSession");
		String username=sessionInfo.getUserName();
		boolean login=utilities.DataCheck.IfReferredBy(request, "login.jsp");
		System.out.println(login);
		boolean registration=utilities.DataCheck.IfReferredBy(request, "registration.jsp");
		System.out.println(registration);
		
		String primaryKey=sessionInfo.getUserPrimaryKey();
		int pk=Integer.parseInt(primaryKey);
		
		if (username==null)
		{
			response.sendRedirect("login.jsp");
		}
		else //if (login || registration) 
		{
			//retrieves the current cards from the db for the user with pk 
			model.User_Collection_Info.getCurrentCards(pk, sessionInfo);
			
			//retrieves the available cards from the db
			model.Available_Cards_Info.putAvailableCardsIntoSession(sessionInfo);
		
		}
		
		utilities.createForm.buildCardCollectionForm(sessionInfo,pk);
	
		utilities.createForm.buildAvailableCardsForm(sessionInfo);
	
		response.sendRedirect("cardTrader.jsp");
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

}
