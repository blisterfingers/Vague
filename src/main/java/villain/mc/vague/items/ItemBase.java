package villain.mc.vague.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import villain.mc.vague.Init;
import villain.mc.vague.Reference;
import villain.mc.vague.utils.LogHelper;

public class ItemBase extends Item {
	
	public ItemBase(String unlocalizedName, int maxStackSize, boolean addToCreativeTab){
		setUnlocalizedName(unlocalizedName);
		setMaxStackSize(maxStackSize);
		setCreativeTab(Init.CREATIVETAB_MAIN);
		GameRegistry.registerItem(this, unlocalizedName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		LogHelper.info("registering icons: " + (Reference.RESOURCE_PREFIX + getUnwrappedUnlocalizedName(super.getUnlocalizedName())));
		itemIcon = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName() {
		return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return getUnlocalizedName();
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}