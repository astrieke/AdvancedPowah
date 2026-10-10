package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.furnator.FurnatorBlock;

public class AdvancedFurnatorBlock extends FurnatorBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedFurnatorBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setGenerator(this, tier::generatorCapacity, tier::generatorTransfer, tier::activeGeneration);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
