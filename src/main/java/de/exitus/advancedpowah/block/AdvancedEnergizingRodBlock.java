package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.energizing.EnergizingRodBlock;

public class AdvancedEnergizingRodBlock extends EnergizingRodBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnergizingRodBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setCapacity(this, tier::rodCapacity);
        PowahHooks.setTransfer(this, tier::rodTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
