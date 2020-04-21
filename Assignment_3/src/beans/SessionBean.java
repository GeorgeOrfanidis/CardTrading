package beans;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

//is a java class that basically holds information to pass it to other pages 
// sessions 
public class SessionBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public SessionBean() { // zero-argument (empty) constructor
	}
	
	private String userName = "";
	private String password="";
	private String userPrimaryKey;
	private String Message = "";
	private String cardCollectionForm = "";
	private String cardsForSaleForm= "";
	private String transactionDetailsTable= "";
	private String QuantityOfCard="0";//help us update the number of cards after a purchase or sell 
	private HashMap<String, String[]> cardsForSale = new HashMap<String, String[]>();
	private HashMap<String, String[]> cardCollection = new HashMap<String, String[]>();
	private HashMap<String, String[]> transactionDetails= new HashMap<String, String[]>();

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	// cardCollection setters and getters 
	public Set<Entry<String, String[]>> getCardCollection() {
		Set<Entry<String, String[]>> cardCollectionSet = cardCollection.entrySet();
    	return cardCollectionSet;
    	// Set - A collection that contains no duplicate elements.
    	// .entrySet() is used to create a set out of the same elements contained in the hash map.
    	// https://www.geeksforgeeks.org/hashmap-entryset-method-in-java/
	}
	public void setCardCollection(String pk,String key_nameOfCard, String cardNumber, String value, String ammount_owned, String totalValue) {
		 this.cardCollection.put(key_nameOfCard, new String[] {pk,cardNumber, value, ammount_owned, totalValue});
	}
	
	public void cleanCardCollection() {
		cardCollection.clear();
	}
	
	//getCardsForSale setters and getters 
	public Set<Entry<String, String[]>> getCardsForSale() {
		Set<Entry<String, String[]>> cardsForSaleSet = cardsForSale.entrySet();
    	return cardsForSaleSet;
	}
	public void setCardsForSale(String key_cardName, String numberOfCard, String cost) {
		this.cardsForSale.put(key_cardName, new String[] {numberOfCard, cost});
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardCollectionForm() {
		return cardCollectionForm;
	}
	public void setCardCollectionForm(String cardCollectionForm) {
		this.cardCollectionForm = cardCollectionForm;
	}
	public String getCardsForSaleForm() {
		return cardsForSaleForm;
	}
	public void setCardsForSaleForm(String cardsForSaleForm) {
		this.cardsForSaleForm = cardsForSaleForm;
	}
	public Set<Entry<String, String[]>> getTransactionDetails() {
		Set<Entry<String, String[]>> transactionDetailsSet = transactionDetails.entrySet();
    	return transactionDetailsSet;
	}
	public void setTransactionDetails(String pk,String key_cardName, String param1, String param2,String param3,String param4,String param5) {
		this.transactionDetails.put(key_cardName, new String[] {pk,param1, param2,param3,param4,param5});
	}
	//clean the hashmap that holds the info from the transaction so only one card is available for checkout
	public void cleanTransactionDetails() {
		transactionDetails.clear();
	}
	public String getTransactionDetailsTable() {
		return transactionDetailsTable;
	}
	public void setTransactionDetailsTable(String transactionDetailsTable) {
		this.transactionDetailsTable = transactionDetailsTable;
	}
	public String getQuantityOfCard() {
		return QuantityOfCard;
	}
	public void setQuantityOfCard(String prevQuantityOfCard) {
		this.QuantityOfCard = prevQuantityOfCard;
	}
	public String getUserPrimaryKey() {
		return userPrimaryKey;
	}
	public void setUserPrimaryKey(String userPrimaryKey) {
		this.userPrimaryKey = userPrimaryKey;
	}

	
}
