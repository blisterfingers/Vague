package villain.mc.vague.rendering.tiles;

import org.lwjgl.opengl.GL11;

import codechicken.lib.vec.Vector3;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import villain.mc.vague.Reference;
import villain.mc.vague.tileents.TileEntityLanternaMagica;
import villain.mc.vague.utils.Colour;

public class RendererLanternaMagica extends TileEntitySpecialRenderer implements IItemRenderer {
	
	private static final ResourceLocation LOC_MODEL = new ResourceLocation(Reference.RESOURCE_PREFIX + "models/lanterna/lanterna.obj");
	private static final ResourceLocation LOC_TEXTURE = new ResourceLocation(Reference.RESOURCE_PREFIX + "textures/tileentities/lanternamagica.png");
	
	private static final ResourceLocation LOC_MODELBOUNDS = new ResourceLocation(Reference.RESOURCE_PREFIX + "models/lanterna/lanternabounds.obj");
	private static final ResourceLocation LOC_TEXTUREBOUNDS = new ResourceLocation(Reference.RESOURCE_PREFIX + "textures/tileentities/lanternabounds.png");
	
	private static final double SPIN_MULTIPLIER = 0.05;
	
	private static final Colour COLOUR_DEFAULT = new Colour(0xFF57b8f0);
	private static final Colour COLOUR_RECORDING = new Colour(0xFFf05757);
	private static final Colour COLOUR_PLAYBACK = new Colour(0xFF65f057);
	
	private static final TileEntityLanternaMagica ITEM_TILEENT = new TileEntityLanternaMagica();
	
	private IModelCustom model = AdvancedModelLoader.loadModel(LOC_MODEL);
	private IModelCustom modelBounds = AdvancedModelLoader.loadModel(LOC_MODELBOUNDS);
	
	private final Vector3 offset = new Vector3();
	
	private EntityItem entityItem;
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z,	float f) {
		TileEntityLanternaMagica lanternEntity = (TileEntityLanternaMagica)tileEntity;
		
		if(entityItem == null){
			entityItem = new EntityItem(tileEntity.getWorldObj());
		}

		// Get meta
		int meta = 1;
		if(lanternEntity.getWorldObj() != null){
			meta = lanternEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			if(meta == 3) meta = 1;
			else if(meta == 1) meta = 3;
		}
		
		// Set up OpenGL
		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		
		// Rotate
		GL11.glRotated((90f * meta) + 180f, 0.0, 1.0, 0.0);
		
		// Bind texture
		bindTexture(LOC_TEXTURE);
		
		// Render Model
		model.renderAll();
		
		// Render Item
		if(lanternEntity.hasSlideStack()){
			entityItem.setEntityItemStack(lanternEntity.getSlideStack());
			float time = (float)((System.currentTimeMillis() * SPIN_MULTIPLIER) % 360l);
			GL11.glRotatef(time, 0f, 1f, 0f);
			RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0, -0.15, 0.0, 0f, 0f);
		}
		
		// Clean up
		GL11.glPopMatrix();
		
		// Render Bounds
		if(lanternEntity.getWorldObj() != null){
			bindTexture(LOC_TEXTUREBOUNDS);
			
			GL11.glPushMatrix();
			GL11.glTranslated(x + lanternEntity.getProjectionOffsetX() + 0.5, y + lanternEntity.getProjectonOffsetY() + 0.5, z + lanternEntity.getProjectionOffsetZ() + 0.5);
			GL11.glScaled(lanternEntity.getProjectionWidth(), lanternEntity.getProjectionHeight(), lanternEntity.getProjectionDepth());
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			
			Colour colour = COLOUR_DEFAULT;
			if(lanternEntity.hasSlideStack()){
				colour = COLOUR_RECORDING;
			}
			if(lanternEntity.getWorldObj().getBlockPowerInput(lanternEntity.xCoord, lanternEntity.yCoord, lanternEntity.zCoord) > 0){
				colour = COLOUR_PLAYBACK;
			}
			GL11.glColor4f(colour.getR(), colour.getG(), colour.getB(), colour.getA());
			
			modelBounds.renderAll();
			
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		offset.set(0, 0, 0);
		switch(type){
			case ENTITY:
				offset.x = -0.5;
				offset.y = -0.5;
				offset.z = -0.5;
				break;
			case EQUIPPED:
				offset.x = -0.1;
				offset.z = -0.1;
				break;
			case EQUIPPED_FIRST_PERSON:
				offset.y = 0.4;
				break;
			case INVENTORY:
				offset.y = -0.1;
				break;
			default:
		}
		TileEntityRendererDispatcher.instance.renderTileEntityAt(ITEM_TILEENT, offset.x, offset.y, offset.z, 0);
	}
}