package villain.mc.vague.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import villain.mc.vague.Reference;
import villain.mc.vague.data.LanternaBlockData;
import villain.mc.vague.utils.ItemNBTHelper;

public class ItemLanternSlide extends ItemBase {
	
	public static void addBlockData(ItemStack stack, LanternaBlockData data){
		// Ensure that the stack has nbt tag compound
		ItemNBTHelper.checkAndCreateNBT(stack);
		
		// Get the list
		NBTTagList list = stack.stackTagCompound.getTagList("list", NBT.TAG_COMPOUND);
		
		// Create the NBT for this data
		NBTTagCompound tag = new NBTTagCompound();
		data.writeToNBT(tag);
		
		// Append this data to the list
		list.appendTag(tag);
		
		// Reapply the list to the stack's nbt
		stack.stackTagCompound.setTag("list", list);
	}
	
	public static ArrayList<LanternaBlockData> getAsList(ItemStack stack){
		ArrayList<LanternaBlockData> arList = new ArrayList<LanternaBlockData>();
		
		// Return in case no nbt
		if(stack.stackTagCompound == null) return arList;
		
		// Get the nbt list
		NBTTagList nbtList = stack.stackTagCompound.getTagList("list", NBT.TAG_COMPOUND);
		for(int i = 0; i < nbtList.tagCount(); i++){
			NBTTagCompound tag = nbtList.getCompoundTagAt(i);
			LanternaBlockData data = new LanternaBlockData(tag);
			arList.add(data);
		}
		return arList;
	}
	
	/*public static void addOwner(ItemStack stack, EntityPlayer player){
		ItemNBTHelper.checkAndCreateNBT(stack);
		NBTTagList list = stack.stackTagCompound.getTagList("players", NBT.TAG_STRING);
		for(int i = 0; i < list.tagCount(); i++){
			if(list.getStringTagAt(i).equals(player.getDisplayName())){
				return;
			}
		}
		list.appendTag(new NBTTagString(player.getDisplayName()));
		stack.stackTagCompound.setTag("players", list);
	}
	
	public static ArrayList<String> getOwners(ItemStack stack){
		if(!stack.hasTagCompound()) return null;
		ArrayList<String> names = new ArrayList<String>();
		NBTTagList list = stack.stackTagCompound.getTagList("players", NBT.TAG_STRING);
		for(int i = 0; i < list.tagCount(); i++){
			names.add(list.getStringTagAt(i));
		}
		return names;
	}*/
	
	private static int[] SIZES = new int[]{ 
			16,
			32,
			64
	};
	
	private byte type;

	public ItemLanternSlide(byte type){
		super("lanternslide" + SIZES[type], 1, true);
		this.type = type;
	}	
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "lanternslide" + SIZES[type]);
	}
	
	/*@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		addOwner(itemStack, player);
		return itemStack;
	}*/
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
		list.add(getSize() + "x" + getSize());
		if(itemStack.hasTagCompound() && itemStack.stackTagCompound.hasKey("list")){
			list.add("Contains recording.");
		}
		/*if(itemStack.hasTagCompound()){
			ArrayList<String> names = getOwners(itemStack);
			for(int i = 0; i < names.size(); i++){
				list.add(names.get(i));
			}
		}*/
	}
	
	public byte getType(){
		return type;
	}
	
	public int getSize(){
		return SIZES[type];
	}
}