import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.scene.Scene;
	import javafx.scene.control.Menu;
	import javafx.scene.control.MenuBar;
	import javafx.scene.control.MenuItem;
	import javafx.scene.layout.BorderPane;
	import javafx.stage.Stage;
	
public class Admin {
	BorderPane bpane;
	Scene scene;
	MenuBar barmenu;
	MenuItem menuconcert, menuartist;
	Menu manage, logout;

		public void init() {
			bpane = new BorderPane();
			scene = new Scene(bpane, 1000, 600);

			barmenu = new MenuBar();
			manage = new Menu("Manage");
			logout = new Menu("Logout");
			
			menuconcert = new MenuItem("Manage Concert");
			menuartist = new MenuItem("Manage Artist");
			barmenu.setUseSystemMenuBar(true);
			barmenu.getMenus().addAll(manage, logout);
			bpane.setTop(barmenu);
			manage.getItems().addAll(menuconcert, menuartist);
		}
		
		public void button(Stage stage) {
			menuconcert.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ManageConcert concert = new ManageConcert();
					concert.setWindow(bpane);
					
				}
			});
			menuartist.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ManageArtist artist = new ManageArtist();
					artist.setWindow(bpane);
				}
			});
			logoutinit(logout);
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
		
		public void setStage(Stage Primarystage) {
			init();
			button(Primarystage);
			Primarystage.setScene(scene);
			Primarystage.show();
		}

		public static void logoutinit(Menu menu){
		    final MenuItem mItem = new MenuItem();
		    menu.getItems().add(mItem);
		    menu.addEventHandler(Menu.ON_SHOWN, event -> menu.fire());
		}

	}


