
public class ConcertDB {
	private String concertID;
	private int artistID;
	private String concertName;
	private int Price;
	private int Stock;
	
	
	public ConcertDB(String concertID, int artistID, String concertName, int Price, int Stock) {
		super();
		this.concertID = concertID;
		this.artistID = artistID;
		this.concertName = concertName;
		this.Price = Price;
		this.Stock = Stock;
	}
	
	public boolean insertConcertDatabase() {
		int count = 0;
		try {
			String query = String.format("INSERT INTO concert VALUES ('%s', %d, '%s', %d, %d)", this.concertID, this.artistID, this.concertName, this.Price, this.Stock);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean updateConcertDatabase() {
		int count = 0;
		try {
			String query = String.format("UPDATE concert SET ArtistID = %d, ConcertName = '%s', TicketPrice = %d, TicketStock = %d WHERE ConcertID = '%s'", this.artistID, this.concertName, this.Price, this.Stock, this.concertID);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean deleteConcertDatabase() {
		int count = 0;
		try {
			String query = String.format("DELETE FROM concert WHERE ConcertID = '%s'", this.concertID);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}

	public String getConcertID() {
		return concertID;
	}

	public void setConcertID(String concertID) {
		this.concertID = concertID;
	}

	public int getArtistID() {
		return artistID;
	}

	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}

	public String getConcertName() {
		return concertName;
	}

	public void setConcertName(String concertName) {
		this.concertName = concertName;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int Price) {
		this.Price = Price;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int Stock) {
		this.Stock = Stock;
	}
	
	
}
