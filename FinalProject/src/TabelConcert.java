
public class TabelConcert {
	private String concertID;
	private String concertName;
	private String artistName;
	private int ticketPrice;
	private int ticketStock;

	public TabelConcert(String concertID, String concertName, String artistName, int ticketPrice, int ticketStock) {
		super();
		this.concertID = concertID;
		this.concertName = concertName;
		this.artistName = artistName;
		this.ticketPrice = ticketPrice;
		this.ticketStock = ticketStock;
	}
	public String getConcertID() {
		return concertID;
	}
	public void setConcertID(String concertID) {
		this.concertID = concertID;
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
	public int getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTicketStock() {
		return ticketStock;
	}
	public void setTicketStock(int ticketStock) {
		this.ticketStock = ticketStock;
	}
}
