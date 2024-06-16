package dev.cacahuete.entitlements.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.cacahuete.entitlements.tab.CreativeTabRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import static dev.cacahuete.entitlements.Entitlements.MOD_ID;

public class ItemRegister {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> ICON
            = ITEMS.register("icon", () -> new Item(new Item.Properties()));

    public static void register() {
        ITEMS.register();
    }
}
