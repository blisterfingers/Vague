package villain.mc.vague.rendering.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import villain.mc.vague.Init;
import villain.mc.vague.entities.EntityGrenade;
import villain.mc.vague.utils.LogHelper;

@SideOnly(Side.CLIENT)
public class RenderGrenade extends Render
{
    public RenderGrenade() {
    	super();
    }

    public void doRender(Entity entity, double posX, double posY, double posZ, float par5, float par6) {
    	if(entity instanceof EntityGrenade == false){
    		LogHelper.error("Using RenderGrenade for something other than an EntityGrenade.");
    		return;
    	}
    	
    	EntityGrenade entityGrenade = (EntityGrenade)entity;
    	    	
        IIcon iicon = Init.grenades[entityGrenade.getType()].getIconFromDamage(0);

        if (iicon != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)posX, (float)posY, (float)posZ);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.bindEntityTexture(entity);
            Tessellator tessellator = Tessellator.instance;

            /*if (iicon == ItemPotion.func_94589_d("bottle_splash"))
            {
                int i = PotionHelper.func_77915_a(((EntityPotion)entity).getPotionDamage(), false);
                float f2 = (float)(i >> 16 & 255) / 255.0F;
                float f3 = (float)(i >> 8 & 255) / 255.0F;
                float f4 = (float)(i & 255) / 255.0F;
                GL11.glColor3f(f2, f3, f4);
                GL11.glPushMatrix();
                this.tessalate(tessellator, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }*/

            tesselateIcon(tessellator, iicon);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationItemsTexture;
    }

    private void tesselateIcon(Tessellator tessaellator, IIcon icon)
    {
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessaellator.startDrawingQuads();
        tessaellator.setNormal(0.0F, 1.0F, 0.0F);
        tessaellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
        tessaellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
        tessaellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
        tessaellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
        tessaellator.draw();
    }
}
