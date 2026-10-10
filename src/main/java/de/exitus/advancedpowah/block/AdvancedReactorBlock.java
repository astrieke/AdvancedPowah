package de.exitus.advancedpowah.block;

import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.PowahHooks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import owmii.powah.block.Tier;
import owmii.powah.block.reactor.ReactorBlock;
import owmii.powah.block.reactor.ReactorBlockEntity;

public class AdvancedReactorBlock extends ReactorBlock implements AdvancedTiered {
    private final AdvancedTier advancedTier;

    public AdvancedReactorBlock(Properties properties, AdvancedTier tier) {
        super(properties, Tier.NITRO);
        this.advancedTier = tier;
        PowahHooks.setGenerator(this, tier::reactorCapacity, tier::reactorTransfer, tier::reactorGeneration);
    }

    @Override
    public AdvancedTier getAdvancedTier() {
        return advancedTier;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        BlockEntityTicker<T> powahTicker = super.getTicker(level, state, blockEntityType);
        if (powahTicker == null || level.isClientSide()) {
            return powahTicker;
        }
        return (tickLevel, pos, tickState, blockEntity) -> {
            powahTicker.tick(tickLevel, pos, tickState, blockEntity);
            if (blockEntity instanceof ReactorBlockEntity reactor && reactor.isRunning()) {
                double extra = (advancedTier.reactorFuelMultiplier() - 1.0D) * reactor.calcConsumption();
                if (extra > 0.0D) {
                    reactor.fuel.back(extra);
                }
            }
        };
    }
}
