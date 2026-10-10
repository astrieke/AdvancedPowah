package de.exitus.advancedpowah.client;

import de.exitus.advancedpowah.machine.MachineBlockEntity;
import de.exitus.advancedpowah.machine.MachineMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import owmii.powah.client.screen.Textures;
import owmii.powah.lib.client.screen.container.PowahBaseEnergyScreen;
import owmii.powah.lib.logistics.energy.Energy;
import owmii.powah.util.Util;

public class MachineScreen extends PowahBaseEnergyScreen<MachineBlockEntity, MachineMenu> {
    public MachineScreen(MachineMenu container, Inventory inv, Component title) {
        super(container, inv, title, Textures.WIDE_ENERGY);
    }

    @Override
    protected void drawBackground(GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(guiGraphics, partialTicks, mouseX, mouseY);
        Textures.WIDE_ENERGY_GAUGE.drawScalableW(guiGraphics, this.te.getEnergy().subSized(), this.leftPos + 6, this.topPos + 6);
    }

    @Override
    protected void drawForeground(GuiGraphicsExtractor gui, int mouseX, int mouseY) {
        super.drawForeground(gui, mouseX, mouseY);
        int color = ARGB.black(0.4f);
        Energy energy = this.te.getEnergy();
        String stored = Util.addCommas(energy.getStored()) + "/" + Util.numFormat(energy.getCapacity()) + " FE";
        gui.text(this.font, stored, 12, 13, color, false);
        gui.text(this.font, statusLine(), 12, 27, color, false);
    }

    private String statusLine() {
        if (!this.te.isActive()) {
            return Component.translatable("info.advancedpowah.idle").getString();
        }
        return Util.numFormat(this.te.getCurrentRate()) + " FE/t";
    }
}
