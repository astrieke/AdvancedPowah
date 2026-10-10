package de.exitus.advancedpowah.machine;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jspecify.annotations.Nullable;
import owmii.powah.block.Tier;
import owmii.powah.lib.block.PowahBaseBlockEntity;
import owmii.powah.lib.block.PowahBaseEnergyBlock;
import owmii.powah.lib.client.util.Text;
import owmii.powah.lib.item.EnergyBlockItem;
import owmii.powah.lib.logistics.Transfer;
import owmii.powah.lib.logistics.inventory.BaseMenu;
import owmii.powah.util.Util;

public class MachineBlock extends PowahBaseEnergyBlock<MachineBlock> {
    private static final VoxelShape PANEL_SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    private final MachineType machineType;

    public MachineBlock(Properties properties, MachineType machineType) {
        super(properties, Tier.HARDENED, machineType::capacity, machineType::transfer);
        this.machineType = machineType;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    @Override
    public EnergyBlockItem<MachineBlock> getBlockItem(Item.Properties properties, @Nullable ResourceKey<CreativeModeTab> group) {
        return super.getBlockItem(properties.stacksTo(1), group);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return machineType.createBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public BaseMenu getContainer(int id, Inventory inventory, PowahBaseBlockEntity te, BlockHitResult result) {
        if (te instanceof MachineBlockEntity machine) {
            return new MachineMenu(id, inventory, machine);
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (machineType.isPanel()) {
            return PANEL_SHAPE;
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public Transfer getTransferType() {
        return machineType.transferType();
    }

    @Override
    public void additionalEnergyInfo(ItemStack stack, EnergyHandler energy, Consumer<Component> tooltip) {
        tooltip.accept(Component.translatable("info.lollipop.generates").withStyle(ChatFormatting.GRAY).append(Text.COLON)
                .append(Component.translatable("info.lollipop.fe.pet.tick", Util.numFormat(machineType.rate()))
                        .withStyle(ChatFormatting.DARK_GRAY)));
        tooltip.accept(Component.translatable("info.advancedpowah." + machineType.name().toLowerCase(java.util.Locale.ROOT))
                .withStyle(ChatFormatting.DARK_AQUA));
    }
}
