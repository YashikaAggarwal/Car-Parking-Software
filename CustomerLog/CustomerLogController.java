/**
 * Sample Skeleton for 'CustomerLog.fxml' Controller Class
 */

package CustomerLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnection.DBConnection;
import CustomerLog.CustomerBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CustomerLogController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tblCustomer"
    private TableView<CustomerBean> tblCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="comboMob"
    private ComboBox<String> comboMob; // Value injected by FXMLLoader


    Connection con;
    //PreparedStatement pst;
    ObservableList<CustomerBean> list;
    
    public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

        private ComboBox comboBox;
        private StringBuilder sb;
        private ObservableList<T> data;
        private boolean moveCaretToPos = false;
        private int caretPos;

        public AutoCompleteComboBoxListener(final ComboBox comboBox) {
            this.comboBox = comboBox;
            sb = new StringBuilder();
            data = comboBox.getItems();

            this.comboBox.setEditable(true);
            this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    comboBox.hide();
                }
            });
            this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
        }

        @Override
        public void handle(KeyEvent event) {

            if(event.getCode() == KeyCode.UP) {
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if(event.getCode() == KeyCode.DOWN) {
                if(!comboBox.isShowing()) {
                    comboBox.show();
                }
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if(event.getCode() == KeyCode.BACK_SPACE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
            } else if(event.getCode() == KeyCode.DELETE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.isControlDown() || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                return;
            }

            ObservableList list = FXCollections.observableArrayList();
            for (int i=0; i<data.size(); i++) {
                if(data.get(i).toString().toLowerCase().startsWith(
                    AutoCompleteComboBoxListener.this.comboBox
                    .getEditor().getText().toLowerCase())) {
                    list.add(data.get(i));
                }
            }
            String t = comboBox.getEditor().getText();

            comboBox.setItems(list);
            comboBox.getEditor().setText(t);
            if(!moveCaretToPos) {
                caretPos = -1;
            }
            moveCaret(t.length());
            if(!list.isEmpty()) {
                comboBox.show();
            }
        }

        private void moveCaret(int textLength) {
            if(caretPos == -1) {
                comboBox.getEditor().positionCaret(textLength);
            } else {
                comboBox.getEditor().positionCaret(caretPos);
            }
            moveCaretToPos = false;
        }

    }
    
    void fetchAllRecords(PreparedStatement pst)
    {
    	list=FXCollections.observableArrayList();
    	try{
        
        	ResultSet table= pst.executeQuery();
        		
        		while(table.next())
        		{
        			String mob=table.getString("mob");
        			String name=table.getString("name");
        			String address=table.getString("address");
        			String city=table.getString("city");
        			String pic=table.getString("pic");
        			System.out.println(mob+"  "+name+"  "+address+"   "+city+"   "+pic);
        			CustomerBean sb=new CustomerBean(mob,name,address,city,pic);
        			list.add(sb);
        			
        		}
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    	
    }
    
    void fillColumns()
    {
    	TableColumn<CustomerBean, String> mob=new TableColumn<CustomerBean, String>("Mobile");
     	mob.setCellValueFactory(new PropertyValueFactory<>("mob"));

     	TableColumn<CustomerBean, String> name=new TableColumn<CustomerBean, String>("Customer Name");
     	name.setCellValueFactory(new PropertyValueFactory<>("name"));
     	
     	TableColumn<CustomerBean, String> address=new TableColumn<CustomerBean, String>("Address");
     	address.setCellValueFactory(new PropertyValueFactory<>("address"));

     	TableColumn<CustomerBean, String> city=new TableColumn<CustomerBean, String>("City");
     	city.setCellValueFactory(new PropertyValueFactory<>("city"));
     	
     	TableColumn<CustomerBean, String> pic=new TableColumn<CustomerBean, String>("Picture");
     	pic.setCellValueFactory(new PropertyValueFactory<>("pic"));
     	
     	tblCustomer.getColumns().clear();
     	tblCustomer.getColumns().addAll(mob,name,address,city,pic);
    }
    
    @FXML
    void doCustomerRegisteration(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
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
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  DASHBOARD");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
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
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ParkingLayout/ParkingLayout.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  LAYOUT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
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
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
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
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntryLog/VehicleEntryLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
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
    		Scene scene1=(Scene)tblCustomer.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleExit/VehicleExit.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  EXIT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblCustomer.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doDisplayAll(ActionEvent event) {
    	try {
			
   		 PreparedStatement	pst=con.prepareStatement("select * from customers");
   		
   		  fetchAllRecords(pst);
       	 
   	      tblCustomer.setItems(list);
		} 
   	 catch (SQLException e) 
   	 {
			
			e.printStackTrace();
		}
    }
    
    @FXML
    void doSearch(ActionEvent event) {
    	try {
 			
   		 PreparedStatement	pst=con.prepareStatement("select * from customers where mob=?");
   		 pst.setString(1,comboMob.getSelectionModel().getSelectedItem());
   		
   		 fetchAllRecords(pst);
       	 
   	     tblCustomer.setItems(list);
		} 
   	 catch (SQLException e) 
   	 {
			
			e.printStackTrace();
		}
    }
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
        	FileChooser chooser=new FileChooser();
	    	
        	chooser.setTitle("Select Path:");
        	
        	chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                    
                );
        	 File file=chooser.showSaveDialog(null);
        	String filePath=file.getAbsolutePath();
        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
        	{
        		filePath=filePath+".csv";
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Mobile ,Name,Address,City,Picture\n";
            writer.write(text);
            for (CustomerBean p : list)
            {
				text = p.getMob()+ "," + p.getName()+ "," + p.getAddress()+ "," + p.getCity()+ "," + p.getPic()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        }
    }
    @FXML
    void doExport(ActionEvent event) throws Exception {
    	writeExcel();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	con=DBConnection.doConnect();
        fillColumns();
        try {
			PreparedStatement pst = con.prepareStatement("select distinct mob from customers order by mob");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {  
		            comboMob.getItems().addAll(rst.getString("mob")); 
		       }
		     new AutoCompleteComboBoxListener<String>(comboMob);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
