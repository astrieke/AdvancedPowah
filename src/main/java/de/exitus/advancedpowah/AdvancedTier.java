package de.exitus.advancedpowah;

import com.mojang.serialization.Codec;
import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum AdvancedTier implements StringRepresentable {
    VOLTAIC(0x3B6BFF, 10_000L, 250L, 2.0D),
    ASTRAL(0xD06BFF, 50_000L, 600L, 4.0D),
    QUANTUM(0xFF4FB8, 200_000L, 1_500L, 8.0D);

    public static final Codec<AdvancedTier> CODEC = StringRepresentable.fromEnum(AdvancedTier::values);

    private final int color;
    private final long defaultScale;
    private final long defaultPassiveScale;
    private final double defaultReactorFuelMultiplier;
    private final String id = name().toLowerCase(Locale.ROOT);

    AdvancedTier(int color, long defaultScale, long defaultPassiveScale, double defaultReactorFuelMultiplier) {
        this.color = color;
        this.defaultScale = defaultScale;
        this.defaultPassiveScale = defaultPassiveScale;
        this.defaultReactorFuelMultiplier = defaultReactorFuelMultiplier;
    }

    public String id() {
        return id;
    }

    @Override
    public String getSerializedName() {
        return id;
    }

    public int color() {
        return color;
    }

    public long defaultScale() {
        return defaultScale;
    }

    public long defaultPassiveScale() {
        return defaultPassiveScale;
    }

    public double defaultReactorFuelMultiplier() {
        return defaultReactorFuelMultiplier;
    }

    public double reactorFuelMultiplier() {
        return AdvancedConfig.get(AdvancedConfig.REACTOR_FUEL_MULTIPLIER.get(this));
    }

    public long scale() {
        return AdvancedConfig.get(AdvancedConfig.SCALE.get(this));
    }

    public long passiveScale() {
        return AdvancedConfig.get(AdvancedConfig.PASSIVE_SCALE.get(this));
    }

    public long batteryCapacity() {
        return scale() * 1_000_000L;
    }

    public long batteryTransfer() {
        return scale() * 1_000L;
    }

    public long cableTransfer() {
        return scale() * 500L;
    }

    public long chargingTransfer() {
        return cableTransfer();
    }

    public long generatorCapacity() {
        return scale() * 20_000L;
    }

    public long generatorTransfer() {
        return scale() * 80L;
    }

    public long activeGeneration() {
        return scale() * 20L;
    }

    public long passiveGeneration() {
        return passiveScale() * 20L;
    }

    public long reactorCapacity() {
        return scale() * 250_000L;
    }

    public long reactorTransfer() {
        return scale() * 1_000L;
    }

    public long reactorGeneration() {
        return scale() * 250L;
    }

    public long rodCapacity() {
        return scale() * 10_000L;
    }

    public long rodTransfer() {
        return scale() * 100L;
    }
}
