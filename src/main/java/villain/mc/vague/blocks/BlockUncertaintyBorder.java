package villain.mc.vague.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import villain.mc.vague.Init;
import villain.mc.vague.Reference;
import villain.mc.vague.tileents.TileEntityUncertaintyBorder;

public class BlockUncertaintyBorder extends BlockBase implements ITileEntityProvider{

	private IIcon[] icons;
	public static int[] textureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 
			18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 
			3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 
			5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 
			21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20, 20, 16, 16, 28, 
			28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 
			18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 
			19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 
			22, 26, 17, 17, 22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 
			4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };
	
	public BlockUncertaintyBorder(){
		super("uncertaintyborder", Material.iron, true);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[47];
		for(int i = 0; i < 47; i++){
			icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "uncertainty/uncertainty-" + (i + 1));
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[0];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0){
			return icons[0];
		}
		
		boolean[] bitMatrix = new boolean[8];
		
		if(side == 0 || side == 1){
			bitMatrix[0] = world.getBlock(x - 1, 	y, 		z - 1) == this && world.getBlockMetadata(x - 1, 	y, 		z - 1) == 1;
			bitMatrix[1] = world.getBlock(x,		y,		z - 1) == this && world.getBlockMetadata(x, 		y, 		z - 1) == 1;
			bitMatrix[2] = world.getBlock(x + 1,	y,		z - 1) == this && world.getBlockMetadata(x + 1, 	y, 		z - 1) == 1;
			bitMatrix[3] = world.getBlock(x - 1,	y,		z) == this && world.getBlockMetadata(x - 1, 		y, 		z) == 1;
			bitMatrix[4] = world.getBlock(x + 1,	y,		z) == this && world.getBlockMetadata(x + 1, 		y, 		z) == 1;
			bitMatrix[5] = world.getBlock(x - 1, 	y,		z + 1) == this && world.getBlockMetadata(x - 1, 	y, 		z + 1) == 1;
			bitMatrix[6] = world.getBlock(x,		y,		z + 1) == this && world.getBlockMetadata(x, 		y, 		z + 1) == 1;
			bitMatrix[7] = world.getBlock(x + 1, 	y,		z + 1) == this && world.getBlockMetadata(x + 1, 	y, 		z + 1) == 1;
		}
		else if(side == 2 || side == 3){
			bitMatrix[0] = world.getBlock(x + (side == 2 ? 1 : -1), y + 1, z) == this && 	world.getBlockMetadata(x + (side == 2 ? 1 : -1), 	y + 1, 		z) == 1;
			bitMatrix[1] = world.getBlock(x,	y + 1,	z) == this && 						world.getBlockMetadata(x, 	y + 1, 		z) == 1;
			bitMatrix[2] = world.getBlock(x + (side == 3 ? 1 : -1),	y + 1, z) == this && 	world.getBlockMetadata(x + (side == 3 ? 1 : -1), 	y + 1, 		z) == 1;
			bitMatrix[3] = world.getBlock(x + (side == 2 ? 1 : -1), y, z) == this && 		world.getBlockMetadata(x + (side == 2 ? 1 : -1), 	y, 		z) == 1;
			bitMatrix[4] = world.getBlock(x + (side == 3 ? 1 : -1), y, z) == this && 		world.getBlockMetadata(x + (side == 3 ? 1 : -1), 	y, 		z) == 1;
			bitMatrix[5] = world.getBlock(x + (side == 2 ? 1 : -1), y - 1, z) == this && 	world.getBlockMetadata(x + (side == 2 ? 1 : -1), 	y - 1, 		z) == 1;
			bitMatrix[6] = world.getBlock(x, y - 1, z) == this && 							world.getBlockMetadata(x, 	y - 1, 		z) == 1;
			bitMatrix[7] = world.getBlock(x + (side == 3 ? 1 : -1), y - 1, z) == this && 	world.getBlockMetadata(x + (side == 3 ? 1 : -1), y - 1, z) == 1;
		}
		else if(side == 4 || side == 5){
			bitMatrix[0] = world.getBlock(x, 	y + 1, 	z + (side == 5 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y + 1, 		z + (side == 5 ? 1 : -1)) == 1;
			bitMatrix[1] = world.getBlock(x, 	y + 1, 	z) == this && 							world.getBlockMetadata(x, 	y + 1, 		z) == 1;
			bitMatrix[2] = world.getBlock(x, 	y + 1, 	z + (side == 4 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y + 1, 		z + (side == 4 ? 1 : -1)) == 1;
			bitMatrix[3] = world.getBlock(x, 	y, 		z + (side == 5 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y, 			z + (side == 5 ? 1 : -1)) == 1;
			bitMatrix[4] = world.getBlock(x, 	y, 		z + (side == 4 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y, 			z + (side == 4 ? 1 : -1)) == 1;
			bitMatrix[5] = world.getBlock(x, 	y - 1, 	z + (side == 5 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y - 1, 		z + (side == 5 ? 1 : -1)) == 1;
			bitMatrix[6] = world.getBlock(x, 	y - 1, 	z) == this && 							world.getBlockMetadata(x, 	y - 1, 		z) == 1;
			bitMatrix[7] = world.getBlock(x, 	y - 1, 	z + (side == 4 ? 1 : -1)) == this && 	world.getBlockMetadata(x, 	y - 1, 		z + (side == 4 ? 1 : -1)) == 1;
		}
		
		
		int idBuilder = 0;
		
		for(int i = 0; i < 8; i++){
			idBuilder += (bitMatrix[i] ? (i == 0 ? 1 : (i == 1 ? 2 : (i == 2 ? 4 : (i == 3 ? 8 : 
				(i == 4 ? 16 : (i == 5 ? 32 : (i == 6 ? 64 : 128))))))) : 0);
		}
		
		return idBuilder > 255 || idBuilder < 0 ? icons[0] : icons[textureRefByID[idBuilder]];
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntityUncertaintyBorder();
	}	
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)world.getTileEntity(x, y, z);
		tileEnt.checkAndFormMultiblockStructure();			
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		if(block == Init.blockUncertaintyBorder){
			// Ensure multiblock still exists
			TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)world.getTileEntity(x, y, z);
			if(tileEnt.isMaster() || tileEnt.isSlave()){
				tileEnt.checkMultiblockAndDestroy();
			}
		}
	}
	
}