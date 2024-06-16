package dev.cacahuete.entitlements.tab;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.cacahuete.entitlements.item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static dev.cacahuete.entitlements.Entitlements.MOD_ID;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MAIN = TABS.register(
            "entitlements_main_tab", // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.literal("Entitlements"), // Tab Name
                    () -> new ItemStack(ItemRegister.ICON.get()) // Icon
            )
    );

    public static void register() {
        TABS.register();
    }
}
