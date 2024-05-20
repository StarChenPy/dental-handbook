package xyz.starchenpy.keepyourmouthhealthy.common.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;

public class ModTriggers {
    private static final List<CriterionTrigger<?>> deferredRegister = new ArrayList<>();

    public static final BrushingTrigger AFTER_BRUSHING_TEETH = reg(new BrushingTrigger());

    public static <T extends CriterionTrigger<?>> T reg(T criterion) {
        deferredRegister.add(criterion);
        return criterion;
    }

    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (CriterionTrigger<?> trigger : deferredRegister) {
                CriteriaTriggers.register(trigger);
            }
        });
    }
}
