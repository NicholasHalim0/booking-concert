import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.Window;

public class ManageArtist {
	TableView<TableArtist> artistTable;
	
	
	Label labelconcert, labelinsert,labelupdate;
	
	Window window;
	VBox vbox;

	Button insertButton, updateButton;
	TextField NameTextField ,NameTextField2;

	ComboBox<String> artistNameBox = new ComboBox<>();
	
	ArrayList<TableArtist> artistList = new ArrayList<>();

	GridPane gpane,gpane2,gpane3;
	
	FlowPane flowPane;
	TableColumn<TableArtist, String> kolom3;
	
	public void init() {
		
		artistTable = new TableView<>();
		TableColumn<TableArtist, String> kolom1 = new TableColumn<>("Artist ID");
		kolom1.setCellValueFactory(new PropertyValueFactory<>("artistID"));
		TableColumn<TableArtist, String> kolom2 = new TableColumn<>("Artist Name");
		kolom2.setCellValueFactory(new PropertyValueFactory<>("artistName"));
		kolom3 = new TableColumn<>("Action");
		artistTable.getColumns().addAll(kolom1, kolom2, kolom3);
		
		labelconcert = new Label("Available Artist");
		window = new Window("Artist Menu");
		window.setStyle("-fx-font: 14 serif;");
		vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		labelinsert = new Label("Insert a new Artist here !");
		labelupdate = new Label("Choose which one to update");
		insertButton = new Button("Insert");
		insertButton.setMinSize(100, 30);
		updateButton = new Button("Update");
		updateButton.setMinSize(100, 30);
		NameTextField = new TextField();
		NameTextField.setPromptText("Artist Name");
		NameTextField.setMaxWidth(150);
		NameTextField2 = new TextField();
		NameTextField2.setPromptText("Artist's New Name");
		NameTextField2.setMaxWidth(150);
		artistNameBox = new ComboBox<>();
		artistNameBox.setPromptText("Artist Name");
		artistNameBox.setPrefSize(130, 30);
		artistList = new ArrayList<>();
		gpane = new GridPane();
		gpane2 = new GridPane();
		gpane2.setVgap(5);
		gpane2.setHgap(15);
		gpane2.setPadding(new Insets(10));
		gpane2.setStyle("-fx-border-color:#5D5D5D;");
		gpane3 = new GridPane();
		flowPane = new FlowPane();
		flowPane.setHgap(20);
		flowPane.setPadding(new Insets(10));
		artistTable.setMinWidth(980);
		artistTable.setMaxHeight(300);
		vbox.getChildren().addAll(labelconcert, artistTable);
		gpane.add(vbox, 0, 0);
		gpane.add(flowPane ,0,1);
		window.getContentPane().getChildren().add(gpane);
	
		gpane2.add(labelinsert, 0, 0);
		gpane2.add(labelupdate, 1, 0);
		gpane2.add(NameTextField, 0, 1);
		gpane2.add(artistNameBox, 1, 1);
		gpane2.add(insertButton, 0, 2);
		gpane2.add(NameTextField2, 1, 2);
		gpane2.add(updateButton, 1, 3);
		flowPane.getChildren().addAll(gpane2, gpane3);
		artistTable();
		refreshArtistTable();
		
	}
	
	
	
	public void artistTable() {
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
	
	public void artistdata() {
		String query = "SELECT ArtistID, ArtistName FROM artist";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		try {
			artistList.clear();
			while(DBConnection.rs.next()) {
				Integer artistID = DBConnection.rs.getInt("ArtistID");
				String artistName = DBConnection.rs.getString("ArtistName");
				artistList.add(new TableArtist(artistID, artistName));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshArtistTable() {
		artistdata();
		ButtonTable();
		ObservableList<TableArtist> artistObs = FXCollections.observableArrayList(artistList);
		artistTable.setItems(artistObs);
	}
	
	 private void ButtonTable() {
	   Callback<TableColumn<TableArtist, String>,TableCell<TableArtist,String>> cellFactory = (e)-> {
	   final TableCell<TableArtist, String>cell = new TableCell<TableArtist, String>(){
	    			public void updateItem(String item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if(empty) {
	    					setGraphic(null);
	    					setText(null);
	    				}else {
	    					final Button deletebutton = new Button("Delete");
	    					deletebutton.setOnAction(e -> {
	    						TableArtist arts = getTableView().getItems().get(getIndex());		
	    						TableArtist artist = new TableArtist(arts.getArtistID(), "");
	    						artist.DBDeleteArtist();
	    						refreshArtistTable();
	    						artistTable(); 
	    						Alert alert = new Alert(AlertType.INFORMATION);
	    						alert.setHeaderText("Message");
	    						alert.setTitle("Message");
	    						alert.setContentText("The artist has been deleted");
	    						alert.show();	
	    					});
	    					setGraphic(deletebutton);
	    					setText(null);
	    				}
	    			}
	    			
	    		};
	    		
	    		return cell;
	    	};
	    	kolom3.setCellFactory(cellFactory);
	    	
	    }
	
	public void button() {
		insertButton.setOnAction(e -> {
			String artistName = NameTextField.getText();
			Alert alert = new Alert(AlertType.ERROR);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			if(artistName.isBlank()) {
				alert.setHeaderText("Error");
				alert.setContentText("Please fill the blank field");
				alert.show();
			}else if(artistName.length()<3 || artistName.length()>15) {
				alert.setHeaderText("Error");
				alert.setContentText("Artist Name has to be between 3 and 15");
				alert.show();
			}else {
				TableArtist tabarts = new TableArtist(0, artistName);
				boolean success = tabarts.DBInsertArtist();
				if(success) {
					alert2.setHeaderText("Message");
					alert2.setContentText("The artist has been added");
					alert2.show();
				}else if(!success){
					alert.setHeaderText("Error");
					alert.setContentText("Failed to add");
					alert.show();
				}	
				NameTextField.clear();
				artistTable(); 
				refreshArtistTable();
			}
		});
		
		updateButton.setOnAction(e -> {
			String artistNewName = NameTextField2.getText();
			String artistName = artistNameBox.getValue();
			int artistID = 0;
			Alert alert = new Alert(AlertType.ERROR);
			Alert alert2 = new Alert(AlertType.INFORMATION);
			if(artistNewName.isBlank()) {
				alert.setHeaderText("Error");
				alert.setContentText("Please fill the blank field");
				alert.show();
			}else if(artistNewName.length()<3 || artistNewName.length()>15) {
				alert.setHeaderText("Error");
				alert.setContentText("Artist Name has to be between 3 and 15");
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
				TableArtist staffA2 = new TableArtist(artistID, artistNewName);
				boolean success = staffA2.DBUpdateArtist();
				if(success) {
					alert2.setHeaderText("Message");
					alert2.setContentText("The artist has been updated");
					alert2.show();
				}else if(!success){
					alert.setHeaderText("Error");
					alert.setContentText("Failed to update");
					alert.show();
				}
				NameTextField2.clear();
				artistTable(); 
				refreshArtistTable();
			}
		});
	}
	
	public void setWindow(BorderPane borderPane) {
		init();
		button();
		borderPane.setCenter(window);
	}
}
