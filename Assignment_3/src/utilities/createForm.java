package utilities;

import java.util.HashMap;

import beans.SessionBean;

public class createForm {
	public static void buildCardCollectionForm(SessionBean sessionInfo, int pk)
	{
		String CardCollectionHtml="";
		if(model.User_Collection_Info.isCurrent_CardsEmpty(pk))
		{
			CardCollectionHtml="<h3 class=\"herror\">---You have not purchased any cards yet!---</h3>";
		}
		else 
		{
			CardCollectionHtml="<table border=\"1\">\r\n" + 
					"			<tr>\r\n" + 
					"			   <th>Card</th>\r\n" + 
					"			   <th>Card Number</th>\r\n" + 
					"			   <th>Value</th>\r\n" + 
					"			   <th>Owned Amount</th>\r\n" + 
					"			   <th>Total Value</th>\r\n" + 
					"			   <th>Action</th>\r\n" + 
					"			   <th>Quantity</th>\r\n" + 
					"			   <th></th>\r\n" + 
					"		  	</tr>\r\n" ;
			for (HashMap.Entry<String, String[]> entry : sessionInfo.getCardCollection()) 
			{
				String cardName = entry.getKey();
	            String InfoForIndividualCard[] = entry.getValue();
	            Float value,totalValue;
	            int quantity,primaryKey;
	            if(InfoForIndividualCard[0] != null)
	            {
	            	primaryKey=Integer.parseInt(InfoForIndividualCard[0]);
	            	CardCollectionHtml += "<input type=\"hidden\" name=\"pkUserCardCollection\" value="+primaryKey+">";
	            }
	            else 
	            {
	            	CardCollectionHtml +="<tr>";
	            }
	            value=Float.parseFloat(InfoForIndividualCard[2]);
	            quantity=Integer.parseInt(InfoForIndividualCard[3]);
	            totalValue=quantity*value;
	            System.out.println(cardName);
	            CardCollectionHtml += //"<tr>"+ 
					    "<td>"+cardName+"</td>"+
					    "<td>"+InfoForIndividualCard[1]+"</td>"+
					    "<td>"+"$"+value+"</td>"+
					    "<td>"+quantity+"</td>"+
					    "<td>"+"$"+totalValue+"</td>"+
					    "<td>"+
					       "<form action=\"TransactionControllerServlet\">"+
					         "<select name=\"buyOrSell\">"+
					           "<option value=\"buy\">Buy</option>"+
					           "<option value=\"sell\">Sell</option>"+
					         "</select>"+
					    "</td>"+
					    "<td> <input type=\"text\" name=\"qty\"></td>"+
					    "<input type=\"hidden\" name=\"cardName\" value="+"\""+cardName+"\""+">"+ 
					    "<input type=\"hidden\" name=\"cardNumber\" value="+InfoForIndividualCard[1]+">"+
					    "<input type=\"hidden\" name=\"cardPrice\" value="+value+">"+ 
					    "<input type=\"hidden\" name=\"ammountOwned\" value="+quantity+">"+ 
					   // "<input type=\"hidden\" name=\"pkUserCardCollection\" value="+primaryKey+">"+ 
					    "<td><input type=\"submit\" name=\"cardCollection\" value=\"Make Transaction\"></td>"+
					    "</form>"+
					 "</tr>";
					 
	
			}
			CardCollectionHtml +="</table>";
		}
		sessionInfo.setCardCollectionForm(CardCollectionHtml);
	}
	
	public static void buildAvailableCardsForm(SessionBean sessionInfo)
	{
		String availableCardsHtml="";
		for (HashMap.Entry<String, String[]> entry : sessionInfo.getCardsForSale()) 
		{
			String cardName = entry.getKey();
            String InfoForIndividualCard[] = entry.getValue();
            availableCardsHtml += "<tr>"+
			        "<form action=\"TransactionControllerServlet\">"+
			          "<td>"+cardName+"</td>"+
			          "<td>"+InfoForIndividualCard[0]+"</td>"+
			          "<td>$"+InfoForIndividualCard[1]+"</td>"+
			          "<td><input type=\"text\" name=\"qty\" /></td>"+
			          "<td><input type=\"submit\" name=\"availableCards\" value=\"Purchase\" /></td>"+
			          "<input type=\"hidden\" name=\"cardName\" value="+"\""+cardName+"\""+">"+
			          "<input type=\"hidden\" name=\"cardNumber\" value="+InfoForIndividualCard[0]+">"+ 
			          "<input type=\"hidden\" name=\"cardPrice\" value="+InfoForIndividualCard[1]+">"+ 
			        "</form>"+
			      "</tr>";

		}
		sessionInfo.setCardsForSaleForm(availableCardsHtml);
	}
	//creates table for transaction details 
	public static void transactionDetailsTable(SessionBean sessionInfo)
	{
		String transactionDetails="";
		for (HashMap.Entry<String, String[]> entry : sessionInfo.getTransactionDetails()) 
		{
			String cardName = entry.getKey();
			System.out.println(cardName);
            String infoForCard[] = entry.getValue();
            Float cardPrice=Float.parseFloat(infoForCard[4]);
            int quantity=Integer.parseInt(infoForCard[3]);
            if (infoForCard[0]!=null)
            {
            	int primaryKey=Integer.parseInt(infoForCard[0]);
            	transactionDetails += "<tr>"+"<input type=\"hidden\" name=\"pkFromTransactionDetails\" value="+"\""+primaryKey+"\""+">";
            }
            else
            {
            	transactionDetails += "<tr>";
            }
          //System.out.println("pk from creat form transaction details "+primaryKey);
           Float total=cardPrice*quantity;
           if (infoForCard[2]==null)
           {
        	  infoForCard[2]="buy"; 
           }
            transactionDetails += //"<tr>"+
        	        "<td>"+cardName+"<input type=\"hidden\" name=\"cardNameTd\" value="+"\""+cardName+"\""+">"+"</td>"+
        	        "<td>"+infoForCard[1]+"<input type=\"hidden\" name=\"cardNumberTd\" value="+"\""+infoForCard[1]+"\""+">"+"</td>"+
        	        "<td>"+infoForCard[2]+"<input type=\"hidden\" name=\"buyOrSellTd\" value="+"\""+infoForCard[2]+"\""+">"+"</td>"+
        	        "<td>"+quantity+"<input type=\"hidden\" name=\"quantityTd\" value="+"\""+quantity+"\""+">"+"</td>"+
        	        "<td>"+cardPrice+"<input type=\"hidden\" name=\"cardPriceTd\" value="+"\""+cardPrice+"\""+">"+"</td>"+
        	        "<td>"+total+"<input type=\"hidden\" name=\"totalTd\" value="+"\""+total+"\""+">"+
        	       // "<input type=\"hidden\" name=\"pkFromTransactionDetails\" value="+"\""+primaryKey+"\""+">"+
        	        "</td>"+
        	      "</tr>";

		}
		sessionInfo.setTransactionDetailsTable(transactionDetails);
	}

}

	
