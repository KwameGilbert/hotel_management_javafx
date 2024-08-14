package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import database.Database;
import essentials.essentials;

public class dashboardController implements Initializable {

	@FXML
	private Button logout_btn;

	@FXML
	private AnchorPane main_anchor;

	@FXML
	private Button availableRooms_addBtn;

	@FXML
	private Button availableRooms_checkInBtn;

	@FXML
	private Button availableRooms_clearBtn;

	@FXML
	private TableColumn<roomData, String> availableRooms_col_roomNumber;

	@FXML
	private TableColumn<roomData, String> availableRooms_col_roomPrice;

	@FXML
	private TableColumn<roomData, String> availableRooms_col_roomStatus;

	@FXML
	private TableColumn<roomData, String> availableRooms_col_roomType;

	@FXML
	private Button availableRooms_deleteBtn;

	@FXML
	private AnchorPane availableRooms_form;

	@FXML
	private TextField availableRooms_price;

	@FXML
	private TextField availableRooms_roomNumber;

	@FXML
	private ComboBox<?> availableRooms_roomStatus;

	@FXML
	private ComboBox<?> availableRooms_roomType;

	@FXML
	private TextField availableRooms_search;

	@FXML
	private TableView<roomData> availableRooms_tableView;

	@FXML
	private Button availableRooms_updateBtn;

	@FXML
	private Button available_rooms_btn;

	@FXML
	private TableView<CustomerData> customer_tableView;

	@FXML
	private TableColumn<CustomerData, String> customer_checkedIn;

	@FXML
	private TableColumn<CustomerData, String> customer_checkedOut;

	@FXML
	private TableColumn<CustomerData, String> customer_firstName;

	@FXML
	private TableColumn<CustomerData, String> customer_lastName;

	@FXML
	private TableColumn<CustomerData, String> customerID;
	
    @FXML
    private TableColumn<CustomerData, String> customer_roomNumber;

	@FXML
	private TableColumn<CustomerData, String> customer_phoneNumber;

	@FXML
	private TableColumn<CustomerData, String> customer_totalPayment;

	@FXML
	private Button customers_btn;

	@FXML
	private AnchorPane customers_form;

	@FXML
	private TextField customer_search;

	@FXML
	private AreaChart<String, Integer> dashboard_area_chart;

	@FXML
	private Label dashboard_booking_today;

	@FXML
	private Button dashboard_btn;

	@FXML
	private AnchorPane dashboard_form;

	@FXML
	private Label dashboard_income_today;

	@FXML
	private Label dashboard_total_income;

	@FXML
	private Label username;

	// Essentials Variables
	essentials essential = new essentials();

	// Database Variables
	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	@SuppressWarnings("unused")
	private Statement statement;

