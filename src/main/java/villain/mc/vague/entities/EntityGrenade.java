package villain.mc.vague.entities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import scala.util.Random;
import villain.mc.vague.Init;
import villain.mc.vague.items.ItemGrenade;
import villain.mc.vague.utils.SchematicHelper;

public class EntityGrenade extends EntityThrowable {
	
	private static final float DAMPENING = 0.4f;
	private static final float YDAMPENING = 0.1f;
	private static final int COOK_TIME_TICKS = 40;
	private static final float EXPLOSION_SIZE = 5f;
	private static final boolean IS_FLAMING = false;
	private static final boolean IS_SMOKING = true;
	private static final int SPONGE_RANGE = 9;
	private static final int TORCH_RANGE = 9;
	
	private static final Random random = new Random();
	
	private int age = 0;
	private int lifespan = COOK_TIME_TICKS;
	private short type = 0;
	private boolean settle = false;

	public EntityGrenade(World world){
		super(world);
	}
	
	public EntityGrenade(World world, short type){
		super(world);
		init(type);
	}
	
	public EntityGrenade(World world, EntityPlayer entityPlayer, short type) {
		super(world, entityPlayer);
		init(type);		
	}
	
	private void init(short type){
		this.type = type;
		if(type == ItemGrenade.TYPE_TORCH || type == ItemGrenade.TYPE_SHELTER){
			settle = true;
		}
	}
	
