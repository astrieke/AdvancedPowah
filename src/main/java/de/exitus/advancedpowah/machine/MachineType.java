package de.exitus.advancedpowah.machine;

import de.exitus.advancedpowah.AdvancedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import owmii.powah.lib.logistics.Transfer;

public enum MachineType {
    LUNAR_PANEL(Transfer.EXTRACT),
    HYBRID_PANEL(Transfer.EXTRACT);

    private final Transfer transferType;

    MachineType(Transfer transferType) {
        this.transferType = transferType;
    }

    public Transfer transferType() {
        return transferType;
    }

    public long rate() {
        return switch (this) {
        case LUNAR_PANEL -> AdvancedConfig.get(AdvancedConfig.LUNAR_PANEL_GENERATION);
        case HYBRID_PANEL -> AdvancedConfig.get(AdvancedConfig.HYBRID_PANEL_GENERATION);
        };
    }

    public boolean isPanel() {
        return this == LUNAR_PANEL || this == HYBRID_PANEL;
    }

    public long capacity() {
        return Math.max(10_000L, rate() * 2_000L);
    }

    public long transfer() {
        return Math.max(1_000L, rate() * 8L);
    }

    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return switch (this) {
        case LUNAR_PANEL -> new LunarPanelBlockEntity(pos, state);
        case HYBRID_PANEL -> new HybridPanelBlockEntity(pos, state);
        };
    }
}
