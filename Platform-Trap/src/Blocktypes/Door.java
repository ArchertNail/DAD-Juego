package Blocktypes;

import Items.Block;

public class Door extends Block {

	private int destination;
	

	public Door(BlockType Door, int x, int y, int destination) {
		
		super(BlockType.DOOR, x, y);
		
		this.destination = destination;
		
	}
	
	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	
	
}
