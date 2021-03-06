package villain.mc.vague.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;

public class ItemNBTHelper {

	public static void checkAndCreateNBT(ItemStack stack){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
	
	public static boolean hasTag(ItemStack stack, String tag){
		if(stack.stackTagCompound == null) return false;
		return stack.stackTagCompound.hasKey(tag);
	}
	
	public static boolean getBool(ItemStack stack, String tag){
		if(stack.stackTagCompound == null) return false;
		return stack.stackTagCompound.getBoolean(tag);
	}
	
	public static void setBool(ItemStack stack, String tag, boolean value){
		checkAndCreateNBT(stack);
		stack.stackTagCompound.setBoolean(tag, value);
	}
	
	/**
	 * @param stack
	 * @param tag
	 * @return The newly set value
	 */
	public static boolean toggleBool(ItemStack stack, String tag){
		boolean current = getBool(stack, tag);
		setBool(stack, tag, !current);
		return !current;		
	}
	
	public static NBTTagCompound getCompound(ItemStack stack, String tag){
		checkAndCreateNBT(stack);		
		return stack.getTagCompound().getCompoundTag(tag);
	}
	
	public static void setCompound(ItemStack stack, String tag, NBTTagCompound compound){
		checkAndCreateNBT(stack);
		stack.getTagCompound().setTag(tag, compound);
	}
	
	public static void setIntArray(ItemStack stack, String tag, int[] intAr){
		checkAndCreateNBT(stack);
		stack.getTagCompound().setIntArray(tag, intAr);
	}
}