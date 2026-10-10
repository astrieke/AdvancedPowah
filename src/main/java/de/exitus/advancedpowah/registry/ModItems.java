package de.exitus.advancedpowah.registry;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.item.AdvancedBatteryItem;
import java.util.EnumMap;
import java.util.Map;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import owmii.powah.item.CapacitorItem;
import owmii.powah.lib.item.PowahBaseItem;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AdvancedPowah.MOD_ID);

    public static final Map<AdvancedTier, DeferredItem<PowahBaseItem>> CRYSTAL = new EnumMap<>(AdvancedTier.class);
    public static final Map<AdvancedTier, DeferredItem<CapacitorItem>> CAPACITOR = new EnumMap<>(AdvancedTier.class);
    public static final Map<AdvancedTier, DeferredItem<AdvancedBatteryItem>> BATTERY = new EnumMap<>(AdvancedTier.class);

    static {
        for (AdvancedTier tier : AdvancedTier.values()) {
            CRYSTAL.put(tier, ITEMS.registerItem("crystal_" + tier.id(), PowahBaseItem::new));
        }
        for (AdvancedTier tier : AdvancedTier.values()) {
            CAPACITOR.put(tier, ITEMS.registerItem("capacitor_" + tier.id(), CapacitorItem::new));
        }
        for (AdvancedTier tier : AdvancedTier.values()) {
            BATTERY.put(tier, ITEMS.registerItem("battery_" + tier.id(),
                    props -> new AdvancedBatteryItem(props.stacksTo(1), tier)));
        }
    }

    private ModItems() {
    }
}
