package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User_Login_Register_Info {
	
	 
	//returns true if username is in the DB, otherwise false 
	 public static boolean isUsernameInDB(String username) {
		 
	Connection con = Database_Connect.Connect2LocalDB();
	 String usernameFromDB = "";
     int usernameFlag = 0;
     try {
    	 //allocate a prepare statement in the connection and initialize it at the same time
         PreparedStatement statement = con.prepareStatement("SELECT user_name FROM usernamespasswords");
         ResultSet rows = statement.executeQuery();
         while (rows.next()){//while there are more rows in the DB table 
         	usernameFromDB = rows.getString("user_name");
					if (usernameFromDB.equalsIgnoreCase(username)){
						usernameFlag= 1;					
					}
         }

     } catch (SQLException ex) {
         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() +   "; " + ex.getMessage(), ex);
     }
     try {
         con.close();
     } catch (SQLException ex) {
         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
     }
     
     if (usernameFlag == 0) {
         return false;
     } else {
         return true;
     }
 }

	 
	 //returns true if username and password are found on the same line 
	 public static boolean isUsernameAndPasswordInDB(String username_para, String password_para) {
		 
		 Connection con = Database_Connect.Connect2LocalDB();
		 String usernameFromDB = "";
		 String passwordFromDB = "";
	     int usernameAndPasswordFlag = 0;
	     try {
	    	 //allocate a prepare statement in the connection and initialize it at the same time
	         PreparedStatement statement = con.prepareStatement("SELECT user_name, user_password FROM usernamespasswords");
	         ResultSet rows = statement.executeQuery();
	         while (rows.next()){//while there are more rows in the DB table 
	         	usernameFromDB = rows.getString("user_name");
	         	passwordFromDB = rows.getString("user_password");
						if (usernameFromDB.equalsIgnoreCase(username_para) && passwordFromDB.equals(password_para)){
							usernameAndPasswordFlag= 1;					
						}
	         }

	     } catch (SQLException ex) {
	         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() + ex.getMessage(), ex);
	     }
	     try {
	         con.close();
	     } catch (SQLException ex) {
	         Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
	     }
	     
	     if (usernameAndPasswordFlag == 0) {
	         return false;
	     } else {
	         return true;
	     }
	 }
	 
	 
	 //get the primary key of the user with username and password
	 public static String getUserInfoPrimaryKey(String username) {

		 	Connection con = Database_Connect.Connect2LocalDB();
	        String usernameKey="";

	        try {
	            PreparedStatement key = con.prepareStatement("SELECT user_primary_key FROM usernamespasswords WHERE user_name = ?");
	            key.setString(1, username);//this set the first ? to username since it is a prepared statement 
	            ResultSet rows = key.executeQuery();
	            rows.next();
	            usernameKey = rows.getString(1);
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName(), ex);
	        }
	        try {
	            con.close();
	        } catch (SQLException ex) {
	            Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return usernameKey;
	    }


	 	//add new user to the database
		public static void addUser(String username, String password) {
		
		Connection con = Database_Connect.Connect2LocalDB();
	    try {
	    	
	        PreparedStatement prep = con.prepareStatement("INSERT INTO usernamespasswords (user_name,user_password) VALUES (?,?)");
	        prep.setString(1, username);
	        prep.setString(2, password);
	        prep.executeUpdate();
	
	    } catch (SQLException ex) {
	        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, User_Login_Register_Info.class.getName() + ".addUser ", ex);
	    }
	    try {
	        con.close();
	    } catch (SQLException ex) {
	        Logger.getLogger(User_Login_Register_Info.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

}
