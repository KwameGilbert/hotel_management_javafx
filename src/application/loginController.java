package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginController {

	@FXML
	private Button close;

	@FXML
	private Button login_btn;

	@FXML
	private AnchorPane main_form;

	@FXML
	private PasswordField password;

	@FXML
	private StackPane stack_form;

	@FXML
	private TextField username;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	
	public void login() {

		String user = username.getText();
		String pass = password.getText();

		String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

		try {
			Alert alert;

			// Check if username or password is empty
			if (user.isEmpty() || pass.isEmpty()) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please provide both username and password");
				alert.showAndWait();

			} else {
				connect = Database.connectDb();
				prepare = connect.prepareStatement(sql);
				prepare.setString(1, user);
				prepare.setString(2, pass);

				result = prepare.executeQuery();

				if (result.next()) {
					// alert the user that they have logged in successfully
					alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Information Message");
					alert.setContentText("Successfully Logged In");
					alert.showAndWait();

					login_btn.getScene().getWindow().hide();
					// load the dashboard after hiding the login scene
					try {
						Parent rootDash = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
						Stage stage = new Stage();
						Scene scene = new Scene(rootDash);

						stage.initStyle(StageStyle.TRANSPARENT);

						stage.setScene(scene);
						stage.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// hide the login window if the credentials are correct
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error Message");
					alert.setContentText("Wrong Username/Password");
					alert.showAndWait();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
				if (prepare != null)
					prepare.close();
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void exit() {
		System.exit(0);
	}

	public void initialize(URL url, ResourceBundle rb) {
		// TODO

	}

}
