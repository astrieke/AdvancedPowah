package de.exitus.advancedpowah.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.exitus.advancedpowah.block.AdvancedTiered;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import owmii.powah.Powah;
import owmii.powah.block.reactor.ReactorBlockEntity;
import owmii.powah.client.model.CubeModel;
import owmii.powah.client.model.PowahLayerDefinitions;
import owmii.powah.client.model.ReactorModel;
import owmii.powah.client.render.tile.ReactorRenderer;
import owmii.powah.client.render.tile.ReactorRendererState;

public class AdvancedReactorRenderer extends ReactorRenderer {
    private final ReactorModel reactorModel;
    private final CubeModel reactorPartModel;

    public AdvancedReactorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.reactorModel = new ReactorModel(context.bakeLayer(PowahLayerDefinitions.REACTOR));
        this.reactorPartModel = new CubeModel(RenderTypes::entitySolid, context.bakeLayer(PowahLayerDefinitions.REACTOR_PART));
    }

    @Override
    public ReactorRendererState createRenderState() {
        return new AdvancedReactorState();
    }

    @Override
    public void extractRenderState(ReactorBlockEntity blockEntity, ReactorRendererState state, float partialTicks, Vec3 cameraPosition,
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

        var tier = advancedState.advancedTier;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(1.0f, -1.0f, -1.0f);
        if (!state.built) {
            var renderType = reactorPartModel.renderType(ReactorTextures.block(tier));
            submitNodeCollector.submitModel(reactorPartModel, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
        } else {
            poseStack.translate(0.0D, -1.0D, 0.0D);

            var renderType = reactorModel.renderType(Powah.id("textures/model/tile/reactor.png"));
            submitNodeCollector.submitModel(reactorModel, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);

            if (state.hasFuel) {
                var pulsingLightCoords = LightCoordsUtil.addSmoothBlockEmission(state.lightCoords, state.lightPulse);
                var renderTypeFilled = reactorModel.renderType(Powah.id("textures/model/tile/reactor_filled.png"));
                submitNodeCollector.submitModel(reactorModel, state, poseStack, renderTypeFilled, pulsingLightCoords, OverlayTexture.NO_OVERLAY, 0,
                        null);
            }

            var renderTypeTier = reactorModel.renderType(ReactorTextures.built(tier));
            submitNodeCollector.submitModel(reactorModel, state, poseStack, renderTypeTier, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
        }
        poseStack.popPose();
    }
}
