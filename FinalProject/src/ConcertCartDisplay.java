
public class ConcertCartDisplay {
	private String concertName;
	private int quantity;
	private int totalPrice;
	public ConcertCartDisplay(String concertName, int quantity, int totalPrice) {
		super();
		this.concertName = concertName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public String getConcertName() {
		return concertName;
	}
	public void setConcertName(String concertName) {
		this.concertName = concertName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
