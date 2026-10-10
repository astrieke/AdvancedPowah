package de.exitus.advancedpowah.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.block.AdvancedTiered;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import owmii.powah.block.cable.CableBlockEntity;
import owmii.powah.client.model.CableModel;
import owmii.powah.client.model.PowahLayerDefinitions;
import owmii.powah.client.render.tile.CableRenderer;
import owmii.powah.client.render.tile.CableRendererState;
import owmii.powah.lib.logistics.Transfer;

public class AdvancedCableRenderer extends CableRenderer {
    private final CableModel model;

    public AdvancedCableRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.model = new CableModel(context.bakeLayer(PowahLayerDefinitions.CABLE));
    }

    public static class State extends CableRendererState {
        public @Nullable AdvancedTier advancedTier;
    }

    @Override
    public CableRendererState createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(CableBlockEntity blockEntity, CableRendererState state, float partialTicks, Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (state instanceof State advancedState) {
            advancedState.advancedTier = blockEntity.getBlockState().getBlock() instanceof AdvancedTiered tiered
                    ? tiered.getAdvancedTier()
                    : null;
        }
    }

    @Override
    public void submit(CableRendererState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!(state instanceof State advancedState) || advancedState.advancedTier == null) {
            super.submit(state, poseStack, submitNodeCollector, camera);
            return;
        }

        String tier = advancedState.advancedTier.id();
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.translate(0.0, -0.125, 0.0);
        poseStack.scale(1.0F, -1.0F, -1.0F);

        for (var side : Direction.values()) {
            var transfer = state.transfer[side.get3DDataValue()];
            if (transfer != Transfer.NONE) {
                var suffix = switch (transfer) {
                case ALL -> "all";
                case RECEIVE -> "out";
                case EXTRACT -> "in";
                case NONE -> throw new UnsupportedOperationException();
                };
                var texture = AdvancedPowah.id("textures/model/tile/energy_cable_" + tier + "_" + suffix + ".png");
                var renderType = model.renderType(texture);
                submitNodeCollector.submitModelPart(model.getIndicatorPart(side), poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY,
                        null);
                submitNodeCollector.submitModelPart(model.getPlatePart(side), poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY,
                        null);
            }
        }

        poseStack.popPose();
    }
}
