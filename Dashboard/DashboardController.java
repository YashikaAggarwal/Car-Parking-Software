/**
 * Sample Skeleton for 'Dashboard.fxml' Controller Class
 */

package Dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import DBConnection.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardController {
	Connection con;
    PreparedStatement pst;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="lblDashboard"
    private Label lblDashboard; // Value injected by FXMLLoader

    @FXML
    void doCustomerLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerLog/CustomerLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			   //Scene scene1=(Scene)lblDashboard.getScene();
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
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)lblDashboard.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doParkingLayout(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ParkingLayout/ParkingLayout.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  LAYOUT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)lblDashboard.getScene();
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
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)lblDashboard.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVehicleEntryLobg(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntryLog/VehicleEntryLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)lblDashboard.getScene();
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
    		Scene scene1=(Scene)lblDashboard.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleExit/VehicleExit.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  EXIT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			
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
    }
}

