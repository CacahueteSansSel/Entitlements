package dev.cacahuete.entitlements.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.cacahuete.entitlements.item.DescriptionBlockItem;
import dev.cacahuete.entitlements.tab.CreativeTabRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static dev.cacahuete.entitlements.Entitlements.MOD_ID;

public class BlockRegister {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> REGION_BROADCAST
            = BLOCKS.register("region_broadcast", () ->
            new RegionBroadcastBlock(BlockBehaviour.Properties.of().destroyTime(0.1f).strength(0.8f)));

    public static void registerBlockItemsInto(DeferredRegister<Item> register) {
        for (RegistrySupplier<Block> block : BLOCKS) {
            register.register(block.getId(), () -> {
                if (block.get() instanceof DescriptionBlock) {
                    return new DescriptionBlockItem(block.get(),
                            new Item.Properties().arch$tab(CreativeTabRegister.MAIN));
                }

                return new BlockItem(block.get(),
                        new Item.Properties().arch$tab(CreativeTabRegister.MAIN));
            });
        }
    }

    public static void register() {
        BLOCKS.register();
    }
}
