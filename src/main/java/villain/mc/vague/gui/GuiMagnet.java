package villain.mc.vague.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import villain.mc.vague.Reference;
import villain.mc.vague.container.ContainerMagnet;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.net.MagnetItemUpdatePacket;
import villain.mc.vague.net.PacketHandler;
import villain.mc.vague.utils.Colour;
import villain.mc.vague.utils.GuiHelper;
import villain.mc.vague.utils.ItemNBTHelper;

@SideOnly(Side.CLIENT)
public class GuiMagnet extends GuiContainer {

	public static final int SLOTS_PER_PAGE = 10;
	private static final Colour COLOR_OFF = new Colour(0xFF6e6e6e);
	private static final Colour COLOR_OVER = new Colour(0xFF5d679e);
	private static final Colour COLOR_ON = new Colour(0xFF6dd263);
	private static final Colour COLOR_ONOVER = new Colour(0xFFa5ee9e);
	
	private ResourceLocation textureResourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.GUIIMAGE_MAGNET);
	private final ContainerMagnet container;
	private final ItemStack magnetStack;
	
	private final InventoryMagnetBlacklist inventory;
	
	private int currentPage = 0;
	
	public GuiMagnet(ContainerMagnet container, ItemStack magnetStack){
		super(container);
		this.container = container;
		this.magnetStack = magnetStack;
		
		inventory = container.getMagnetInventory();
		
		xSize = 176;
		ySize = 193;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button){
		super.mouseClicked(x, y, button);
		
		// Is the mouse over the next/prev buttons?
		if(GuiHelper.isPointInArea(x, y, guiLeft + 7, guiTop + 97, 18, 10)){
			currentPage--;
			if(currentPage < 0){
				currentPage = (inventory.getSizeInventory() / SLOTS_PER_PAGE) - 1;
			}
			container.setPage(currentPage);
		}
		else if(GuiHelper.isPointInArea(x, y, guiLeft + 151, guiTop + 97, 18, 10)){
			currentPage++;
			if(currentPage >= (inventory.getSizeInventory() / SLOTS_PER_PAGE)){
				currentPage = 0;
			}
			container.setPage(currentPage);
		}
		
		// Buttons
		for(int i = 0; i < SLOTS_PER_PAGE; i++){
			int slotIndex = (currentPage * SLOTS_PER_PAGE) + i;
			
			if(container.getBlacklistSlot(slotIndex).getHasStack()){
				boolean clicked = false;
				
				if(GuiHelper.isPointInArea(x, y, guiLeft + (i < 5 ? 32 : 113), guiTop + 10 + ((i % 5) * 17), 11, 12)){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					flags.setBoolean("slot" + slotIndex + "meta", !flags.getBoolean("slot" + slotIndex + "meta"));
					ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
					clicked = true;
				}
				else if(GuiHelper.isPointInArea(x, y, guiLeft + (i < 5 ? 52 : 133), guiTop + 10 + ((i % 5) * 17), 10, 12)){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					flags.setBoolean("slot" + slotIndex + "nbt", !flags.getBoolean("slot" + slotIndex + "nbt"));
					ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
					clicked = true;
				}
				else if(GuiHelper.isPointInArea(x, y, guiLeft + (i < 5 ? 70 : 151), guiTop + 10 + ((i % 5) * 17), 10, 12)){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					flags.setBoolean("slot" + slotIndex + "trash", !flags.getBoolean("slot" + slotIndex + "trash"));
					ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
					clicked = true;
				}
				
				if(clicked){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					boolean useMeta = flags.getBoolean("slot" + slotIndex + "meta");
					boolean useNBT = flags.getBoolean("slot" + slotIndex + "nbt");
					boolean useTrash = flags.getBoolean("slot" + slotIndex + "trash");
					PacketHandler.net.sendToServer(new MagnetItemUpdatePacket.MagnetItemUpdateMessage(slotIndex, useMeta, useNBT, useTrash));
				}
			}
		}
		
		
		// Checkboxes
		/*for(int i = 0; i < SLOTS_PER_PAGE; i++){
			int slotIndex = (currentPage * SLOTS_PER_PAGE) + i;
			
			if(container.getBlacklistSlot(slotIndex).getHasStack()){
				boolean checkBoxClicked = false;
				
				if(GuiHelper.isPointInArea(x, y, guiLeft + 30, guiTop + 11 + (i * 17), 10, 10)){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					flags.setBoolean("slot" + slotIndex + "meta", !flags.getBoolean("slot" + slotIndex + "meta"));					
					ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
					
					checkBoxClicked = true;
				}
				else if(GuiHelper.isPointInArea(x, y, guiLeft + 92, guiTop + 11 + (i * 17), 10, 10)){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					flags.setBoolean("slot" + slotIndex + "nbt", !flags.getBoolean("slot" + slotIndex + "nbt"));
					ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
					
					checkBoxClicked = true;
				}
				
				if(checkBoxClicked){
					NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
					boolean useMeta = flags.getBoolean("slot" + slotIndex + "meta");
					boolean useNBT = flags.getBoolean("slot" + slotIndex + "nbt");
					PacketHandler.net.sendToServer(new MagnetItemUpdatePacket.MagnetItemUpdateMessage(slotIndex, useMeta, useNBT));
				}
			}
		}*/
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
		// Prepare
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureResourceLocation);
		
		// Background
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		// Button Prev
		if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 7, guiTop + 97, 18, 10)){
			drawTexturedModalRect(guiLeft + 7, guiTop + 97, 176, 12, 18, 10);
		}
		
		// Button Next
		if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 151, guiTop + 97, 18, 19)){
			drawTexturedModalRect(guiLeft + 151, guiTop + 97, 194, 12, 18, 10);
		}
		
		// Draw blacklist items
		for(int i = 0; i < SLOTS_PER_PAGE; i++){
			drawBlackListItemBG((currentPage * SLOTS_PER_PAGE) + i, i, mouseX, mouseY);
		}
		
		// Page Info
		String drawString = (currentPage + 1) + "/" + (inventory.getSizeInventory() / SLOTS_PER_PAGE);
		int stringWidth = fontRendererObj.getStringWidth(drawString);
		drawString(fontRendererObj, drawString, guiLeft + (xSize / 2) - (stringWidth / 2), guiTop + 98, 0xFFFFFF);
		
		// Info
		/*for(int i = 0; i < SLOTS_PER_PAGE; i++){
			if(container.getBlacklistSlot((currentPage * SLOTS_PER_PAGE) + i).getHasStack()){
				drawString(fontRendererObj, "Use Meta", guiLeft + 43, guiTop + 12 + (i * 17), 0xFFFFFF);
				drawString(fontRendererObj, "Use NBT", guiLeft + 104, guiTop + 12 + (i * 17), 0xFFFFFF);
			}
		}*/
	}
	
	private void drawBlackListItemBG(int slotIndex, int pageIndex, int mouseX, int mouseY){
		boolean hasStack = container.getBlacklistSlot(slotIndex).getHasStack();
		boolean left = pageIndex < 5;
		
		// Draw the ghost
		if(!hasStack){
			drawTexturedModalRect(guiLeft + (left ? 11 : 92), guiTop + 10 + ((pageIndex % 5) * 17), 176, 0, 10, 12);
		}
		// Draw M/N/T
		else if(hasStack){
			NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
			boolean useMeta = flags.getBoolean("slot" + slotIndex + "meta");
			boolean useNBT = flags.getBoolean("slot" + slotIndex + "nbt");
			boolean useTrash = flags.getBoolean("slot" + slotIndex + "trash");
			
			// M			
			if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + (left ? 32 : 113), guiTop + 10 + ((pageIndex % 5) * 17), 11, 12)){
				if(useMeta){
					GL11.glColor4f(COLOR_ONOVER.getR(), COLOR_ONOVER.getG(), COLOR_ONOVER.getB(), COLOR_ONOVER.getA());
				}
				else {
					GL11.glColor4f(COLOR_OVER.getR(), COLOR_OVER.getG(), COLOR_OVER.getB(), COLOR_OVER.getA());
				}
			}
			else {
				if(useMeta){
					GL11.glColor4f(COLOR_ON.getR(), COLOR_ON.getG(), COLOR_ON.getB(), COLOR_ON.getA());
				}
				else {
					GL11.glColor4f(COLOR_OFF.getR(), COLOR_OFF.getG(), COLOR_OFF.getB(), COLOR_OFF.getA());
				}
			}			
			drawTexturedModalRect(guiLeft + (left ? 32 : 113), guiTop + 10 + ((pageIndex % 5) * 17), 206, 0, 11, 12);
			
			// N
			if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + (left ? 52 : 133), guiTop + 10 + ((pageIndex % 5) * 17), 10, 12)){
				if(useNBT){
					GL11.glColor4f(COLOR_ONOVER.getR(), COLOR_ONOVER.getG(), COLOR_ONOVER.getB(), COLOR_ONOVER.getA());
				}
				else {
					GL11.glColor4f(COLOR_OVER.getR(), COLOR_OVER.getG(), COLOR_OVER.getB(), COLOR_OVER.getA());
				}
			}
			else {
				if(useNBT){
					GL11.glColor4f(COLOR_ON.getR(), COLOR_ON.getG(), COLOR_ON.getB(), COLOR_ON.getA());
				}
				else {
					GL11.glColor4f(COLOR_OFF.getR(), COLOR_OFF.getG(), COLOR_OFF.getB(), COLOR_OFF.getA());
				}
			}			
			drawTexturedModalRect(guiLeft + (left ? 52 : 133), guiTop + 10 + ((pageIndex % 5) * 17), 217, 0, 10, 12);
			
			// T
			if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + (left ? 70 : 151), guiTop + 10 + ((pageIndex % 5) * 17), 10, 12)){
				if(useTrash){
					GL11.glColor4f(COLOR_ONOVER.getR(), COLOR_ONOVER.getG(), COLOR_ONOVER.getB(), COLOR_ONOVER.getA());
				}
				else {
					GL11.glColor4f(COLOR_OVER.getR(), COLOR_OVER.getG(), COLOR_OVER.getB(), COLOR_OVER.getA());
				}
			}
			else {
				if(useTrash){
					GL11.glColor4f(COLOR_ON.getR(), COLOR_ON.getG(), COLOR_ON.getB(), COLOR_ON.getA());
				}
				else {
					GL11.glColor4f(COLOR_OFF.getR(), COLOR_OFF.getG(), COLOR_OFF.getB(), COLOR_OFF.getA());
				}
			}			
			drawTexturedModalRect(guiLeft + (left ? 70 : 151), guiTop + 10 + ((pageIndex % 5) * 17), 196, 0, 10, 12);
		}
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		/*
		//Draw the trash-can
		if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 152, guiTop + 8 + (slotPageIndex * 17), 16, 16)){
			drawTexturedModalRect(guiLeft + 155, guiTop + 9 + (slotPageIndex * 17), 186, 0, 10, 13);
		}
		
		// Draw the checkboxes
		if(hasStack){
			NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
			boolean useMeta = flags.getBoolean("slot" + slotIndex + "meta");
			boolean useNBT = flags.getBoolean("slot" + slotIndex + "nbt");
			
			if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 30, guiTop + 11 + (slotPageIndex * 17), 10, 10)){
				drawTexturedModalRect(guiLeft + 30, guiTop + 11 + (slotPageIndex * 17), useMeta ? 206 : 196, 13, 10, 10);
			}
			else {
				drawTexturedModalRect(guiLeft + 30, guiTop + 11 + (slotPageIndex * 17), useMeta ? 186 : 176, 13, 10, 10);
			}
			
			if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 92, guiTop + 11 + (slotPageIndex * 17), 10, 10)){
				drawTexturedModalRect(guiLeft + 92, guiTop + 11 + (slotPageIndex * 17), useNBT ? 206 : 196, 13, 10, 10);
			}
			else {
				drawTexturedModalRect(guiLeft + 92, guiTop + 11 + (slotPageIndex * 17), useNBT ? 186 : 176, 13, 10, 10);
			}
		}
		*/
	}
}