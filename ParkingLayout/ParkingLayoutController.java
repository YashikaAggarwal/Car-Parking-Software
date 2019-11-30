/**
 * Sample Skeleton for 'ParkingLayout.fxml' Controller Class
 */

package ParkingLayout;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public class ParkingLayoutController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtFloor"
    private ComboBox<String> txtFloor; // Value injected by FXMLLoader

    @FXML // fx:id="txtCapacity"
    private TextField txtCapacity; // Value injected by FXMLLoader

    @FXML // fx:id="txtTwo"
    private RadioButton txtTwo; // Value injected by FXMLLoader

    @FXML // fx:id="type"
    private ToggleGroup type; // Value injected by FXMLLoader

    @FXML // fx:id="txtFour"
    private RadioButton txtFour; // Value injected by FXMLLoader

    @FXML // fx:id="msgsuccess"
    private Label msgsuccess; // Value injected by FXMLLoader

    @FXML // fx:id="msgerror"
    private Label msgerror; // Value injected by FXMLLoader
    
    Connection con;
    PreparedStatement pst;
    String txtpath = "";
    int floorsInt[] = {-3,-2,-1,0,1,2};
    
    @FXML
    void doClose(ActionEvent event) {
    	Alert confirm=new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("CLOSE");
		confirm.setContentText("Are You Sure?");
		confirm.setHeaderText("CLOSING WINDOW");
		Optional<ButtonType> res = confirm.showAndWait();
        if(res.get() == ButtonType.OK)
    	System.exit(1);
    }

    @FXML
    void doSave(ActionEvent event) {
         int floor = txtFloor.getSelectionModel().getSelectedIndex();
         int capacity = Integer.parseInt(txtCapacity.getText());
         String type = "";
         if(txtTwo.isSelected()){
        	 type = "Two Wheeler";
         }
         else if(txtFour.isSelected()){
        	 type = "Four Wheeler";
         }
         boolean flag = false;
         
         try {
			pst = con.prepareStatement("insert into parkinglayout values (?,?,?,?,0)");
			pst.setInt(1, floorsInt[floor]);
			pst.setString(2, txtFloor.getSelectionModel().getSelectedItem());
			pst.setInt(3, capacity);
			pst.setString(4, type);
			pst.executeUpdate();
			msgerror.setText("");
			msgsuccess.setText("Saved Successfully !");
			flag = true;
		} 
         catch (SQLException e) {
	 			e.printStackTrace();
		}
         if(flag == false){
       	    msgsuccess.setText("");
 		   	msgerror.setText("Entry Not Saved !");
         }
    }
    @FXML
    void doCustomerLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerLog/CustomerLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			   //Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void DoDashboard(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  DASHBOARD");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doCustomerRegisteration(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVehicleEntry(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVehicleEntryLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntryLog/VehicleEntryLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVehicleExit(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtTwo.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleExit/VehicleExit.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  EXIT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtTwo.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	con=DBConnection.doConnect();
    	String floors[]={"UnderGround Third","UnderGround Second","UnderGround First","Ground","First","Second"};
    	txtFloor.getItems().addAll(floors);

    }
}
