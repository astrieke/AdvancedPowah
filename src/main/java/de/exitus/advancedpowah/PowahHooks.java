package de.exitus.advancedpowah;

import java.lang.reflect.Field;
import java.util.function.LongSupplier;
import owmii.powah.block.hopper.EnergyHopperBlock;
import owmii.powah.block.transmitter.PlayerTransmitterBlock;
import owmii.powah.lib.block.PowahBaseEnergyBlock;
import owmii.powah.lib.block.PowahBaseGeneratorBlock;

public final class PowahHooks {
    private static final Field CAPACITY = find(PowahBaseEnergyBlock.class, "capacitySupplier");
    private static final Field TRANSFER = find(PowahBaseEnergyBlock.class, "transferSupplier");
    private static final Field GENERATION = find(PowahBaseGeneratorBlock.class, "generationSupplier");
    private static final Field HOPPER_CHARGING = find(EnergyHopperBlock.class, "chargingRateSupplier");
    private static final Field TRANSMITTER_CHARGING = find(PlayerTransmitterBlock.class, "chargingSpeedSupplier");

    private PowahHooks() {
    }

    public static void setCapacity(PowahBaseEnergyBlock<?> block, LongSupplier capacity) {
        set(CAPACITY, block, capacity);
    }

    public static void setTransfer(PowahBaseEnergyBlock<?> block, LongSupplier transfer) {
        set(TRANSFER, block, transfer);
    }

    public static void setGeneration(PowahBaseGeneratorBlock<?> block, LongSupplier generation) {
        set(GENERATION, block, generation);
    }

    public static void setGenerator(PowahBaseGeneratorBlock<?> block, LongSupplier capacity, LongSupplier transfer, LongSupplier generation) {
        setCapacity(block, capacity);
        setTransfer(block, transfer);
        setGeneration(block, generation);
    }

    public static void setHopperChargingRate(EnergyHopperBlock block, LongSupplier chargingRate) {
        set(HOPPER_CHARGING, block, chargingRate);
    }

    public static void setTransmitterChargingSpeed(PlayerTransmitterBlock block, LongSupplier chargingSpeed) {
        set(TRANSMITTER_CHARGING, block, chargingSpeed);
    }

    private static Field find(Class<?> owner, String name) {
        try {
            Field field = owner.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException | RuntimeException e) {
            throw new IllegalStateException("Advanced Powah! is not compatible with this version of Powah: field "
                    + owner.getSimpleName() + "." + name + " was not found or is not accessible", e);
        }
    }

    private static void set(Field field, Object target, LongSupplier value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Advanced Powah! could not set " + field.getName(), e);
        }
    }
}
