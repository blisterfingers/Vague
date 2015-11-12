package villain.mc.vague.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
		if(!stack.stackTagCompound.hasKey(tag)) return false;
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
}