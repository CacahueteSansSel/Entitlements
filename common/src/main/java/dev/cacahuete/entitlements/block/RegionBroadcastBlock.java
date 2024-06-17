package dev.cacahuete.entitlements.block;

import com.mojang.serialization.MapCodec;
import dev.cacahuete.entitlements.block.entity.BlockEntityRegister;
import dev.cacahuete.entitlements.block.entity.RegionBroadcastBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RegionBroadcastBlock extends BaseEntityBlock implements DescriptionBlock {
    public RegionBroadcastBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RegionBroadcastBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityRegister.DISC_ENCODER.get(), (_level, pos, state, entity) -> entity.tick(_level, pos, state));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity entity = level.getBlockEntity(blockPos);
        if (!(entity instanceof RegionBroadcastBlockEntity bd)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        DataComponentMap components = itemStack.getComponents();

        if (itemStack.is(Items.NAME_TAG) && !components.isEmpty()) {
            String tagName = itemStack.getDisplayName().getString();
            tagName = tagName.substring(1, tagName.length() - 1); // Remove the [] characters

            bd.setTitle(tagName);
            player.displayClientMessage(Component.literal("Changed region name to " + tagName), true);

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        BlockEntity entity = level.getBlockEntity(blockPos);
        if (!(entity instanceof RegionBroadcastBlockEntity bd)) {
            return InteractionResult.PASS;
        }

        if (player.isCrouching()) {
            int newRadius = bd.getRadius() * 2;
            if (newRadius > 300) newRadius = 10;

            bd.setRadius(newRadius);
            player.displayClientMessage(Component.literal("Set region radius to " + newRadius + " blocks"), true);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public MutableComponent getDescription() {
        return Component.literal("Broadcasts a region name text to nearby entering players");
    }

    @Override
    public List<BlockInteraction> getInteractions() {
        BlockInteraction[] array = {
                new BlockInteraction("Right Click with Name Tag", "change region name"),
                new BlockInteraction("SHIFT + Right Click", "change action radius")
        };

        return Arrays.stream(array).toList();
    }
}
