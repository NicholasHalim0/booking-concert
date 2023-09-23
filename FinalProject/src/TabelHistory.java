import javafx.scene.control.Button;

public class TabelHistory {
	private int transactionID;
	private String transactionDate;
	private int ticketBought;
	private Button action;
	public TabelHistory(int transactionID, String transactionDate, int ticketBought) {
		super();
		this.transactionID = transactionID;
		this.transactionDate = transactionDate;
		this.ticketBought = ticketBought;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getTicketBought() {
		return ticketBought;
	}
	public void setTicketBought(int ticketBought) {
		this.ticketBought = ticketBought;
	}
}
