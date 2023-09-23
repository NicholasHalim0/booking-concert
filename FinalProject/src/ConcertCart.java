
public class ConcertCart {
	private int userID;
	private String concertID;
	private int quantity;
	

	public boolean insertConcertCart() {
		int count = 0;
		try {
			String query = String.format("insert into cart value ('%d','%s','%d')", this.userID, this.concertID, this.quantity );
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean updateConcertCart() {
		int count = 0;
		try {
			String query = String.format("update cart set Quantity = '%d' WHERE ConcertID = '%s' AND UserID = '%d'", this.quantity, this.concertID, this.userID);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean deleteConcertCart() {
		int count = 0;
		try {
			String query = String.format("delete from cart where UserID = '%s'", this.userID);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}

	public ConcertCart(int userID, String concertID, int quantity) {
		super();
		this.userID = userID;
		this.concertID = concertID;
		this.quantity = quantity;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getConcertID() {
		return concertID;
	}
	public void setConcertID(String concertID) {
		this.concertID = concertID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
