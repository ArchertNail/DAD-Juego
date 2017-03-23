package dad.juego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox.KeySelectionManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Blocktypes.Door;
import Items.Block;
import Items.Block.BlockType;
import Items.Character;
import Items.LevelData;
import dad.juego.menu.MenuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import levelInterpreter.LevelInterpreter;

public class GameApp {

	public static ArrayList<Block> platformas = new ArrayList<>(); 
	private HashMap<KeyCode,Boolean> keys = new HashMap<>();
	
	private MenuController menuController;
	
	Image backgroundImg = new Image(getClass().getResourceAsStream("/img/background.png"));
	public static final int BLOCK_SIZE = 35;
	public static final int SAKURA_SIZE = 55;
	
	public static final Pane appRoot = new Pane();
	public static Pane gameRoot = new Pane();
	
	public Character player;
	
	int levelNumber = 0;
	private String EntryDirection;
	
	private int levelWidth;
	private LevelInterpreter levelInterpreter;
	
	private GameplayAttributes gameplayStats;
	
	private Boolean sinSonido;
	
	private URL ostBackground;
	private URL ostCongrats;
	
	private MediaPlayer ost ;
	private MediaPlayer congrats;
	
	//Imagenes para la animacion
	Image skuraQuieta;
	Image skuraCaminando;
	Image skuraSaltando;
	
	AnimationTimer timer;
	
	public void stopBackgroundMusic(){
		ost.stop();
	}
	
	private void initContent() {
		
		ostBackground =getClass().getResource("/img/ost.mp3");
		ostCongrats =getClass().getResource("/img/congrats.mp3");
		
		ost = new MediaPlayer(new Media(ostBackground.toString()));
		congrats = new MediaPlayer(new Media (ostCongrats.toString()));
		
		skuraQuieta = new Image(getClass().getResourceAsStream("/img/skura.png"));
		skuraCaminando = new Image(getClass().getResourceAsStream("/img/skuraCaminando.png"));
		skuraSaltando = new Image(getClass().getResourceAsStream("/img/skuraSalto.png"));
		
		gameplayStats= new GameplayAttributes();
		
		if (!sinSonido){
			ost.play();
		}
		
		
		
		LoadLevel(1,"west");
		
	}

