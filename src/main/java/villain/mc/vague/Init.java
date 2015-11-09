package villain.mc.vague;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import villain.mc.vague.blocks.BlockTipharesPlant;
import villain.mc.vague.blocks.BlockUncertaintyBorder;
import villain.mc.vague.blocks.tests.BlockConnectedTest;
import villain.mc.vague.blocks.tests.BlockNumBlock;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.items.ItemTestThing;
import villain.mc.vague.items.ItemTipharesEmbryo;
import villain.mc.vague.tileents.TileEntityUncertaintyBorder;

public class Init {

	// Test Blocks
	public static BlockNumBlock blockNumBlock;
	public static BlockConnectedTest blockConnectedTest;
	
	// Blocks
	public static BlockUncertaintyBorder blockUncertaintyBorder;
	public static BlockTipharesPlant blockTipharesPlant;
	
	// Test Items
	public static ItemTestThing itemTestThing;
	
	// Items
	public static ItemMagnet itemMagnet;
	public static ItemTipharesEmbryo itemTipharesEmbryo;
	
	public static void initItems(){
		// Test Blocks
		blockNumBlock = new BlockNumBlock();
		blockConnectedTest = new BlockConnectedTest();
		
		// Blocks
		blockUncertaintyBorder = new BlockUncertaintyBorder();
		blockTipharesPlant = new BlockTipharesPlant();
		
		// Test Items
		itemTestThing = new ItemTestThing();
		
		// Items
		itemMagnet = new ItemMagnet();
		itemTipharesEmbryo = new ItemTipharesEmbryo();
		
		// TileEntities
		GameRegistry.registerTileEntity(TileEntityUncertaintyBorder.class, "TileEntityUncertaintyBorder");
	}
	
	// Creative Tabs
	public static final CreativeTabs CREATIVETAB_MAIN = new CreativeTabs(Reference.MOD_ID.toLowerCase()){
		@Override
		public Item getTabIconItem() {
			return itemMagnet;
		}
	};
}