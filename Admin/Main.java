package Admin;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
			Scene scene = new Scene(root,1200,800);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("ADMIN  LOGIN  PAGE");
			Image icon = new Image("/Parking-Cicle-Icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
			
		} 
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
