
public class TabelDetail {
	private int transactionID;
	private String concertName;
	private String artistName;
	private int quantity;
	public TabelDetail(int transactionID, String concertName, String artistName, int quantity) {
		super();
		this.transactionID = transactionID;
		this.concertName = concertName;
		this.artistName = artistName;
		this.quantity = quantity;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public String getConcertName() {
		return concertName;
	}
	public void setConcertName(String concertName) {
		this.concertName = concertName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
