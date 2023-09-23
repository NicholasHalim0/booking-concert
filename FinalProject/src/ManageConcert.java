import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.Window;


public class ManageConcert {
	TableView<TabelConcert> concertTable;
	TableColumn<TabelConcert, String> kolom6;
	Label concertLabel;
	Window window;
	VBox vbox;
	FlowPane fpane;
	Button insertbutton,updatebutton;
	ArrayList<TabelConcert> concertList = new ArrayList<>();
	GridPane gridPane;

	public void init() {
		concertTable = new TableView<>();
		TableColumn<TabelConcert, String> kolom1 = new TableColumn<>("Concert ID");
		kolom1.setCellValueFactory(new PropertyValueFactory<>("concertID"));
		TableColumn<TabelConcert, String> kolom2 = new TableColumn<>("Concert Name");
		kolom2.setCellValueFactory(new PropertyValueFactory<>("concertName"));
		TableColumn<TabelConcert, String> kolom3 = new TableColumn<>("Artist Name");
		kolom3.setCellValueFactory(new PropertyValueFactory<>("artistName"));
		TableColumn<TabelConcert, String> kolom4 = new TableColumn<>("Ticket Price");
		kolom4.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
		TableColumn<TabelConcert, String> kolom5 = new TableColumn<>("Ticket Stock");
		kolom5.setCellValueFactory(new PropertyValueFactory<>("ticketStock"));
		kolom6 = new TableColumn<>("");
		concertTable.getColumns().addAll(kolom1, kolom2, kolom3, kolom4, kolom5, kolom6);
		
		window = new Window("Concert Menu");
		window.setStyle("-fx-font: 14 serif;");
		gridPane = new GridPane();
		fpane = new FlowPane();
		fpane.setHgap(5);
		concertLabel = new Label("Concert List");
		concertTable.setMinWidth(980);
		concertTable.setMaxHeight(150);
		vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		insertbutton = new Button("Insert");
		insertbutton.setMinSize(70, 30);
		updatebutton = new Button("Update");
		updatebutton.setMinSize(70, 30);
		fpane.getChildren().addAll(insertbutton, updatebutton);
		vbox.getChildren().addAll(concertLabel, concertTable, fpane);
		gridPane.add(vbox, 0, 0);
		window.getContentPane().getChildren().add(gridPane);
		
		refreshConcertTable();
	}
	
	
	
	public void ConcertDB() {
		String query = "SELECT ConcertID, ConcertName, ArtistName, TicketPrice, TicketStock FROM concert, artist WHERE concert.ArtistID = artist.ArtistID";
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		
		try {
			concertList.clear();
			while(DBConnection.rs.next()) {
				String concertID = DBConnection.rs.getString("ConcertID");
				String concertName = DBConnection.rs.getString("ConcertName");
				String artistName = DBConnection.rs.getString("ArtistName");
				Integer ticketPrice = DBConnection.rs.getInt("TicketPrice");
				Integer ticketStock = DBConnection.rs.getInt("TicketStock");
				concertList.add(new TabelConcert(concertID, concertName, artistName, ticketPrice, ticketStock));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void refreshConcertTable() {
		ConcertDB();
		ButtonTable();
		ObservableList<TabelConcert> concertObs = FXCollections.observableArrayList(concertList);
		concertTable.setItems(concertObs);
	}
	
	 private void ButtonTable() {
	    	Callback<TableColumn<TabelConcert, String>,TableCell<TabelConcert,String>> cellFactory = (e)-> {
	    		final TableCell<TabelConcert, String>cell = new TableCell<TabelConcert, String>(){
	    			public void updateItem(String item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if(empty) {
	    					setGraphic(null);
	    					setText(null);
	    				}else {
	    					final Button deleteBtn = new Button("Delete");
	    					deleteBtn.setOnAction(e -> {
	    						TabelConcert tab = getTableView().getItems().get(getIndex());
	    						ConcertDB concert = new ConcertDB(tab.getConcertID(), 0, "", 0, 0);
	    						concert.deleteConcertDatabase();
	    						refreshConcertTable();
	    						
	    						Alert alert = new Alert(AlertType.INFORMATION);
	    						alert.setHeaderText("Message");
	    						alert.setTitle("Message");
	    						alert.setContentText("The Concert has been deleted");
	    						alert.show();
	    					});
	    					setGraphic(deleteBtn);
	    					setText(null);
	    				}
	    			}
	    			
	    		};
	    		
	    		return cell;
	    	};
	    	kolom6.setCellFactory(cellFactory);
	    }
	
	public void button(GridPane gridPane) {
		UpdateConcert updt = new UpdateConcert();
		InsertConcert ins = new InsertConcert();
			updatebutton.setOnAction(e -> {
				updt.closePane(gridPane);
				refreshConcertTable();
				if(!gridPane.getChildren().contains(updt.fpane)) {
					updt.setPane(gridPane);
				}
				ins.closePane(gridPane);
			});  
			insertbutton.setOnAction(e -> {
				ins.closePane(gridPane);
				refreshConcertTable();
				
				if(!gridPane.getChildren().contains(ins.fpane)) {
					ins.setPane(gridPane);
				}
				updt.closePane(gridPane);
			});
		
	}
	
	public void setWindow(BorderPane borderPane) {
		init();
		borderPane.setCenter(window);
		button(gridPane);
	}
	
	


}
