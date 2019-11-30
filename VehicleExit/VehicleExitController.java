/**
 * Sample Skeleton for 'VehicleExit.fxml' Controller Class
 */

package VehicleExit;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sms.SST_SMS;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VehicleExitController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtvehicleno"
    private TextField txtvehicleno; // Value injected by FXMLLoader

    @FXML // fx:id="txtfloor"
    private TextField txtfloor; // Value injected by FXMLLoader

    @FXML // fx:id="txttype"
    private TextField txttype; // Value injected by FXMLLoader

    @FXML // fx:id="txtentrytime"
    private TextField txtentrytime; // Value injected by FXMLLoader

    @FXML // fx:id="txtentrydate"
    private TextField txtentrydate; // Value injected by FXMLLoader

    @FXML // fx:id="txtexitdate"
    private TextField txtexitdate; // Value injected by FXMLLoader

    @FXML // fx:id="txtexittime"
    private TextField txtexittime; // Value injected by FXMLLoader

    @FXML // fx:id="lblamount"
    private Label lblamount; // Value injected by FXMLLoader
    
    @FXML
    private Label lblmsg;
    
    @FXML
    private Button btnNew;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnBill;
    
    Connection con;
    PreparedStatement pst;
    String mob;
    
    /*public void doSMS() {
    	System.out.println("******");
    	String resp=sms.SST_SMS.bceSunSoftSend(mob,"Vehicle + "+txtvehicleno.getText()+" has successfully exit from Parking at curdate(); curtime();\n Thanks for Coming !" );
    	if(resp.contains("successfully"))
			System.out.println("Sent...");
	else
		if(resp.contains("Unknown"))
			System.out.println("Check Internet connection");
		else
			System.out.println("Invalid Mobile Number");
		
    }*/
    
    public void send(String[] args) 
    {   
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
    	String m="Vehicle "+txtvehicleno.getText()+" has successfully exit from Parking at "+formatter.format(date)+" .Your Bill is "+lblamount.getText()+" . Thanks for Coming !" ;
    	
    	String resp=SST_SMS.bceSunSoftSend(mob, m);
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
    void doBill(ActionEvent event) {
        String entryDate = txtentrydate.getText();
        String entryTime = txtentrytime.getText();
        String exitDate = txtexitdate.getText();
        String exitTime = txtexittime.getText();
        
        String entry = entryDate+" "+entryTime;
        String exit = exitDate+" "+exitTime;
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date entrydt=null;
        Date exitdt=null;
        try {
        	entrydt = format.parse(entry);
        	exitdt = format.parse(exit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long diffmsec = exitdt.getTime() - entrydt.getTime(); //This is difference in milliseconds
        long diffsec = diffmsec/1000;
        long diffmin = diffsec/60;
        long dhour = diffmin/60;
        long dmin = diffmin%60;
        
        double hourcost = dhour*5;
        double mincost = dmin/12;
        //System.out.println(dhour+" "+dmin+" "+hourcost+" "+mincost);
        double totalBill = hourcost + mincost;
        //System.out.println(totalBill);
        lblamount.setText("Rs. "+totalBill);
    }

    @FXML
    void doFetch(ActionEvent event) {
    	String vehicle = txtvehicleno.getText();
    	Date date = new Date();
    	SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
    	String strDate= DATE.format(date);
    	SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    	String strTime= TIME.format(date);
    	boolean jasus = false;
        try {
			pst = con.prepareStatement("select * from vehicle_entry where vehicleno = ? and status = 1");
			pst.setString(1, vehicle);
			ResultSet table = pst.executeQuery();
			
			while(table.next()){
				
				txtfloor.setText(table.getString("floor"));
				txtentrydate.setText(table.getString("entrydate"));
				txtentrytime.setText(table.getString("entrytime"));
				txttype.setText(table.getString("vehicletype"));
				txtexitdate.setText(strDate);
				txtexittime.setText(strTime);
				mob=table.getString("mob");
				jasus = true;
			}
        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
        if(jasus == true){
        btnUpdate.setDisable(false);
        btnBill.setDisable(false);
        }
        else
        {
        	Alert al = new Alert(AlertType.WARNING);
        	al.setTitle("NO VALID DATA FOUND");
		    al.setContentText("Please Fill Valid Vehicle Number !!!");
			al.setHeaderText("NO VALID DATA !");
			al.show();
			txtvehicleno.setText("");
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
    	String vehicle = txtvehicleno.getText();
    	String floor = txtfloor.getText();
    	boolean jasus = true;
        try {
			pst = con.prepareStatement("update vehicle_entry set status=0,exitdate=curdate(),exittime=curtime() where vehicleno = ?");
			pst.setString(1, vehicle);
			pst.executeUpdate();
			
		} 
        
        catch (SQLException e) {
			e.printStackTrace();
			jasus = false;
		}
        
        try {
			pst = con.prepareStatement("update parkinglayout set occupied = occupied-1 where floorName = ?");
			pst.setString(1, floor);
			pst.executeUpdate();
		} 
        catch (SQLException e) {
			e.printStackTrace();
			jasus = false;
		}
       if(jasus == false){
    	    Alert al = new Alert(AlertType.WARNING);
       	    al.setTitle("EXIT UNSUCCESSFUL");
		    al.setContentText("Exit Unsuccessful !!!\nTry Again");
			al.setHeaderText("ERROR OCCURED");
			al.show();
			txtvehicleno.setText("");
			txtfloor.setText("");
			txtentrydate.setText("");
			txtentrytime.setText("");
			txttype.setText("");
			txtexitdate.setText("");
			txtexittime.setText("");
			lblamount.setText("Rs. 0.00");
       }
       else{

           lblmsg.setText("Successful Exit !");
           btnNew.setDisable(false);
           send(null);
       }
    }
    
    @FXML
    void doNew(ActionEvent event) {
    	txtvehicleno.setText("");
		txtfloor.setText("");
		txtentrydate.setText("");
		txtentrytime.setText("");
		txttype.setText("");
		txtexitdate.setText("");
		txtexittime.setText("");
		lblamount.setText("Rs. 0.00");
		lblmsg.setText("");
		btnBill.setDisable(true);
		btnUpdate.setDisable(true);
		btnNew.setDisable(true);
    }

    @FXML
    void doCustomerLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerLog/CustomerLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			   //Scene scene1=(Scene)txttype.getScene();
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
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  DASHBOARD");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txttype.getScene();
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
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ParkingLayout/ParkingLayout.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  LAYOUT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txttype.getScene();
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
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txttype.getScene();
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
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntryLog/VehicleEntryLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txttype.getScene();
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
    		Scene scene1=(Scene)txttype.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)txttype.getScene();
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
