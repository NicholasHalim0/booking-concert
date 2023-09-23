
public class TableArtist {
	private int artistID;
	private String artistName;
	
	public TableArtist(int artistID, String artistName) {
		super();
		this.artistID = artistID;
		this.artistName = artistName;
	}
	
	public boolean DBDeleteArtist() {
		int count = 0;
		try {
			String query = String.format("DELETE FROM artist WHERE ArtistID = '%s'", this.artistID);
			count = DBConnection.getInstance().executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean DBUpdateArtist() {
		int count = 0;
		try {
			String query = String.format("UPDATE artist SET ArtistName = '%s' WHERE ArtistID = %d", this.artistName, this.artistID);
			count = DBConnection.getInstance().executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	public boolean DBInsertArtist() {
		int count = 0;
		try {
			String query = String.format("INSERT INTO artist VALUES (%d, '%s')", this.artistID, this.artistName);
			count = DBConnection.getInstance().executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count>0;
	}
	
	
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	
}
