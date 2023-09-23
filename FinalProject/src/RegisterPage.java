
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RegisterPage extends BorderPane{

	GridPane registerPane;
	Scene scene;
	Label lblRegister, lblUsername, lblPassword, lblConfirmPassword, lblEmail, lblGender, lblLogin;
	
	TextField tfUsername, tfEmail;
	
	PasswordField pfPassword, pfConfirmPassword;
	
	RadioButton rbMale, rbFemale;
	
	ToggleGroup tgGender;
	
	FlowPane genderPane;
	
	String radiobuttonlabel;
	
	Button btnRegister;
	
	public static class MyConnection {

	    public static Connection getConnection(){
	     
	        Connection con = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/finalproject", "root", "");
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        
	        return con;
	    }
	    
	}    
	
	public void RegisterPage() {
		
		VBox formPane = new VBox();
		scene = new Scene(formPane, 1000, 600);
		Label titleLabel = new Label("Register");
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
		lblConfirmPassword = new Label("Confirm Password: ");
		lblConfirmPassword.setMaxWidth(300);
		pfConfirmPassword = new PasswordField();
		pfConfirmPassword.setMaxWidth(300);
		pfConfirmPassword.setPromptText("Re Enter Password");
		
		lblEmail = new Label("Email: ");
		lblEmail.setMaxWidth(300);
		tfEmail = new TextField();
		tfEmail.setMaxWidth(300);
		tfEmail.setPromptText("Enter Email");
		
		lblGender = new Label("Gender: ");
		rbMale = new RadioButton("Male");
		rbFemale = new RadioButton("Female");
		genderPane = new FlowPane(rbMale, rbFemale);
		tgGender = new ToggleGroup();
		tgGender.getToggles().addAll(rbMale,rbFemale);
		genderPane.setAlignment(Pos.CENTER);
		lblLogin = new Label ("Already have an account ?\nLogin here!");
		lblLogin.setTextAlignment(TextAlignment.CENTER);
		btnRegister = new Button("Register");
		formPane.getChildren().addAll(titleLabel,nameLabel, tfUsername, paswordLabel, pfPassword, lblConfirmPassword, pfConfirmPassword, lblEmail, tfEmail, lblGender, genderPane, btnRegister, lblLogin );
		formPane.setAlignment(Pos.CENTER);
		lblLogin.setAlignment(Pos.CENTER);
		formPane.setSpacing(15);
		btnRegister.setPrefSize(100, 30);
		
	}
	
	public boolean checkUsername(String username)
    {
        PreparedStatement ps;
        ResultSet rs;
        boolean user = false;
        String query = "SELECT * FROM user WHERE Username =?";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                user = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterPage.class.getName()).log(Level.SEVERE, null, ex);
        }
         return user;
    }

	
	public void btn (Stage regis) {
		btnRegister.setOnAction(e -> {
			PreparedStatement pst;
			int rs2;
			
			String names = tfUsername.getText();
			String pass = pfPassword.getText();
			String cpass = pfConfirmPassword.getText();
			String roles = "customer";
			String email = tfEmail.getText();
			Alert alert = new Alert (AlertType.ERROR);
			Alert alurt = new Alert (AlertType.INFORMATION);
			String gender = "";
			if(rbMale.isSelected()) {
				gender = rbMale.getText();
			}else if(rbFemale.isSelected()){
				gender = rbFemale.getText();
			}
			try {
				
				if(names.equals("") || pass.equals("") || email.equals("") || gender.equals("")) {
					alert.setHeaderText("Error");
					alert.setContentText("Please fill the blank field");
					alert.show();
				}
				else if(!pass.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).*$")) {
					alert.setHeaderText("Error");
					alert.setContentText("Password must contains number, upper case, and lower case letter");
					alert.show();
				}
				else if(!pass.equals(cpass)) {
					alert.setHeaderText("Error");
					alert.setContentText("Password must be the same as the Confirm Password");
					alert.show();
				}
				else if(!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
					alert.setHeaderText("Error");
					alert.setContentText("Please input valid email");
					alert.show();
				}
				else if(checkUsername(names)){
					alert.setHeaderText("Error");
					alert.setContentText("Username has already been taken!");
					alert.show();
				}
				else {
					pst = MyConnection.getConnection().prepareStatement("INSERT INTO user (Username, UserPassword, UserRole, UserEmail, UserGender) VALUES (?,?,?,?,?)");
					
					pst.setString(1, names);
					pst.setString(2, pass);
					pst.setString(3, roles);
					pst.setString(4, email);
					pst.setString(5, gender);
					
					
					rs2 = pst.executeUpdate();
					
					alurt.setHeaderText("Message");
					alurt.setContentText("You have been successfully registered!");
					alurt.show();
					
					Main login = new Main();
					try {
						login.start(regis);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		lblLogin.setOnMouseClicked(e -> {
			Main login = new Main();
			try {
				login.start(regis);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
	
	public void setStage(Stage stage) {
		RegisterPage();
		btn(stage);
		stage.setScene(scene);
		stage.show();
		

	}	
	
	
}
