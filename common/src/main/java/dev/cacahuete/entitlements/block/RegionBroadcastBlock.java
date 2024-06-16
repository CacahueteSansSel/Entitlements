package dev.cacahuete.entitlements.block;

import dev.cacahuete.entitlements.block.entity.BlockEntityRegister;
import dev.cacahuete.entitlements.block.entity.RegionBroadcastBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RegionBroadcastBlock extends BaseEntityBlock implements DescriptionBlock {
    public RegionBroadcastBlock(Properties properties) {
        super(properties);
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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity entity = level.getBlockEntity(blockPos);
        if (!(entity instanceof RegionBroadcastBlockEntity bd)) {
            return InteractionResult.PASS;
        }

        if (player.isCrouching()) {
            int newRadius = bd.getRadius() * 2;
            if (newRadius > 256) newRadius = 10;

            bd.setRadius(newRadius);
            player.displayClientMessage(Component.literal("Set region radius to " + newRadius + " blocks"), true);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        ItemStack item = player.getItemInHand(interactionHand);

        if (item.is(Items.NAME_TAG) && item.hasTag()) {
            String tagName = item.getDisplayName().getString();
            tagName = tagName.substring(1, tagName.length() - 1); // Remove the [] characters

            bd.setTitle(tagName);
            player.displayClientMessage(Component.literal("Changed region name to " + tagName), true);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
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
