package xyz.starchenpy.dental_handbook.common.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class ToothpasteParticle extends TextureSheetParticle {
    private final float uo;
    private final float vo;
    private final boolean turnOver;

    ToothpasteParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ItemStack itemStack) {
        this(level, x, y, z, itemStack);
        this.xd *= 0.10000000149011612;
        this.yd *= 0.10000000149011612;
        this.zd *= 0.10000000149011612;
        this.xd += xSpeed;
        this.yd += ySpeed;
        this.zd += zSpeed;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    /**
     * 刷牙时的牙膏颗粒
     */
    protected ToothpasteParticle(ClientLevel level, double x, double y, double z, ItemStack itemStack) {
        super(level, x, y, z, 0.0, 0.0, 0.0);
        var model = Minecraft.getInstance().getItemRenderer().getModel(itemStack, level, null, 0);
        BakedModel bakedModel = model.getOverrides().resolve(model, itemStack, level, null, 0);
        if (bakedModel != null) {
            this.setSprite(bakedModel.getParticleIcon(ModelData.EMPTY));
        }
        this.gravity = 1.0F;
        this.quadSize /= 2.0F;
        this.uo = 12.0F + this.random.nextFloat() * 4.0F;
        this.vo = this.random.nextFloat() * 4.0F;
        this.turnOver = this.random.nextBoolean();
    }

    @Override
    protected float getU0() {
        return this.sprite.getU(this.uo);
    }

    @Override
    protected float getU1() {
        return this.sprite.getU(this.uo + 1.0F);
    }

    @Override
    protected float getV0() {
        return this.sprite.getV(this.vo);
    }

    @Override
    protected float getV1() {
        return this.sprite.getV(this.vo + 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public static class Provider implements ParticleProvider<ToothpasteParticleOption> {
        public Particle createParticle(ToothpasteParticleOption type, ClientLevel level, double x, double y, double z, double speedX, double speedY, double speedZ) {
            return new ToothpasteParticle(level, x, y, z, speedX, speedY, speedZ, type.getItem());
        }
    }
}
