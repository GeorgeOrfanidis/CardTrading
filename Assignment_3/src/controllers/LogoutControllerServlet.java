package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutControllerServlet
 */
@WebServlet("/LogoutControllerServlet")
public class LogoutControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		response.sendRedirect("login.jsp");
		HttpSession session = request.getSession(false);
		session.invalidate();
		//PrintWriter out = response.getWriter();
		/*try 
		{
			out.println("Welcome Page");
		} 
		finally
		{// always executes when try block exists
			out.close();
		} */
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}


}