	@Override
	public void onEntityUpdate() {
		
		if(!settle){
			age++;
			if(age > lifespan){
				explode();
			}
		}
		else {
			// Are we still?
			if(Math.abs(motionX) < 0.1 && Math.abs(motionY) < 0.1 && Math.abs(motionZ) < 0.1){
				age++;
				if(age > lifespan){
					if(!worldObj.isRemote) explode();
				}
			}
			else {
				age = 0;
			}
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(mop.typeOfHit == MovingObjectType.BLOCK){
			switch(mop.sideHit){
				 case 0: 
					 mop.blockY--;
					 setVelocity(motionX * DAMPENING, -motionY * YDAMPENING, motionZ * DAMPENING);
					 break;
				 case 1:
					 mop.blockY++;
					 setVelocity(motionX * DAMPENING, -motionY * YDAMPENING, motionZ * DAMPENING);
					 break;
				 case 2:
					 mop.blockZ--;
					 setVelocity(motionX * DAMPENING, motionY * DAMPENING, -motionZ * DAMPENING);
					 break;
				 case 3:
					 mop.blockZ++;
					 setVelocity(motionX * DAMPENING, motionY * DAMPENING, -motionZ * DAMPENING);
					 break;
				 case 4:
					 mop.blockX--;
					 setVelocity(-motionX * DAMPENING, motionY * DAMPENING, motionZ * DAMPENING);
					 break;
				 case 5:
					 mop.blockX++;
					 setVelocity(-motionX * DAMPENING, motionY * DAMPENING, motionZ * DAMPENING);
					 break;
			}
		}
	}
	
	private void explode(){
		switch(type){
			case ItemGrenade.TYPE_NORMAL:
				explodeNormal();
				break;
			case ItemGrenade.TYPE_CLUSTER:
				explodeCluster();
				break;
			case ItemGrenade.TYPE_SPONGE:
				explodeSponge();
				break;
			case ItemGrenade.TYPE_TORCH:
				explodeTorch();
				break;
			case ItemGrenade.TYPE_SHELTER:
				explodeShelter();
				break;
		}
		setDead();
	}
	
	private void explodeNormal(){
		if(!worldObj.isRemote){
			worldObj.newExplosion(this, posX, posY, posZ, EXPLOSION_SIZE, IS_FLAMING, IS_SMOKING);
		}
	}
	
	private void explodeCluster(){
		if(!worldObj.isRemote){
			for(int i = 0; i < 4; i++){
				worldObj.newExplosion(this, posX, posY, posZ, EXPLOSION_SIZE / 2f, IS_FLAMING, IS_SMOKING);
				
				EntityGrenade entityGrenade = new EntityGrenade(worldObj, ItemGrenade.TYPE_NORMAL);
				entityGrenade.setThrowableHeading(0d, 0.5d + random.nextDouble(), 0d, 0.25f, 50f); // dirX, dirY, dirZ, speed, spread
				entityGrenade.posX = posX;
				entityGrenade.posY = posY;
				entityGrenade.posZ = posZ;
				entityGrenade.motionX = motionX - 0.5f + random.nextFloat();
				entityGrenade.motionY = motionY - 0.5f + random.nextFloat();
				entityGrenade.motionZ = motionZ - 0.5f + random.nextFloat();
				entityGrenade.setSettle(true);			
				
				worldObj.spawnEntityInWorld(entityGrenade);
			}
		}
	}
	
	private void explodeSponge(){
		if(!worldObj.isRemote){
			int curX = 0, curY = 0, curZ = 0;
			int startX = (int)posX;
			int startY = (int)posY;
			int startZ = (int)posZ;
			int dist, dx, dy, dz;
			Block block;
			
			for(int y = 0; y < SPONGE_RANGE * 2; y++){
				curY = startY - SPONGE_RANGE + y;
				for(int x = 0; x < SPONGE_RANGE * 2; x++){
					curX = startX - SPONGE_RANGE + x;
					for(int z = 0; z < SPONGE_RANGE * 2; z++){
						curZ = startZ - SPONGE_RANGE + z;
						
						// Is curX/Y/Z inside sphere of influence?
						dx = curX - startX;
						dy = curY - startY;
						dz = curZ - startZ;
						dist = (int)Math.sqrt(dx * dx + dy * dy + dz * dz);
						
						if(dist <= SPONGE_RANGE){
							// Mop up water here
							block = worldObj.getBlock(curX, curY, curZ);
							if(block == Blocks.water || block == Blocks.lava || block == Blocks.flowing_water || block == Blocks.flowing_lava ||
									FluidRegistry.isFluidRegistered(block.getUnlocalizedName())){
								worldObj.setBlock(curX, curY, curZ, Blocks.air);
							}
						}
					}
				}
			}
		}
	}
	
	private void explodeTorch(){
		if(!worldObj.isRemote){
			int curX = 0, curY = 0, curZ = 0;
			int px = (int)posX;
			int py = (int)posY;
			int pz = (int)posZ;
			int startX = px - TORCH_RANGE;
			int startY = py - TORCH_RANGE;
			int startZ = pz - TORCH_RANGE;
			int ran, nx, ny, nz, dx, dy, dz, dist;
			Block b, b2;			
			ArrayList<Integer> sides = new ArrayList<Integer>();
			sides.add(0);
			sides.add(1);
			sides.add(2);
			sides.add(3);
			sides.add(4);
			
			for(int y = 0; y < TORCH_RANGE * 2; y++){
				curY = startY + y;
				for(int x = 0; x < TORCH_RANGE * 2; x++){
					curX = startX + x;
					for(int z = 0; z < TORCH_RANGE * 2; z++){
						curZ = startZ + z;
						
						dx = curX - px;
						dy = curY - py;
						dz = curZ - pz;
						
						dist = (int)Math.sqrt(dx * dx + dy * dy + dz * dz);
						
						if(dist <= TORCH_RANGE){
							
							b = worldObj.getBlock(curX, curY, curZ);
													
							if(b != Blocks.air) continue;
							
							for(int i = 0; i < 5; i++){
								ran = sides.remove(random.nextInt(sides.size()));
								
								nx = curX;
								ny = curY;
								nz = curZ;
								
								switch(ran){
									case 0:
										ny -=1;
										break;
									case 1:
										nx -=1;
										break;
									case 2:
										nx += 1;
										break;
									case 3:
										nz -= 1;
										break;
									case 4:
										nz += 1;
										break;
								}
								
								b2 = worldObj.getBlock(nx, ny, nz);
								
								if(b2 != Blocks.air && b2 != Blocks.torch){
									worldObj.setBlock(curX, curY, curZ, Blocks.torch);
									break;
								}
							}
							
							// Reset sides
							sides.clear();
							sides.add(0);
							sides.add(1);
							sides.add(2);
							sides.add(3);
							sides.add(4);
							
						}
						
						// Couldn't find placement for this xyz
						
												
					}
				}
			}
			
			
			setDead();
		}
	}
		
	private void explodeShelter(){
		
		SchematicHelper.placeSchematic(worldObj, Init.shelterGrenadeSchematic, MathHelper.floor_double(posX), MathHelper.floor_double(posY),
				MathHelper.floor_double(posZ));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = nbt.getShort("grenadeType");
		settle = nbt.getBoolean("settle");
		age = nbt.getInteger("age");
		lifespan = nbt.getInteger("lifespan");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("grenadeType", (short)type);
		nbt.setBoolean("settle", settle);
		nbt.setInteger("age", age);
		nbt.setInteger("lifespan", lifespan);
	}
	
	public void setLifespan(int ticks){
		this.lifespan = ticks;
	}
	
	public void setSettle(boolean value){
		this.settle = value;
	}
	
	public int getType(){
		return type;
	}
}