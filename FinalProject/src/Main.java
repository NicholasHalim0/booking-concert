import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	StackPane sPane;
	Scene scene;
	TextField tfUsername;
	PasswordField pfPassword;
	Button loginButton;
	Label lblRegister;
	public void login() {
		
		VBox formPane = new VBox();
		scene = new Scene(formPane, 1000, 600);
		Label titleLabel = new Label("LOGIN");
		titleLabel.setFont(new Font("Arial", 30));
		titleLabel.setMaxWidth(Double.MAX_VALUE);
		titleLabel.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label("Username");
		nameLabel.setMaxWidth(300);
		tfUsername = new TextField();
		tfUsername.setMaxWidth(300);
		tfUsername.setPromptText("Enter Username");
		
		Label paswordLabel = new Label("Password");
		paswordLabel.setMaxWidth(300);
		pfPassword = new PasswordField();
		pfPassword.setMaxWidth(300);
		pfPassword.setPromptText("Enter Password");
		
		
		loginButton = new Button("Login");
		
		lblRegister = new Label ("Register here!");
		formPane.getChildren().addAll(titleLabel,nameLabel, tfUsername, paswordLabel, pfPassword, loginButton, lblRegister );
		formPane.setAlignment(Pos.CENTER);
		formPane.setSpacing(15);
		loginButton.setPrefSize(100, 30);
	}
		
		public void btn(Stage primaryStage) {
		loginButton.setOnAction(e -> {
			Connection con;
			PreparedStatement pst;
			ResultSet rs;
			String names = tfUsername.getText();
			String pass = pfPassword.getText();
			Alert alurt = new Alert (AlertType.INFORMATION);
			Alert alert = new Alert (AlertType.ERROR);
			if (names.equals("") && pass.equals("")) {
				
				
				alert.setHeaderText("Error");
				alert.setContentText("Username or Password cannot Blank");
				alert.show();
				
			}
			else {
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
					
					pst = con.prepareStatement("select * from user where Username=? and UserPassword=?");
					
					pst.setString(1, names);
					pst.setString(2, pass);
					
					rs = pst.executeQuery();
					if (rs.next()) {
						alurt.setTitle("Message");
						alurt.setHeaderText("Message");
						alurt.setContentText("Login Successfully!!");
						Integer userID = rs.getInt("UserID");
						if(rs.getString("UserRole").equalsIgnoreCase("staff")) {
							Admin admin = new Admin();
							admin.setStage(primaryStage);
						}else if(rs.getString("UserRole").equalsIgnoreCase("customer")) {
							Customer customer = new Customer();
							customer.setStage(primaryStage, userID);
							
						}
						}
					else {
						alert.setHeaderText("Failed");
						alert.setContentText("Login Failed");
						alert.show();
						tfUsername.setText("");
						pfPassword.setText("");
						tfUsername.requestFocus();
					}
						}catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
			});
		lblRegister.setOnMouseClicked(e -> {
			RegisterPage regist = new RegisterPage();
			regist.setStage(primaryStage);
		});
		
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		login();
		btn(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
