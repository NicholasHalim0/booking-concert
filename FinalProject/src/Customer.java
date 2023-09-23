import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;

public class Customer {
	
	Menu manage, logout;
	BorderPane borderpane;
	Scene scene;
	MenuBar menubar;
	MenuItem buy ,history;
	Window window;
	
	public void init() {
		borderpane = new BorderPane();
		scene = new Scene(borderpane, 1000, 600);
		manage = new Menu("Transaction");
		logout = new Menu("Logout");
		menubar = new MenuBar();
		menubar.setUseSystemMenuBar(true);
		menubar.getMenus().addAll(manage, logout);
		borderpane.setTop(menubar);
		buy = new MenuItem("Buy Tickets");
		history = new MenuItem("Transaction History") ;
		manage.getItems().addAll(buy, history);
	}
	public void menu(Stage stage, int userID) {
		buy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TabelBuy cs = new TabelBuy(userID);
				cs.setWindow(borderpane);
			}
		});
		history.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TransactionHistory ct = new TransactionHistory(userID);
				ct.setWindow(borderpane);
			}
		});
		onAction(logout);
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main main = new Main();
				try {
					main.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}

	private void onAction(Menu logout) {
		// TODO Auto-generated method stub
		final MenuItem menuItem = new MenuItem();
	    logout.getItems().add(menuItem);
	    logout.addEventHandler(Menu.ON_SHOWN, event -> logout.fire());
	}

	public void setStage(Stage primaryStage, Integer userID) {
		// TODO Auto-generated method stub
		init();
		menu(primaryStage, userID);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

