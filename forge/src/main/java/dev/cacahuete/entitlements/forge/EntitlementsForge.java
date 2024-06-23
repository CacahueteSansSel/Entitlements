package dev.cacahuete.entitlements.forge;

import dev.cacahuete.entitlements.Entitlements;
import net.neoforged.fml.common.Mod;

@Mod(Entitlements.MOD_ID)
public class EntitlementsForge {
    public EntitlementsForge() {
		// Submit our event bus to let architectury register our content on the right time
        Entitlements.init();
    }
}