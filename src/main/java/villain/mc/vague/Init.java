package villain.mc.vague;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import villain.mc.vague.blocks.BlockLanternaMagica;
import villain.mc.vague.blocks.BlockTipharesPlant;
import villain.mc.vague.blocks.BlockUncertaintyBorder;
import villain.mc.vague.blocks.tests.BlockConnectedTest;
import villain.mc.vague.blocks.tests.BlockNumBlock;
import villain.mc.vague.entities.EntityGrenade;
import villain.mc.vague.items.ItemFiringPin;
import villain.mc.vague.items.ItemGrenade;
import villain.mc.vague.items.ItemGrenadeCasing;
import villain.mc.vague.items.ItemIronNugget;
import villain.mc.vague.items.ItemLanternSlide;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.items.ItemPin;
import villain.mc.vague.items.ItemTestThing;
import villain.mc.vague.items.ItemTipharesEmbryo;
import villain.mc.vague.tileents.TileEntityLanternaMagica;
import villain.mc.vague.tileents.TileEntityTipharesPlant;
import villain.mc.vague.tileents.TileEntityUncertaintyBorder;
import villain.mc.vague.utils.Schematic;
import villain.mc.vague.utils.SchematicHelper;

public class Init {

	// Test Blocks
	public static BlockNumBlock blockNumBlock;
	public static BlockConnectedTest blockConnectedTest;
	
	// Blocks
	public static BlockUncertaintyBorder blockUncertaintyBorder;
	public static BlockTipharesPlant blockTipharesPlant;
	public static BlockLanternaMagica blockLanternaMagica;
	
	// Test Items
	public static ItemTestThing itemTestThing;
	
	// Items
	public static ItemMagnet itemMagnet;
	public static ItemTipharesEmbryo itemTipharesEmbryo;
	public static ItemLanternSlide itemLanternSlide16;
	public static ItemLanternSlide itemLanternSlide32;
	public static ItemLanternSlide itemLanternSlide64;
	
	public static ItemGrenadeCasing grenadeCasing;
	public static ItemFiringPin firingPin;
	public static ItemIronNugget ironNugget;
	public static ItemPin pin;
	public static ItemGrenade[] grenades;
	
	// Entities
	private static int modEntityID = 0;
	
	// Misc
	public static Schematic shelterGrenadeSchematic;
		
	public static void initAll(){
		// Test Blocks
		blockNumBlock = new BlockNumBlock();
		blockConnectedTest = new BlockConnectedTest();
		
		// Blocks
		blockUncertaintyBorder = new BlockUncertaintyBorder();
		blockTipharesPlant = new BlockTipharesPlant();
		blockLanternaMagica = new BlockLanternaMagica();
		
		// Test Items
		itemTestThing = new ItemTestThing();
		
		// Items
		itemMagnet = new ItemMagnet();
		itemTipharesEmbryo = new ItemTipharesEmbryo();
		itemLanternSlide16 = new ItemLanternSlide((byte)0);
		itemLanternSlide32 = new ItemLanternSlide((byte)1);
		itemLanternSlide64 = new ItemLanternSlide((byte)2);
		
		grenadeCasing = new ItemGrenadeCasing();
		firingPin = new ItemFiringPin();
		ironNugget = new ItemIronNugget();
		pin = new ItemPin();
		grenades = new ItemGrenade[]{
				new ItemGrenade(ItemGrenade.TYPE_NORMAL),
				new ItemGrenade(ItemGrenade.TYPE_CLUSTER),
				new ItemGrenade(ItemGrenade.TYPE_SPONGE),
				new ItemGrenade(ItemGrenade.TYPE_TORCH),
				new ItemGrenade(ItemGrenade.TYPE_SHELTER)};
		
		// Entities
		EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", modEntityID++, Vague.instance, 128, 2, true);
		
		// TileEntities
		GameRegistry.registerTileEntity(TileEntityUncertaintyBorder.class, "TileEntityUncertaintyBorder");
		GameRegistry.registerTileEntity(TileEntityTipharesPlant.class, "TileEntityTipharesPlant");
		GameRegistry.registerTileEntity(TileEntityLanternaMagica.class, "TileEntityLanternaMagica");
		
		// Misc
		shelterGrenadeSchematic = SchematicHelper.loadSchematic("darklogtest");
	}
	
	// Creative Tabs
	public static final CreativeTabs CREATIVETAB_MAIN = new CreativeTabs(Reference.MOD_ID.toLowerCase()){
		@Override
		public Item getTabIconItem() {
			return itemMagnet;
		}
	};
}