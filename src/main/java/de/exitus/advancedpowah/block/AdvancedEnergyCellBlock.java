package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.energycell.EnergyCellBlock;

public class AdvancedEnergyCellBlock extends EnergyCellBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedEnergyCellBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setCapacity(this, tier::batteryCapacity);
        PowahHooks.setTransfer(this, tier::batteryTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
