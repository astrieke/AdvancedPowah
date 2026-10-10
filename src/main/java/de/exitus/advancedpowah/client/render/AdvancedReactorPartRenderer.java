package de.exitus.advancedpowah.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.exitus.advancedpowah.block.AdvancedTiered;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import owmii.powah.block.reactor.ReactorPartBlockEntity;
import owmii.powah.client.model.CubeModel;
import owmii.powah.client.model.PowahLayerDefinitions;
import owmii.powah.client.render.tile.ReactorPartRenderer;
import owmii.powah.client.render.tile.ReactorRendererState;

public class AdvancedReactorPartRenderer extends ReactorPartRenderer {
    private final CubeModel reactorPartModel;

    public AdvancedReactorPartRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.reactorPartModel = new CubeModel(RenderTypes::entitySolid, context.bakeLayer(PowahLayerDefinitions.REACTOR_PART));
    }

    @Override
    public ReactorRendererState createRenderState() {
        return new AdvancedReactorState();
    }

    @Override
    public void extractRenderState(ReactorPartBlockEntity blockEntity, ReactorRendererState state, float partialTicks, Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (state instanceof AdvancedReactorState advancedState) {
            advancedState.advancedTier = blockEntity.getBlockState().getBlock() instanceof AdvancedTiered tiered
                    ? tiered.getAdvancedTier()
                    : null;
        }
    }

    @Override
    public void submit(ReactorRendererState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!(state instanceof AdvancedReactorState advancedState) || advancedState.advancedTier == null) {
            super.submit(state, poseStack, submitNodeCollector, camera);
            return;
        }
        if (state.built) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(1.0f, -1.0f, -1.0f);
        var renderType = RenderTypes.entitySolid(ReactorTextures.block(advancedState.advancedTier));
        submitNodeCollector.submitModel(reactorPartModel, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
        poseStack.popPose();
    }
}
