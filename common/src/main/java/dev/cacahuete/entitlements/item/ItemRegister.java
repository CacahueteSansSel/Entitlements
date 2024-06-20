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

    public static final RegistrySupplier<Item> COPPER_WRENCH
            = ITEMS.register("copper_wrench", () -> new CopperWrenchItem(new Item.Properties().stacksTo(1).arch$tab(CreativeTabRegister.MAIN), CopperWrenchItem.Mode.Default));

    public static final RegistrySupplier<Item> COPPER_WRENCH_RADIUS
            = ITEMS.register("copper_wrench_radius", () -> new CopperWrenchItem(new Item.Properties().stacksTo(1).arch$tab(CreativeTabRegister.MAIN), CopperWrenchItem.Mode.Radius));

    public static final RegistrySupplier<Item> COPPER_WRENCH_TIME
            = ITEMS.register("copper_wrench_time", () -> new CopperWrenchItem(new Item.Properties().stacksTo(1).arch$tab(CreativeTabRegister.MAIN), CopperWrenchItem.Mode.Time));

    public static void register() {
        ITEMS.register();
    }
}
