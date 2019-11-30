/**
 * Sample Skeleton for 'VehicleEntryLog.fxml' Controller Class
 */

package VehicleEntryLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import CustomerLog.CustomerBean;
import CustomerLog.CustomerLogController.AutoCompleteComboBoxListener;
import DBConnection.DBConnection;
import VehicleEntryLog.VehicleBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VehicleEntryLogController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboVehicleNo"
    private ComboBox<String> comboVehicleNo; // Value injected by FXMLLoader

    @FXML // fx:id="fromDate"
    private DatePicker fromDate; // Value injected by FXMLLoader

    @FXML // fx:id="ToDate"
    private DatePicker ToDate; // Value injected by FXMLLoader

    @FXML // fx:id="tblLog"
    private TableView<VehicleBean> tblLog; // Value injected by FXMLLoader

    ObservableList<VehicleBean> list;
    Connection con;
    int status;
    
    void fetchAllRecords(PreparedStatement pst)
    {
    	list=FXCollections.observableArrayList();
    	try{
        
        	ResultSet table= pst.executeQuery();
        		
        		while(table.next())
        		{
        			String vehicleno=table.getString("vehicleno");//col. name acc. to table
        			String vehicletype=table.getString("vehicletype");
        			String mob=table.getString("mob");
        			String floor=table.getString("floor");
        			Date entrydate = table.getDate("entrydate");
        			Time entrytime = table.getTime("entrytime");
        			Date exitdate = table.getDate("exitdate");
        			Time exittime = table.getTime("exittime");
        			status = table.getInt("status");
        			if(status == 0){
        				System.out.println(vehicleno+"  "+vehicletype+"  "+mob+"   "+floor+"  "+entrydate+"  "+entrytime+"   "+exitdate+"   "+exittime);
            			System.out.println("******************************************************************");
            			VehicleBean sb=new VehicleBean(vehicleno,vehicletype,mob,floor,entrydate,entrytime,exitdate,exittime);
            			list.add(sb);}
        			else
        			{
        				System.out.println(vehicleno+"  "+vehicletype+"  "+mob+"   "+floor+"  "+entrydate+"  "+entrytime+"   "+"-"+"   "+"-");
            			System.out.println("******************************************************************");
            			VehicleBean sb=new VehicleBean(vehicleno,vehicletype,mob,floor,entrydate,entrytime);
            			list.add(sb);
        			}

        		}
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    	
    }
    void fillColumns()
    {
    	TableColumn<VehicleBean, String> vehicleno=new TableColumn<VehicleBean, String>("Vehicle Number");
    	vehicleno.setCellValueFactory(new PropertyValueFactory<>("vehicleno"));

     	TableColumn<VehicleBean, String> vehicletype=new TableColumn<VehicleBean, String>("Vehicle Type");
     	vehicletype.setCellValueFactory(new PropertyValueFactory<>("vehicletype"));
     	
     	TableColumn<VehicleBean, String> mob=new TableColumn<VehicleBean, String>("Mobile");
     	mob.setCellValueFactory(new PropertyValueFactory<>("mob"));
     	
     	TableColumn<VehicleBean, String> floor=new TableColumn<VehicleBean, String>("Floor");
     	floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
     	
     	TableColumn<VehicleBean, Date> entrydate=new TableColumn<VehicleBean, Date>("Entry Date");
     	entrydate.setCellValueFactory(new PropertyValueFactory<>("entrydate"));
     	
     	TableColumn<VehicleBean, Time> entrytime=new TableColumn<VehicleBean, Time>("Entry Time");
     	entrytime.setCellValueFactory(new PropertyValueFactory<>("entrytime"));
     	
     	TableColumn<VehicleBean, Date> exitdate=new TableColumn<VehicleBean, Date>("Exit Date");
     	exitdate.setCellValueFactory(new PropertyValueFactory<>("exitdate"));
     	
     	TableColumn<VehicleBean, Time> exittime=new TableColumn<VehicleBean, Time>("Exit Time");
     	exittime.setCellValueFactory(new PropertyValueFactory<>("exittime"));
     	
     	tblLog.getColumns().clear();
     	tblLog.getColumns().addAll(vehicleno,vehicletype,mob,floor,entrydate,entrytime,exitdate,exittime);
  	
    }
    
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
    
    @FXML
    void doCustomerLog(MouseEvent event) {
    	try {
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerLog/CustomerLog.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  LOG");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			   //Scene scene1=(Scene)tblLog.getScene();
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
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/Dashboard.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  DASHBOARD");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblLog.getScene();
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
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ParkingLayout/ParkingLayout.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("PARKING  LAYOUT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblLog.getScene();
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
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleEntry/VehicleEntry.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  ENTRY");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblLog.getScene();
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
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerReg/Customers.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("CUSTOMER  REGISTERATION  PORTAL");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblLog.getScene();
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
    		Scene scene1=(Scene)tblLog.getScene();
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VehicleExit/VehicleExit.fxml")); 
			Scene scene = new Scene(root,scene1.getWidth(),scene1.getHeight());
			
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("VEHICLE  EXIT");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
			//to hide the opened window
			 
			//Scene scene1=(Scene)tblLog.getScene();
			   scene1.getWindow().hide();
			 
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void doFind(ActionEvent event) {
    	try {
 			
      		 PreparedStatement	pst=con.prepareStatement("select * from vehicle_entry where vehicleno=? order by status");
      		 pst.setString(1,comboVehicleNo.getSelectionModel().getSelectedItem());
      		
      		 fetchAllRecords(pst);
          	 
      	     tblLog.setItems(list);
   		} 
      	 catch (SQLException e) 
      	 {
   			
   			e.printStackTrace();
   		}
    }
    
    @FXML
    void doSearch(ActionEvent event) throws ParseException {
    	try {
 			
     		PreparedStatement	pst=con.prepareStatement("select * from vehicle_entry where (entrydate>=? and exitdate<=?) or (entrydate<=? and exitdate>=?) order by status");
     		
     		java.sql.Date fromdate = java.sql.Date.valueOf(fromDate.getValue());
     		java.sql.Date todate = java.sql.Date.valueOf(ToDate.getValue());
     		
     
     		if(fromdate.after(todate)){
     			Alert al=new Alert(AlertType.WARNING);
     			al.setTitle("ERROR");
     			al.setContentText("From Date should be before To Date !!!");
     			al.setHeaderText("NO VALID DATA FOUND");
     			al.show();
     			tblLog.setItems(null);
     		}
     		else{
     		pst.setDate(1,fromdate);
     		pst.setDate(2,todate);
     		pst.setDate(3,todate);
     		pst.setDate(4,fromdate);
     		
     		 fetchAllRecords(pst);
         	 
     	     tblLog.setItems(list);
     		
  		}
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
            String text="Vehicle No. ,Vehicle Type,Mobile,Floor,Entry Date,Entry Time,Exit Date,Exit Time\n";
            writer.write(text);
            for (VehicleBean p : list)
            {
				text = p.getVehicleno()+ "," + p.getVehicletype()+ "," + p.getMob()+ "," + p.getFloor()+ "," + p.getEntrydate()+ "," + p.getEntrytime()+ "," + p.getExitdate()+ "," + p.getExittime()+"\n";
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
			PreparedStatement pst = con.prepareStatement("select distinct vehicleno from vehicle_entry order by vehicleno");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {  
				comboVehicleNo.getItems().addAll(rst.getString("vehicleno")); 
		       }
		     new AutoCompleteComboBoxListener<String>(comboVehicleNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
