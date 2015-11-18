package villain.mc.vague.data;

import net.minecraft.nbt.NBTTagCompound;

public class LanternaBlockData {

	private int timeIndex;
	private String blockName;
	private int x, y, z;
	
	public LanternaBlockData(int timeIndex, String blockName, int x, int y, int z){
		this.timeIndex = timeIndex;
		this.blockName = blockName;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public LanternaBlockData(NBTTagCompound nbt){
		readFromNBT(nbt);
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("time", timeIndex);
		nbt.setString("blockName", blockName);
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		timeIndex = nbt.getInteger("time");
		blockName = nbt.getString("blockName");
		x = nbt.getInteger("x");
		y = nbt.getInteger("y");
		z = nbt.getInteger("z");
	}

	public int getTimeIndex() {
		return timeIndex;
	}

	public String getBlockName() {
		return blockName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}