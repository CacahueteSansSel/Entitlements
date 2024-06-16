package dev.cacahuete.entitlements.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.cacahuete.entitlements.Entitlements;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Entitlements.MOD_ID)
public class EntitlementsForge {
    public EntitlementsForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Entitlements.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Entitlements.init();
    }
}