	public void LoadLevel(int levelNumber, String direccionEntrada) {
		int[] spawnPoint;
		
		System.out.println(direccionEntrada);
		
		platformas = new ArrayList<>();
		
		appRoot.getChildren().remove(gameRoot);
		appRoot.setId("panelsito");
		
		gameRoot = new Pane();

		System.out.println(gameRoot.getChildren().size());
		
		
		
		try {
			
			this.levelInterpreter = new LevelInterpreter();
			
			gameRoot=this.levelInterpreter.loadLevel(levelNumber,direccionEntrada);
			
			levelWidth= this.levelInterpreter.getLevelWidth();
			
			spawnPoint = levelInterpreter.getSpawnPoint();
			
	        player =new Character(this);
	   
	        System.out.println(spawnPoint[0]+" + "+spawnPoint[1]);
	        
	        player.setTranslateX(spawnPoint[0]);
	        
	        player.setTranslateY(spawnPoint[1]);
	        
	        this.levelNumber = levelNumber;
	        this.EntryDirection = direccionEntrada;
	        
	        gameplayStats.restartKey();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                
        System.out.println(levelWidth);
        
        gameRoot.getChildren().add(player);
        appRoot.getChildren().addAll(gameRoot);
        
        
        player.animQuieto.play();
	}
	
	public void resetToSpawn(){
		int[] spawnPoint = this.levelInterpreter.getSpawnPoint();
		player.setTranslateX(spawnPoint[0]);
		player.setTranslateY(spawnPoint[1]);
	}
	
	private void update(){
		
		//Si esta en el suelo y esta quieta
				if(!player.isEnAire() && !isPressed(KeyCode.LEFT) && !isPressed(KeyCode.RIGHT) ){
			    	player.getImageView().setImage(skuraQuieta);
			    	player.animCaminar.stop();
			    	player.animSaltar.stop();
			    	player.animQuieto.play();
				}
				
				
				//MOVERSE A LA DERECHA O IZQUIERDA	
			    if(isPressed(KeyCode.LEFT) && player.getTranslateX()>=5){
			    	player.animQuieto.stop();
			    	if(!player.isEnAire()){
			    		player.animSaltar.stop();
				    	player.getImageView().setImage(skuraCaminando);
				    	player.setScaleX(-1);
				        player.animCaminar.play();
			    	}
			        player.moveX(-3);
			    }
			    
			    if(isPressed(KeyCode.RIGHT) && isPressed(KeyCode.Z) && player.getTranslateX()+40 <=levelWidth-5){
			    	player.animQuieto.stop();	    	
			    	if(!player.isEnAire()){
			    		player.animSaltar.stop();
				    	player.getImageView().setImage(skuraCaminando);
				        player.setScaleX(1);	
				        player.animCaminar.play();
			    	}
			    	
			    	player.moveX(3);
			    }
			    
			    // ------   CORRER

			    if(isPressed(KeyCode.LEFT) && isPressed(KeyCode.Z) && player.getTranslateX()>=5){
			    	player.animQuieto.stop();
			    	if(!player.isEnAire()){
			    		player.animSaltar.stop();
				    	player.getImageView().setImage(skuraCaminando);
				    	player.setScaleX(-1);
				        player.animCaminar.play();
			    	}
			        player.moveX(-3);
			    }
			    
			    if(isPressed(KeyCode.RIGHT) && player.getTranslateX()+40 <=levelWidth-5){
			    	player.animQuieto.stop();	    	
			    	if(!player.isEnAire()){
			    		player.animSaltar.stop();
				    	player.getImageView().setImage(skuraCaminando);
				        player.setScaleX(1);	
				        player.animCaminar.play();
			    	}
			    	
			    	player.moveX(3);
			    }
			    

			    //CONTROL DE SALTO Y GRAVEDAD
				if(player.playerVelocity.getY()<10){                         
					player.playerVelocity = player.playerVelocity.add(0,1);
			    }
				
				
				if(isPressed(KeyCode.UP) && player.getTranslateY()>=5){
					player.animQuieto.stop();
					player.getImageView().setImage(skuraSaltando);
					player.animSaltar.play();
					player.jumpPlayer();
			    }
				
				 player.moveY((int)player.playerVelocity.getY());
	}
	
	public void runTrap(Block trap){
		platformas.remove(trap);
		gameRoot.getChildren().remove(trap);
	}
	
	public void pickUpKey(Block key){
		platformas.remove(key);
		gameRoot.getChildren().remove(key);
		gameplayStats.givekey();
	}
	
	//devuelve la tecla pulsada
	private boolean isPressed(KeyCode key){
		return keys.getOrDefault(key,false);
		
	}
	
	public void playCongrats(){
		congrats= new MediaPlayer(new Media(ostCongrats.toString()));
		congrats.play();
	}
	public void stopCongrats(){
		congrats.stop();
	}
	
	public GameApp(MenuController menucontroller, Boolean sinSonido) {
		this.menuController = menucontroller;
		this.sinSonido=sinSonido;
		
		initContent();
		
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        
        
	}
	

	public int getLevelNumber() {
		return levelNumber;
	}

	public String getEntryDirection() {
		return EntryDirection;
	}

	public GameplayAttributes getGameplayStats() {
		return gameplayStats;
	}

	public MenuController getMenuController() {
		return menuController;
	}
	
	public Pane getView(){
		return appRoot;
	}

	public void setScene(Scene sceneGame) {
		sceneGame.setOnKeyPressed(event-> keys.put(event.getCode(), true));
		sceneGame.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
           
        });
		
	}

	public AnimationTimer getTimer() {
		return timer;
	}


	
}
