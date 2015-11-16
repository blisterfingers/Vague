package villain.mc.vague.tileents;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLanternaMagica extends TileEntity {
	
	private static final int DEFAULT_SIZE = 5;	
	private static final int MINIMUM_SIZE = 1;
	private static final int MAXIMUM_SIZE = 64;	
	private static final int MAX_OFFSET = 16;	
	
	private int projectionHeight = DEFAULT_SIZE;
	private int projectionWidth = DEFAULT_SIZE;
	private int projectionDepth = DEFAULT_SIZE;
	private int projectionOffsetX = 0;
	private int projectionOffsetY = DEFAULT_SIZE / 2;
	private int projectionOffsetZ = 0;
	
	private ItemStack slideStack;

	public TileEntityLanternaMagica(){
		
	}
	
	public void expand(ForgeDirection dir){
		switch(dir){
			case NORTH:
				projectionDepth += 2;
				break;
			case SOUTH:
				projectionDepth -= 2;
				break;
			case EAST:
				projectionWidth -= 2;
				break;
			case WEST:
				projectionWidth += 2;
				break;
			case UP:
				projectionHeight += 2;
				break;
			case DOWN:
				projectionHeight -= 2;
				break;
			case UNKNOWN:
				// No-Op
				break;
		}
		
		if(projectionWidth < MINIMUM_SIZE) projectionWidth = MINIMUM_SIZE;
		else if(projectionWidth > MAXIMUM_SIZE) projectionWidth = MAXIMUM_SIZE;
		
		if(projectionHeight < MINIMUM_SIZE) projectionHeight = MINIMUM_SIZE;
		else if(projectionHeight > MAXIMUM_SIZE) projectionHeight = MAXIMUM_SIZE;
		
		if(projectionDepth < MINIMUM_SIZE) projectionDepth = MINIMUM_SIZE;
		else if(projectionDepth > MAXIMUM_SIZE) projectionDepth = MAXIMUM_SIZE;
	}
	
	public void shift(ForgeDirection dir){
		switch(dir){
			case NORTH:
				projectionOffsetZ -= 1;
				break;
			case SOUTH:
				projectionOffsetZ += 1;
				break;
			case EAST:
				projectionOffsetX += 1;
				break;
			case WEST:
				projectionOffsetX -= 1;
				break;
			case UP:
				projectionOffsetY += 1;
				break;
			case DOWN:
				projectionOffsetY -= 1;
				break;
			case UNKNOWN:
				// No-Op
				break;
		}
		
		if(projectionOffsetX > MAX_OFFSET) projectionOffsetX = MAX_OFFSET;
		else if(projectionOffsetX < -MAX_OFFSET) projectionOffsetX = -MAX_OFFSET;
		
		if(projectionOffsetY > MAX_OFFSET) projectionOffsetY = MAX_OFFSET;
		else if(projectionOffsetY < -MAX_OFFSET) projectionOffsetY = -MAX_OFFSET;
		
		if(projectionOffsetZ > MAX_OFFSET) projectionOffsetZ = MAX_OFFSET;
		else if(projectionOffsetZ < -MAX_OFFSET) projectionOffsetZ = -MAX_OFFSET;
	}
	
	public boolean hasSlideStack(){
		return slideStack != null;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(hasSlideStack()){
			if(worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0){
				// Playback
				
			}
			else {
				// Recording
				
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("projHeight", projectionHeight);
		nbt.setInteger("projWidth", projectionWidth);
		nbt.setInteger("projDepth", projectionDepth);
		nbt.setInteger("projOffsetX", projectionOffsetX);
		nbt.setInteger("projOffsetY", projectionOffsetY);
		nbt.setInteger("projOffsetZ", projectionOffsetZ);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		projectionHeight = nbt.getInteger("projHeight");
		projectionWidth = nbt.getInteger("projWidth");
		projectionDepth = nbt.getInteger("projDepth");
		projectionOffsetX = nbt.getInteger("projOffsetX");
		projectionOffsetY = nbt.getInteger("projOffsetY");
		projectionOffsetZ = nbt.getInteger("projOffsetZ");
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		int minX = Math.min(xCoord, xCoord + projectionOffsetX - (projectionWidth / 2));
		int minY = Math.min(yCoord, yCoord + projectionOffsetY - (projectionHeight / 2));
		int minZ = Math.min(zCoord, zCoord + projectionOffsetZ - (projectionDepth / 2));
		
		int maxX = Math.max(xCoord, xCoord + projectionOffsetX + (projectionWidth / 2) + 1);
		int maxY = Math.max(yCoord, yCoord + projectionOffsetY + (projectionHeight / 2) + 1);
		int maxZ = Math.max(zCoord, zCoord + projectionOffsetZ + (projectionDepth / 2) + 1);
		
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public int getProjectionHeight() {
		return projectionHeight;
	}

	public int getProjectionWidth() {
		return projectionWidth;
	}

	public int getProjectionDepth() {
		return projectionDepth;
	}

	public int getProjectionOffsetX() {
		return projectionOffsetX;
	}

	public int getProjectonOffsetY() {
		return projectionOffsetY;
	}

	public int getProjectionOffsetZ() {
		return projectionOffsetZ;
	}
	
	public void setInitialDirection(int metaDir){
		switch(metaDir){
			case 0:
				projectionOffsetZ = -((DEFAULT_SIZE / 2) + 1);
				break;
			case 1:
				projectionOffsetX = (DEFAULT_SIZE / 2) + 1;
				break;
			case 2:
				projectionOffsetZ = (DEFAULT_SIZE / 2) + 1;
				break;
			case 3:
				projectionOffsetX = -((DEFAULT_SIZE / 2) + 1);
				break;
		}
	}
	
	public ItemStack getSlideStack(){
		return slideStack;
	}
	
	public void setSlideStack(ItemStack slideStack){
		this.slideStack = slideStack;
	}
}