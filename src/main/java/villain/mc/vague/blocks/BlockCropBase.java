package villain.mc.vague.blocks;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import villain.mc.vague.Reference;

public abstract class BlockCropBase extends BlockBush implements IGrowable {

	private String name;
	private int stages;
	private IIcon[] icons;
	private int heightStages = 1;
	private int fullyGrownExtraSeeds;
	private boolean needsFarmland;
	private EnumPlantType plantType;
	private int renderType = 1;
	
	public BlockCropBase(String name, int stages, int heightStages, int fullyGrownExtraSeeds, boolean needsFarmland,
			EnumPlantType plantType, int renderType){
		super();
		this.name = name;
		this.stages = stages;
		this.heightStages = heightStages;
		this.fullyGrownExtraSeeds = fullyGrownExtraSeeds;
		this.needsFarmland = needsFarmland;
		this.plantType = plantType;
		this.renderType = renderType;
		
		setBlockName(name);
		setTickRandomly(true);
		setBlockBounds(0, 0, 0, 1, 0.25f, 1f);
		setCreativeTab(null);
		setHardness(0f);
		setStepSound(soundTypeGrass);
		disableStats();
		
		GameRegistry.registerBlock(this, name);
	}
	
	@Override
	public int getRenderType() {
		// Defined in RenderBlocks.renderBlockByRenderType
		return renderType;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[stages];
		for(int i = 0; i < stages; i++){
			icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + name + "-" + i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		if(metadata < icons.length){
			return icons[metadata];
		}
		//LogHelper.warn("Returning null: meta = " + metadata);
		return null;
	}
	
	 @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z){
        return world.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z);
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z){
		
		Block block = world.getBlock(x, y - 1, z);
		
		boolean canStay = (needsFarmland && (block == Blocks.farmland || (heightStages > 1 && block == this))) || 
				   (!needsFarmland && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass || (heightStages > 1 && block == this)));
		//LogHelper.info("canStay: " + x + ", " + y + ", " + z + " : " + canStay);		
		return canStay;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
		int metaBeforeSuper = world.getBlockMetadata(x, y, z);
		super.onNeighborBlockChange(world, x, y, z, block);
		if(heightStages > 1 && metaBeforeSuper >= stages - 1){
			world.notifyBlockOfNeighborChange(x, y + 1, z, this);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		Block belowBlock = world.getBlock(x, y - 1, z);
		if(belowBlock != this){
			if(metadata >= stages){
				ret.add(new ItemStack(getSeedItem(), fullyGrownExtraSeeds, 0));
			}
			ret.add(new ItemStack(getSeedItem(), 1, 0));
		}
		return ret;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
		int metadata = world.getBlockMetadata(x, y, z);
		setBlockBounds(0, 0, 0, 1, metadata == 0? 0.375f : metadata == 1 ? 0.625f : metadata == 2 ? 0.875f : 1, 1);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z){
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		this.checkAndDropBlock(world, x, y, z);
		int light = world.getBlockLightValue(x, y, z);
		if(light >= 12){
			int meta = world.getBlockMetadata(x, y, z);
			if(meta >= stages){
				return;
			}
			
			float growth = getGrowthSpeed(world, x, y, z, meta, light);
			if(random.nextInt((int)(50f / growth) + 1) == 0){
				if(meta < stages - 1){
					meta++;
					world.setBlockMetadataWithNotify(x, y, z, meta, 3);
				}
				if(heightStages > 1 && meta >= stages && world.isAirBlock(x, y + 1, z)){
					world.setBlock(x, y + 1, z, this, meta + 1, 3);
				}
			}
		}
		
	}
	
	private float getGrowthSpeed(World world, int x, int y, int z, int meta, int light){
		float growth = 0.125f * (light - 11);
		if(world.canBlockSeeTheSky(x, y, z)){
			growth += 2f;
		}
		if(world.getBlock(x, y - 1, z) != null && (!needsFarmland || world.getBlock(x, y - 1, z).isFertile(world, x, y - 1, z))){
			growth *= 1.5f;
		}
		return 1f + growth;
	}
	
	@Override
	protected boolean canPlaceBlockOn(Block block) {
		return block != null && 
				((needsFarmland && block == Blocks.farmland) ||
						(!needsFarmland && (block == Blocks.dirt || block == Blocks.grass)));
				
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return plantType;
	}
	
	// isNotGrown / isStillGrowing
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean client){
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < stages - 1){
			return true;
		}
		else if(heightStages > 1 && getHeightFromBase(world, x, y, z) < heightStages){
			Block blockAbove = world.getBlock(x, y + 1, z);
			if(blockAbove.getMaterial() == Material.air){
				return true;
			}
			else if(blockAbove == this){
				return func_149851_a(world, x, y + 1, z, client);
			}
		}
		return false;
	}
	
	// canBonemeal?
	@Override
	public boolean func_149852_a(World world, Random random, int x, int y, int z){
		return func_149851_a(world, x, y, z, !world.isRemote);
	}
	
	// incrementGrowthStage
	@Override
	public void func_149853_b(World world, Random random, int x, int y, int z){
		checkAndGrow(world, x, y, z);
	}
	
	protected void checkAndGrow(World world, int x, int y, int z){
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < stages - 1){
			world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
		}
		else if(heightStages > 1 && getHeightFromBase(world, x, y, z) < heightStages){
			Block blockAbove = world.getBlock(x, y + 1, z);
			if(blockAbove.getMaterial() == Material.air){
				// Grow here
				world.setBlock(x, y + 1, z, this);
			}
			else if(blockAbove == this){
				checkAndGrow(world, x, y + 1, z);
			}
			
		}
	}
	
	protected int getHeightFromBase(World world, int x, int y, int z){
		int i = 0;
		Block block = world.getBlock(x, y - i, z);
		while(block == this){
			i++;
			block = world.getBlock(x, y - i, z);
		}
		return i;
	}
	
	public abstract Item getSeedItem();	
	
	
	
	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}	
}