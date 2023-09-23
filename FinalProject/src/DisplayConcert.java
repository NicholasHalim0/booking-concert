
public class DisplayConcert {
	private String concert;
	private String artist;
	private int price;
	private int stock;
	public DisplayConcert(String concert, String artist, int price, int stock) {
		super();
		this.concert = concert;
		this.artist = artist;
		this.price = price;
		this.stock = stock;
	}
	public String getConcert() {
		return concert;
	}
	public void setConcert(String concert) {
		this.concert = concert;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
