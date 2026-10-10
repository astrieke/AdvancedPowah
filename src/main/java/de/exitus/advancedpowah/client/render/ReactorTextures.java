package de.exitus.advancedpowah.client.render;

import de.exitus.advancedpowah.AdvancedPowah;
import de.exitus.advancedpowah.AdvancedTier;
import net.minecraft.resources.Identifier;

final class ReactorTextures {
    private ReactorTextures() {
    }

    static Identifier block(AdvancedTier tier) {
        return AdvancedPowah.id("textures/model/tile/reactor_block_" + tier.id() + ".png");
    }

    static Identifier built(AdvancedTier tier) {
        return AdvancedPowah.id("textures/model/tile/reactor_" + tier.id() + ".png");
    }
}
