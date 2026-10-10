package de.exitus.advancedpowah.machine;

import de.exitus.advancedpowah.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class HybridPanelBlockEntity extends MachineBlockEntity {
    private boolean canSeeSky;

    public HybridPanelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HYBRID_PANEL.get(), pos, state);
    }

    @Override
    protected int postTick(Level world) {
        if (isRemote()) {
            return -1;
        }
        boolean changed = extractFromSides(world) > 0;
        long produced = 0;
        if (checkRedstone()) {
            if (this.ticks % 40 == 0) {
                this.canSeeSky = world.canSeeSky(this.worldPosition.above());
            }
            if (this.canSeeSky && world.dimensionType().hasSkyLight() && !getEnergy().isFull()) {
                produced = getEnergy().produce(getMachineType().rate());
            }
        }
        changed |= setStatus(produced > 0, produced);
        return changed || produced > 0 ? 5 : -1;
    }

    @Override
    public boolean isEnergyPresent(@Nullable Direction side) {
        return Direction.DOWN.equals(side);
    }
}
