package villain.mc.vague.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import villain.mc.vague.Init;
import villain.mc.vague.tileents.TileEntityTipharesPlant;
import villain.mc.vague.utils.LogHelper;

public class BlockTipharesPlant extends BlockCropBase implements ITileEntityProvider {

	public static final int STAGES = 7;
	
	// TODO Set bounds for different growth stages correctly
	
	public BlockTipharesPlant(){
		super("tipharesplant", STAGES, 64, 0, false, EnumPlantType.Plains, 6);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		
		Block blockBelow = world.getBlock(x, y - 1, z);
		if(blockBelow != this){
			// Set this as master
			TileEntityTipharesPlant tileEnt = (TileEntityTipharesPlant)world.getTileEntity(x, y, z);
			tileEnt.setAsMaster();
			return;
		}
		
		// Find master
		int i = 1;
		blockBelow = world.getBlock(x, y - i, z);
		while(blockBelow == this){
			blockBelow = world.getBlock(x, y - i, z);
			TileEntityTipharesPlant tileEnt = (TileEntityTipharesPlant)world.getTileEntity(x, y - i, z);
			if(tileEnt.isMaster()){
				// Set this as slave
				tileEnt = (TileEntityTipharesPlant)world.getTileEntity(x, y, z);
				tileEnt.setAsSlave(x, y - i, z);
				return;
			}				
			i++;
		}
		
		LogHelper.warn("TipharesPlant: Could not find master entity.");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem().getItem() == Items.diamond){
			TileEntityTipharesPlant tiphEntity = (TileEntityTipharesPlant)world.getTileEntity(x, y, z);
			if(tiphEntity == null) return false;
			
			if(!tiphEntity.isMaster()){
				return false;
			}
			
			player.getHeldItem().stackSize--;
			if(player.getHeldItem().stackSize == 0){
				player.setCurrentItemOrArmor(0, null);
			}
			
			tiphEntity.applyDiamond();
		}
		
		return false;
	}
	
	@Override
	public Item getSeedItem() {
		return Init.itemTipharesEmbryo;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityTipharesPlant();
	}
}