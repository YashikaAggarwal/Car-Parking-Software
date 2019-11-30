/**
 * Sample Skeleton for 'Admin.fxml' Controller Class
 */

package Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminController {

	@FXML
    private GridPane AdminPane;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtuid"
    private TextField txtuid; // Value injected by FXMLLoader

    @FXML // fx:id="txtpwd"
    private PasswordField txtpwd; // Value injected by FXMLLoader

    @FXML
    void doLogin(ActionEvent event) {
         String uid = txtuid.getText();
         String pwd = txtpwd.getText();
         
         if(uid.equals("Admin123") && pwd.equals("Parking@$123")){
        	 try {
        		 Scene scene1=(Scene)txtuid.getScene();
     			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
     			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
     			
     			Stage primaryStage=new Stage();
     			primaryStage.setScene(scene);
     			primaryStage.setTitle("PARKING  DASHBOARD");
     			Image icon = new Image("/Parking-Cicle-Icon.png");
     			primaryStage.getIcons().add(icon);
     			primaryStage.show();
     			
     			//to hide the opened window
     			 
     			
     			   scene1.getWindow().hide();
     			 
        		 /*GridPane pane = FXMLLoader.load(getClass().getResource("/Dashboard/Dashboard.fxml"));
        		 AdminPane.getChildren().setAll(pane);*/
     	    } 
     	catch(Exception e)
     		{
     			e.printStackTrace();
     		}
         }
         else{
        	 Alert al=new Alert(AlertType.ERROR);
     		al.setTitle("ERROR");
     		al.setContentText("Please Enter Valid Login Credentials !!!");
     		al.setHeaderText("INVALID USERNAME AND PASSWORD");
     		al.show();
         }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
    }
}
