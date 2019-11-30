/**
 * Sample Skeleton for 'VehicleEntry.fxml' Controller Class
 */

package VehicleEntry;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sms.SST_SMS;

public class VehicleEntryController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

    @FXML // fx:id="txtvehicleno"
    private TextField txtvehicleno; // Value injected by FXMLLoader

   /* @FXML // fx:id="radRegular"
    private RadioButton radRegular; // Value injected by FXMLLoader
*/
//    @FXML // fx:id="customerType"
//    private ToggleGroup customerType; // Value injected by FXMLLoader

   /* @FXML // fx:id="radRandom"
    private RadioButton radRandom; // Value injected by FXMLLoader
*/
    @FXML // fx:id="radFour"
    private RadioButton radFour; // Value injected by FXMLLoader

    @FXML // fx:id="vehicleNumber"
    private ToggleGroup vehicleType; // Value injected by FXMLLoader

    @FXML // fx:id="radTwo"
    private RadioButton radTwo; // Value injected by FXMLLoader

    @FXML // fx:id="listfloorno"
    private ListView<String> listfloorno; // Value injected by FXMLLoader

    @FXML // fx:id="listavailable"
    private ListView<String> listavailable; // Value injected by FXMLLoader
    
    @FXML
    private Label lblmsg;

    @FXML
    private Button btnSave;
    
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    String txtpath = "";
    String customertype = ""; 
    
    public void send(String[] args) 
    {   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
    	String m="Vehicle "+txtvehicleno.getText()+" successfully entered in Parking at "+formatter.format(date)+" .";
    	
    	String resp=SST_SMS.bceSunSoftSend(txtmob.getText(), m);
    	System.out.println(resp);
    	
    	if(resp.indexOf("Exception")!=-1)
    		System.out.println("Check Internet Connection");
    	
    	else
    		if(resp.indexOf("successfully")!=-1)
        		System.out.println("Sent");
    		else
    		System.out.println( "Invalid Number");
    }
    @FXML
    void doSave(ActionEvent event) throws SQLException {
    	//String customertype = "";
      /*if(radRegular.isSelected()){
    	  customertype = "Regular";
      }
      else if(radRandom.isSelected()){
    	  customertype = "Random";
      }*/
    	pst = con.prepareStatement("select * from customers where mob='"+txtmob.getText()+"'");
  	    ResultSet table1 = pst.executeQuery();
			if(table1.next()){
				customertype = "Regular";
			}
			else
			{
				customertype = "Random";
			}
      String mob = txtmob.getText();
      String vehicleno = txtvehicleno.getText();
      String vehicletype = "";
      if(radTwo.isSelected()){
    	  vehicletype = "Two-Wheeler";
      }
      else if(radFour.isSelected()){
    	  vehicletype = "Four-Wheeler";
      }
      String floor = listfloorno.getSelectionModel().getSelectedItem();
      int x = Integer.parseInt(listavailable.getSelectionModel().getSelectedItem());
      
      
      x = x - 1;
     
      try {
    	  String floorname = listfloorno.getSelectionModel().getSelectedItem();
  		pst = con.prepareStatement("select * from parkinglayout where floorName='"+floorname+"'");
  	    ResultSet table = pst.executeQuery();
			while(table.next() && x>=0){
				int occu = table.getInt("occupied");
				occu = occu+1;
				pst1 = con.prepareStatement("update parkinglayout set occupied = ? where floorName='"+floorname+"'");
				pst1.setInt(1 , occu);
				pst1.executeUpdate();
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
      try {
		pst = con.prepareStatement("insert into vehicle_entry(customertype ,mob ,vehicleno ,vehicletype , floor ,entrydate ,entrytime,status,exitdate,exittime) values (?,?,?,?,?,curdate(),curtime() ,1,curdate(),curtime())");
		pst.setString(1, customertype);
		pst.setString(2, mob);
		pst.setString(3, vehicleno);
		pst.setString(4, vehicletype);
		pst.setString(5, floor);
		pst.executeUpdate();
	} 
      catch (SQLException e) {
		
		e.printStackTrace();
	}
      lblmsg.setText("Entry Saved Successfully !");
      send(null);
    }
    
    @FXML
    void doNew(ActionEvent event) {
    	
        txtmob.setText("");
        txtvehicleno.setText("");
        vehicleType.selectToggle(null);
        listfloorno.getItems().clear();
        listavailable.getItems().clear();
        lblmsg.setText("");
    }
    
    @FXML
    void onDblClick(MouseEvent event) {
      if(event.getClickCount() == 2){
    	  int index = listfloorno.getSelectionModel().getSelectedIndex();
    	  listavailable.getSelectionModel().select(index);
    	  if(listavailable.getSelectionModel().getSelectedItem()=="NOT AVAILABLE"){
  				Alert al=new Alert(AlertType.INFORMATION);
  				al.setTitle("No More Slots Available on this Floor");
  				al.setContentText("This Floor is Full !!!\nPlease Select some other Floor.");
  				al.setHeaderText("SORRY ! THIS FLOOR IS FULL");
  				al.show();
  			    
    	  }
    	  else
    	  {
    		  btnSave.setDisable(false);
    	  }
      }
    }

    @FXML
    void doVehicleType(ActionEvent event) {
    	
  	    int x;
      if(radTwo.isSelected()){
    	    listfloorno.getItems().clear();
			listavailable.getItems().clear();
			ObservableList<String> floorObsList = FXCollections.observableArrayList("FLOOR NUMBER","");
	        listfloorno.setItems(floorObsList);
	  		ObservableList<String> flooravailObsList=FXCollections.observableArrayList("AVAILABLE","");
	  	    listavailable.setItems(flooravailObsList);	
        try {
      		pst = con.prepareStatement("select * from parkinglayout where type='Two Wheeler'");	
      		ResultSet table = pst.executeQuery();
  			while(table.next()){
  				String floorname = table.getString("floorName");
  				int cap = table.getInt("capacity");
  				int occu = table.getInt("occupied");
  				x = cap - occu;
  				listfloorno.getItems().add(floorname);
  				if(x<=0)
  				listavailable.getItems().add("NOT AVAILABLE");
  				else
  				listavailable.getItems().add(String.valueOf(x));
  			}
  			
  		} catch (Exception e) {
  		
  			e.printStackTrace();
  		}
  	    
      }
      else if(radFour.isSelected()){
    	    listfloorno.getItems().clear();
			listavailable.getItems().clear();
			ObservableList<String> floorObsList = FXCollections.observableArrayList("FLOOR NUMBER","");
	        listfloorno.setItems(floorObsList);
	  		ObservableList<String> flooravailObsList=FXCollections.observableArrayList("AVAILABLE","");
	  	    listavailable.setItems(flooravailObsList);	
      try {
    		pst = con.prepareStatement("select * from parkinglayout where type='Four Wheeler'");	
    		ResultSet table = pst.executeQuery();
			while(table.next()){
				String floorname = table.getString("floorName");
				int cap = table.getInt("capacity");
				int occu = table.getInt("occupied");
				x = cap - occu;
				listfloorno.getItems().add(floorname);
				if(x<=0)
	  			listavailable.getItems().add("NOT AVAILABLE");
	  			else
	  			listavailable.getItems().add(String.valueOf(x));
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
      }
    }
    @FXML
    void doCustomerLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerLog/CustomerLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			   //Scene scene1=(Scene)txtmob.getScene();
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
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  DASHBOARD");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtmob.getScene();
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
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtmob.getScene();
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
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ParkingLayout/ParkingLayout.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  LAYOUT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtmob.getScene();
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
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntryLog/VehicleEntryLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtmob.getScene();
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
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleExit/VehicleExit.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  EXIT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txtmob.getScene();
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
