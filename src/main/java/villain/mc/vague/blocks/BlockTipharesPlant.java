package villain.mc.vague.blocks;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import villain.mc.vague.Init;

public class BlockTipharesPlant extends BlockCropBase {

	// TODO Set bounds for different growth stages correctly
	
	public BlockTipharesPlant(){
		super("tipharesplant", 7, 64, 0, false, EnumPlantType.Plains, 6);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public Item getSeedItem() {
		return Init.itemTipharesEmbryo;
	}
}