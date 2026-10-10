package de.exitus.advancedpowah;

import java.util.EnumMap;
import java.util.Map;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AdvancedConfig {
    private static final long MAX_SCALE = 2_000_000L;

    public static final ModConfigSpec SPEC;

    public static final Map<AdvancedTier, ModConfigSpec.LongValue> SCALE = new EnumMap<>(AdvancedTier.class);
    public static final Map<AdvancedTier, ModConfigSpec.LongValue> PASSIVE_SCALE = new EnumMap<>(AdvancedTier.class);
    public static final Map<AdvancedTier, ModConfigSpec.DoubleValue> REACTOR_FUEL_MULTIPLIER = new EnumMap<>(AdvancedTier.class);

    public static final ModConfigSpec.LongValue LUNAR_PANEL_GENERATION;
    public static final ModConfigSpec.LongValue HYBRID_PANEL_GENERATION;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment(
                "Scaling of the new tiers. For comparison: Nitro uses scale = 2000 and passive_scale = 100 in Powah.",
                "All values are derived from these with Powah's own formulas:",
                "  Energy Cell/Battery/Hopper/Transmitter: capacity = scale * 1,000,000 FE, transfer = scale * 1,000 FE/t",
                "  Cable = scale * 500 FE/t, Ender Cell = scale * 1,000 FE/t, Energizing Rod = scale * 100 FE/t",
                "  Furnator/Magmator = scale * 20 FE/t, Reactor = scale * 250 FE/t",
                "  Solar Panel/Thermo Generator = passive_scale * 20 FE/t",
                "reactor_fuel_multiplier: fuel consumption of the reactor compared to a Nitro reactor (1.0 = same as Nitro).")
                .push("tiers");
        for (AdvancedTier tier : AdvancedTier.values()) {
            builder.push(tier.id());
            SCALE.put(tier, builder.defineInRange("scale", tier.defaultScale(), 1L, MAX_SCALE));
            PASSIVE_SCALE.put(tier, builder.defineInRange("passive_scale", tier.defaultPassiveScale(), 1L, MAX_SCALE));
            REACTOR_FUEL_MULTIPLIER.put(tier,
                    builder.defineInRange("reactor_fuel_multiplier", tier.defaultReactorFuelMultiplier(), 1.0D, 1000.0D));
            builder.pop();
        }
        builder.pop();

        builder.push("machines");
        LUNAR_PANEL_GENERATION = builder
                .comment("FE/t the Lunar Panel generates at night under open sky.")
                .defineInRange("lunar_panel_generation", 1_200L, 1L, 1_000_000_000L);
        HYBRID_PANEL_GENERATION = builder
                .comment("FE/t the Hybrid Panel generates by day and by night under open sky.")
                .defineInRange("hybrid_panel_generation", 2_000L, 1L, 1_000_000_000L);
        builder.pop();

        SPEC = builder.build();
    }

    private AdvancedConfig() {
    }

    public static long get(ModConfigSpec.LongValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double get(ModConfigSpec.DoubleValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
