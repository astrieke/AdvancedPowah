package de.exitus.advancedpowah.machine;

import de.exitus.advancedpowah.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import owmii.powah.lib.logistics.inventory.BaseEnergyMenu;

public class MachineMenu extends BaseEnergyMenu<MachineBlockEntity> {
    public MachineMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        super(ModMenus.MACHINE.get(), id, inventory, buffer);
    }

    public MachineMenu(int id, Inventory inventory, MachineBlockEntity te) {
        super(ModMenus.MACHINE.get(), id, inventory, te);
    }

    public static MachineMenu create(int id, Inventory inventory, FriendlyByteBuf buffer) {
        return new MachineMenu(id, inventory, buffer);
    }

    @Override
    protected void init(Inventory inventory, MachineBlockEntity te) {
        super.init(inventory);
        addPlayerInventory(inventory, 8, 59, 4);
    }
}
