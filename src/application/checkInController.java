package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import database.Database;
import essentials.essentials;

public class checkInController implements Initializable {

	@FXML
	private DatePicker checkInDate;

	@FXML
	private AnchorPane checkInForm;

	@FXML
	private DatePicker checkOutDate;

	@FXML
	private Label customerNumberLabel;

	@FXML
	private TextField emailAddress;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField phoneNumber;

	@FXML
	private Label totalLabel;

	@FXML
	private Label totalDaysLabel;

	@FXML
	private ComboBox<?> roomNumberBox;

	@FXML
	private ComboBox<?> roomTypeBox;

	@FXML
	private Button reset_btn;
	
	// Essentials Variables
	essentials essential = new essentials();

	// Database Variables
	private Connection connect;
	private ResultSet result;
	private PreparedStatement prepare;
	private Statement statement;

	public void customerCheckIn() {
		String insertCustomerData = "INSERT INTO customer(customerID, roomType, roomNumber, firstName, lastName, phoneNumber, email, checkInDate, checkOutDate, total) VALUE(?,?,?,?,?,?,?,?,?,?)";
		String customerReceipt = "INSERT INTO customer_receipt(customerID, date, total) VALUES(?,?,?)";

		try {
			// Get information of customer typed in the check-in form
			String customerNumber = customerNumberLabel.getText();
			String roomT = (String) roomTypeBox.getSelectionModel().getSelectedItem();
			String roomN = String.valueOf((int) roomNumberBox.getSelectionModel().getSelectedItem());
			String firstN = firstName.getText();
			String lastN = lastName.getText();
			String phoneNum = phoneNumber.getText();
			String email = emailAddress.getText();
			String checkIn = String.valueOf(checkInDate.getValue());
			String checkOut = String.valueOf(checkOutDate.getValue());
			// String totalD = totalDaysLabel.getText();
			String totalP = totalPrice();

			if (customerNumber == null || roomT == null || roomN == null || firstN == null || lastN == null
					|| phoneNum == null || email == null || checkIn == null || checkOut == null) {
				essential.showAlert(AlertType.ERROR, "Error Message", "Please fill out all customer details");
			} else {
				// CHECK IN CONFIRMATION ALERT
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Are you sure?");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to check-in " + firstN + " " + lastN + " for " + roomT
						+ " room " + roomN + " ?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.get().equals(ButtonType.OK)) {
					connect = Database.connectDb();
					// Prepare statement for customer
					prepare = connect.prepareStatement(insertCustomerData);
					prepare.setString(1, customerNumber);
					prepare.setString(2, roomT);
					prepare.setString(3, roomN);
					prepare.setString(4, firstN);
					prepare.setString(5, lastN);
					prepare.setString(6, phoneNum);
					prepare.setString(7, email);
					prepare.setString(8, checkIn);
					prepare.setString(9, checkOut);
					prepare.setString(10, totalP);
					// Execute statement for customer info
					prepare.executeUpdate();

					// Prepare statement for customer receipt
					prepare = connect.prepareStatement(customerReceipt);
					prepare.setString(1, customerNumber);
					prepare.setString(2, checkIn);
					prepare.setString(3, totalP);

					// Execute statement for customer receipt
					prepare.execute();

					// Change room status to occupied after check-in
					String roomOccupied = "UPDATE room SET status = 'Occupied' WHERE roomNumber = '" + roomN + "'";

					statement = connect.createStatement();
					statement.executeUpdate(roomOccupied);

					essential.showAlert(AlertType.INFORMATION, "Information Message", "Successfully Checked-In "
							+ firstN + " " + lastN + " for " + roomT + " room " + roomN + " !");

					// Clear/reset the values of the form after submission
					// reset();
				} else {
					return;
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

	public int customerNumber;

	public void getCustomerNumber() {
		String customerNum = "SELECT customerID FROM customer";
		try {
			connect = Database.connectDb();
			prepare = connect.prepareStatement(customerNum);
			result = prepare.executeQuery();

			while (result.next()) {
				customerNumber = result.getInt("customerID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void roomTypeList() {
		String roomType = "SELECT DISTINCT type FROM room WHERE status = 'Available' GROUP BY type ORDER BY type ASC";

		try {
			// List<String> roomTypeData = new ArrayList<>();
			ObservableList roomTypeList = FXCollections.observableArrayList();
			connect = Database.connectDb();
			prepare = connect.prepareStatement(roomType);
			result = prepare.executeQuery();

			while (result.next()) {
				roomTypeList.add(result.getString("type"));
			}
			roomTypeBox.setItems(roomTypeList);

			roomNumberList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepare != null) {
					prepare.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void roomNumberList() {
		String roomType = (String) roomTypeBox.getSelectionModel().getSelectedItem();
		String roomNumber = "SELECT roomNumber FROM room WHERE type ='" + roomType + "' ORDER BY roomNumber ASC";

		try {
			// List<Integer> roomNumberData = new ArrayList<>();
			ObservableList roomNumberList = FXCollections.observableArrayList();
			connect = Database.connectDb();
			prepare = connect.prepareStatement(roomNumber);
			result = prepare.executeQuery();

			while (result.next()) {
				roomNumberList.add(result.getInt("roomNumber"));
			}

			roomNumberBox.setItems(roomNumberList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepare != null) {
					prepare.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void displayCustomerNumber() {
		getCustomerNumber();
		String custNo = Integer.toString(customerNumber + 1);
		customerNumberLabel.setText(custNo);
	}

	public String totalDays() {
		int totalDays;
		String totalD = "";
		// Date date = new Date();

		LocalDate outDate = checkOutDate.getValue();
		LocalDate inDate = checkInDate.getValue();

		if (inDate == null || outDate == null) {
			// Do nothing

		} else if (!(outDate.isAfter(inDate))) {
			essential.showAlert(AlertType.ERROR, "Error Message", "Invalid check-out date");
			checkOutDate.setValue(null);

		} else {
			// Calculate the total number of days
			totalDays = (int) ChronoUnit.DAYS.between(inDate, outDate);
			totalD = Integer.toString(totalDays);
		}
		return totalD;
	}

	public String totalPrice() {
		String totalP = "0"; // Default value if calculation fails

		try {
			Object selectedItem = roomNumberBox.getSelectionModel().getSelectedItem();
			if (selectedItem == null) {
				// Handle the case where no room is selected
				essential.showAlert(AlertType.ERROR, "No Room Selected", "Please select a room number");
				return totalP;
			}
			int roomNumber = (int) selectedItem;
			String roomPriceQuery = "SELECT price FROM room WHERE roomNumber = ?";
			connect = Database.connectDb();
			prepare = connect.prepareStatement(roomPriceQuery);
			prepare.setInt(1, roomNumber);
			result = prepare.executeQuery();

			if (result.next()) {
				double price = result.getDouble("price");
				int totalD = Integer.parseInt(totalDays());
				double totalPrice = totalD * price;
				totalP = Double.toString(totalPrice);
			} else {
				essential.showAlert(AlertType.ERROR, "No Room Found", "No room found for the selected room number");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Log the exception
		} finally {
			try {
				if (result != null) {
					result.close();
				}
				if (prepare != null) {
					prepare.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(); // Log the exception
			}
		}
		return totalP;
	}

	public void displayTotal(ActionEvent event) {

		if (event.getSource() == reset_btn) {
			//Do nothing
		} else {
			// Set the total number of days
			totalDaysLabel.setText(totalDays());

			// Set the total price to be paid for the number of days
			totalLabel.setText("GH₵" + String.valueOf(totalPrice()));
		}
	}

	public void reset() {
		// Get information of customer typed in the check-in form
		// customerNumberLabel.setText(Integer.toString(Integer.parseInt(customerNumberLabel.getText())
		// + 1));
		roomTypeBox.getSelectionModel().clearSelection();
		roomNumberBox.getSelectionModel().clearSelection();
		firstName.clear();
		lastName.clear();
		phoneNumber.clear();
		emailAddress.clear();
		checkInDate.setValue(null);
		checkOutDate.setValue(null);
		totalDaysLabel.setText("---");
		totalLabel.setText("GH₵0.00");

	}

	public void initialize(URL location, ResourceBundle resources) {
		displayCustomerNumber();
		roomTypeList();
		roomNumberList();
	}

}
