package villain.mc.vague.utils;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class WorldHelper {
	
	public static final int NEIGHBOUR_YNEG = 0;
	public static final int NEIGHBOUR_YPOS = 1;
	public static final int NEIGHBOUR_XNEG = 2;
	public static final int NEIGHBOUR_XPOS = 3;
	public static final int NEIGHBOUR_ZNEG = 4;
	public static final int NEIGHBOUR_ZPOS = 5;
	
	//ÁLL
	
	public static final int NEIGHBOUR_YNEG_XNEG_ZPOS = 0;
	public static final int NEIGHBOUR_YNEG_XCEN_ZPOS = 1;
	public static final int NEIGHBOUR_YNEG_XPOS_ZPOS = 2;
	
	public static final int NEIGHBOUR_YNEG_XNEG_ZCEN = 3;
	public static final int NEIGHBOUR_YNEG_XCEN_ZCEN = 4;
	public static final int NEIGHBOUR_YNEG_XPOS_ZCEN = 5;
	
	public static final int NEIGHBOUR_YNEG_XNEG_ZNEG = 6;
	public static final int NEIGHBOUR_YNEG_XCEN_ZNEG = 7;
	public static final int NEIGHBOUR_YNEG_XPOS_ZNEG = 8;
	
	public static final int NEIGHBOUR_YCEN_XNEG_ZPOS = 9;
	public static final int NEIGHBOUR_YCEN_XCEN_ZPOS = 10;
	public static final int NEIGHBOUR_YCEN_XPOS_ZPOS = 11;
	
	public static final int NEIGHBOUR_YCEN_XNEG_ZCEN = 12;
	//public static final int NEIGHBOUR_YCEN_XCEN_ZCEN = 1;
	public static final int NEIGHBOUR_YCEN_XPOS_ZCEN = 13;
	
	public static final int NEIGHBOUR_YCEN_XNEG_ZNEG = 14;
	public static final int NEIGHBOUR_YCEN_XCEN_ZNEG = 15;
	public static final int NEIGHBOUR_YCEN_XPOS_ZNEG = 16;
	
	public static final int NEIGHBOUR_YPOS_XNEG_ZPOS = 17;
	public static final int NEIGHBOUR_YPOS_XCEN_ZPOS = 18;
	public static final int NEIGHBOUR_YPOS_XPOS_ZPOS = 19;
	
	public static final int NEIGHBOUR_YPOS_XNEG_ZCEN = 20;
	public static final int NEIGHBOUR_YPOS_XCEN_ZCEN = 21;
	public static final int NEIGHBOUR_YPOS_XPOS_ZCEN = 22;
	
	public static final int NEIGHBOUR_YPOS_XNEG_ZNEG = 23;
	public static final int NEIGHBOUR_YPOS_XCEN_ZNEG = 24;
	public static final int NEIGHBOUR_YPOS_XPOS_ZNEG = 25;

	public static Block[] getNeighbours(IBlockAccess world, int x, int y, int z){
		Block[] blocks = new Block[6];
		
		blocks[0] = world.getBlock(x, y - 1, z);
		blocks[1] = world.getBlock(x, y + 1, z);
		
		blocks[2] = world.getBlock(x - 1, y, z);
		blocks[3] = world.getBlock(x + 1, y, z);
		
		blocks[4] = world.getBlock(x, y, z - 1);
		blocks[5] = world.getBlock(x, y, z + 1);
		
		return blocks;
	}
	
	public static Block[] getAllNeighbours(IBlockAccess world, int x, int y, int z){
		Block[] blocks = new Block[26];
		
		blocks[NEIGHBOUR_YNEG_XNEG_ZPOS] = world.getBlock(x - 1, 	y - 1, 		z + 1);
		blocks[NEIGHBOUR_YNEG_XCEN_ZPOS] = world.getBlock(x, 		y - 1, 		z + 1);
		blocks[NEIGHBOUR_YNEG_XPOS_ZPOS] = world.getBlock(x + 1, 	y - 1, 		z + 1);
		
		blocks[NEIGHBOUR_YNEG_XNEG_ZCEN] = world.getBlock(x - 1, 	y - 1, 		z);
		blocks[NEIGHBOUR_YNEG_XCEN_ZCEN] = world.getBlock(x, 		y - 1, 		z);
		blocks[NEIGHBOUR_YNEG_XPOS_ZCEN] = world.getBlock(x + 1, 	y - 1, 		z);
		
		blocks[NEIGHBOUR_YNEG_XNEG_ZNEG] = world.getBlock(x - 1, 	y - 1, 		z - 1);
		blocks[NEIGHBOUR_YNEG_XCEN_ZNEG] = world.getBlock(x, 		y - 1, 		z - 1);
		blocks[NEIGHBOUR_YNEG_XPOS_ZNEG] = world.getBlock(x + 1,	y - 1, 		z - 1);
		
		blocks[NEIGHBOUR_YCEN_XNEG_ZPOS] = world.getBlock(x - 1, 	y, 			z + 1);
		blocks[NEIGHBOUR_YCEN_XCEN_ZPOS] = world.getBlock(x, 		y, 			z + 1);
		blocks[NEIGHBOUR_YCEN_XPOS_ZPOS] = world.getBlock(x + 1, 	y, 			z + 1);
		
		blocks[NEIGHBOUR_YCEN_XNEG_ZCEN] = world.getBlock(x - 1, 	y, 			z);
		blocks[NEIGHBOUR_YCEN_XPOS_ZCEN] = world.getBlock(x + 1, 	y, 			z);
		
		blocks[NEIGHBOUR_YCEN_XNEG_ZNEG] = world.getBlock(x - 1, 	y, 			z - 1);
		blocks[NEIGHBOUR_YCEN_XCEN_ZNEG] = world.getBlock(x, 		y, 			z - 1);
		blocks[NEIGHBOUR_YCEN_XPOS_ZNEG] = world.getBlock(x + 1, 	y, 			z - 1);
		
		blocks[NEIGHBOUR_YPOS_XNEG_ZPOS] = world.getBlock(x - 1, 	y + 1, 		z + 1);
		blocks[NEIGHBOUR_YPOS_XCEN_ZPOS] = world.getBlock(x,	 	y + 1, 		z + 1);
		blocks[NEIGHBOUR_YPOS_XPOS_ZPOS] = world.getBlock(x + 1, 	y + 1, 		z + 1);
		
		blocks[NEIGHBOUR_YPOS_XNEG_ZCEN] = world.getBlock(x - 1, 	y + 1, 		z);
		blocks[NEIGHBOUR_YPOS_XCEN_ZCEN] = world.getBlock(x,	 	y + 1, 		z);
		blocks[NEIGHBOUR_YPOS_XPOS_ZCEN] = world.getBlock(x + 1, 	y + 1, 		z);
		
		blocks[NEIGHBOUR_YPOS_XNEG_ZNEG] = world.getBlock(x - 1, 	y + 1, 		z - 1);
		blocks[NEIGHBOUR_YPOS_XCEN_ZNEG] = world.getBlock(x,	 	y + 1, 		z - 1);
		blocks[NEIGHBOUR_YPOS_XPOS_ZNEG] = world.getBlock(x + 1, 	y + 1, 		z - 1);
		
		return blocks;
	}
}