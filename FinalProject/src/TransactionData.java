
public class TransactionData {
	private int trxID;
	private int userID;
	private String trxDate;
	public TransactionData(int trxID, int userID, String trxDate) {
		super();
		this.trxID = trxID;
		this.userID = userID;
		this.trxDate = trxDate;
	}
	
	public boolean insertTransactionHeaderDatabase() {
		int count = 0;
		try {
			String query = String.format("INSERT INTO transactionheader VALUES ('%d','%d','%s')", this.trxID, this.userID, this.trxDate);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public int getTrxID() {
		return trxID;
	}
	public void setTrxID(int trxID) {
		this.trxID = trxID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
}
