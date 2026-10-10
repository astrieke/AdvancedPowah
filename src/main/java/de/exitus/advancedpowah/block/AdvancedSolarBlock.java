package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.solar.SolarBlock;

public class AdvancedSolarBlock extends SolarBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedSolarBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setGenerator(this, tier::generatorCapacity, tier::generatorTransfer, tier::passiveGeneration);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
