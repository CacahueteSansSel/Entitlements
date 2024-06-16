package dev.cacahuete.entitlements;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.cacahuete.entitlements.block.BlockRegister;
import dev.cacahuete.entitlements.block.entity.BlockEntityRegister;
import dev.cacahuete.entitlements.item.ItemRegister;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import dev.cacahuete.entitlements.tab.CreativeTabRegister;

public class Entitlements
{
	public static final String MOD_ID = "entitlements";

	public static void init() {
		ClientGuiEvent.RENDER_HUD.register(RegionTitleOverlay::render);

		CreativeTabRegister.register();

		ItemRegister.register();
		BlockRegister.register();
		BlockRegister.registerBlockItemsInto(ItemRegister.ITEMS);

		BlockEntityRegister.register();
	}
}
