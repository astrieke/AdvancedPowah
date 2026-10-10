package de.exitus.advancedpowah.registry;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.machine.HybridPanelBlockEntity;
import de.exitus.advancedpowah.machine.LunarPanelBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,
            AdvancedPowah.MOD_ID);

    public static final Supplier<BlockEntityType<LunarPanelBlockEntity>> LUNAR_PANEL = register("lunar_panel",
            LunarPanelBlockEntity::new, ModBlocks.LUNAR_PANEL);
    public static final Supplier<BlockEntityType<HybridPanelBlockEntity>> HYBRID_PANEL = register("hybrid_panel",
            HybridPanelBlockEntity::new, ModBlocks.HYBRID_PANEL);

    private ModBlockEntities() {
    }

    private static <BE extends BlockEntity> Supplier<BlockEntityType<BE>> register(String path,
            BlockEntityType.BlockEntitySupplier<BE> supplier, Supplier<? extends Block> block) {
        return BLOCK_ENTITIES.register(path, () -> new BlockEntityType<>(supplier, new Block[] { block.get() }));
    }
}
