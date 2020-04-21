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
 * Servlet implementation class TransactionControllerServlet
 */
@WebServlet("/TransactionControllerServlet")
public class TransactionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		SessionBean sessionInfo = (SessionBean) session.getAttribute("UserSession");
		
		String cardName=request.getParameter("cardName");
		String cardNumber=request.getParameter("cardNumber");
		String cardPrice=request.getParameter("cardPrice");
		String ammountOwned=request.getParameter("ammountOwned");
		String buyOrSell=request.getParameter("buyOrSell");
		String quantity=request.getParameter("qty");
		String newQuantity_s="";
		int newQuantity;
		
		String primaryKey=sessionInfo.getUserPrimaryKey();
		//System.out.println("pk "+primaryKey);
		int pk=Integer.parseInt(primaryKey);
		
		if (request.getParameter("cardCollection")!=null) //received info from first table 
		{
			
			if (utilities.DataCheck.NullOrEmpty(cardName))
			{
				sessionInfo.setMessage("****Empty field:Card Name!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if (utilities.DataCheck.NullOrEmpty(cardNumber))
			{
				sessionInfo.setMessage("****Empty field:Card Number!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if (utilities.DataCheck.NullOrEmpty(cardPrice))
			{
				sessionInfo.setMessage("****Empty field:Card Price!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if (utilities.DataCheck.NullOrEmpty(ammountOwned))
			{
				sessionInfo.setMessage("****Empty field:Ammount Owned!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if (utilities.DataCheck.NullOrEmpty(buyOrSell))
			{
				sessionInfo.setMessage("****Empty field:Buy or Sell!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if (utilities.DataCheck.NullOrEmpty(quantity))
			{
				sessionInfo.setMessage("****Empty field:Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			try 
			{
				Integer.parseInt(quantity);
			} 
			catch (NumberFormatException e)
			{
				sessionInfo.setMessage("****Wrong input type:Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
		    }
			if (Integer.parseInt(quantity)<0)
			{
				sessionInfo.setMessage("****Sorry, you can NOT specify negative Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			if ((Integer.parseInt(quantity)>Integer.parseInt(ammountOwned)) && buyOrSell.equalsIgnoreCase("sell"))
			{
				sessionInfo.setMessage("****Sorry, you can NOT "+buyOrSell+" more than you own!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
		}
		else if (request.getParameter("availableCards")!=null)//received info from second table 
		{
			if (utilities.DataCheck.NullOrEmpty(quantity))
			{
				sessionInfo.setMessage("****Empty field:Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
			try 
			{
				Integer.parseInt(quantity);
			} 
			catch (NumberFormatException e)
			{
				sessionInfo.setMessage("****Wrong input type:Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
		    }
			if (Integer.parseInt(quantity)<0)
			{
				sessionInfo.setMessage("****Sorry, you can NOT specify negative Quantity!****");
				response.sendRedirect("cardTrader.jsp");
				return;
				
			}
		}
		//check to see from where we came 
			boolean cardTrader=utilities.DataCheck.IfReferredBy(request, "cardTrader.jsp");
			boolean transactionDetails=utilities.DataCheck.IfReferredBy(request, "transactionDetails.jsp");
			if (cardTrader)//if we came here from cardTrader.jsp
			{
			
				if (request.getParameter("cardCollection")!=null)//if we came from first table 
				{
					if(buyOrSell.matches("buy"))
					{
						String cardPkFromUserCardCollection=request.getParameter("pkUserCardCollection");
						//System.out.println("pk from usercardcollection "+ cardPkFromUserCardCollection);
						newQuantity=Integer.parseInt(ammountOwned)+Integer.parseInt(quantity);
						newQuantity_s=Integer.toString(newQuantity);
						sessionInfo.setQuantityOfCard(newQuantity_s);
						sessionInfo.setTransactionDetails(cardPkFromUserCardCollection, cardName,cardNumber,buyOrSell,quantity,cardPrice,"");
						//information about transaction that will be sent
						utilities.createForm.transactionDetailsTable(sessionInfo);
					}
					else //you sell
					{
						String cardPkFromUserCardCollection=request.getParameter("pkUserCardCollection");
						//System.out.println("pk from usercardcollection "+ cardPkFromUserCardCollection);
						newQuantity=Integer.parseInt(ammountOwned)-Integer.parseInt(quantity);
						newQuantity_s=Integer.toString(newQuantity);
						sessionInfo.setQuantityOfCard(newQuantity_s);
						sessionInfo.setTransactionDetails(cardPkFromUserCardCollection, cardName,cardNumber,buyOrSell,quantity,cardPrice,"");
						//information about transaction that will be sent
						utilities.createForm.transactionDetailsTable(sessionInfo);
					}
					
				}
				else if(request.getParameter("availableCards")!=null)//if we come from second table 
				{
					String cardPkFromUserCardCollection=request.getParameter("pkUserCardCollection");
					//System.out.println("pk from usercardcollection "+ cardPkFromUserCardCollection);
					newQuantity=Integer.parseInt(quantity);
					newQuantity_s=Integer.toString(newQuantity);
					sessionInfo.setQuantityOfCard(newQuantity_s);
					sessionInfo.setTransactionDetails(cardPkFromUserCardCollection, cardName,cardNumber,buyOrSell,quantity,cardPrice,"");
					//information about transaction that will be sent
					utilities.createForm.transactionDetailsTable(sessionInfo);
				}
				response.sendRedirect("transactionDetails.jsp");
				return;
			}
			else if (transactionDetails) //we are coming from transactionDetails.jsp 
			{
				//get parameters from the form submitted 
				//the calculations happen before the program reaches this block, they happen when the card is sending to the transactionDetails.jsp
				String cardNameTd=request.getParameter("cardNameTd");
				String cardNumberTd=request.getParameter("cardNumberTd");
				String cardPriceTd=request.getParameter("cardPriceTd");
				String buyOrSellTd=request.getParameter("buyOrSellTd");
				String quantityTd=request.getParameter("quantityTd");
				String totalTd=request.getParameter("totalTd");
				String holderName=request.getParameter("accountName");//account holder name
				String routingNumber=request.getParameter("routingNumber");//rooting number
				String primaryK=request.getParameter("pkFromTransactionDetails");
				
				System.out.println("i am the PKKK  "+primaryK);
				System.out.println(cardNameTd);
				System.out.println(cardNumberTd);
				System.out.println(cardPriceTd);
				System.out.println(buyOrSellTd);
				System.out.println(quantityTd);
				System.out.println(totalTd);
				System.out.println(holderName);
				System.out.println(routingNumber); 
				System.out.println(quantityTd);
				
					//model.User_Collection_Info.updateCurrentCard(primaryKey, primaryK, quantityTd);
					
				// the information regarding the quantity of the card is calculated above and it is saved in the session 
				// so you dont have to recalculate it. You only have to pull it from the session and use it
				String quantityOfCard=sessionInfo.getQuantityOfCard();
				
				//to get the primary key from the card that send from transactionDetails you can create a function
				// a simple function like SELECT primary_key FROM available_cards WHERE card_name=?
				//basically is the pk of the card that came from the jsp page that you can confirm the transaction or cancell it 
				//it can either be pk of card you want to be or pk of card you want to sell. 
				//for simplicity the function called ...toBuy, it could be toBuyOrSell 
				int pkCardYouWantToBuy=model.Available_Cards_Info.getPKofCardYouWantToBuy(cardNameTd);
				//System.out.println("pk of card you want to buy " + pkCardYouWantToBuy);
				
				//to find if this is a new card that we add in our list or is an existing card that we need to update create a function 
				// that will be a boolean. You will know the user's pk and the card's pk 
				// the function should be like this,SELECT * FROM current_cards WHERE user_primary_key=1 AND card_primary_key=15
				// if the result set is null this means you are inserting a new card to your collection 
				// if it is not null you updating the amount of a current card in your collection 
				boolean isAnewCard=model.User_Collection_Info.isAnewCard(pk, pkCardYouWantToBuy);
				System.out.println("is new card " + isAnewCard);
				if (isAnewCard == true)//add new card to user's collection 
				{
					//add new card to the  specified user 
					String userpk=Integer.toString(pk);
					String cardpk=Integer.toString(pkCardYouWantToBuy);
					model.User_Collection_Info.insertToCurrentCard(userpk, cardpk, quantityOfCard);
					sessionInfo.cleanCardCollection();
					model.User_Collection_Info.getCurrentCards(pk, sessionInfo);//inserts them into the session as well
					//sessionInfo.setCardCollection(primaryK,cardNameTd,cardNumberTd, cardPriceTd, quantityOfCard,"");
					//model.Available_Cards_Info.removeFromAvailable(pkCardYouWantToBuy);
					utilities.createForm.buildCardCollectionForm(sessionInfo,pk);//built the updated table 
					
				}
				else//user already has the card but we need to update it 
				{
					String userpk=Integer.toString(pk);
					String cardpk=Integer.toString(pkCardYouWantToBuy);
					model.User_Collection_Info.updateCurrentCard(userpk, cardpk, quantityOfCard);
					 boolean flag=model.User_Collection_Info.removeFromCollection(userpk);//removes rows that have 0 for the quantity 
					 sessionInfo.cleanCardCollection();
					 model.User_Collection_Info.getCurrentCards(pk, sessionInfo);
					 // System.out.println("remove or not "+flag);
					 if(flag == false)//there is no negative quantity, so we just did an update, we didnt remove anything 
					 {
						 model.User_Collection_Info.getCurrentCards(pk, sessionInfo);//takes the cards that are currently in the db and inserts them into the session 
						 
						 //sessionInfo.setCardCollection(primaryK,cardNameTd,cardNumberTd, cardPriceTd, quantityOfCard,"");
						 //you have to combine the other funtions instead of using this setcardCollection 
						 //see how these functions are being used in cardTradingServlet 
						 //see how they are implemented in User_Collection_Info
						 
					 }
					 else //we remove something bc quantity is 0 
					 {
						 model.User_Collection_Info.getCurrentCards(pk, sessionInfo);
					 }
					 model.User_Collection_Info.getCurrentCards(pk, sessionInfo);
					 utilities.createForm.buildCardCollectionForm(sessionInfo,pk);//built the updated table 
					
					//remove it from available cards only if the user sells the whole ammount  
					
				}
				
				
				//String quantityOfCard=sessionInfo.getQuantityOfCard();
				//sessionInfo.setCardCollection(primaryK,cardNameTd,cardNumberTd, cardPriceTd, quantityOfCard,"");
				//utilities.createForm.buildCardCollectionForm(sessionInfo,pk);//build the new table
				response.sendRedirect("cardTrader.jsp");
				return;
			}
	
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}


}
