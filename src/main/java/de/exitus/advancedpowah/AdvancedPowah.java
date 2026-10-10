package de.exitus.advancedpowah;

import de.exitus.advancedpowah.registry.ModBlockEntities;
import de.exitus.advancedpowah.registry.ModBlocks;
import de.exitus.advancedpowah.registry.ModItems;
import de.exitus.advancedpowah.registry.ModMenus;
import de.exitus.advancedpowah.registry.ModTabs;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import owmii.powah.block.Tiles;
import owmii.powah.lib.item.IEnergyContainingItem;
import owmii.powah.lib.logistics.energy.EnergyItemHandler;

@Mod(AdvancedPowah.MOD_ID)
public class AdvancedPowah {
    public static final String MOD_ID = "advancedpowah";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public AdvancedPowah(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, AdvancedConfig.SPEC);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModTabs.TABS.register(modEventBus);

        modEventBus.addListener(BlockEntityTypeAddBlocksEvent.class, this::addBlocksToPowahBlockEntities);
        modEventBus.addListener(RegisterCapabilitiesEvent.class, this::registerCapabilities);
    }

    private void addBlocksToPowahBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        for (AdvancedTier tier : AdvancedTier.values()) {
            event.modify(Tiles.ENERGY_CELL.get(), ModBlocks.ENERGY_CELL.get(tier).get());
            event.modify(Tiles.ENDER_CELL.get(), ModBlocks.ENDER_CELL.get(tier).get());
            event.modify(Tiles.ENDER_GATE.get(), ModBlocks.ENDER_GATE.get(tier).get());
            event.modify(Tiles.CABLE.get(), ModBlocks.ENERGY_CABLE.get(tier).get());
            event.modify(Tiles.ENERGIZING_ROD.get(), ModBlocks.ENERGIZING_ROD.get(tier).get());
            event.modify(Tiles.FURNATOR.get(), ModBlocks.FURNATOR.get(tier).get());
            event.modify(Tiles.MAGMATOR.get(), ModBlocks.MAGMATOR.get(tier).get());
            event.modify(Tiles.THERMO_GEN.get(), ModBlocks.THERMO_GENERATOR.get(tier).get());
            event.modify(Tiles.SOLAR_PANEL.get(), ModBlocks.SOLAR_PANEL.get(tier).get());

            event.modify(Tiles.REACTOR.get(), ModBlocks.REACTOR.get(tier).get());
            event.modify(Tiles.REACTOR_PART.get(), ModBlocks.REACTOR.get(tier).get());
            event.modify(Tiles.PLAYER_TRANSMITTER.get(), ModBlocks.PLAYER_TRANSMITTER.get(tier).get());
            event.modify(Tiles.ENERGY_HOPPER.get(), ModBlocks.ENERGY_HOPPER.get(tier).get());
            event.modify(Tiles.ENERGY_DISCHARGER.get(), ModBlocks.ENERGY_DISCHARGER.get(tier).get());
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModBlockEntities.LUNAR_PANEL.get(),
                (be, side) -> be.getExternalStorage(side));
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModBlockEntities.HYBRID_PANEL.get(),
                (be, side) -> be.getExternalStorage(side));

        for (var entry : ModItems.ITEMS.getEntries()) {
            if (entry.get() instanceof IEnergyContainingItem energyItem) {
                event.registerItem(Capabilities.Energy.ITEM,
                        (stack, itemAccess) -> new EnergyItemHandler(itemAccess, energyItem.getEnergyInfo()),
                        entry.get());
            }
        }
    }
}
