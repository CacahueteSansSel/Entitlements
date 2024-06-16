package dev.cacahuete.entitlements.fabric;

import dev.cacahuete.entitlements.Entitlements;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EntitlementsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Entitlements.init();
    }
}