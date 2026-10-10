package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.ender.EnderCellBlock;

public class AdvancedEnderCellBlock extends EnderCellBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnderCellBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;

        PowahHooks.setTransfer(this, tier::batteryTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
