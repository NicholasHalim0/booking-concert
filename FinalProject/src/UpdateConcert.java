import java.sql.SQLException;

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

public class UpdateConcert {
	
	FlowPane fpane;
	GridPane gpane ,gpane2;
	TextField concertNameField;
	TextField ticketPriceField ;
	Button uploadbutton;
	Label ticketStockLbl = new Label("Ticket Stock");
	Spinner<Integer> ticketStockSpin = new Spinner<>(1, 10000, 1);
	ComboBox<String> concertIDBox;
	ComboBox<String> artistNameBox;

	public void init() {
		fpane = new FlowPane();
		gpane = new GridPane();
		gpane2 = new GridPane();
		concertNameField = new TextField();
		ticketPriceField = new TextField();
		ticketStockLbl = new Label("Ticket Stock");
		uploadbutton = new Button("Update");
		concertIDBox = new ComboBox<>();
		artistNameBox = new ComboBox<>();
		gpane.add(concertIDBox, 0, 0);
		gpane.add(concertNameField, 0, 1);
		gpane.add(ticketPriceField, 1, 1);
		gpane.add(artistNameBox, 0, 2);
		gpane.add(ticketStockLbl, 1, 2);
		gpane.add(ticketStockSpin, 1, 3);
		gpane2.add(uploadbutton, 0, 0);
		fpane.getChildren().addAll(gpane, gpane2);
		concertcombo();
		artistcombo();
		
		concertNameField.setPromptText("Concert Name");
		ticketPriceField.setPromptText("Ticket Price");
		ticketStockSpin.setMaxWidth(150);
		ticketPriceField.setMaxWidth(150);
		concertNameField.setMaxWidth(150);
		concertIDBox.setPromptText("Concert ID");
		artistNameBox.setPromptText("Artist's Name");
		gpane.setVgap(5);
		gpane.setHgap(5);
		gpane.setPadding(new Insets(10));
		gpane.setStyle("-fx-border-color:#5f727b;");
		fpane.setHgap(10);
		fpane.setPadding(new Insets(10));
	}
	
	public void concertcombo() {
		String query = "SELECT ConcertID FROM concert";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		
		try {
			concertIDBox.getItems().clear();
			while(DBConnection.rs.next()) {
				concertIDBox.getItems().add(DBConnection.rs.getString("ConcertID"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void artistcombo() {
		String query = "SELECT ArtistName FROM artist";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		try {
			artistNameBox.getItems().clear();
			while(DBConnection.rs.next()) {
				artistNameBox.getItems().add(DBConnection.rs.getString("ArtistName"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	public void button() {
		uploadbutton.setOnAction(e -> {
			String concertName = concertNameField.getText();
			Integer ticketStock = ticketStockSpin.getValue();
			String concertID = concertIDBox.getValue();
			String artistName = artistNameBox.getValue();
			int artistID = 0;
			Alert alert = new Alert(AlertType.ERROR);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			
			if(concertName.isBlank() || concertName.isBlank() || ticketPriceField.getText().isBlank() || concertID==null || artistName==null) {
				alert.setHeaderText("Error");
				alert.setContentText("Please fill the blank field");
				alert.show();
			}else if(concertName.length()<5 || concertName.length()>25) {
				alert.setHeaderText("Error");
				alert.setContentText("Concert Name has to be between 5 and 25");
				alert.show();
			}else if(!ticketPriceField.getText().matches("^[0-9]+$")) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Price must be in number");
				alert.show();
			}else if(Integer.parseInt(ticketPriceField.getText()) <= 100000) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Price must be more than 100000");
				alert.show();
			}else if(ticketStock <= 0) {
				alert.setHeaderText("Error");
				alert.setContentText("Ticket Stock must be more than 0");
				alert.show();
			}else {
				String query = String.format("SELECT ArtistID FROM artist WHERE ArtistName = '%s'", artistName);
				DBConnection.rs = DBConnection.getInstance().executeQuery(query);
				try {
					while(DBConnection.rs.next()) {
					artistID = DBConnection.rs.getInt("ArtistID");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int ticketPrice = Integer.parseInt(ticketPriceField.getText());
				ConcertDB conDB = new ConcertDB(concertID, artistID, concertName, ticketPrice, ticketStock);
				boolean success = conDB.updateConcertDatabase();
				if(success) {
					alert2.setHeaderText("Message");
					alert2.setContentText("The Concert has been updated");
					alert2.show();
				}else if(!success){
					alert.setHeaderText("Error");
					alert.setContentText("Failed to Register");
					alert.show();
				}	
				
				concertNameField.clear();
				ticketPriceField.clear();
				ticketStockSpin.getValueFactory().setValue(1);

			}
		});
	}
	
	public void setPane(GridPane gridPane) {
		init();
		button();
		if (!gridPane.getChildren().contains(fpane)) {
		    gridPane.add(fpane, 0, 1);
		}
	}
	
	public void closePane(GridPane gridPane) {
		if (gridPane.getChildren().contains(fpane)) {
		    gridPane.getChildren().remove(fpane);
		    fpane.getChildren().clear();
		    gpane.getChildren().clear();
		    gpane2.getChildren().clear();
		}
	}


}
