package de.exitus.advancedpowah.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import owmii.powah.lib.block.IInventoryHolder;
import owmii.powah.lib.block.PowahBaseEnergyStorageBlockEntity;
import owmii.powah.lib.logistics.Transfer;

public abstract class MachineBlockEntity extends PowahBaseEnergyStorageBlockEntity<MachineBlock> implements IInventoryHolder {
    private static final String ACTIVE = "advanced_active";
    private static final String RATE = "advanced_rate";

    private boolean active;
    private long currentRate;

    protected MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MachineType getMachineType() {
        return getBlock().getMachineType();
    }

    public boolean isActive() {
        return active;
    }

    public long getCurrentRate() {
        return currentRate;
    }

    protected boolean setStatus(boolean active, long currentRate) {
        if (this.active == active && this.currentRate == currentRate) {
            return false;
        }
        this.active = active;
        this.currentRate = currentRate;
        return true;
    }

    @Override
    public void readSync(ValueInput input) {
        super.readSync(input);
        this.active = input.getBooleanOr(ACTIVE, false);
        this.currentRate = input.getLongOr(RATE, 0L);
    }

    @Override
    public void writeSync(ValueOutput output) {
        output.putBoolean(ACTIVE, this.active);
        output.putLong(RATE, this.currentRate);
        super.writeSync(output);
    }

    @Override
    public boolean keepEnergy() {
        return true;
    }

    @Override
    public Transfer getTransferType() {
        return getMachineType().transferType();
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return false;
    }
}
