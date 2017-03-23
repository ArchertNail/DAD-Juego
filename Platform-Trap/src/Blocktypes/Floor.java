package Blocktypes;

import java.awt.geom.Rectangle2D;

import Items.Block;

public class Floor extends Block{
 
	private Rectangle2D viewport;
	
	public Floor(int x, int y) {
		super(BlockType.FLOOR, x, y);
	}

}
