package xyz.starchenpy.dental_handbook.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.items.ItemStackHandler;
import xyz.starchenpy.dental_handbook.common.ModDamageType;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.gui.DentureMenu;
import xyz.starchenpy.dental_handbook.common.util.CapabilityUtil;
import xyz.starchenpy.dental_handbook.common.util.MathUtil;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.function.Consumer;

public class DentalForceps extends Item {

    public DentalForceps() {
        super(new Properties().stacksTo(1));
    }

    /**
     * 使用自定义动画应返回 CUSTOM
     */
    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getUseDuration(ItemStack itemStack) {
        return 40;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // 按shift的时候右键打开选择假牙的GUI
            if (player.isShiftKeyDown()) {
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (id, inv, player1) -> new DentureMenu(id, inv, player1, itemInHand),
                        Component.translatable("menu.title.dental_handbook.select_tooth")));
                return InteractionResultHolder.success(itemInHand);
            } else {
                if (this.verifyToothExtraction(player, itemInHand)) {
                    player.startUsingItem(usedHand);
                    return InteractionResultHolder.pass(itemInHand);
                }
            }
        }
        return InteractionResultHolder.fail(itemInHand);
    }

    /**
     * 验证能否拔牙
     * @param player        玩家
     * @param itemInHand    手中的工具
     */
    private boolean verifyToothExtraction(Player player, ItemStack itemInHand) {
        // 工具没选择假牙，跳过
        int slot = NbtUtil.getSelectedDentureSlot(itemInHand);
        if (slot < 0) {
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_forceps.no_select_tooth"), true);
            return false;
        }

        ItemStackHandler data = CapabilityUtil.getDenture(player);
        if (data == null) return false;

        // 选择的假牙槽没有牙，跳过
        if (data.getStackInSlot(slot).isEmpty()) {
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_forceps.no_tooth"), true);
            return false;
        }

        return true;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            int slot = NbtUtil.getSelectedDentureSlot(pStack);

            ItemStackHandler data = CapabilityUtil.getDenture(player);
            if (data == null) return super.finishUsingItem(pStack, pLevel, pLivingEntity);

            // 拔牙并放入玩家物品栏
            ItemStack itemStack = data.extractItem(slot, 1, false);
            if (itemStack.isEmpty()) return super.finishUsingItem(pStack, pLevel, pLivingEntity);

            boolean added = player.getInventory().add(itemStack);
            // 如果物品栏满了，就扔到玩家脚下
            if (!added) {
                player.drop(itemStack, false);
            }

            if (!player.getAbilities().instabuild) {
                player.hurt(player.damageSources().source(ModDamageType.ORAL_BLEEDING), 6);
                player.addEffect(new MobEffectInstance(ModEffects.INJURY_ORAL.get(), 1800));
            }

            player.getCooldowns().addCooldown(this, 10);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    /**
     * 在使用拔牙钳击中实体时，触发概率掉落一颗牙
     * @param pStack    使用的 itemStack
     * @param pTarget   目标实体
     * @param pAttacker 攻击者
     * @return  是否触发awardStat
     */
    @Override
    @ParametersAreNonnullByDefault
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Random random = new Random();
        if (random.nextInt(100) < 4) {
            pTarget.spawnAtLocation(new ItemStack(ModItems.INCISOR.get()), 1.2f);
        } else if (random.nextInt(100) < 4) {
            pTarget.spawnAtLocation(new ItemStack(ModItems.CANINE.get()), 1.2f);
        } else if (random.nextInt(100) < 6) {
            pTarget.spawnAtLocation(new ItemStack(ModItems.MOLAR.get()), 1.2f);
        } else if (random.nextInt(100) < 2) {
            pTarget.spawnAtLocation(new ItemStack(ModItems.WISDOM.get()), 1.2f);
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide) {
            return;
        }

        // 生成粒子效果与音效
        if (shouldTriggerItemUseEffects(livingEntity, stack)) {
            this.spawnItemParticles(level, livingEntity);
        }
    }

    private boolean shouldTriggerItemUseEffects(LivingEntity entity, ItemStack stack) {
        int i = entity.getUseItemRemainingTicks();
        boolean flag = i <= this.getUseDuration(stack) - (this.getUseDuration(stack) / 10);
        return flag && i % 4 == 0;
    }

    private void spawnItemParticles(Level level, LivingEntity entity) {
        for (int i = 0; i < 3; i++) {
            Vec3 speedVec3 = new Vec3(((double)level.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            speedVec3 = speedVec3.xRot(-entity.getXRot() * (float) (Math.PI / 180.0));
            speedVec3 = speedVec3.yRot(-entity.getYRot() * (float) (Math.PI / 180.0));

            double d0 = (double)(-level.random.nextFloat()) * 0.6 - 0.3;
            Vec3 posVec3 = new Vec3(((double)level.random.nextFloat() - 0.5) * 0.3, d0, 0.6);
            posVec3 = posVec3.xRot(-entity.getXRot() * (float) (Math.PI / 180.0));
            posVec3 = posVec3.yRot(-entity.getYRot() * (float) (Math.PI / 180.0));
            posVec3 = posVec3.add(entity.getX(), entity.getEyeY(), entity.getZ());

            level.addParticle(DustParticleOptions.REDSTONE, posVec3.x, posVec3.y, posVec3.z, speedVec3.x, speedVec3.y + 0.05, speedVec3.z);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            /**
             * 自定义使用动画
             * @param poseStack    姿势
             * @param player       玩家
             * @param arm          拿东西的手
             * @param itemInHand   具体的物品
             * @param partialTick  一部分tick 用来插值使动画平滑
             * @param equipProcess 十字指针下面的剑型槽 用来画拿出来的动画
             * @param swingProcess 摆动时间
             */
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                this.applyItemArmTransform(poseStack, arm, equipProcess);

                if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                    float progress = 1 - ((player.getUseItemRemainingTicks() - partialTick) / itemInHand.getUseDuration());

                    float nonlinear = MathUtil.easeOutQuint(progress, 50);

                    poseStack.translate(nonlinear * -0.3, nonlinear * 0.3, 0);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(nonlinear * 90));
                    poseStack.mulPose(Axis.XP.rotationDegrees(nonlinear * 45));

                    // 根据进度调整位置
                    if (progress > 0.2) {
                        poseStack.translate(nonlinear * -0.3, nonlinear * 0.3, 0);
                    }
                }

                return true;
            }

            /**
             * 物品取出时的上抬动作
             */
            private void applyItemArmTransform(PoseStack poseStack, HumanoidArm hand, float equipProcess) {
                int i = hand == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate((float)i * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
            }
        });
    }
}
