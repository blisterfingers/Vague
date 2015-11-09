package villain.mc.vague.tileents;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMultiblock extends TileEntity {

	private boolean isMaster = false;
	private boolean hasMaster = false;
	private int masterX, masterY, masterZ;
	
	public TileEntityMultiblock(){
		super();
	}	
	
	public abstract boolean checkAndFormMultiblockStructure();
	protected abstract void formMultiblockStructure();
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("masterX", masterX);
		nbt.setInteger("masterY", masterY);
		nbt.setInteger("masterZ", masterZ);
		nbt.setBoolean("hasMaster", hasMaster);
		nbt.setBoolean("isMaster", isMaster);
		// Other values should only be saved to master
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		masterX = nbt.getInteger("masterX");
		masterY = nbt.getInteger("masterY");
		masterZ = nbt.getInteger("masterZ");
		hasMaster = nbt.getBoolean("hasMaster");
		isMaster = nbt.getBoolean("isMaster");
		// Other values should only be read from master
	}
	
	public boolean hasMaster(){
		return hasMaster;
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public int getMasterX(){
		return masterX;
	}
	
	public int getMasterY(){
		return masterY;
	}
	
	public int getMasterZ(){
		return masterZ;
	}
	
	public void setHasMaster(boolean hasMaster, int masterX, int masterY, int masterZ){
		this.hasMaster = hasMaster;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
	}
	
	public void setIsMaster(boolean isMaster){
		this.isMaster = isMaster;
	}
}