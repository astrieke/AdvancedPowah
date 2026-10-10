package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import owmii.powah.block.Tier;
import owmii.powah.block.transmitter.PlayerTransmitterBlock;

public class AdvancedPlayerTransmitterBlock extends PlayerTransmitterBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedPlayerTransmitterBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setCapacity(this, tier::batteryCapacity);
        PowahHooks.setTransfer(this, tier::batteryTransfer);
        PowahHooks.setTransmitterChargingSpeed(this, tier::chargingTransfer);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
