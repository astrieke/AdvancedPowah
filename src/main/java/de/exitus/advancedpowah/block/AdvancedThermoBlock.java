package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.thermo.ThermoBlock;

public class AdvancedThermoBlock extends ThermoBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedThermoBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setGenerator(this, tier::generatorCapacity, tier::generatorTransfer, tier::passiveGeneration);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
