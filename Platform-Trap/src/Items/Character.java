package Items;

import Blocktypes.Door;
import Items.Block.BlockType;
import dad.juego.GameApp;
import dad.juego.SpriteAnimation;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane{
	
	private GameApp sceneryController;
	
	Image sakuraImg = new Image(getClass().getResourceAsStream("/img/skura.png"));
    ImageView imageView = new ImageView(sakuraImg);
    
    int count = 5;
    int columns = 5;
    int offsetX = 0;
    int offsetY = 0;
    int width = 33;
    int height = 55;
    public SpriteAnimation animQuieto;
    public SpriteAnimation animCaminar;
    public SpriteAnimation animSaltar;
    public SpriteAnimation animAtack;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;
    private boolean enAire = false;
    
	public Character(GameApp gameApp) {
		
		sceneryController = gameApp;
		
		animQuieto = new SpriteAnimation(this.imageView,Duration.millis(600),5,5,offsetX,offsetY,width,height);
		animCaminar = new SpriteAnimation(this.imageView,Duration.millis(600),6,6,offsetX,offsetY,width,height);
		animSaltar = new SpriteAnimation(this.imageView,Duration.millis(600),1,1,offsetX,offsetY,width,height);
		animAtack = new SpriteAnimation(this.imageView,Duration.millis(600),5,5,offsetX,offsetY,67,60);
        getChildren().addAll(this.imageView);
	}
	
	public void moveX(int value){
		
		boolean movingRight = value >0;
		
		
		for(int i=0; i<Math.abs(value); i++){  
			for (Block platform : GameApp.platformas) {
				
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					
					if(platform.getType().equals("DOOR")
							&& sceneryController.getGameplayStats().hasKey()){
                    	Door puelta = (Door) platform;
                    	
                    	System.out.println("Door, DESTINO: "+ puelta.getDestination() );
               
                    	if (value<0){
                    		sceneryController.LoadLevel(puelta.getDestination(), "east"); 
                    	}
                    	else{
                    		sceneryController.LoadLevel(puelta.getDestination(), "west" ); 
                    	}
                    	
                    	
                    	
                    	sceneryController.playCongrats();
                    	
                    	if (puelta.getDestination()==1){
                    		sceneryController.getMenuController().showMenuAgain();
                    	
                    	}
                    	
                    	return;
                    }
            		
					if (platform.getType().equals("KEY")){
						sceneryController.pickUpKey(platform);
						sceneryController.stopCongrats();
						return;
					}
					
					if(movingRight){						
						if (this.getTranslateX() + 33 == platform.getTranslateX()){
							this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
					}
					
					else{
						this.setTranslateX(this.getTranslateX() + 1);
						return;
					}
				}
			}
			
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1:-1));
		}
	}
	
	
	public void moveY(int value){
		
		boolean movingDown = value >0;
        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :GameApp.platformas){
               
            	if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                   
            		if(platform.getType().equals("TRAP")){
                    	sceneryController.LoadLevel(sceneryController.getLevelNumber(), sceneryController.getEntryDirection());
                        
                    	sceneryController.getGameplayStats().die();
//                    	if(sceneryController.getGameplayStats().getDeath()>3){
//                    		
//                    		sceneryController.stopBackgroundMusic();
//                    		
//                    		sceneryController.getMenuController().showMenuAgain();
//                    	
//                    	}
                    	
                    	return;
                    }
            		
            		if(platform.getType().equals("TRAP_PLATFORM")){
            			
            			sceneryController.runTrap(platform);
            			
            			return;
            		}
            		
            		if (platform.getType().equals("KEY")){
						sceneryController.pickUpKey(platform);
						return;
					}
            		
            		if(movingDown){
                        if(this.getTranslateY()+ GameApp.SAKURA_SIZE == platform.getTranslateY()){
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            enAire = false;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == platform.getTranslateY()+ GameApp.BLOCK_SIZE){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }
            
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            if(this.getTranslateY()>640){
                this.setTranslateX(0);
                this.setTranslateY(400);
                GameApp.gameRoot.setLayoutX(0);
            }
        }
	}
	
	
	public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-25);
            canJump = false;
            enAire = true;
        }
    }

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public boolean isEnAire() {
		return enAire;
	}

	public void setEnAire(boolean enAire) {
		this.enAire = enAire;
	}
	
	
	

}
