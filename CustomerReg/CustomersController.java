/**
 * Sample Skeleton for 'Customers.fxml' Controller Class
 */

package CustomerReg;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sms.SST_SMS;
public class CustomersController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

    @FXML // fx:id="txtcity"
    private TextField txtcity; // Value injected by FXMLLoader

    @FXML // fx:id="txtaddress"
    private TextField txtaddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtname"
    private TextField txtname; // Value injected by FXMLLoader

    @FXML // fx:id="profileImg"
    private ImageView profileImg; // Value injected by FXMLLoader
    

    @FXML // fx:id="msgsuccess"
    private Label msgsuccess; // Value injected by FXMLLoader

    @FXML // fx:id="msgerror"
    private Label msgerror; // Value injected by FXMLLoader

    Connection con;
    PreparedStatement pst;
    String txtpath = "";

    public void send(String[] args) 
    {        
    	String m="Congratulations! "+txtname.getText()+","+txtmob.getText()+" is Registered Successfully in Parking System . Thank You !";
    	
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
    void doBrowse(ActionEvent event) {
    	
    	 FileChooser fc = new FileChooser();
         fc.setInitialDirectory(new File("D:/Pictures"));
         File f = fc.showOpenDialog(null);
         if(f != null)
         {
        	 txtpath = f.getAbsolutePath();
         }
         FileInputStream inputstream;
		try {
			 inputstream = new FileInputStream(txtpath);
			 Image image = new Image(inputstream);
			 profileImg.setImage(image);
			 profileImg.setFitHeight(150);
			 profileImg.setPreserveRatio(true);
		   } 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		   }
    }

    @FXML
    void doNew(ActionEvent event) {
         txtname.setText("");
         txtmob.setText("");
         txtaddress.setText("");
         txtcity.setText("");
         msgsuccess.setText("");
         msgerror.setText("");
         txtpath = "D:/Pictures/users81.png";
         FileInputStream inputstream;
 		try {
 			 inputstream = new FileInputStream(txtpath);
 			 Image image = new Image(inputstream);
 			 profileImg.setImage(image);
 			 profileImg.setFitHeight(150);
 			 profileImg.setPreserveRatio(true);
 		   } 
 		catch (FileNotFoundException e) {
 			e.printStackTrace();
 		   }
         
    }

    @FXML
    void doSave(ActionEvent event) {
          String mob = txtmob.getText();
          String name = txtname.getText();
          String address  = txtaddress.getText();
          String city = txtcity.getText();
          boolean flag = false;
          
          try {
			pst = con.prepareStatement("insert into customers values (?,?,?,?,?)");
			pst.setString(1, mob);
			pst.setString(2, name);
			pst.setString(3, address);
			pst.setString(4, city);
			pst.setString(5, txtpath);
			pst.executeUpdate();
			msgerror.setText("");
			msgsuccess.setText("Saved Successfully !");
			//doSMS();
			send(null);
			flag = true;
		} 
          catch (SQLException e) {
			
			e.printStackTrace();
		}
          if(flag == false){
        	  msgsuccess.setText("");
  			msgerror.setText("Record Not Saved !\nPlease Check");
          }
    }

    @FXML
    void doFetch(ActionEvent event) {
    	String mob = txtmob.getText();
        try {
			pst = con.prepareStatement("select * from customers where mob = ?");
			pst.setString(1, mob);
			ResultSet table = pst.executeQuery();
			boolean jasus = false;
			while(table.next()){
				jasus = true;
				txtmob.setText(table.getString("mob"));
				txtname.setText(table.getString("name"));
				txtaddress.setText(table.getString("address"));
				txtcity.setText(table.getString("city"));
				txtpath = table.getString("pic");
				msgsuccess.setText("");
			    msgerror.setText("");
				FileInputStream inputstream;
		 		try {
		 			 inputstream = new FileInputStream(txtpath);
		 			 Image image = new Image(inputstream);
		 			 profileImg.setImage(image);
		 			profileImg.setFitHeight(150);
		 			profileImg.setPreserveRatio(true);
		 		   } 
		 		catch (FileNotFoundException e) {
		 			e.printStackTrace();
		 		   }
		 		
			}
			if(jasus == false){
				txtname.setText("");
		         txtaddress.setText("");
		         txtcity.setText("");
				msgsuccess.setText("");
			    msgerror.setText("No Record Found !!!");
			    FileInputStream inputstream;
		 		try {
		 			 inputstream = new FileInputStream("D:/Pictures/users81.png");
		 			 Image image = new Image(inputstream);
		 			 profileImg.setImage(image);
		 			profileImg.setFitHeight(150);
		 			profileImg.setPreserveRatio(true);
		 		   } 
		 		catch (FileNotFoundException e) {
		 			e.printStackTrace();
		 		   }
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    @FXML
    void doUpdate(ActionEvent event) {
        String mob = txtmob.getText();
        String name = txtname.getText();
        String address = txtaddress.getText();
        String city = txtcity.getText();
        
        
        try {
			pst = con.prepareStatement("update customers set name=?,address=?,city=?,pic=? where mob=?");
			
			pst.setString(1, name);
			pst.setString(2, address);
			pst.setString(3, city);
			pst.setString(4, txtpath);
			pst.setString(5, mob);
			int count = pst.executeUpdate();
			msgerror.setText("");
			msgsuccess.setText(count+" Record Updated !");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			msgsuccess.setText("");
			msgerror.setText("Updation Failed !");
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
    void doVehicleEntry(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)txtmob.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
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
    	msgerror.setText("");
    	msgsuccess.setText("");
    }
}
