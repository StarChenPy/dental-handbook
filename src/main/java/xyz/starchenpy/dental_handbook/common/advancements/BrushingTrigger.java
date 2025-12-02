package xyz.starchenpy.dental_handbook.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class BrushingTrigger extends SimpleCriterionTrigger<BrushingTrigger.TriggerInstance> {
    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "after_brushing_teeth");

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        ItemPredicate item = ItemPredicate.fromJson(json.get("item"));
        return new TriggerInstance(predicate, item);
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(stack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item) {
            super(BrushingTrigger.ID, player);
            this.item = item;
        }

        public boolean matches(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Nonnull
        @Override
        @ParametersAreNonnullByDefault
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject json = super.serializeToJson(conditions);
            json.add("item", this.item.serializeToJson());
            return json;
        }
    }
}
