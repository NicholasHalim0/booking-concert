import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.Window;

public class TabelBuy {
	
	TableView<DisplayConcert> tabelconcert;
	TableView<ConcertCartDisplay> tabelcart;
	MenuBar barMenu;
	Menu manage,logout;
	Scene scene;
	BorderPane borderPane;
	MenuItem buy, history;
	Window window;
	VBox Vbox, Vboxx;
	FlowPane flowPane;
	Label avaiLabel, cartLabel, connameLabel, addLabel, qtyLabel;
	TextField qtyField;
	Button addButton,checkoutButton;
	ComboBox<String>connameBox = new ComboBox<>();
	ArrayList<DisplayConcert>concertArrayList = new ArrayList<>();
	ArrayList<ConcertCartDisplay> cartaArrayList = new ArrayList<>();
	int userID;
	
	public TabelBuy(int userID) {
			this.userID = userID;
	}
	
	
	
	public void tabel() {
		tabelconcert = new TableView<>();
	    TableColumn<DisplayConcert, String> column1 = 
	    new TableColumn<>("Concert Name");
	    column1.setCellValueFactory(new PropertyValueFactory<>("concert"));
	    TableColumn<DisplayConcert, String> column2 = 
	    new TableColumn<>("Artist Name");
	    column2.setCellValueFactory(new PropertyValueFactory<>("artist"));
	    TableColumn<DisplayConcert, String> column3 = 
	    new TableColumn<>("Ticket Price");
	    column3.setCellValueFactory(new PropertyValueFactory<>("Price"));
	    TableColumn<DisplayConcert, String> column4 = 
	    new TableColumn<>("Ticket Stock");
	    column4.setCellValueFactory(new PropertyValueFactory<>("Stock"));
	    tabelconcert.getColumns().addAll(column1,column2,column3,column4);
	    refreshconcert();
	    
	    column1.setPrefWidth(250);
		column2.setPrefWidth(150);
		column3.setPrefWidth(150);
		column4.setPrefWidth(150);
		
		tabelcart = new TableView<>();
	    TableColumn<ConcertCartDisplay, String> kolom1 = 
	    new TableColumn<>("Concert Name");
	    kolom1.setCellValueFactory(new PropertyValueFactory<>("concertName"));
	    TableColumn<ConcertCartDisplay, String> kolom2 = 
	    new TableColumn<>("Quantity");
	    kolom2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	    TableColumn<ConcertCartDisplay, String> kolom3 = 
	    new TableColumn<>("Total Price");
	    kolom3.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
	    tabelcart.getColumns().addAll(kolom1,kolom2,kolom3);
	    refreshcart();
	    
	    
	    window = new Window("Buy Ticket");
	    window.setStyle("-fx-font: 14 serif;");
		flowPane = new FlowPane();
		Vbox = new VBox();
		Vbox.setSpacing(15);
		Vbox.setBackground(null);
		Vbox.setPadding(new Insets(10));
		Vboxx = new VBox();
		Vboxx.setSpacing(5);
		avaiLabel = new Label("Available Concerts");
		cartLabel = new Label("Cart");
		connameLabel = new Label("Concert Name");
		addLabel = new Label("Add to Cart");
		qtyLabel = new Label("Quantity");
		qtyField = new TextField();
		qtyField.setPrefSize(200,30);
		addButton = new Button("Add to Cart");
		addButton.setPrefSize(120, 30);
		checkoutButton = new Button("Checkout");
		checkoutButton.setPrefSize(120, 40);
		connameBox = new ComboBox<>();
		connameBox.setPrefSize(200,30);
		tabelconcert.setPrefSize(750, 200);
		tabelcart.setMaxSize(750, 200);
		flowPane.getChildren().addAll(tabelconcert, Vboxx);
		FlowPane.setMargin(tabelconcert, new Insets(0, 20, 0, 0));
		Vboxx.getChildren().addAll(addLabel, connameLabel, connameBox, qtyLabel, qtyField, addButton);
		Vbox.getChildren().addAll(avaiLabel, flowPane, cartLabel, tabelcart, checkoutButton);
		window.getContentPane().getChildren().add(Vbox);
 
		for(DisplayConcert list: concertArrayList) {
			connameBox.getItems().add(list.getConcert());
		}
	}
	public void ConcertDB() {
		String query = "SELECT ConcertName, ArtistName, TicketPrice, TicketStock FROM concert, artist WHERE concert.ArtistID = artist.ArtistID";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		try {
			concertArrayList.clear();
			while(DBConnection.rs.next()) {
				String concertname = DBConnection.rs.getString("ConcertName");
				String artistname = DBConnection.rs.getString("ArtistName");
				Integer ticketprice = DBConnection.rs.getInt("TicketPrice");
				Integer ticketstock = DBConnection.rs.getInt("TicketStock");
				concertArrayList.add(new DisplayConcert(concertname, artistname, ticketprice, ticketstock));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CartDB() {
		String query = String.format("SELECT concert.ConcertName, Quantity, ticketPrice*Quantity AS TotalPrice FROM concert JOIN cart ON cart.ConcertID = concert.ConcertID JOIN user ON user.UserID = cart.UserID WHERE user.userID = '%s'", this.userID);;
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		try {
			cartaArrayList.clear();
			while(DBConnection.rs.next()) {
				String concertname = DBConnection.rs.getString("ConcertName");
				Integer quantity = DBConnection.rs.getInt("Quantity");
				Integer totalprice = DBConnection.rs.getInt("TotalPrice");
				cartaArrayList.add(new ConcertCartDisplay(concertname, quantity, totalprice));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void refreshconcert() {
		// TODO Auto-generated method stub
		ConcertDB();
		ObservableList<DisplayConcert> obsDisplayConcerts = FXCollections.observableArrayList(concertArrayList);
		tabelconcert.setItems(obsDisplayConcerts);
	}

	private void refreshcart() {
		// TODO Auto-generated method stub
		CartDB();
		ObservableList<ConcertCartDisplay> obsDisplayCart = FXCollections.observableArrayList(cartaArrayList);
		tabelcart.setItems(obsDisplayCart);
	}
	public void custbtn () {
		addButton.setOnAction(e -> {	
			String connameclick = connameBox.getValue();
			String qty = qtyField.getText();
			Alert alert = new Alert(AlertType.ERROR);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			int stockticket = 0;
			
			if(connameclick != null) {
				for(int i=0;i<concertArrayList.size();i++) {
					if(concertArrayList.get(i).getConcert().equalsIgnoreCase(connameclick)) {
						stockticket = concertArrayList.get(i).getStock();
						break;
					}
				}
			}
			
			if(connameclick == null) {
				alert.setHeaderText("Error");
				alert.setContentText("You must select a concert ticket you want to buy");
				alert.show();
			}else if(!qty.matches("^[0-9]*$")) {
				alert.setHeaderText("Error");
				alert.setContentText("Quantity must be in number");
				alert.show();
			}else if(qty.isBlank()) {
				alert.setHeaderText("Error");
				alert.setContentText("You must input quantity to add cart");
				alert.show();
			}else if(!(Integer.parseInt(qtyField.getText()) > 0 && Integer.parseInt(qtyField.getText()) <= stockticket)) {
				alert.setHeaderText("Error");
				alert.setContentText("Quantity must be within scope");
				alert.show();
			}else {
				
				String concertID = null;
				Integer quantity = Integer.parseInt(qtyField.getText());

				String query = String.format("SELECT ConcertID FROM concert WHERE ConcertName = '%s'", connameclick);
				DBConnection.rs = DBConnection.getInstance().executeQuery(query);
				
				try {
					while(DBConnection.rs.next()) {
						concertID = DBConnection.rs.getString("ConcertID");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				

				String query2 = String.format("SELECT ConcertName FROM concert JOIN cart ON cart.ConcertID = concert.ConcertID JOIN user ON user.UserID = cart.UserID WHERE user.userID = '%s' AND ConcertName = '%s'", this.userID, connameclick);
				DBConnection.rs = DBConnection.getInstance().executeQuery(query2);
				try {
					if(!DBConnection.rs.next()) {

						ConcertCart cart = new ConcertCart(this.userID, concertID, quantity);
						boolean success = cart.insertConcertCart();
						if(success) {

							alert2.setHeaderText("Message");
							alert2.setContentText("The order has been added to cart");
							alert2.show();
	
							
						}else if(!success) {
							alert.setHeaderText("Error");
							alert.setContentText("Failed to add");
							alert.show();
						}
					}else{

						ConcertCart cart = new ConcertCart(this.userID, concertID, quantity);
						boolean success = cart.updateConcertCart();
						if(success) {

							alert2.setHeaderText("Message");
							alert2.setContentText("Cart has been updated");
							alert2.show();
						}else if(!success) {
							alert.setHeaderText("Error");
							alert.setContentText("Failed to add");
							alert.show();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refreshcart();
			}
		});
		checkoutButton.setOnAction(e -> {
			tabelcart.getItems().clear();
			TransactionData tData = new TransactionData(0, userID, getDate());
			tData.insertTransactionHeaderDatabase();
			Alert alert = new Alert(AlertType.INFORMATION);
			String query = String.format("SELECT TransactionID, cart.ConcertID, Quantity FROM transactionheader JOIN cart ON cart.UserID = transactionheader.UserID WHERE cart.userID = '%s'", this.userID);
			DBConnection.rs = DBConnection.getInstance().executeQuery(query);
			try {
				while(DBConnection.rs.next()) {
					Integer transactionID = DBConnection.rs.getInt("TransactionID");
					String concertID = DBConnection.rs.getString("ConcertID");
					Integer quantity = DBConnection.rs.getInt("Quantity");
					DBConnection.getInstance().executeUpdate(String.format("UPDATE concert SET TicketStock = IF(TicketStock - %d < 0, 0, TicketStock - %d) WHERE ConcertID = '%s'", transactionID, quantity, concertID));
					TransactionInfo tInfo = new TransactionInfo(transactionID, concertID, quantity);
					tInfo.insertTrxInfo();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}	
			ConcertCart cart = new ConcertCart(this.userID, "", 0);
			cart.deleteConcertCart();
			alert.setHeaderText("Message");
			alert.setContentText("You have checked out your order");
			alert.show();
		});
	}
	
	public String getDate() {
		 DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		 LocalDateTime realtime = LocalDateTime.now(); 
		 return date.format(realtime);
	}
	
	public void setWindow(BorderPane borderPane) {
		tabel();
		custbtn();
		borderPane.setCenter(window);
	}
}
