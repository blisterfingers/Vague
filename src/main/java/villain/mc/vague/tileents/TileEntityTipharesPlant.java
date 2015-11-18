package villain.mc.vague.tileents;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import villain.mc.vague.Init;
import villain.mc.vague.blocks.BlockTipharesPlant;
import villain.mc.vague.utils.LogHelper;

public class TileEntityTipharesPlant extends TileEntity {
	
	private final int RADIUS = 16;
	private final int MIN_HEIGHT = 8;

	private boolean isMaster;
	private boolean hasMaster;
	private int masterX, masterY, masterZ;
	private boolean genned = false;
	private boolean diamondApplied = false;
	
	private NoiseGeneratorOctaves noiseGen;
	
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
				
				if(c > MIN_HEIGHT){
					gen2(c);
					genned = true;
				}
			}
		}
	}
	
	private void gen(){
		int fullSize = RADIUS * 2;
		Block[][][] blocks = new Block[fullSize][fullSize][fullSize];
		int[][][] metas = new int[fullSize][fullSize][fullSize];
		
		for(int y = -RADIUS; y < RADIUS; y++){
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					blocks[x + RADIUS][y + RADIUS][z + RADIUS] = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
					metas[x + RADIUS][y + RADIUS][z + RADIUS] = worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z);
				}
			}
		}
		
		for(int y = -RADIUS; y < RADIUS; y++){
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					Block block = blocks[x + RADIUS][y + RADIUS][z + RADIUS];
					double dist = Math.abs(Math.sqrt(x * x + y * y + z * z));
					if(block != Init.blockTipharesPlant && dist < RADIUS){
						worldObj.setBlock(xCoord + x, yCoord + y + 32, zCoord + z, block);
						worldObj.setBlockMetadataWithNotify(xCoord + x, yCoord + y + 32, zCoord + z, metas[x + RADIUS][y + RADIUS][z + RADIUS], 3);
						worldObj.setBlockToAir(xCoord + x, yCoord + y, zCoord + z);
					}
				}
			}
		}
	}
		
	private void gen2(int height){
		
		LogHelper.info("Genning");
		
		int fullSize = RADIUS * 2;
		
		double[] noise = noiseGen.generateNoiseOctaves(null, xCoord, yCoord, zCoord, fullSize, fullSize, fullSize, 10, 10, 10);
		Block[] blocks = new Block[fullSize * fullSize * fullSize];
		double min = Double.MAX_VALUE;
		double max = -Double.MAX_VALUE;
		
		// Loop 1
		for(int y = -RADIUS; y < 0; y++){
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					// gather min/max noise values
					double n = noise[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)];
					if(n < min) min = n;
					if(n > max) max = n;
				}
			}
		}
		
		// Loop 2
		for(int y = -RADIUS; y <= 0; y++){
			double falloff = ((double)(y + RADIUS) / (double)RADIUS);
			for(int x = -RADIUS; x < RADIUS; x++){
				for(int z = -RADIUS; z < RADIUS; z++){
					double dist = Math.abs(Math.sqrt(x * x + y * y + z * z));
					double n = normalise(noise[(x + RADIUS) + ((y + RADIUS) * fullSize) + ((z + RADIUS) * fullSize * fullSize)], min, max);
					
					if(x == 0 && z == 0){
						LogHelper.info("y: " + y + ", dist: " + dist + ", n: " + n + ", f: " + falloff + " both: " + (n * falloff));
					}
					
					
					double distMul = 1.0 - (dist / RADIUS);
					
					
					double n2 = n * falloff * distMul;
					Block b = null;
					if(n2 > 0.6){
						b = Blocks.dirt;
					}
					else if(n2 > 0.5){
						b = Blocks.gravel;
					}
					else if(n2 > 0.1){
						b = Blocks.stone;
					}
					
					if(b != null) worldObj.setBlock(xCoord + x, yCoord + y + height + RADIUS, zCoord + z, b);
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
		
		noiseGen = new NoiseGeneratorOctaves(worldObj.rand, 16);
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