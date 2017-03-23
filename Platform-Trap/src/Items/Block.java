package Items;

import dad.juego.GameApp;
import dad.juego.GameplayAttributes;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane{
	
	Image blocksImg = new Image(getClass().getResourceAsStream("/img/Escenario.png"));

	ImageView block;
	
	String type;
	
	public enum BlockType {
        FLOOR,PLATFORM,DOOR,TRAP_PLATFORM,TRAP,KEY
    }
	
	public Block(BlockType blockType, int x, int y){
		
		block = new ImageView(blocksImg);
		block.setFitWidth(GameApp.BLOCK_SIZE);
		block.setFitHeight(GameApp.BLOCK_SIZE);
		setTranslateX(x);
        setTranslateY(y);
        
        this.type = blockType.name();
        
        switch (blockType) {
        
        case FLOOR:
	        block.setViewport(new Rectangle2D(0, 211, 70, 16));
	        block.setFitWidth(GameApp.BLOCK_SIZE * 1);
	        break;
 
        case PLATFORM:
            block.setViewport(new Rectangle2D(0, 211, 70, 16));
            block.setFitWidth(GameApp.BLOCK_SIZE * 3);
            break;
       
        case DOOR:
        	 block.setViewport(new Rectangle2D(0, 340, 50, 96));
             block.setFitWidth(GameApp.BLOCK_SIZE * 1);
             block.setFitHeight(GameApp.BLOCK_SIZE * 2);
        	break;
        case TRAP:
        	block.setViewport(new Rectangle2D(287, 185, 100, 58));
        	block.setFitWidth(GameApp.BLOCK_SIZE*1);
        	block.setFitHeight(GameApp.BLOCK_SIZE*1);
        	break;
        case TRAP_PLATFORM:
        	block.setViewport(new Rectangle2D(0, 211, 70, 16));
        	block.setFitWidth(GameApp.BLOCK_SIZE*1);
        	block.setFitHeight(GameApp.BLOCK_SIZE*1);
        	break;
        	
        case KEY:
        	block.setViewport(new Rectangle2D(350, 250, 58, 110));
        	block.setFitHeight(GameApp.BLOCK_SIZE);
        	block.setFitWidth(GameApp.BLOCK_SIZE);
        	break;
    }
      
        getChildren().add(block);
        GameApp.platformas.add(this);
        GameApp.gameRoot.getChildren().add(this);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
