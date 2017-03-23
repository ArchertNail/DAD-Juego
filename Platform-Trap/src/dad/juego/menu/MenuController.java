package dad.juego.menu;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.prism.impl.BaseMesh.FaceMembers;

import dad.juego.GameApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {

	private BorderPane vista;
	private StackPane stack;
	private Stage stage;
	private Stage gameStage;
	
	private Boolean sinSonido;
	private MediaPlayer player;
	
	private Scene sceneGame;
	
	public MenuController(Stage stage) {
				
		this.stage=stage;
		FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaMenu.fxml"));
		loader.setController(this);
		try {
			vista=loader.load();
		} catch (IOException e) {
     	e.printStackTrace();}
		
		URL resource = getClass().getResource("/img/menu.mp3");
			    player = new MediaPlayer( new Media(resource.toString()));
			    player.play();
		sinSonido=false;
	}
	
	
	
	private GameApp Juego;
	
	@FXML
    private JFXButton newGameButton;
    @FXML
    private JFXButton comoJugarButton;
    @FXML
    private JFXButton salirButton;
    @FXML
    private JFXToggleButton soundToggle;
    @FXML
    private JFXColorPicker colorPicker;
    
    
    
    @FXML
    void onColorPickerAction(ActionEvent event) {

    }


    @FXML
    void onNewGameAction(ActionEvent event) throws Exception {

    	
    	
    	player.stop();
//    	if (stage.getScene()!= null){
//    		stage.getScene().setRoot(null);
//    	}
    	
    	stage.close();
    	
    	gameStage = new Stage();
    	Juego = new GameApp(this,sinSonido);

    	if (sceneGame == null) {
    		sceneGame = new Scene(Juego.getView());
    	}
		Juego.setScene(sceneGame);
    	gameStage.setScene(sceneGame);
    	gameStage.setResizable(false);
    	
    	gameStage.show();
    	
    }

    public void showMenuAgain(){
    	
    	gameStage.close();
    	
    	Juego.getTimer().stop();
    	
    	stage.show();
    	
    	Alert fin = new Alert(AlertType.CONFIRMATION);
    	fin.setTitle("FIN");
    	fin.setContentText("Has ganado");
    }
    
    public MenuController getMenucontroller(){
		return this;
    	
    }
    
    @FXML
    void onSalirAction(ActionEvent event) {

    	stage.close();
    }

    @FXML
    void soundToggleAction(ActionEvent event) {
    	if(sinSonido){
    		sinSonido=false;
    		player.play();
    	}
    	else{
    		sinSonido=true;
    		player.stop();
    	}
    }
	
    
    
    @FXML
    void onAutoresAction(ActionEvent event) {

    	Alert alert = new Alert(AlertType.INFORMATION);
    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/img/iconoapp.png").toString()));
    	alert.setTitle("Informacion");
    	alert.setHeaderText("Informacion de integrantes del grupo:");
    	alert.setContentText("Este juego a sido desarollado para DAD 2ºDAM IES PEREZ MINIK por Killian Dario Deniz,Hector Mariño Fernandez,& Ricardo Vargas.");
    	alert.showAndWait();
    }
    
    
    
    public BorderPane getVista() {
		return vista;
	}

	public void setVista(BorderPane vista) {
		this.vista = vista;
	}
	
	
	
	
	
}
