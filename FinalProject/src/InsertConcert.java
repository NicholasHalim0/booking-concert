import java.sql.SQLException;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;



public class InsertConcert {
	FlowPane fpane;
	GridPane gpane, gpane2;
	TextField ConName, TicketPrice;
	Button insertButton;
	Label stock;
	Spinner<Integer> stockSpinner;
	
	ComboBox<String> artistComboBox;
	
	public void init() {
		fpane = new FlowPane();
		gpane = new GridPane();
		gpane2 = new GridPane();
		ConName = new TextField();
		TicketPrice = new TextField();
		insertButton = new Button("Insert");
		stock = new Label("Ticket Stock");
		stockSpinner = new Spinner<>(1, 10000, 1);
		artistComboBox = new ComboBox<>();
		String query = "SELECT ArtistName FROM artist";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		try {
			artistComboBox.getItems().clear();
			while(DBConnection.rs.next()) {
				artistComboBox.getItems().add(DBConnection.rs.getString("ArtistName"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gpane.add(ConName, 0, 0);
		gpane.add(TicketPrice, 1, 0);
		gpane.add(artistComboBox, 0, 1);
		gpane.add(stock, 1, 1);
		gpane.add(stockSpinner, 1, 2);

		gpane2.add(insertButton, 0, 0);

		fpane.getChildren().addAll(gpane, gpane2);
		ConName.setPromptText("Insert Concert Name");
		TicketPrice.setPromptText("Insert Ticket Price");
		stockSpinner.setMaxSize(200,30);
		TicketPrice.setMaxSize(200,30);
		ConName.setMaxSize(200,30);

		artistComboBox.setPromptText("Artist's Name");
		artistComboBox.setMaxSize(200, 30);
		gpane.setVgap(10);
		gpane.setHgap(15);
		gpane.setPadding(new Insets(20));
		gpane.setStyle("-fx-border-color:#898989;");
		fpane.setHgap(20);
		fpane.setPadding(new Insets(20));
		insertButton.setMinSize(100, 40);
	}
	
	
	
	
	public void btn() {
		insertButton.setOnAction(e -> {

			String conname = ConName.getText();
			Integer stocktiket = stockSpinner.getValue();
			String nameartist = artistComboBox.getValue();
			int artistID = 0;
			Alert alert = new Alert(AlertType.ERROR);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			
			if(conname.isBlank() || TicketPrice.getText().isBlank() || nameartist==null || conname.isBlank()) {
				alert.setHeaderText("Error");
				alert.setContentText("Please fill the blank field");
				alert.show();
			}else if(conname.length()<5 || conname.length()>25) {
				alert.setHeaderText("Error");
				alert.setContentText("Concert Name has to be between 5 and 25");
				alert.show();
			}else if(!TicketPrice.getText().matches("^[0-9]+$")) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Price must be in number");
				alert.show();
			}else if(Integer.parseInt(TicketPrice.getText()) <= 100000) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Price must be more than 100000");
				alert.show();
			}else if(stocktiket <= 0) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Stock must be more than 0");
				alert.show();
			}else {
				String concertID = id.generateID();

				String query = String.format("SELECT ArtistID FROM artist WHERE ArtistName = '%s'", nameartist);
				DBConnection.rs = DBConnection.getInstance().executeQuery(query);
				try {
					while(DBConnection.rs.next()) {
					artistID = DBConnection.rs.getInt("ArtistID");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int ticketPrice = Integer.parseInt(TicketPrice.getText());
				ConcertDB conDB = new ConcertDB(concertID, artistID, conname, ticketPrice, stocktiket);
				boolean success = conDB.insertConcertDatabase();
				if(success) {
					alert2.setHeaderText("Message");
					alert2.setContentText("The Concert has been updated");
					alert2.show();
				}else if(!success){
					alert.setHeaderText("Error");
					alert.setContentText("Failed to updated");
					alert.show();
				}	

				ConName.clear();
				TicketPrice.clear();
				stockSpinner.getValueFactory().setValue(1);

			}
			
		});
	}
	
	public void setPane(GridPane gpane) {
		init();
		btn();
		if (!gpane.getChildren().contains(fpane)) {
		    gpane.add(fpane, 0, 1);
		}
	}
	
	public void closePane(GridPane gpane) {
		if (gpane.getChildren().contains(fpane)) {
		    gpane.getChildren().remove(fpane);
		    fpane.getChildren().clear();
		    gpane.getChildren().clear();
		    gpane2.getChildren().clear();
		}
	}
}
