package de.exitus.advancedpowah.item;

import de.exitus.advancedpowah.AdvancedTier;
import net.minecraft.world.item.Item;
import owmii.powah.block.Tier;
import owmii.powah.item.BatteryItem;

public class AdvancedBatteryItem extends BatteryItem {
    private final AdvancedTier advancedTier;

    public AdvancedBatteryItem(Item.Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
    }

    @Override
    public long getEnergyCapacity() {
        return advancedTier.batteryCapacity();
    }

    @Override
    public long getEnergyTransfer() {
        return advancedTier.batteryCapacity();
    }

    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }
}
