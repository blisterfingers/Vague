package villain.mc.vague.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import villain.mc.vague.Reference;
import villain.mc.vague.container.ContainerMagnet;
import villain.mc.vague.utils.LogHelper;
import villain.mc.vague.utils.MagnetBlackListItem;

@SideOnly(Side.CLIENT)
public class GuiMagnet extends GuiContainer {

	private ItemStack magnetStack;
	private ResourceLocation textureResourceLocation;
	private ContainerMagnet container;
	
	public GuiMagnet(InventoryPlayer inventoryPlayer, int magnetSlot){
		super(new ContainerMagnet(inventoryPlayer, magnetSlot));
		
		container = (ContainerMagnet)inventorySlots;
		
		textureResourceLocation = new ResourceLocation(Reference.MOD_ID, Reference.GUIIMAGE_MAGNET);
		
		magnetStack = inventoryPlayer.getStackInSlot(magnetSlot);
		
		if(magnetStack.stackTagCompound == null) return;
		
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button){
		super.mouseClicked(x, y, button);
		
		// Is the mouse currently over the add button?
		if(x > guiLeft + 145 && x < guiLeft + 145 + 9 &&
				y > guiTop + 8 + (container.getBlacklistItemCount() * 17) + 3 && y < guiTop + 8 + (container.getBlacklistItemCount() * 17) + 3 + 9){
			LogHelper.info("Item added");
			MagnetBlackListItem item = container.addBlackListItem();
		}
		else {
			// Is the mouse over one of the blacklist trashcans?
			for(int i = 0; i < container.getBlacklistItemCount(); i++){
				if(x >= guiLeft + 8 + 133 && x < guiLeft + 8 + 133 + 16 &&
						y >= guiTop + 8 + (i * 17) && y < guiTop + 8 + (i * 17) + 16){
					// Delete this item
					container.removeBlackListItem(i);
				}
			}
		}
		
		LogHelper.info("mouseClicked");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
		// Prepare
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureResourceLocation);
		
		// Background
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		// Draw black list items
		
		for(int i = 0; i < container.getBlacklistItemCount(); i++){
			drawBlackListItem(container.getBlackListItem(i), i * 17, mouseX, mouseY);
		}
		
		// Draw AddItem
		drawAddItem(container.getBlacklistItemCount() * 17, mouseX, mouseY);
	}
	
	private void drawBlackListItem(MagnetBlackListItem item, int yOffset, int mouseX, int mouseY){
		drawBlackListItemBG(yOffset, mouseX, mouseY);
	}
	
	private void drawBlackListItemBG(int yOffset, int mouseX, int mouseY){
		// Draw the list item bg
		drawTexturedModalRect(guiLeft + 8, guiTop + 8 + yOffset, 0, 166, 149, 17);
		
		// Draw the ghost
		drawTexturedModalRect(guiLeft + 8 + 3, guiTop + 8 + yOffset + 2, 149, 166, 10, 12);
		
		// Draw the trash-can
		if(mouseX >= guiLeft + 8 + 133 && mouseX < guiLeft + 8 + 133 + 16 &&
				mouseY >= guiTop + 8 + yOffset && mouseY < guiTop + 8 + yOffset + 16){
			drawTexturedModalRect(guiLeft + 8 + 136, guiTop + 8 + yOffset + 2, 10, 183, 10, 12);
			//LogHelper.info("drawing hover");
		}
		else {
			drawTexturedModalRect(guiLeft + 8 + 136, guiTop + 8 + yOffset + 2, 0, 183, 10, 12);
			//LogHelper.info("drawing other");
		}
	}
	
	private void drawAddItem(int yOffset, int mouseX, int mouseY){		
		if(mouseX >= guiLeft + 145 && mouseX < guiLeft + 145 + 9 &&
				mouseY >= guiTop + 8 + yOffset + 3 && mouseY < guiTop + 8 + yOffset + 3 + 9){
			drawTexturedModalRect(guiLeft + 145, guiTop + 8 + yOffset + 3, 168, 175, 9, 9);			
		}
		else {
			drawTexturedModalRect(guiLeft + 145, guiTop + 8 + yOffset + 3, 168, 166, 9, 9);		
		}
	}
}