	//DASHBOARD RELATED FUNCTIONS
	//Count Booking Today
	public void countBookingToday() {
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        java.sql.Date today = new java.sql.Date(date.getTime());
		
		String bookingTodayQuery = "SELECT COUNT(id) FROM customer WHERE checkInDate = '"+today+"'";
		int count = 0;
		try {
			connect = Database.connectDb();
			prepare = connect.prepareStatement(bookingTodayQuery);
			result = prepare.executeQuery();
			
			while(result.next()) {
				count = result.getInt("COUNT(id)");
			}
			String countValue = String.valueOf(count);
			dashboard_booking_today.setText(countValue);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Sum total income for today
	public void totalIncomeToday() {
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        java.sql.Date today = new java.sql.Date(date.getTime());
		
		String incomeTodayQuery = "SELECT SUM(total) FROM customer WHERE checkInDate = '"+today+"'";
		double total = 0.00;
		
		try {
			connect = Database.connectDb();
			prepare = connect.prepareStatement(incomeTodayQuery);
			result = prepare.executeQuery();
			
			while(result.next()) {
				total = result.getFloat("SUM(total)");
			}
			String totalValue = String.valueOf(total);
			dashboard_income_today.setText("₵" + totalValue);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Sum total income for the month
	public void totalIncome() {
			
			String monthTotalQuery = "SELECT SUM(total) FROM customer";
			double month_total = 0.00;
			
			try {
				connect = Database.connectDb();
				prepare = connect.prepareStatement(monthTotalQuery);
				result = prepare.executeQuery();
				
				while(result.next()) {
					month_total = result.getDouble("SUM(total)");
				}
				String totalValue = String.valueOf(month_total);
				dashboard_total_income.setText("₵" + totalValue);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	
	public void dashboardChart() {
	    dashboard_area_chart.getData().clear();

	    String chartQuery = "SELECT date, total FROM customer_receipt WHERE date BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() GROUP BY date ORDER BY TIMESTAMP(date) ASC ";

	    XYChart.Series<String, Integer> chart = new XYChart.Series<>();
	    try {
	        connect = Database.connectDb();
	        prepare = connect.prepareStatement(chartQuery);
	        result = prepare.executeQuery();

	        while (result.next()) {
	            chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
	        }

	        dashboard_area_chart.getData().add(chart);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	//ROOM RELATED FUNCTIONS
	public ObservableList<roomData> availableRoomListData() {
		ObservableList<roomData> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM room";

		connect = Database.connectDb();

		try {
			roomData roomData;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {
				roomData = new roomData(result.getInt("roomNumber"), result.getString("type"),
						result.getString("status"), result.getDouble("price"));

				listData.add(roomData);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listData;
	}

	private ObservableList<roomData> roomDataList;

	public void showRoomListData() {
		roomDataList = availableRoomListData();

		availableRooms_col_roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
		availableRooms_col_roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
		availableRooms_col_roomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		availableRooms_col_roomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Set the table items to roomDataList which contains the return values of
		// availableRoomListData()
		availableRooms_tableView.setItems(roomDataList);

		// Set the sort oder to roomNumber
		availableRooms_tableView.getSortOrder().add(availableRooms_col_roomNumber);

		// Enable sorting
		availableRooms_tableView.setSortPolicy(param -> true);
	}

	
	public void customerSearch(){
		FilteredList<CustomerData> filter = new FilteredList<>(listCustomerData, e -> true);

		customer_search.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setPredicate(CustomerData -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all items if the search field is empty
				}
				String searchKey = newValue.toLowerCase();

				if (CustomerData.getCustomerID().toString().contains(searchKey)) {
					return true;
				} else if (CustomerData.getFirstName().toLowerCase().contains(searchKey)) {
					return true;
				} else if (CustomerData.getLastName().toLowerCase().contains(searchKey)) {
					return true;
				} else if (CustomerData.getTotal().toString().contains(searchKey)) {
					return true;
				} else if (CustomerData.getPhoneNumber().toLowerCase().contains(searchKey)) {
					return true;
				}else if(CustomerData.getRoomNumber().toString().contains(searchKey)) {
					return true;
				} else if (CustomerData.getCheckInDate().toString().contains(searchKey)) {
					return true;
				} else if (CustomerData.getCheckOutDate().toString().contains(searchKey)) {
					return true;
				}
				return false; // No match found
			});
		});

		SortedList<CustomerData> sortList = new SortedList<>(filter);
		sortList.comparatorProperty().bind(customer_tableView.comparatorProperty());
		customer_tableView.setItems(sortList);
	}
	
	public void roomAdd() {
		String sql = "INSERT INTO room(roomNumber, type, status, price) VALUES( ?, ?, ?, ?);";
		connect = Database.connectDb();

		try {
			String roomNumber = availableRooms_roomNumber.getText();
			String type = (String) availableRooms_roomType.getSelectionModel().getSelectedItem();
			String status = (String) availableRooms_roomStatus.getSelectionModel().getSelectedItem();
			String price = availableRooms_price.getText();

			Alert alert;

			// Check if any of the fields are empty
			if (roomNumber.isEmpty() || type.isEmpty() || status.isEmpty() || price.isEmpty()) {

				// Alert user to fill all room information if any are empty
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all room information");
				alert.showAndWait();

			} else {
				// Query to check if the room number user is adding already exists in the
				// database
				String check = "SELECT roomNumber FROM room WHERE roomNumber='" + roomNumber + "'";

				prepare = connect.prepareStatement(check);
				result = prepare.executeQuery();

				if (result.next()) {
					// Alert user room number already exists
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Room number " + roomNumber + " already exists!");
					alert.showAndWait();

				} else {

					// Execute sql query if room number doesn't already exist in the database
					prepare = connect.prepareStatement(sql);
					prepare.setString(1, roomNumber);
					prepare.setString(2, type);
					prepare.setString(3, status);
					prepare.setString(4, price);

					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Added!!!");
					alert.showAndWait();

					// Update Room List Data
					showRoomListData();

					// Clear Input information after updating
					clearRooms();
				}
			}
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

	public String type[] = { "Conference Room", "King Size", "Queen Size", "Quad Room", "Triple Room", "Double Room",
	"Single Room" };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void roomTypes() {
		List<String> listData = new ArrayList<>();

		for (String data : type) {
			listData.add(data);
		}

		ObservableList list = FXCollections.observableArrayList(listData);
		availableRooms_roomType.setItems(list);
	}

	public String status[] = { "Available", "Not Available", "Occupied" };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void roomStatus() {
		List<String> listData = new ArrayList<>();

		for (String data : status) {
			listData.add(data);
		}

		ObservableList list = FXCollections.observableArrayList(listData);
		availableRooms_roomStatus.setItems(list);
	}

	public void selectedRoomData() {
		roomData roomData = availableRooms_tableView.getSelectionModel().getSelectedItem();
		int num = availableRooms_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		availableRooms_roomNumber.setText(String.valueOf(roomData.getRoomNumber()));
		availableRooms_price.setText(String.valueOf(roomData.getPrice()));
	}

	public void roomUpdate() {
		String type = (String) availableRooms_roomType.getSelectionModel().getSelectedItem();
		String status = (String) availableRooms_roomStatus.getSelectionModel().getSelectedItem();
		String price = availableRooms_price.getText();
		String roomNumber = availableRooms_roomNumber.getText();

		String sql = "UPDATE room SET type = '" + type + "', status = '" + status + "', price='" + price
				+ "' WHERE roomNumber='" + roomNumber + "' ";

		connect = Database.connectDb();

		try {

			// Check if any of the fields are empty
			if (roomNumber.isEmpty() || type.isEmpty() || status.isEmpty() || price.isEmpty()) {

				essential.showAlert(AlertType.ERROR, "Error Message", "Please select the room first");
			} else {

				prepare = connect.prepareStatement(sql);
				prepare.executeUpdate();

				essential.showAlert(AlertType.INFORMATION, "Information Message", "Successfully Update!!!");

				// Show Updated Room List Data
				showRoomListData();

				// Clear Input information after updating
				clearRooms();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void roomDelete() {

		String roomNumber = availableRooms_roomNumber.getText();

		String deleteRoom = "DELETE FROM room WHERE roomNumber=?";

		connect = Database.connectDb();

		try {

			if (roomNumber.isEmpty()) {
				essential.showAlert(AlertType.ERROR, "Error Message", "Please select the room information first");
			} else {
				Alert confirmDeleteAlert = new Alert(AlertType.CONFIRMATION);
				confirmDeleteAlert.setTitle("Confirmation Message");
				confirmDeleteAlert.setHeaderText(null);
				confirmDeleteAlert.setContentText("Are you sure you want to delete room '" + roomNumber + "'?");

				Optional<ButtonType> option = confirmDeleteAlert.showAndWait();

				if (option.isPresent() && option.get() == ButtonType.OK) {

					prepare = connect.prepareStatement(deleteRoom);
					prepare.setString(1, roomNumber);
					int rowsAffected = prepare.executeUpdate();

					if (rowsAffected > 0) {
						essential.showAlert(AlertType.INFORMATION, "Information Message",
								"Room '" + roomNumber + "' successfully deleted!!!");
						// Show Updated Room List Data
						showRoomListData();
						// Clear Input information after updating
						clearRooms();
					} else {
						essential.showAlert(AlertType.ERROR, "Error Message",
								"Failed to delete room '" + roomNumber + "'");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			essential.showAlert(AlertType.ERROR, "Error Message",
					"An error occurred while deleting the room: " + e.getMessage());
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

	public void clearRooms() {
		availableRooms_roomNumber.clear();
		availableRooms_roomType.getSelectionModel().clearSelection();
		availableRooms_roomStatus.getSelectionModel().clearSelection();
		availableRooms_price.clear();
	}
	
	public void checkInForm() {
		try {
			Parent rootCheck = FXMLLoader.load(getClass().getResource("checkIn.fxml"));
			Stage stageCheck = new Stage();
			Scene sceneCheck = new Scene(rootCheck);

			stageCheck.initStyle(StageStyle.DECORATED);
			stageCheck.setScene(sceneCheck);
			stageCheck.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//CUSTOMER RELATED FUNCTIONS
	public ObservableList<CustomerData> customerDataList() {
		ObservableList<CustomerData> listData = FXCollections.observableArrayList();

		String customerListQuery = "SELECT * FROM customer GROUP BY customerID ORDER BY customerID ASC";

		try (Connection connect = Database.connectDb();
				PreparedStatement prepare = connect.prepareStatement(customerListQuery);
				ResultSet result = prepare.executeQuery()) {

			while (result.next()) {
				CustomerData customerData = new CustomerData(result.getInt("customerID"), result.getString("firstName"),
						result.getString("lastName"), result.getString("phoneNumber"), result.getInt("roomNumber"), result.getDouble("total"),
						result.getDate("checkInDate"), result.getDate("checkOutDate"));
				listData.add(customerData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<CustomerData> listCustomerData;

	public void customerShowData() {
		listCustomerData = customerDataList();

		customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		customer_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		customer_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		customer_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		customer_roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
		customer_totalPayment.setCellValueFactory(new PropertyValueFactory<>("total"));
		customer_checkedIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
		customer_checkedOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

		customer_tableView.setItems(listCustomerData);

		customer_tableView.getSortOrder().add(customerID);

		customer_tableView.setSortPolicy(param -> true);
	}

	// Search for data in the customer list table
	public void roomSearch() {
		FilteredList<roomData> filter = new FilteredList<>(roomDataList , e -> true);

		availableRooms_search.textProperty().addListener((Observable, oldValue, newValue) -> {
			filter.setPredicate(roomData -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all items if the search field is empty
				}

				String searchKey = newValue.toLowerCase();

				if (roomData.getRoomNumber().toString().contains(searchKey)) {
					return true;
				}else if(roomData.getRoomType().toLowerCase().contains(searchKey)){
					return true;
				}else if(roomData.getPrice().toString().contains(searchKey)){
					return true;
				}else if(roomData.getStatus().toLowerCase().contains(searchKey)){
					return true;
				}else return false; // No match found
			});
		});

		SortedList<roomData> sortList = new SortedList<>(filter);
		sortList.comparatorProperty().bind(availableRooms_tableView.comparatorProperty());
		availableRooms_tableView.setItems(sortList);
	}

	public void switchForm(ActionEvent event) {
		
		if(event.getSource() == dashboard_btn) {
			//Switch to dashboard if dashboard button is clicked
			dashboard_form.setVisible(true);
			availableRooms_form.setVisible(false);
			customers_form.setVisible(false);
			
			countBookingToday();
			totalIncomeToday();
			totalIncome();
			dashboardChart();
			
			//Set background of button when clicked
			dashboard_btn.setStyle("-fx-background-color: linear-gradient( to bottom right, #790918, #db8b41)");
			available_rooms_btn.setStyle("-fx-background-color: transparent");
			customers_btn.setStyle("-fx-background-color: transparent");
			
		}else if(event.getSource() == available_rooms_btn) {
			//Switch to available room if room button is clicked
			dashboard_form.setVisible(false);
			availableRooms_form.setVisible(true);
			customers_form.setVisible(false);
			
			showRoomListData();
			//Start room search listener to search rooms
			roomSearch();
			
			//Set background of button when clicked
			dashboard_btn.setStyle("-fx-background-color: transparent");
			available_rooms_btn.setStyle("-fx-background-color: linear-gradient( to bottom right, #790918, #db8b41)");
			customers_btn.setStyle("-fx-background-color: transparent");
			
		}else if(event.getSource() == customers_btn) {
			//Switch to customers page if customers button is clicked.
			dashboard_form.setVisible(false);
			availableRooms_form.setVisible(false);
			customers_form.setVisible(true);
			
			customerShowData();
			
			// Start customer Search listener to sort
			customerSearch();
			
			//Set background of button when clicked
			dashboard_btn.setStyle("-fx-background-color: transparent");
			available_rooms_btn.setStyle("-fx-background-color: transparent");
			customers_btn.setStyle("-fx-background-color: linear-gradient( to bottom right, #790918, #db8b41)");
			
		}
	}

	public void logout() {
		try {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {

				// Load the login scene if the button clicked is OK
				Parent root = FXMLLoader.load(getClass().getResource("loginDesign.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

				logout_btn.getScene().getWindow().hide();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		System.exit(0);
	}

	public void minimize() {
		Stage stage = (Stage) main_anchor.getScene().getWindow();
		stage.setIconified(true);
	}

	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		//count and display number of bookings for today
		countBookingToday();
		
		//Total Income today
		totalIncomeToday();
		
		//Overall total income
		totalIncome();
		
		//Dashboard Chart	
		dashboardChart();
		
		//Set room type and status
		roomTypes();
		roomStatus();

		// Show Room List Data in the table
		showRoomListData();

		//Start room search listener to search rooms
		roomSearch();
		 
		// Show customer data in the customer table
		customerShowData();

		// Start customer Search listener to sort
		customerSearch();

	}

}
