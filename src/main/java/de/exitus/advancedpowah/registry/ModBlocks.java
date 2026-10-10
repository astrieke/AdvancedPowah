package de.exitus.advancedpowah.registry;

import static owmii.powah.lib.block.Properties.metal;
import static owmii.powah.lib.block.Properties.metalNoSolid;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.block.AdvancedCableBlock;
import de.exitus.advancedpowah.block.AdvancedEnderCellBlock;
import de.exitus.advancedpowah.block.AdvancedEnderGateBlock;
import de.exitus.advancedpowah.block.AdvancedEnergizingRodBlock;
import de.exitus.advancedpowah.block.AdvancedEnergyCellBlock;
import de.exitus.advancedpowah.block.AdvancedEnergyDischargerBlock;
import de.exitus.advancedpowah.block.AdvancedEnergyHopperBlock;
import de.exitus.advancedpowah.block.AdvancedFurnatorBlock;
import de.exitus.advancedpowah.block.AdvancedMagmatorBlock;
import de.exitus.advancedpowah.block.AdvancedPlayerTransmitterBlock;
import de.exitus.advancedpowah.block.AdvancedReactorBlock;
import de.exitus.advancedpowah.block.AdvancedSolarBlock;
import de.exitus.advancedpowah.block.AdvancedThermoBlock;
import de.exitus.advancedpowah.machine.MachineBlock;
import de.exitus.advancedpowah.machine.MachineType;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import owmii.powah.lib.block.PowahBaseBlock;
import owmii.powah.lib.item.PowahBlockItem;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AdvancedPowah.MOD_ID);

    public static final Map<AdvancedTier, DeferredBlock<Block>> CRYSTAL_BLOCK = new EnumMap<>(AdvancedTier.class);
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnergyCellBlock>> ENERGY_CELL = tiered("energy_cell",
            (tier, props) -> new AdvancedEnergyCellBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnderCellBlock>> ENDER_CELL = tiered("ender_cell",
            (tier, props) -> new AdvancedEnderCellBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnderGateBlock>> ENDER_GATE = tiered("ender_gate",
            (tier, props) -> new AdvancedEnderGateBlock(metalNoSolid(props, 2.0f, 20.0f).noCollision().noLootTable(), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedCableBlock>> ENERGY_CABLE = tiered("energy_cable",
            (tier, props) -> new AdvancedCableBlock(metalNoSolid(props, 2.0f, 20.0f).noCollision(), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnergizingRodBlock>> ENERGIZING_ROD = tiered("energizing_rod",
            (tier, props) -> new AdvancedEnergizingRodBlock(metalNoSolid(props, 2.0f, 20.0f).noCollision().noLootTable(), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedFurnatorBlock>> FURNATOR = tiered("furnator",
            (tier, props) -> new AdvancedFurnatorBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedMagmatorBlock>> MAGMATOR = tiered("magmator",
            (tier, props) -> new AdvancedMagmatorBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedThermoBlock>> THERMO_GENERATOR = tiered("thermo_generator",
            (tier, props) -> new AdvancedThermoBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedSolarBlock>> SOLAR_PANEL = tiered("solar_panel",
            (tier, props) -> new AdvancedSolarBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedReactorBlock>> REACTOR = tiered("reactor",
            (tier, props) -> new AdvancedReactorBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedPlayerTransmitterBlock>> PLAYER_TRANSMITTER = tiered("player_transmitter",
            (tier, props) -> new AdvancedPlayerTransmitterBlock(metalNoSolid(props, 2.0f, 20.0f).noLootTable(), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnergyHopperBlock>> ENERGY_HOPPER = tiered("energy_hopper",
            (tier, props) -> new AdvancedEnergyHopperBlock(metalNoSolid(props, 2.0f, 20.0f), tier));
    public static final Map<AdvancedTier, DeferredBlock<AdvancedEnergyDischargerBlock>> ENERGY_DISCHARGER = tiered("energy_discharger",
            (tier, props) -> new AdvancedEnergyDischargerBlock(metalNoSolid(props, 2.0f, 20.0f), tier));

    public static final DeferredBlock<MachineBlock> LUNAR_PANEL = BLOCKS.registerBlock("lunar_panel",
            props -> new MachineBlock(metalNoSolid(props, 2.0f, 20.0f), MachineType.LUNAR_PANEL));
    public static final DeferredBlock<MachineBlock> HYBRID_PANEL = BLOCKS.registerBlock("hybrid_panel",
            props -> new MachineBlock(metalNoSolid(props, 2.0f, 20.0f), MachineType.HYBRID_PANEL));

    static {
        for (AdvancedTier tier : AdvancedTier.values()) {
            CRYSTAL_BLOCK.put(tier, BLOCKS.registerBlock(tier.id() + "_crystal_block", props -> new Block(metal(props, 2.0f, 20.0f))));
        }

        for (var entry : BLOCKS.getEntries()) {
            ModItems.ITEMS.registerItem(entry.getId().getPath(), props -> {
                props.useBlockDescriptionPrefix();
                BlockItem blockItem;
                Block block = entry.get();
                if (block instanceof PowahBaseBlock<?> powahBlock) {
                    blockItem = powahBlock.getBlockItem(props, ModTabs.TAB_KEY);
                } else {
                    blockItem = new PowahBlockItem<>(block, props, ModTabs.TAB_KEY);
                }
                return blockItem;
            });
        }
    }

    private ModBlocks() {
    }

    private static <B extends Block> Map<AdvancedTier, DeferredBlock<B>> tiered(String name,
            BiFunction<AdvancedTier, BlockBehaviour.Properties, B> factory) {
        Map<AdvancedTier, DeferredBlock<B>> map = new EnumMap<>(AdvancedTier.class);
        for (AdvancedTier tier : AdvancedTier.values()) {
            DeferredBlock<B> block = BLOCKS.registerBlock(name + "_" + tier.id(), props -> factory.apply(tier, props));
            map.put(tier, block);
        }
        return map;
    }
}
