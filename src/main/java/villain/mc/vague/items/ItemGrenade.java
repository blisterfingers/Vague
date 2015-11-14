package villain.mc.vague.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import villain.mc.vague.entities.EntityGrenade;

public class ItemGrenade extends ItemBase {

	public static final short TYPE_NORMAL = 0;
	public static final short TYPE_CLUSTER = 1;
	public static final short TYPE_SPONGE = 2;
	public static final short TYPE_TORCH = 3;
	public static final short TYPE_SHELTER = 4;
	
	private static final String[] TYPE_TO_NAME = {
		"grenadefrag",
		"grenadecluster",
		"grenadesponge",
		"grenadetorch",
		"grenadeshelter"
	};
	
	private static final int[] TYPE_TO_STACK_SIZE = {
		16,
		8,
		32,
		16,
		8
	};
	
	private short type;
	
	public ItemGrenade(short type){
		super(TYPE_TO_NAME[type], TYPE_TO_STACK_SIZE[type], true);
		
		this.type = type;
	}		
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer){
		if(!world.isRemote){
			world.spawnEntityInWorld(new EntityGrenade(world, entityPlayer, type));
		}	
		itemStack.stackSize--;
		return itemStack;
	}
	
	public int getType(){
		return type;
	}
}