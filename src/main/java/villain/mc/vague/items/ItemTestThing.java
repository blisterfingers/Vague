package villain.mc.vague.items;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import villain.mc.vague.utils.LogHelper;

public class ItemTestThing extends ItemBase {

	public ItemTestThing(){
		super("testthing", 1, true);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityPlayer) {
		if(!world.isRemote){
			/*Iterator<Item> iterator = GameData.getItemRegistry().iterator();
			while(iterator.hasNext()){
				LogHelper.info(iterator.next().getUnlocalizedName());
			}*/
			
			Object[] list = GameData.getItemRegistry().getKeys().toArray();
			int rand = world.rand.nextInt(list.length);
			LogHelper.info("random item key: " + list[rand].getClass().getSimpleName());
			
			Item item = (Item)GameData.getItemRegistry().getObject(list[rand]);
			LogHelper.info("Item: "+ item);
			ItemStack newStack = new ItemStack(item);
			
			entityPlayer.inventory.addItemStackToInventory(newStack);
		}
		return stack;
	}
}