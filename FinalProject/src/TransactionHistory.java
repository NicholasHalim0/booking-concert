import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.Window;

public class TransactionHistory {

	Window window;
	VBox vbox;
	Label historylabel,detaillabel;
	
	TableView<TabelHistory> transactionHistoryTable;
	TableColumn<TabelHistory, String> column1;
	TableColumn<TabelHistory, String> column2;
	TableColumn<TabelHistory, String> column3;
	TableColumn<TabelHistory, String> column4;
	
	TableView<TabelDetail> transactionDetailTable;
	TableColumn<TabelDetail, String> kolom1;
	TableColumn<TabelDetail, String> kolom2;
	TableColumn<TabelDetail, String> kolom3;
	TableColumn<TabelDetail, String> kolom4;
	
	ArrayList<TabelHistory> transactionHistoryList = new ArrayList<>();
	ArrayList<TabelDetail> transactionDetailList = new ArrayList<>();
	int userID;
	public TransactionHistory(int userID) {
		this.userID = userID;
	}
	
	public void init() {
		
		transactionHistoryTable = new TableView<>();
		column1 = new TableColumn<>("Transaction ID");
		column1.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		column2 = new TableColumn<>("Transaction Date");
		column2.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		column3 = new TableColumn<>("Ticket Bought");
		column3.setCellValueFactory(new PropertyValueFactory<>("ticketBought"));
		column4 = new TableColumn<>("Action");
		transactionHistoryTable.getColumns().addAll(column1, column2, column3, column4);
		refreshHistoryTable();
		
		transactionDetailTable = new TableView<>();
		kolom1 = new TableColumn<>("Transaction ID");
		kolom1.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		kolom2 = new TableColumn<>("Concert Name");
		kolom2.setCellValueFactory(new PropertyValueFactory<>("concertName"));
		kolom3 = new TableColumn<>("Artist Name");
		kolom3.setCellValueFactory(new PropertyValueFactory<>("artistName"));
		kolom4 = new TableColumn<>("Quantity");
		kolom4.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		transactionDetailTable.getColumns().addAll(kolom1, kolom2, kolom3, kolom4);
		
		
		window = new Window("Transaction History");
		window.setStyle("-fx-font: 14 serif;");
		vbox = new VBox();
		historylabel = new Label("Transaction History");
		detaillabel = new Label("Transaction Detail");
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10));
		transactionHistoryTable.resizeColumn(column1, 50);
		transactionDetailTable.resizeColumn(kolom1, 50);
		kolom2.setPrefWidth(150);
		kolom3.setPrefWidth(150);
		vbox.getChildren().addAll(historylabel, transactionHistoryTable, detaillabel, transactionDetailTable);
		window.getContentPane().getChildren().add(vbox);
		
	}
	
	public void DetailDB(int tid) {
		String query = String.format("SELECT th.TransactionID, ConcertName, ArtistName, Quantity FROM transactionheader th JOIN transactiondetail td ON th.TransactionID = td.TransactionID JOIN concert ON td.ConcertID = concert.ConcertID JOIN artist ON artist.ArtistID = concert.ArtistID WHERE UserID = '%d' AND td.TransactionID = '%d'", this.userID, tid);
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		
		try {
			transactionDetailList.clear();
			while(DBConnection.rs.next()) {
				Integer transactionID = DBConnection.rs.getInt("TransactionID");
				String concertName = DBConnection.rs.getString("ConcertName");
				String artistName = DBConnection.rs.getString("ArtistName");
				Integer quantity = DBConnection.rs.getInt("Quantity");
				transactionDetailList.add(new TabelDetail(transactionID, concertName, artistName, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	public void refreshDetailTable(int transactionID) {
		DetailDB(transactionID);
		ObservableList<TabelDetail> detailObs = FXCollections.observableArrayList(transactionDetailList);
		transactionDetailTable.setItems(detailObs);
	}
	
	public void HistoryDB() {
		String query = String.format("SELECT th.TransactionID, TransactionDate, SUM(Quantity) AS TotalBought FROM transactionheader th JOIN transactiondetail td ON td.TransactionID = th.TransactionID WHERE UserID = '%s' GROUP BY TransactionID", this.userID);
		DBConnection.rs = DBConnection.getInstance().executeQuery(query);
		
		try {
			transactionHistoryList.clear();
			while(DBConnection.rs.next()) {
				Integer transactionID = DBConnection.rs.getInt("TransactionID");
				String transactionDate = DBConnection.rs.getString("TransactionDate");
				Integer totalBought = DBConnection.rs.getInt("TotalBought");
				transactionHistoryList.add(new TabelHistory(transactionID, transactionDate, totalBought));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshHistoryTable() {
		HistoryDB();
		addButtonToTable();
		ObservableList<TabelHistory> historyObs = FXCollections.observableArrayList(transactionHistoryList);
		transactionHistoryTable.setItems(historyObs);
	}
	
    private void addButtonToTable() {
    	Callback<TableColumn<TabelHistory, String>,TableCell<TabelHistory,String>> cellFactory = (e)-> {
			
    		final TableCell<TabelHistory, String>cell = new TableCell<TabelHistory, String>(){
    			
    			public void updateItem(String item, boolean empty) {
    				super.updateItem(item, empty);
    				if(empty) {
    					setGraphic(null);
    					setText(null);
    				}else {
    					final Button detailbutton = new Button("Detail");
    					detailbutton.setOnAction(e -> {
    						TabelHistory tab = getTableView().getItems().get(getIndex());
    						refreshDetailTable(tab.getTransactionID());
    						
    					});
    					setGraphic(detailbutton);
    					setText(null);
    				}
    			}
    			
    		};
    		
    		return cell;
    	};
    	column4.setCellFactory(cellFactory);
    	
    }
    
    
	

	public void setWindow(BorderPane borderPane) {
		init();
		borderPane.setCenter(window);
	}
}
