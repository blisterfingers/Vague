package villain.mc.vague.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MagnetBlackListItem {

	private String itemUnlocalizedName; // The unlocalized name of the item
	private boolean useMeta; // Should use meta when comparing?
	private boolean useNBT; // Should use NBT when comparing?
	private boolean black; // true = blacklist, false = whitelist
	private int meta; // The meta of the itemstack
	private NBTTagCompound nbtTagCompound; // The nbt of the itemstack
	
	public MagnetBlackListItem(){
		
	}
	
	public MagnetBlackListItem(String itemUnlocalizedName, boolean useMeta, int meta, boolean useNBT, NBTTagCompound nbt, boolean black){
		this.itemUnlocalizedName = itemUnlocalizedName;
		this.useMeta = useMeta;
		this.meta = meta;
		this.useNBT = useNBT;
		this.nbtTagCompound = nbt;
		this.black = black;
	}
	
	public MagnetBlackListItem(NBTTagCompound nbt){
		readFromNBT(nbt);
	}
	
	public NBTTagCompound writeToNBT(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("name", itemUnlocalizedName);
		nbt.setBoolean("useMeta", useMeta);
		nbt.setBoolean("useNBT", useNBT);
		nbt.setInteger("meta", meta);
		nbt.setTag("nbt", nbtTagCompound);
		nbt.setBoolean("black", black);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		itemUnlocalizedName = nbt.getString("name");
		useMeta = nbt.getBoolean("useMeta");
		useNBT = nbt.getBoolean("useNBT");
		meta = nbt.getInteger("meta");
		nbtTagCompound = nbt.getCompoundTag("nbt");
		black = nbt.getBoolean("black");
	}
	
	/**
	 * @return -1 if no match, 0 if match and on blacklist, 1 if match and on whitelist
	 */
	public int doesMatch(ItemStack itemStack){
		if(itemStack.getItem().getUnlocalizedName().equals(itemUnlocalizedName) &&
			(!useMeta || meta == itemStack.getItemDamage()) &&
			(!useNBT || itemStack.stackTagCompound != null && nbtTagCompound.equals(itemStack.stackTagCompound))){
			if(black){
				return 0;
			}
			else {
				return 1;
			}
		}
		return -1;
	}

	public String getItemUnlocalizedName() {
		return itemUnlocalizedName;
	}

	public void setItemUnlocalizedName(String itemUnlocalizedName) {
		this.itemUnlocalizedName = itemUnlocalizedName;
	}

	public boolean isUseMeta() {
		return useMeta;
	}

	public void setUseMeta(boolean useMeta) {
		this.useMeta = useMeta;
	}

	public boolean isUseNBT() {
		return useNBT;
	}

	public void setUseNBT(boolean useNBT) {
		this.useNBT = useNBT;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public NBTTagCompound getNbtTagCompound() {
		return nbtTagCompound;
	}

	public void setNbtTagCompound(NBTTagCompound nbtTagCompound) {
		this.nbtTagCompound = nbtTagCompound;
	}
}