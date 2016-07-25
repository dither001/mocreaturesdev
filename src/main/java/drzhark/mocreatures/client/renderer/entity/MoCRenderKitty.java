package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC {

    public MoCModelKitty pussy1;

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityKitty) par1Entity).getTexture();
    }

    public MoCRenderKitty(ModelBase modelkitty, float f) {
        super(modelkitty, f);
        this.pussy1 = (MoCModelKitty) modelkitty;
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        super.doRender(entitykitty, d, d1, d2, f, f1);
        boolean flag2 = MoCreatures.proxy.getDisplayPetIcons();
        boolean flag3 = false;
        if (entitykitty.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityliving.getDistanceToEntity(this.renderManager.livingPlayer);
            if (f4 < 12F) {
                float f5 = 0.2F;
                if (this.pussy1.isSitting) {
                    f5 = 0.4F;
                }
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 - f5, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.getInstance();
                byte byte0 = -48;

                if (flag2 && ((MoCEntityKitty) entityliving).getIsEmo()) {
                    this.bindTexture(((MoCEntityKitty) entityliving).getEmoteIcon());
                    int i = -90;
                    int k = 32;
                    int l = (k / 2) * -1;
                    float f9 = 0.0F;
                    float f11 = 1.0F / k;
                    float f12 = 1.0F / k;
                    tessellator.getWorldRenderer().startDrawingQuads();
                    tessellator.getWorldRenderer().addVertexWithUV(l, i + k, f9, 0.0D, k * f12);
                    tessellator.getWorldRenderer().addVertexWithUV(l + k, i + k, f9, k * f11, k * f12);
                    tessellator.getWorldRenderer().addVertexWithUV(l + k, i, f9, k * f11, 0.0D);
                    tessellator.getWorldRenderer().addVertexWithUV(l, i, f9, 0.0D, 0.0D);
                    tessellator.draw();
                }

                GL11.glEnable(2896 /* GL_LIGHTING */);
                GL11.glPopMatrix();
            }
        }
    }

    public void doRender2(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        super.doRender(entitykitty, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        if (!entitykitty.getIsAdult()) {
            stretch(entitykitty);
        }
        return entityliving.ticksExisted + f;
    }

    protected void onMaBack(EntityLivingBase entityliving) {
        GL11.glRotatef(90F, 0.0F, 0.0F, -1F);

        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null)) {
            GL11.glTranslatef(-1.5F, 0.2F, -0.2F);
        } else {
            GL11.glTranslatef(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(EntityLivingBase entityliving) {
        GL11.glRotatef(90F, 0.0F, 0.0F, -1F);
        GL11.glTranslatef(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        this.pussy1.isSitting = entitykitty.getIsSitting();
        this.pussy1.isSwinging = entitykitty.getIsSwinging();
        this.pussy1.swingProgress = entitykitty.swingProgress;
        this.pussy1.kittystate = entitykitty.getKittyState();
        if (entitykitty.getKittyState() == 20) {
            onTheSide(entityliving);
        }
        if (entitykitty.climbingTree()) {
            rotateAnimal(entityliving);
        }
        if (entitykitty.upsideDown()) {
            upsideDown(entityliving);
        }
        if (entitykitty.onMaBack()) {
            onMaBack(entityliving);
        }
    }

    protected void rotateAnimal(EntityLivingBase entityliving) {
        if (!entityliving.onGround) {
            GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
        }
    }

    protected void stretch(MoCEntityKitty entitykitty) {
        GL11.glScalef(entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F);
    }

    protected void upsideDown(EntityLivingBase entityliving) {
        GL11.glRotatef(180F, 0.0F, 0.0F, -1F);
        GL11.glTranslatef(-0.35F, 0F, -0.55F);
    }
}
