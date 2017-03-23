package levelInterpreter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;import javax.swing.JList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Blocktypes.Door;
import Blocktypes.Key;
import Blocktypes.Trap;
import Blocktypes.TrapBlock;
import Items.Block;
import Items.LevelData;
import Items.Block.BlockType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LevelInterpreter {

	public static final int BLOCK_SIZE = 35;
	
	//public File levelData ;
	
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	Document doc;
	
	NodeList levelNodes;

	int levelNumber;
	
	public static Pane gameRoot;
	
	public Character player;
	private int levelWidth;

	public int spawnDoor;
	public int spawnX, spawnY;
	
	public Image backgroundImg;
	
 	private String[] levelMap;
 	
 	public LevelInterpreter() throws ParserConfigurationException, SAXException, IOException{
 		
 		backgroundImg = new Image("img/fondoJuego.png");

 		InputStream levelData = this.getClass().getResourceAsStream("levelSchema.xml");
 		dbFactory = DocumentBuilderFactory.newInstance();
 		dBuilder = dbFactory.newDocumentBuilder();
 		doc = dBuilder.parse(levelData); 		
 		
 	}
	
 	public Pane loadLevel(int levelNumber, String string){
 		gameRoot = new Pane();
 		
 		this.levelNumber = levelNumber;
 		String rawLevelMap=""; 
 		String destinationsMap = "";
 		
 		ArrayList<Integer> doorDestinations = new ArrayList<>();
 		
 		Pane levelPane = new Pane();
 		
 		levelNodes = doc.getChildNodes().item(0).getChildNodes();
 		

 		
 		for (int i = 0; i < levelNodes.getLength(); i++) {
 			Node act = levelNodes.item(i);
 			
 			if (act.getNodeType() == Node.ELEMENT_NODE){
 				
 				if (act.getAttributes().getNamedItem("levelNumber").getNodeValue().equals(Integer.toString(levelNumber))){
 					
 					Element element = (Element) act; 
 					
 					rawLevelMap =element.getElementsByTagName("LevelMap").item(0).getTextContent();
 					
 					destinationsMap = element.getChildNodes().item(3).getFirstChild().getTextContent();
 					
 					spawnDoor = Integer.valueOf(element.getChildNodes().item(5).getFirstChild().getTextContent());
 					
 					System.out.println(destinationsMap);
 					
 				}
 			}
 		}
 		
 		System.out.println(destinationsMap);
 		
 		if (destinationsMap.contains(",")){
 			String [] rawDestinationsMap = destinationsMap.split(",");
 	 		
 			for (int i = 0; i < rawDestinationsMap.length; i++) {
 				doorDestinations.add(Integer.valueOf(rawDestinationsMap[i]));
 			}
 		}
 		else{
 			doorDestinations.add(Integer.valueOf(destinationsMap));
 		} 		
 		
        levelMap = rawLevelMap.split(",");
        
    	ImageView backgroundIV = new ImageView(backgroundImg);
		backgroundIV.setFitHeight(levelMap.length*BLOCK_SIZE);  //CANTIDAD DE FILA X TAMAÑO BLOQUES
        backgroundIV.setFitWidth(levelMap[0].length()*BLOCK_SIZE);   //Columnas * Bloques
        
        levelPane.getChildren().add(backgroundIV);
        
        levelWidth = levelMap[0].length()*BLOCK_SIZE;

        int DoorCounter =0;
        
        for(int i=0; i<levelMap.length; i++){

        	String line = levelMap[i]; 

        	for(int j=0; j<line.length();j++){
        		
        		//System.out.print("- "+line.charAt(j)+" -");
        		
        		switch(line.charAt(j)){
					case '0':
						//System.out.print(j*BLOCK_SIZE+"-"+ i*BLOCK_SIZE);
						
						break;
						
					case '1':
						Block Floor = new Block(Block.BlockType.FLOOR, j * BLOCK_SIZE, i * BLOCK_SIZE);
						
						//System.out.print(j*BLOCK_SIZE+"-"+ i*BLOCK_SIZE);
						
						levelPane.getChildren().add(Floor);
	                    break;
	                    
					case '2':
						Block platform = new Block(Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);
						
						//System.out.print(j*BLOCK_SIZE+"-"+ i*BLOCK_SIZE);
						
						levelPane.getChildren().add(platform);
						break;
	                 
					case '3':
						Door door = new Door(BlockType.DOOR, j*BLOCK_SIZE, i*BLOCK_SIZE, doorDestinations.get(DoorCounter)) ;
						
						System.out.println("DEFINIENDO SPAWN+-->"+j);
						
						if (j==0 && string.equals("west")){
							System.out.println("EN PRINCIPIO");
							spawnX = (j*BLOCK_SIZE);
							spawnY = (i*BLOCK_SIZE);
							spawnX = spawnX+BLOCK_SIZE+10;
						}
						else if(string.equals("east")){
							System.out.println("EN FINAL");
							int spawnx= levelWidth-70;
							spawnX = (spawnx-BLOCK_SIZE);
							spawnY = (i*BLOCK_SIZE);							
							//spawnX = spawnX-BLOCK_SIZE-10;
						}
				
						DoorCounter++;
						
						//System.out.print(j*BLOCK_SIZE+"-"+ i*BLOCK_SIZE);
						
						levelPane.getChildren().add(door);
						
						break;
					case '4':
						System.out.println("ITS A TRAP");
						Block trap = new Trap( j * BLOCK_SIZE, i * BLOCK_SIZE);
						
						//System.out.print(j*BLOCK_SIZE+"-"+ i*BLOCK_SIZE);
						
						levelPane.getChildren().add(trap);
						
						break;
						
					case '5':
						System.out.println("CECI EST A TRAP");
						Block trapBlock = new TrapBlock(j * BLOCK_SIZE, i*BLOCK_SIZE);
						
						levelPane.getChildren().add(trapBlock);
						break;
						
					case '6':
						Block key = new Key(j*BLOCK_SIZE,i*BLOCK_SIZE);
						levelPane.getChildren().add(key);
						break;
        		}
        		
        		
        	}
        	//System.out.println(",");
        }
 		return levelPane;
 	}
 	
 	public int[] getSpawnPoint(){
 		int [] salida = {spawnX,spawnY};
 		return salida;
 	}
 	
	public static Pane getGameRoot() {
		return gameRoot;
	}
 	
	public int getLevelWidth() {
		return levelWidth;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}
	
}
