package xyz.starchenpy.dental_handbook.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.util.MathUtil;
import xyz.starchenpy.dental_handbook.common.util.DataComponentUtil;

import javax.annotation.ParametersAreNonnullByDefault;

@EventBusSubscriber(Dist.CLIENT)
public class ToothbrushAnimationRegistry {
    @SubscribeEvent
    public static void toothbrushAnimationRegistry(RegisterClientExtensionsEvent event) {
        event.registerItem(new ToothbrushExtensions(), ModItems.getAllToothbrush());
    }

    /**
     * 匿名类拆出个内部类，动画方面内容
     */
    static class ToothbrushExtensions implements IClientItemExtensions {
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
        @ParametersAreNonnullByDefault
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack
                itemInHand, float partialTick, float equipProcess, float swingProcess) {
            this.applyItemArmTransform(poseStack, arm, equipProcess);

            ItemStack toothpaste = player.getMainHandItem() == itemInHand ? player.getOffhandItem() : player.getMainHandItem();

            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                if (DataComponentUtil.getToothpaste(itemInHand) instanceof AbstractToothpaste) {
                    this.brushingArmTransform(poseStack, arm, player.getUseItemRemainingTicks(), itemInHand.getUseDuration(player), partialTick);
                } else if (toothpaste.getItem() instanceof AbstractToothpaste) {
                    this.applyToothpasteArmTransform(poseStack, arm, player.getUseItemRemainingTicks(), itemInHand.getUseDuration(player), partialTick);
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

        /**
         * 挤牙膏的动作
         */
        private void applyToothpasteArmTransform(PoseStack poseStack, HumanoidArm arm, int remainingDuration, int useDuration, float partialTick) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            // 使用进度
            float progress = MathUtil.easeOutQuint(1 - (remainingDuration - partialTick) / useDuration, 20);
            poseStack.translate(i * progress * -0.3f, 0, 0);
            float angle = i * progress * 30;
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
            poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
        }

        /**
         * 刷牙动作
         */
        private void brushingArmTransform(PoseStack poseStack, HumanoidArm arm, int remainingDuration, int useDuration, float partialTick) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            // 使用进度
            float progress = 1 - ((remainingDuration - partialTick) / useDuration);
            // 摆动幅度
            double amplitude = i * Math.sin(remainingDuration - partialTick) / 4;

            float nonlinear = MathUtil.easeOutQuint(progress, 50);
            poseStack.mulPose(Axis.ZP.rotationDegrees(i * nonlinear * 90));
            poseStack.mulPose(Axis.XP.rotationDegrees(nonlinear * 75));

            // 根据进度调整位置
            if (progress <= 0.1) {
                poseStack.translate(0, 0, -0.1);
            } else if (progress <= 0.4) {
                poseStack.translate(0, 0, amplitude);
            } else if (progress <= 0.7) {
                poseStack.translate(amplitude / 3, 0, 0);
            } else {
                poseStack.translate(0, 0, amplitude);
            }
        }
    }
}
