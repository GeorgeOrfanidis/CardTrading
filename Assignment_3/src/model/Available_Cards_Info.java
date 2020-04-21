package model;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import beans.SessionBean;

import java.sql.*;

public class Available_Cards_Info {

		public static void initializeAvailableCards() {
			
			Connection con = Database_Connect.Connect2LocalDB();
			 try {
				
				 	String stmt="INSERT INTO available_cards (card_name,card_number,card_value) VALUES (?,?,?)";
				 	PreparedStatement prep = con.prepareStatement(stmt);
				 
				 	
				 	prep.setString(1, "1983 Topps Tony Gwynn RC");
					prep.setString(2, "482");
					prep.setString(3, "75.21");
					prep.addBatch();
					
					
				 	prep.setString(1, "1984 Fleer Update Roger Clemens");
					prep.setString(2, "27");
					prep.setString(3, "120");
					prep.addBatch();
					
				
				 	prep.setString(1, "1983 Topps Ryne Sandberg");
					prep.setString(2, "83");
					prep.setString(3, "20");
					prep.addBatch();
					
					
				 	prep.setString(1, "1984 Donruss Don Mattingly");
					prep.setString(2, "248");
					prep.setString(3, "40");
					prep.addBatch();
					
				
				 	prep.setString(1, "1984 Donruss Joe Carter RC");
					prep.setString(2, "41");
					prep.setString(3, "8");
					prep.addBatch();
					
					
				 	prep.setString(1, "1984 Donruss Darryl Strawberry");
					prep.setString(2, "68");
					prep.setString(3, "12");
					prep.addBatch();
					
					prep.executeBatch();
				
			    } catch (SQLException ex) {
			        Logger.getLogger(Available_Cards_Info.class.getName()).log(Level.SEVERE, Available_Cards_Info.class.getName() + "initializeCards", ex);
			    }
			    try {
			        con.close();
			    } catch (SQLException ex) {
			        Logger.getLogger(Available_Cards_Info.class.getName()).log(Level.SEVERE, null, ex);
			    }
		    
		}
		
		//Insert card 
		public static void insertCard(String name, String number, String value) {
			
			Connection con = Database_Connect.Connect2LocalDB();
		    try {
		    	
		        PreparedStatement prep = con.prepareStatement("INSERT INTO available_cards (card_name,card_number,card_value) VALUES (?,?,?)");
		        prep.setString(1, name);
		        prep.setString(2, number);
		        prep.setString(3, value);
		        prep.executeUpdate();
		
		    } catch (SQLException ex) {
		        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() + ".insertCard ", ex);
		    }
		    try {
		        con.close();
		    } catch (SQLException ex) {
		        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		
		// get Cards, it returns a hashmap with all the necessary information and the primary key as the key 
		public static HashMap<String, String[]> getAvailableCards() {
			
			Connection con = Database_Connect.Connect2LocalDB();
			HashMap<String, String[]> cardsInfo = new HashMap<String, String[]>();
			String primaryKey;
			String name, number, value;
	        try {
	            PreparedStatement key = con.prepareStatement("SELECT * FROM available_cards");
	            ResultSet rows = key.executeQuery();
	            while(rows.next())
	            {
	            	primaryKey = rows.getString(1);
	            	name = rows.getString(2);
	            	number = rows.getString(3);
	            	value = rows.getString(4);
	            	
	            	cardsInfo.put(primaryKey,new String[] {name,number,value});
	            }
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName(), ex);
	        }
	        try {
	            con.close();
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return cardsInfo;
	    }
		
		//gets available cards and put them into the session 
		public static void putAvailableCardsIntoSession( SessionBean sessionInfo) {
			
			Connection con = Database_Connect.Connect2LocalDB();
			String primaryKey;
			String name, number, value;
	        try {
	            PreparedStatement key = con.prepareStatement("SELECT * FROM available_cards");
	            ResultSet rows = key.executeQuery();
	            while(rows.next())
	            {
	            	primaryKey = rows.getString(1);
	            	name = rows.getString(2);
	            	number = rows.getString(3);
	            	value = rows.getString(4);
	            	
	            	sessionInfo.setCardsForSale(name, number, value);
	            	
	            }
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName(), ex);
	        }
	        try {
	            con.close();
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
	    }
		
	
		
		//checks if the Available_cards Table is empty or not 
		 public static boolean isAvailable_CardsEmpty() {
			
			 Connection con = Database_Connect.Connect2LocalDB();
			 int flag=0;
		     try {
		    	 //allocate a prepare statement in the connection and initialize it at the same time
		         PreparedStatement statement = con.prepareStatement("SELECT * FROM available_cards");
		         ResultSet rows = statement.executeQuery();
		         if (rows.next() == false){ 
		         
		        	 flag=1;
		         }

		     } catch (SQLException ex) {
		         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() +   "; " + ex.getMessage(), ex);
		     }
		     try {
		         con.close();
		     } catch (SQLException ex) {
		         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
		     }
		     
		     if (flag == 0) {
		         return false;
		     } else {
		         return true;
		     }
		 }
		 
		 //get primary key of card that was sent from transactionDetails.jsp 
		 //from the jsp page that you can confirm or cancel the transaction 
		 public static int getPKofCardYouWantToBuy(String cardName) {

			 	Connection con = Database_Connect.Connect2LocalDB();
			 	int cardPk=0;
		        try {
		            PreparedStatement key = con.prepareStatement("SELECT primary_key FROM available_cards WHERE card_name=?");
		            key.setString(1, cardName);//this set the first ? to cardName since it is a prepared statement 
		            ResultSet rows = key.executeQuery();
		            rows.next();
		            cardPk = rows.getInt(1);
		        } catch (SQLException ex) {
		            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName(), ex);
		        }
		        try {
		            con.close();
		        } catch (SQLException ex) {
		            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
		        }
		        return cardPk;
		    }
		 
		 //remove card from availableCards
		 public static void removeFromAvailable(int primaryKey) {
				
				Connection con = Database_Connect.Connect2LocalDB();
					   try {
					    	
					        PreparedStatement prep = con.prepareStatement("DELETE FROM available_cards WHERE primary_key=?");
					        prep.setInt(1, primaryKey);
					       prep.executeUpdate();
					
					    } catch (SQLException ex) {
					        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() + ".insertToCurrentCard ", ex);
					    }
					    try {
					        con.close();
					    } catch (SQLException ex) {
					        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
					    }
					}	
}
