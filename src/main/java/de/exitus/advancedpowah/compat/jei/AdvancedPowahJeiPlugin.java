package de.exitus.advancedpowah.compat.jei;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import de.exitus.advancedpowah.registry.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import owmii.powah.compat.jei.JeiEnergizingCategory;
import owmii.powah.compat.jei.JeiFluidCoolantCategory;
import owmii.powah.compat.jei.JeiHeatSourceCategory;
import owmii.powah.compat.jei.JeiMagmatorCategory;
import owmii.powah.compat.jei.JeiReactorFuelCategory;
import owmii.powah.compat.jei.JeiSolidCoolantCategory;

@JeiPlugin
public class AdvancedPowahJeiPlugin implements IModPlugin {
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (AdvancedTier tier : AdvancedTier.values()) {
            registration.addCraftingStation(JeiEnergizingCategory.TYPE.get(), ModBlocks.ENERGIZING_ROD.get(tier).get());
            registration.addCraftingStation(JeiMagmatorCategory.TYPE, ModBlocks.MAGMATOR.get(tier).get());

            var thermo = ModBlocks.THERMO_GENERATOR.get(tier).get();
            registration.addCraftingStation(JeiHeatSourceCategory.TYPE, thermo);
            registration.addCraftingStation(JeiFluidCoolantCategory.TYPE, thermo);

            var reactor = ModBlocks.REACTOR.get(tier).get();
            registration.addCraftingStation(JeiSolidCoolantCategory.TYPE, reactor);
            registration.addCraftingStation(JeiFluidCoolantCategory.TYPE, reactor);
            registration.addCraftingStation(JeiReactorFuelCategory.TYPE, reactor);
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(ModBlocks.LUNAR_PANEL.get(), Component.translatable("jei.advancedpowah.lunar_panel"));
        registration.addIngredientInfo(ModBlocks.HYBRID_PANEL.get(), Component.translatable("jei.advancedpowah.hybrid_panel"));
    }

    @Override
    public Identifier getPluginUid() {
        return AdvancedPowah.id("main");
    }
}
