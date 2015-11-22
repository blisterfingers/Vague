package villain.mc.vague.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import villain.mc.vague.Reference;
import villain.mc.vague.utils.ItemNBTHelper;

public class ItemSurveyTool extends ItemBase {

	private IIcon icon1;
	private IIcon icon2;
	private IIcon icon3;
	
	public ItemSurveyTool(){
		super("surveytool", 1, true);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "surveytool-0");
		icon1 = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "surveytool-1");
		icon2 = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "surveytool-2");
		icon3 = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "surveytool-3");
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		boolean first = ItemNBTHelper.hasTag(stack, "first");
		boolean second = ItemNBTHelper.hasTag(stack, "second");
		if(first && second){
			return icon3;
		}
		else if(first && !second){
			return icon1;
		}
		else if(!first && second){
			return icon2;
		}
		else {
			return itemIcon;
		}
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return getIcon(stack, renderPass);
	}
		
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if(!entityPlayer.isSneaking()){
			ItemNBTHelper.setIntArray(itemStack, "first", new int[]{ 
					MathHelper.floor_double(entityPlayer.posX),
					MathHelper.floor_double(entityPlayer.posY),
					MathHelper.floor_double(entityPlayer.posZ)
			});
		}
		else {
			ItemNBTHelper.setIntArray(itemStack, "second", new int[]{ 
					MathHelper.floor_double(entityPlayer.posX),
					MathHelper.floor_double(entityPlayer.posY),
					MathHelper.floor_double(entityPlayer.posZ)
			});
		}
		return itemStack;
	}
}