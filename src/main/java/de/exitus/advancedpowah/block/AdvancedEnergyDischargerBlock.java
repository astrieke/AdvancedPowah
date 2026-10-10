package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.discharger.EnergyDischargerBlock;

public class AdvancedEnergyDischargerBlock extends EnergyDischargerBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnergyDischargerBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;

        PowahHooks.setCapacity(this, tier::batteryTransfer);
        PowahHooks.setTransfer(this, tier::batteryCapacity);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
