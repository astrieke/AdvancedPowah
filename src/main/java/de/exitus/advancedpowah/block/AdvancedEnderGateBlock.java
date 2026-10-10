package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.ender.EnderGateBlock;

public class AdvancedEnderGateBlock extends EnderGateBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnderGateBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;

        PowahHooks.setTransfer(this, tier::cableTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
