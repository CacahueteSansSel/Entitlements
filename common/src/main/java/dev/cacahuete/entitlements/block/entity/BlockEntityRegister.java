package dev.cacahuete.entitlements.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.cacahuete.entitlements.block.BlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static dev.cacahuete.entitlements.Entitlements.MOD_ID;


public class BlockEntityRegister {
    public static DeferredRegister<BlockEntityType<?>> ENTITIES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<RegionBroadcastBlockEntity>> DISC_ENCODER
            = ENTITIES.register("disc_encoder", () -> BlockEntityType.Builder.of(RegionBroadcastBlockEntity::new,
            BlockRegister.REGION_BROADCAST.get()).build(null));

    public static void register() {
        ENTITIES.register();
    }
}
