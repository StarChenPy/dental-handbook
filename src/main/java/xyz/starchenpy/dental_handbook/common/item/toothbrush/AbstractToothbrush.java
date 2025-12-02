package xyz.starchenpy.dental_handbook.common.item.toothbrush;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.starchenpy.dental_handbook.common.ModSounds;
import xyz.starchenpy.dental_handbook.common.advancement.ModTriggers;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.particle.ToothpasteParticleOption;
import xyz.starchenpy.dental_handbook.common.util.DataComponentUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class AbstractToothbrush extends Item {
    protected int useDuration;

    public AbstractToothbrush(Properties item) {
        this(item, 160);
    }

    public AbstractToothbrush(Properties item, int useDuration) {
        super(item);
        this.useDuration = useDuration;
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

    /**
     * 决定了刷牙所需的时间
     * @return 时间(tick)
     */
    @Override
    @ParametersAreNonnullByDefault
    public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
        if (DataComponentUtil.getToothpaste(itemStack) != null) {
            return useDuration;
        }

        return 40;
    }

    /**
     * 决定了刷牙后冷却时间
     * @return 冷却时间(tick)
     */
    public int getCooldown() {
        return 1200;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack toothbrush = player.getItemInHand(hand);
        ItemStack toothpaste = player.getItemInHand(otherHand);

        // 刷牙或者抹牙膏
        if (toothpaste.getItem() instanceof AbstractToothpaste || DataComponentUtil.getToothpaste(toothbrush) instanceof AbstractToothpaste) {
            player.startUsingItem(hand);
            return InteractionResultHolder.pass(toothbrush);
        }

        return InteractionResultHolder.fail(toothbrush);
    }

    private EquipmentSlot getUseHand(Player player) {
        return player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }

    /**
     * 刷牙结束，触发效果、添加冷却、触发成就、消耗耐久
     * @param itemStack 使用完成的牙刷
     * @param level 所在的维度，没用
     * @param entity    使用者
     * @return  物品堆栈
     */
    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        // 如果牙刷上面有牙膏，触发刷牙
        if (DataComponentUtil.getToothpaste(itemStack) instanceof AbstractToothpaste toothpaste) {
            toothpaste.effect(entity);
            DataComponentUtil.removeToothpaste(itemStack);
            if (entity instanceof ServerPlayer player) {
                player.getCooldowns().addCooldown(this, getCooldown());
                itemStack.hurtAndBreak(1, player, this.getUseHand(player));
                ModTriggers.AFTER_BRUSHING_TEETH.get().trigger(player, itemStack);
            }
            return itemStack;
        }

        // 否则，检查另一只手有无牙膏，有则抹牙膏
        if (entity.getMainHandItem().getItem() instanceof AbstractToothpaste item) {
            entity.getMainHandItem().hurtAndBreak(1, entity, EquipmentSlot.MAINHAND);
            DataComponentUtil.setToothpaste(itemStack, item);
        } else if (entity.getOffhandItem().getItem() instanceof AbstractToothpaste item) {
            entity.getOffhandItem().hurtAndBreak(1, entity, EquipmentSlot.OFFHAND);
            DataComponentUtil.setToothpaste(itemStack, item);
        }

        return itemStack;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide) {
            return;
        }

        if (DataComponentUtil.getToothpaste(stack) == null) {
            return;
        }

        // 生成粒子效果与音效
        if (shouldTriggerItemUseEffects(livingEntity, stack)) {
            this.spawnItemParticles(level, livingEntity, DataComponentUtil.getToothpaste(stack));
            livingEntity.playSound(ModSounds.BRUSHING_TEETH_SOUND.get(),
                    0.5F + 0.5F * (float)level.random.nextInt(2),
                    (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    /**
     * 模仿原版，隔一段时间播放一次音效
     * @param entity 实体，应当是玩家
     * @param stack 物品堆栈
     * @return  是否应该播放音效
     */
    private boolean shouldTriggerItemUseEffects(LivingEntity entity, ItemStack stack) {
        int i = entity.getUseItemRemainingTicks();
        boolean flag = i <= this.getUseDuration(stack, entity) - (this.getUseDuration(stack, entity) / 10);
        return flag && i % 4 == 0;
    }

    private void spawnItemParticles(Level level, LivingEntity entity, Item item) {
        for (int i = 0; i < 3; i++) {
            Vec3 speedVec3 = new Vec3(((double)level.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            speedVec3 = speedVec3.xRot(-entity.getXRot() * (float) (Math.PI / 180.0));
            speedVec3 = speedVec3.yRot(-entity.getYRot() * (float) (Math.PI / 180.0));

            double d0 = (double)(-level.random.nextFloat()) * 0.6 - 0.3;
            Vec3 posVec3 = new Vec3(((double)level.random.nextFloat() - 0.5) * 0.3, d0, 0.6);
            posVec3 = posVec3.xRot(-entity.getXRot() * (float) (Math.PI / 180.0));
            posVec3 = posVec3.yRot(-entity.getYRot() * (float) (Math.PI / 180.0));
            posVec3 = posVec3.add(entity.getX(), entity.getEyeY(), entity.getZ());

            ItemStack stack = new ItemStack(item);
            level.addParticle(new ToothpasteParticleOption(stack), posVec3.x, posVec3.y, posVec3.z, speedVec3.x, speedVec3.y + 0.05, speedVec3.z);
        }
    }
}
