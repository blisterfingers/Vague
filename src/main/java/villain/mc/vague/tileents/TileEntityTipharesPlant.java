package villain.mc.vague.tileents;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import villain.mc.vague.Init;

public class TileEntityTipharesPlant extends TileEntity {
	
	private final int RADIUS = 16;

	private boolean isMaster;
	private boolean hasMaster;
	private int masterX, masterY, masterZ;
	private boolean genned = false;
	private boolean diamondApplied = false;
	
	public TileEntityTipharesPlant(){
		super();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isMaster", isMaster);
		nbt.setBoolean("hasMaster", hasMaster);
		if(hasMaster){
			nbt.setInteger("masterX", masterX);
			nbt.setInteger("masterY", masterY);
			nbt.setInteger("masterZ", masterZ);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isMaster = nbt.getBoolean("isMaster");
		hasMaster = nbt.getBoolean("hasMaster");
		if(hasMaster){
			masterX = nbt.getInteger("masterX");
			masterY = nbt.getInteger("masterY");
			masterZ = nbt.getInteger("masterZ");
		}
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote){
			if(isMaster && !genned && diamondApplied){
				// Check the height of the current plant stack
				Block b = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
				int c = 1;
				while(b == Init.blockTipharesPlant){
					c++;
					b = worldObj.getBlock(xCoord, yCoord + c, zCoord);
				}
				
				gen(c);
				genned = true;
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);				
			}
		}
	}
	
	private void gen(int height){
		int fullSize = RADIUS * 2;
		NoiseGeneratorOctaves noiseGen = new NoiseGeneratorOctaves(worldObj.rand, 8);
		double[] noise = noiseGen.generateNoiseOctaves(null, xCoord, yCoord, zCoord, fullSize, fullSize, fullSize, 1, 1, 1);
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		Block[] blocks = new Block[fullSize * fullSize * fullSize];
		int[] metas = new int[fullSize * fullSize * fullSize];
		
		for(int y = -RADIUS; y < RADIUS; y++){
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					double n = noise[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)];
					if(n < min) min = n;
					if(n > max) max = n;
					
					blocks[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)] = 
							worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
					metas[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)] =
							worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z);
				}
			}
		}
		
		int minus = -1;
		for(int y = -RADIUS; y < RADIUS; y++){
			minus++;
			double falloff = (double)(y + RADIUS) / (double)RADIUS;
			double dist = 1.0 - (Math.abs(Math.sqrt(y * y)) / RADIUS);
			double on = noise[(y + RADIUS) * fullSize];
			double n = normalise(on, min, max);
			if(n * dist * falloff > 0.1){
				break;
			}
		}
		
		double falloff;
		for(int y = -RADIUS; y < RADIUS; y++){
			falloff = (double)(y + RADIUS) / (double)RADIUS;
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					double dist = 1.0 - (Math.abs(Math.sqrt(x * x + y * y + z * z)) / RADIUS);
					double on = noise[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)];
					double n = normalise(on, min, max);
					if(n * dist * falloff > 0.1){
						Block b = blocks[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)];
						int meta = metas[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)];
						TileEntity tileEnt = worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
						if(b != null && b != Init.blockTipharesPlant && tileEnt == null){
							worldObj.setBlock(xCoord + x, yCoord + y + height + RADIUS - minus, zCoord + z, b);
							worldObj.setBlockMetadataWithNotify(xCoord + x, yCoord + y + height + RADIUS - minus, zCoord + z, meta, 3);
						}
					}
				}
			}
		}
	}
	
	private double normalise(double value, double min, double max){
		return (((value - min) * (1f - 0f)) / (max - min)) + 0f;
	}
	
	public void applyDiamond(){
		diamondApplied = true;
	}
		
	public void setAsMaster(){
		isMaster = true;
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public void setAsSlave(int masterX, int masterY, int masterZ){
		this.hasMaster = true;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
	}
	
	public boolean isSlave(){
		return hasMaster && !isMaster;
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
}