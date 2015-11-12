package villain.mc.vague.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import villain.mc.vague.Reference;
import villain.mc.vague.container.ContainerMagnet;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;
import villain.mc.vague.net.MagnetItemUpdatePacket;
import villain.mc.vague.net.PacketHandler;
import villain.mc.vague.utils.GuiHelper;

@SideOnly(Side.CLIENT)
public class GuiMagnet extends GuiContainer {

	private static final int SLOTS_PER_PAGE = 5;
	
	private ResourceLocation textureResourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.GUIIMAGE_MAGNET);
	private final InventoryMagnetBlacklist inventory;
	private final ContainerMagnet container;
	
	private int currentPage = 0;
	
	public GuiMagnet(ContainerMagnet container){
		super(container);
		this.container = container;
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
		
		
		// Checkboxes
		for(int i = 0; i < SLOTS_PER_PAGE; i++){
			if(container.getBlacklistSlot((currentPage * SLOTS_PER_PAGE) + i).getHasStack()){
				boolean checkBoxClicked = false;
				
				if(GuiHelper.isPointInArea(x, y, guiLeft + 30, guiTop + 11 + (i * 17), 10, 10)){
					inventory.toggleUseMeta((currentPage * SLOTS_PER_PAGE) + i);
					checkBoxClicked = true;
				}
				else if(GuiHelper.isPointInArea(x, y, guiLeft + 92, guiTop + 11 + (i * 17), 10, 10)){
					inventory.toggleUseNBT((currentPage * SLOTS_PER_PAGE) + i);
					checkBoxClicked = true;
				}
				
				if(checkBoxClicked){
					int slot = (currentPage * SLOTS_PER_PAGE) + i;
					boolean useMeta = inventory.getUseMeta(slot);
					boolean useNBT = inventory.getUseNBT(slot);
					PacketHandler.net.sendToServer(new MagnetItemUpdatePacket.MagnetItemUpdateMessage(slot, useMeta, useNBT));
				}
			}
		}
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
			drawTexturedModalRect(guiLeft + 7, guiTop + 97, 176, 40, 18, 10);
		}
		else {
			drawTexturedModalRect(guiLeft + 7, guiTop + 97, 176, 30, 18, 10);
		}
		
		// Button Next
		if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 151, guiTop + 97, 18, 19)){
			drawTexturedModalRect(guiLeft + 151, guiTop + 97, 194, 40, 18, 10);
		}
		else {
			drawTexturedModalRect(guiLeft + 151, guiTop + 97, 194, 30, 18, 10);
		}
		
		// Draw blacklist items
		for(int i = 0; i < SLOTS_PER_PAGE; i++){
			drawBlackListItemBG((currentPage * SLOTS_PER_PAGE) + i, mouseX, mouseY);
		}
		
		// Page Info
		String drawString = (currentPage + 1) + "/" + (inventory.getSizeInventory() / SLOTS_PER_PAGE);
		int stringWidth = fontRendererObj.getStringWidth(drawString);
		drawString(fontRendererObj, drawString, guiLeft + (xSize / 2) - (stringWidth / 2), guiTop + 98, 0xFFFFFF);
		
		// Info
		for(int i = 0; i < SLOTS_PER_PAGE; i++){
			if(container.getBlacklistSlot((currentPage * SLOTS_PER_PAGE) + i).getHasStack()){
				drawString(fontRendererObj, "Use Meta", guiLeft + 43, guiTop + 12 + (i * 17), 0xFFFFFF);
				drawString(fontRendererObj, "Use NBT", guiLeft + 104, guiTop + 12 + (i * 17), 0xFFFFFF);
			}
		}
	}
	
	private void drawBlackListItemBG(int slotIndex, int mouseX, int mouseY){
		int slotPageIndex = slotIndex - (currentPage * SLOTS_PER_PAGE);
		boolean hasStack = container.getBlacklistSlot(slotIndex).getHasStack();
		
		// Draw the ghost
		if(!hasStack){
			drawTexturedModalRect(guiLeft + 11, guiTop + 10 + (slotPageIndex * 17), 176, 0, 10, 12);
		}
		
		//Draw the trash-can
		if(GuiHelper.isPointInArea(mouseX, mouseY, guiLeft + 152, guiTop + 8 + (slotPageIndex * 17), 16, 16)){
			drawTexturedModalRect(guiLeft + 155, guiTop + 9 + (slotPageIndex * 17), 186, 0, 10, 13);
		}
		
		// Draw the checkboxes
		if(hasStack){
			boolean useMeta = inventory.getUseMeta(slotIndex);
			boolean useNBT = inventory.getUseNBT(slotIndex);
			
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
	}
}