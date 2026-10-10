package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.hopper.EnergyHopperBlock;

public class AdvancedEnergyHopperBlock extends EnergyHopperBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnergyHopperBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setCapacity(this, tier::batteryCapacity);
        PowahHooks.setTransfer(this, tier::batteryTransfer);
        PowahHooks.setHopperChargingRate(this, tier::chargingTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
