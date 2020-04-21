package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import beans.SessionBean;

public class User_Collection_Info {

	
	//Insert card 
	public static void insertToCurrentCard(String userPK, String cardPK, String ammtO) {
				
				Connection con = Database_Connect.Connect2LocalDB();
			   try {
			    	
			        PreparedStatement prep = con.prepareStatement("INSERT INTO current_cards (user_primary_key,card_primary_key,ammount_owned) VALUES (?,?,?)");
			        prep.setString(1, userPK);
			        prep.setString(2, cardPK);
			        prep.setString(3, ammtO);
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
	
	
	//Update card 
		public static void updateCurrentCard(String userPK, String cardPK, String ammtO) {
					
			Connection con = Database_Connect.Connect2LocalDB();
				   try {
				    	
				        PreparedStatement prep = con.prepareStatement("UPDATE current_cards SET ammount_owned=? WHERE user_primary_key=? AND card_primary_key=?");
				        prep.setString(1, ammtO);
				        prep.setString(2, userPK);
				        prep.setString(3, cardPK);
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
		
		
		//gets current cards and put them into the session 
				public static void getCurrentCards(int userPrimaryKey, SessionBean sessionInfo) {
					
					Connection con = Database_Connect.Connect2LocalDB();
					String primaryKey;
					String name, number, value,ammtOwned;
			        try {
			            PreparedStatement key = con.prepareStatement("SELECT available_cards.primary_key,available_cards.card_name,available_cards.card_number,available_cards.card_value,current_cards.ammount_owned FROM available_cards JOIN current_cards ON available_cards.primary_key=current_cards.card_primary_key WHERE current_cards.user_primary_key=?");
			            key.setInt(1, userPrimaryKey);
			            ResultSet rows = key.executeQuery();
			            while(rows.next())
			            {
			            	primaryKey = rows.getString(1);
			            	name = rows.getString(2);
			            	number = rows.getString(3);
			            	value = rows.getString(4);
			            	ammtOwned = rows.getString(5);
			            	
			            	sessionInfo.setCardCollection(primaryKey ,name, number, value, ammtOwned,"");
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
				
				//checks if the Current_cards Table is empty or not for the user with primary key 
				 public static boolean isCurrent_CardsEmpty(int pk) {
					
					 Connection con = Database_Connect.Connect2LocalDB();
					 int flag=0;
				     try {
				    	 //allocate a prepare statement in the connection and initialize it at the same time
				         PreparedStatement statement = con.prepareStatement("SELECT available_cards.primary_key,available_cards.card_name,available_cards.card_number,available_cards.card_value,current_cards.ammount_owned FROM available_cards JOIN current_cards ON available_cards.primary_key=current_cards.card_primary_key WHERE current_cards.user_primary_key=?");
				         statement.setInt(1, pk);
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
				 
				
				//to find if this is a new card that we add in our list or is an existing card that we need to update create a function 
				// that will be a boolean. You will know the user's pk and the card's pk 
				// the function should be like this,SELECT * FROM current_cards WHERE user_primary_key=1 AND card_primary_key=15
				// if the result set is null this means you are inserting a new card to your collection 
				// if it is not null you updating the amount of a current card in your collection 
				 public static boolean isAnewCard(int userPk, int cardPk) {
					
					 Connection con = Database_Connect.Connect2LocalDB();
					 int flag=0;
				     try {
				    	 //allocate a prepare statement in the connection and initialize it at the same time
				         PreparedStatement statement = con.prepareStatement("SELECT * FROM current_cards WHERE user_primary_key=? AND card_primary_key=?");
				         statement.setInt(1, userPk);
				         statement.setInt(2, cardPk);
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
				 
				 //move from collection to available 
				 //returns true if it has been removed 
				 public static boolean removeFromCollection(String userPK) {
						
						Connection con = Database_Connect.Connect2LocalDB();
						int flag=0;
							   try {
							    	
							
							        PreparedStatement prep = con.prepareStatement("SELECT ammount_owned, primary_key FROM current_cards WHERE user_primary_key=?");
							        prep.setString(1, userPK);
							        ResultSet rows = prep.executeQuery();
							        while(rows.next())
							        {
							        	int ammt_owned=rows.getInt(1);
							        	int pk = rows.getInt(2);
							        	System.out.println("ammount owned "+ammt_owned);
							        	if(ammt_owned == 0)
							        	{
							        		PreparedStatement stmt = con.prepareStatement("DELETE FROM current_cards WHERE primary_key=?");
									        stmt.setInt(1, pk);
									        stmt.executeUpdate();
									        flag=1;
							        	}
							        }
							
							    } catch (SQLException ex) {
							        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() + ".insertToCurrentCard ", ex);
							    }
							    try {
							        con.close();
							    } catch (SQLException ex) {
							        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
							    }
							    if (flag==1)
							    {
							    	return true;
							    }
							    else 
							    {
							    	return false;
							    }
							}	
				 
}
