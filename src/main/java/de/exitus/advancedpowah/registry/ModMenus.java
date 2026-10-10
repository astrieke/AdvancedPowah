package de.exitus.advancedpowah.registry;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.machine.MachineMenu;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AdvancedPowah.MOD_ID);

    public static final Supplier<MenuType<MachineMenu>> MACHINE = MENUS.register("machine",
            () -> IMenuTypeExtension.create(MachineMenu::create));

    private ModMenus() {
    }
}
