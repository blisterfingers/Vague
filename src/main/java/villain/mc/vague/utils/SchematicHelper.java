package villain.mc.vague.utils;

import java.io.InputStream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import villain.mc.vague.Reference;

public class SchematicHelper {

	public static Schematic loadSchematic(String name){
		try {
			InputStream is = Schematic.class.getClassLoader().getResourceAsStream("assets/" + Reference.MOD_ID + "/schematics/" + name + ".schematic");
			NBTTagCompound nbtData = CompressedStreamTools.readCompressed(is);
			short width = nbtData.getShort("Width");
			short height = nbtData.getShort("Height");
			short length = nbtData.getShort("Length");
			
			byte[] blocks = nbtData.getByteArray("Blocks");
			byte[] data = nbtData.getByteArray("Data");
			
			NBTTagList tileEnts = nbtData.getTagList("TileEntities", NBT.TAG_COMPOUND);
			
			NBTTagCompound mappings = nbtData.getCompoundTag("SchematicaMapping");
			
			is.close();
			
			return new Schematic(mappings, tileEnts, width, height, length, blocks, data);
		}
		catch(Exception e){
			LogHelper.error("Could not load schematic: " + e.toString());
			return null;
		}
	}	
	
	public static void placeSchematic(World world, Schematic schematic, int centreX, int baseY, int centreZ){
		int startX = centreX - (schematic.getWidth() / 2);
		int startZ = centreZ - (schematic.getLength() / 2);
		
		int i = 0;
		Block block;
		for(int y = 0; y < schematic.getHeight(); y++){
			for(int z = 0; z < schematic.getLength(); z++){
				for(int x = 0; x < schematic.getWidth(); x++){
					block = Block.getBlockById(schematic.getBlocks()[i]);
					if(!(block instanceof BlockAir) && !(block instanceof BlockDoor) 
							&& !(block instanceof BlockBed) && !(block instanceof BlockStairs)){
						world.setBlock(startX + x, baseY + y, startZ + z, block);
						world.setBlockMetadataWithNotify(startX + x, baseY + y, startZ + z, schematic.getData()[i], 3);
					}
					i++;
				}
			}
		}
		
		i = 0;
		for(int y = 0; y < schematic.getHeight(); y++){
			for(int z = 0; z < schematic.getLength(); z++){
				for(int x = 0; x < schematic.getWidth(); x++){
					block = Block.getBlockById(schematic.getBlocks()[i]);
					if(block instanceof BlockDoor || block instanceof BlockBed || block instanceof BlockStairs){
						world.setBlock(startX + x, baseY + y, startZ + z, block);
						world.setBlockMetadataWithNotify(startX + x, baseY + y, startZ + z, schematic.getData()[i], 3);
					}
					i++;
				}
			}
		}
	}
	
	
	
	public static int[] blockCoordsRotation(int blockX, int blockY, int blockZ, int rotation){
		int[] coords = { blockX, blockZ };
		
		switch(rotation){
			case 1: coords[0] = -blockZ; coords[1] = blockX; break;
			case 2: coords[0] = -blockX; coords[1] = -blockZ; break;
			case 3: coords[0] = blockZ; coords[1] = -blockX; break;
		}
		
		return coords;
	}
	
	public static Rotations signRotations = new Rotations(2, 5, 3, 4);
	public static Rotations stairsRotations = new Rotations(2, 1, 3, 0);
	public static Rotations chestRotations = new Rotations(4, 2, 5, 3);
	public static Rotations pumpkinRotations = new Rotations(2, 3, 0, 1);
	public static Rotations torchRotations = new Rotations(4, 1, 3, 2);
	public static Rotations doorRotations = new Rotations(1, 2, 3, 0);
	
	public static int rotateMeta(int blockId, int meta, int rotation){
		if(rotation>0) {
            if(Block.getIdFromBlock(Blocks.torch)==blockId || Block.getIdFromBlock(Blocks.redstone_torch)==blockId)
                return torchRotations.getMeta( (torchRotations.getSide(meta)+rotation)%4 );

            if(meta<4 && Block.getBlockById(blockId) instanceof BlockDoor)
                return doorRotations.getMeta( (doorRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.wall_sign)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.ladder)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.chest)==blockId || Block.getIdFromBlock(Blocks.ender_chest)==blockId)
                return chestRotations.getMeta( (chestRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.furnace)==blockId || Block.getIdFromBlock(Blocks.lit_furnace)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.pumpkin)==blockId || Block.getIdFromBlock(Blocks.lit_pumpkin)==blockId)
                return pumpkinRotations.getMeta( (pumpkinRotations.getSide(meta)+rotation)%4 );

            if(Block.getBlockById(blockId) instanceof BlockStairs)
                return stairsRotations.getMeta((stairsRotations.getSide(meta)+rotation)%4);
        }
        return meta;
	}
			
			
			
	public static class Rotations {
		public int[] rs;
		
		public Rotations(int ...r){
			rs = r;
		}
		
		public int getSide(int meta){
			for(int i = 0; i < rs.length; i++){
				if(rs[i] == meta) return i;
			}
			return -1;
		}
		
		public int getMeta(int side){
			return rs[side];
		}
	}
}