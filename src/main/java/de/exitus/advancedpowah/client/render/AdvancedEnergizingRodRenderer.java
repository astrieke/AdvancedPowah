package de.exitus.advancedpowah.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.block.AdvancedTiered;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import owmii.powah.block.energizing.EnergizingRodBlockEntity;
import owmii.powah.client.render.tile.EnergizingRodRenderer;
import owmii.powah.client.render.tile.EnergizingRodRendererState;
import owmii.powah.lib.client.util.RenderTypes;
import owmii.powah.util.math.V3d;

public class AdvancedEnergizingRodRenderer extends EnergizingRodRenderer {
    private static final RenderType BEAM = RenderTypes.entityBlendedNoDepthWrite(BEAM_TEXTURE);

    public AdvancedEnergizingRodRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public static class State extends EnergizingRodRendererState {
        public @Nullable AdvancedTier advancedTier;
    }

    @Override
    public EnergizingRodRendererState createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(EnergizingRodBlockEntity blockEntity, EnergizingRodRendererState state, float partialTicks,
            Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (state instanceof State advancedState) {
            advancedState.advancedTier = blockEntity.getBlockState().getBlock() instanceof AdvancedTiered tiered
                    ? tiered.getAdvancedTier()
                    : null;
        }
    }

    @Override
    public void submit(EnergizingRodRendererState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        if (!(state instanceof State advancedState) || advancedState.advancedTier == null) {
            super.submit(state, poseStack, submitNodeCollector, camera);
            return;
        }

        var orbCenter = state.orbCenter;
        if (orbCenter == null) {
            return;
        }

        int color = advancedState.advancedTier.color();
        int r = 0xFF & (color >> 16);
        int g = 0xFF & (color >> 8);
        int b = 0xFF & color;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);

        V3d pos = V3d.from(state.blockPos).center();
        V3d orbPos = V3d.from(orbCenter);
        float f2 = 1.0F;
        float f3 = f2 * 0.5F % 1.0F;
        Vec3 vec3d2 = pos.subtract(orbPos);
        double d0 = vec3d2.length();
        vec3d2 = vec3d2.normalize();
        float f5 = (float) Math.acos(Mth.clamp(vec3d2.y, -1.0, 1.0));
        float f6 = (float) Mth.atan2(vec3d2.z, vec3d2.x);

        poseStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
        poseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));

        float d1 = f2 * 0.0F;

        float d12 = Mth.cos((float) (d1 + Math.PI)) * 0.12F;
        float d13 = Mth.sin((float) (d1 + Math.PI)) * 0.12F;
        float d14 = Mth.cos(d1) * 0.12F;
        float d15 = Mth.sin(d1) * 0.12F;

        float d16 = Mth.cos((float) (d1 + (Math.PI / 2D))) * 0.12F;
        float d17 = Mth.sin((float) (d1 + (Math.PI / 2D))) * 0.12F;
        float d18 = Mth.cos((float) (d1 + (Math.PI * 1.5D))) * 0.12F;
        float d19 = Mth.sin((float) (d1 + (Math.PI * 1.5D))) * 0.12F;

        float d22 = (f3 - 1.0F);
        float d23 = (float) (d0 * 5.05D + d22);

        submitNodeCollector.submitCustomGeometry(poseStack, BEAM, (pose, buffer) -> {
            vertex(buffer, pose, d12, 0.0F, d13, r, g, b, 1, d23);
            vertex(buffer, pose, d12, (float) -d0, d13, r, g, b, 1, d22);
            vertex(buffer, pose, d14, (float) -d0, d15, r, g, b, 0.0F, d22);
            vertex(buffer, pose, d14, 0.0F, d15, r, g, b, 0.0F, d23);

            vertex(buffer, pose, d16, 0.0F, d17, r, g, b, 1, d23);
            vertex(buffer, pose, d16, (float) -d0, d17, r, g, b, 1, d22);
            vertex(buffer, pose, d18, (float) -d0, d19, r, g, b, 0.0F, d22);
            vertex(buffer, pose, d18, 0.0F, d19, r, g, b, 0.0F, d23);
        });

        poseStack.popPose();
    }

    private static void vertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, int r, int g, int b, float u, float v) {
        builder.addVertex(pose, x, y, z).setColor(r, g, b, 255).setUv(u, v);
    }
}
