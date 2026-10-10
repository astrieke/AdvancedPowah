package de.exitus.advancedpowah.client;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.client.render.AdvancedCableRenderer;
import de.exitus.advancedpowah.client.render.AdvancedEnergizingRodRenderer;
import de.exitus.advancedpowah.client.render.AdvancedReactorItemRenderer;
import de.exitus.advancedpowah.client.render.AdvancedReactorPartRenderer;
import de.exitus.advancedpowah.client.render.AdvancedReactorRenderer;
import de.exitus.advancedpowah.registry.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import owmii.powah.block.Tiles;

@Mod(value = AdvancedPowah.MOD_ID, dist = Dist.CLIENT)
public final class AdvancedPowahClient {
    public AdvancedPowahClient(IEventBus modEventBus) {
        modEventBus.addListener(RegisterMenuScreensEvent.class, this::registerScreens);
        modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, this::registerRenderers);
        modEventBus.addListener(RegisterSpecialModelRendererEvent.class, this::registerSpecialItemRenderers);
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.MACHINE.get(), MachineScreen::new);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Tiles.CABLE.get(), AdvancedCableRenderer::new);
        event.registerBlockEntityRenderer(Tiles.ENERGIZING_ROD.get(), AdvancedEnergizingRodRenderer::new);
        event.registerBlockEntityRenderer(Tiles.REACTOR.get(), AdvancedReactorRenderer::new);
        event.registerBlockEntityRenderer(Tiles.REACTOR_PART.get(), AdvancedReactorPartRenderer::new);
    }

    private void registerSpecialItemRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(AdvancedReactorItemRenderer.ID, AdvancedReactorItemRenderer.Unbaked.MAP_CODEC);
    }
}
