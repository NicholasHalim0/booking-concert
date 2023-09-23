
public class TransactionInfo {
	private int trxID;
	private String concertID;
	private int qty;
	public TransactionInfo(int trxID, String concertID, int qty) {
		super();
		this.trxID = trxID;
		this.concertID = concertID;
		this.qty = qty;
	}
	
	public boolean insertTrxInfo() {
		int count = 0;
		try {
			String query = String.format("INSERT INTO transactiondetail VALUES ('%d','%s','%d')", this.trxID, this.concertID, this.qty);
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
	public String getConcertID() {
		return concertID;
	}
	public void setConcertID(String concertID) {
		this.concertID = concertID;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}
