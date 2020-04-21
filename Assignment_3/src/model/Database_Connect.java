package model;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;





public class Database_Connect {

//	public static String user = "root", pass = "";
	public static String user = "orfanidisg", pass = "projectpart3";

    

  	
    public static Connection Connect2LocalDB() {

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/csci_441_cards", Database_Connect.user, Database_Connect.pass);

            return con;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Connect.class.getName()).log(Level.SEVERE, Database_Connect.class.getName() + ".Connect2LocalDB" + " ClassNotFoundException", ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database_Connect.class.getName()).log(Level.SEVERE, Database_Connect.class.getName() + ".Connect2LocalDB" + " SQLException", ex);
        }
        if (con == null){
        	con = Connect2RemoteDB();
        }
        return con;

    }   

    public static Connection Connect2RemoteDB() {
System.out.println(Database_Connect.class.getName() + ".Connect2RemoteDB *****************************");    	
    	Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // https://medium.com/modernnerd-code/connecting-to-mysql-db-on-aws-ec2-with-jdbc-for-java-91dba3003abb#.6adz9lk1h
            con = DriverManager.getConnection("jdbc:mysql://35.245.225.155:3306/card_trading", Database_Connect.user, Database_Connect.pass);
System.out.println(Database_Connect.class.getName() + ".Connect2RemoteDB success!!! *****************************");
            return con;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database_Connect.class.getName()).log(Level.SEVERE, Database_Connect.class.getName() + ".Connect2RemoteDB" + " ClassNotFoundException", ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database_Connect.class.getName()).log(Level.SEVERE, Database_Connect.class.getName() + ".Connect2RemoteDB" + " SQLException", ex);
        }
        return con;
    }

    

}
