package dad.juego.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class APP extends Application {

	MenuController menuController;
	Stage stage;
	String[] args;


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		menuController=new MenuController(primaryStage);
		primaryStage.setScene(new Scene(menuController.getVista()));
		primaryStage.setTitle("Menu");
		primaryStage.getIcons().add(new Image("/img/iconoapp.png"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	
     public static void main(String[] args) {
		launch(args);

	}
     
     
     public Stage getStage() {
 		return stage;
 	}


 	public void setStage(Stage stage) {
 		this.stage = stage;
 	}

}
