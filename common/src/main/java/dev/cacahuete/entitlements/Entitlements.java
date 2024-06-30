package dev.cacahuete.entitlements;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.cacahuete.entitlements.block.BlockRegister;
import dev.cacahuete.entitlements.block.entity.BlockEntityRegister;
import dev.cacahuete.entitlements.item.ItemRegister;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import dev.cacahuete.entitlements.tab.CreativeTabRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class Entitlements
{
	public static final String MOD_ID = "entitlements";

	public static void init() {
		CreativeTabRegister.register();

		ItemRegister.register();
		BlockRegister.register();
		BlockRegister.registerBlockItemsInto(ItemRegister.ITEMS);

		BlockEntityRegister.register();

		EnvExecutor.runInEnv(Env.CLIENT, () -> Entitlements::initClient);
	}

	public static void initClient() {
		ClientGuiEvent.RENDER_HUD.register(RegionTitleOverlay::render);
	}
}
