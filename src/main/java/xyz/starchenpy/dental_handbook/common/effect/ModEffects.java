package xyz.starchenpy.dental_handbook.common.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MOD_ID);

    public static final Holder<MobEffect> TOOTH_DECAY = MOB_EFFECTS.register("tooth_decay", () ->
            new ModEffect(MobEffectCategory.HARMFUL, 0x999999));

    public static final Holder<MobEffect> INJURY_ORAL = MOB_EFFECTS.register("injury_oral", () ->
            new ModEffect(MobEffectCategory.HARMFUL, 0xCC0000));

    public static final Holder<MobEffect> CLEAN_ORAL = MOB_EFFECTS.register("clean_oral", () ->
            new ModEffect(MobEffectCategory.BENEFICIAL, 0xFFFF33));

    public static final Holder<MobEffect> ENDER_AFFINITY = MOB_EFFECTS.register("ender_affinity", () ->
            new ModEffect(MobEffectCategory.BENEFICIAL, 0xC541E4));

    public static void register(IEventBus modEventBus) {
        MOB_EFFECTS.register(modEventBus);
    }
}
