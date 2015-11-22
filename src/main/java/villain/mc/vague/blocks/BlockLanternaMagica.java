package villain.mc.vague.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import villain.mc.vague.Init;
import villain.mc.vague.tileents.TileEntityLanternaMagica;
import villain.mc.vague.utils.EntityHelper;

public class BlockLanternaMagica extends BlockBase implements ITileEntityProvider {

	// TODO Make TilEntity's offset/size change when block is right-clicked with an empty hand
	// TODO Create the Film/Photo item that will store the recording
	// TODO Make it so that the Film/Photo item can be right-clicked into the block and then rendered as part of the TileEntity
	// TODO Make that Film/Photo item get saved in the NBT of the TileEntity
	// TODO Make the TileEntity record all block placements/removals inside the area to the held Film/Photo
	// TODO Make the TileEntity 'play back' the recording when a redstone signal is applied and the needed materials are in an adjacent chest
	
	public BlockLanternaMagica(){
		super("lanternamagica", Material.iron, true);		
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		int meta = EntityHelper.getMetaForPlacementDirection(entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		
		TileEntityLanternaMagica lanternEntity = (TileEntityLanternaMagica)world.getTileEntity(x, y, z);
		if(lanternEntity == null) return false;		
		
		if(entityPlayer.getHeldItem() == null){
			// If the lantern has a slide
			if(lanternEntity.hasSlideStack()){
				// Remove the slide stack
				entityPlayer.setCurrentItemOrArmor(0, lanternEntity.getSlideStack());
				lanternEntity.setSlideStack(null);
			}
			else {
				/* Shift/Expand
				ForgeDirection dir = ForgeDirection.getOrientation(side);				
				if(!entityPlayer.isSneaking()){
					lanternEntity.shift(dir);
				}
				else {
					lanternEntity.expand(dir);
				}*/
			}
			return true;
		}
		else if(entityPlayer.getHeldItem().getItem() == Init.itemLanternSlide16 || entityPlayer.getHeldItem().getItem() == Init.itemLanternSlide32 ||
				entityPlayer.getHeldItem().getItem() == Init.itemLanternSlide64){
			// Does the Lantern already have an item?
			if(lanternEntity.hasSlideStack()){
				// Add the lantern's item to the player's inventory
				boolean addedToInv = entityPlayer.inventory.addItemStackToInventory(lanternEntity.getSlideStack());
				if(addedToInv){
					lanternEntity.setSlideStack(null);
				}
			}
			else {
				
				/* Does this slide have an owner set?
				ArrayList<String> owners = ItemLanternSlide.getOwners(entityPlayer.getHeldItem());
				if(owners == null || owners.size() == 0) return false;*/
				
				// Remove the item from the player's inventory
				ItemStack slideStack = entityPlayer.getHeldItem().splitStack(1);
				if(entityPlayer.getHeldItem().stackSize == 0){
					entityPlayer.setCurrentItemOrArmor(0, null);
				}
				
				// Insert the slide into the Lantern.
				lanternEntity.setSlideStack(slideStack);
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityLanternaMagica();
	}
}