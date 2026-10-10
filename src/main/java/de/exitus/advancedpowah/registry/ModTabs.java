package de.exitus.advancedpowah.registry;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModTabs {
    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, AdvancedPowah.id("tab"));

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdvancedPowah.MOD_ID);

    static {
        TABS.register("tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.advancedpowah.tab"))
                .icon(() -> new ItemStack(ModItems.CRYSTAL.get(AdvancedTier.ASTRAL).get()))
                .displayItems((params, output) -> {
                    for (Map<AdvancedTier, ? extends ItemLike> group : groups()) {
                        for (AdvancedTier tier : AdvancedTier.values()) {
                            output.accept(group.get(tier));
                        }
                    }
                    output.accept(ModBlocks.LUNAR_PANEL.get());
                    output.accept(ModBlocks.HYBRID_PANEL.get());
                })
                .build());
    }

    private ModTabs() {
    }

    private static List<Map<AdvancedTier, ? extends ItemLike>> groups() {
        return List.of(
                ModItems.CAPACITOR,
                ModItems.BATTERY,
                ModItems.CRYSTAL,
                ModBlocks.CRYSTAL_BLOCK,
                ModBlocks.ENERGY_CELL,
                ModBlocks.ENDER_CELL,
                ModBlocks.ENDER_GATE,
                ModBlocks.ENERGY_CABLE,
                ModBlocks.ENERGIZING_ROD,
                ModBlocks.FURNATOR,
                ModBlocks.MAGMATOR,
                ModBlocks.THERMO_GENERATOR,
                ModBlocks.SOLAR_PANEL,
                ModBlocks.REACTOR,
                ModBlocks.PLAYER_TRANSMITTER,
                ModBlocks.ENERGY_HOPPER,
                ModBlocks.ENERGY_DISCHARGER);
    }
}
