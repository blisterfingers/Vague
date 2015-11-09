package villain.mc.vague.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import villain.mc.vague.Init;

public class ItemTipharesEmbryo extends ItemBase implements IPlantable {

	public ItemTipharesEmbryo(){
		super("tipharesembyro", 64, true);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ){
		// Can only plant on top of a block
		if(side != 1){
			return false;
		}
		
		if(player.canPlayerEdit(x, y, z, side, itemStack) && player.canPlayerEdit(x, y + 1, z, side, itemStack)){
			if(world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, this) &&
					world.isAirBlock(x, y + 1, z)){
				world.setBlock(x, y + 1, z, Init.blockTipharesPlant);
				--itemStack.stackSize;
				return true;
			}			
		}
		
		return false;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return Init.blockTipharesPlant.getPlantType(world, x, y, z);
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return Init.blockTipharesPlant;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return 0;
	}
}