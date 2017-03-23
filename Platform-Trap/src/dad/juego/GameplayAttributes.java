package dad.juego;

public class GameplayAttributes {

	private int deathCounter;
	private boolean hasKey;
	
	public GameplayAttributes() {
		this.deathCounter=0;
		this.hasKey=false;
	}
	
	public void givekey(){
		this.hasKey=true;
	}
	public void restartKey(){
		this.hasKey=false;
	}
	
	public void die(){
		deathCounter++;
	}
	
	public int getDeath(){
		return deathCounter;
	}
	
	public boolean hasKey(){
		return hasKey;
	}
	
}
