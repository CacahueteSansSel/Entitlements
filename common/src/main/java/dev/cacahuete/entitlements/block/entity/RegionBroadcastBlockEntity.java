package dev.cacahuete.entitlements.block.entity;

import com.sun.jna.platform.win32.WinBase;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.BossEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RegionBroadcastBlockEntity extends BlockEntity {
    static BlockPos lastBlockPos;

    String title = "Untitled Area";
    int radius = 10;

    public RegionBroadcastBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegister.DISC_ENCODER.get(), blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);

        title = compoundTag.getString("Title");
        radius = compoundTag.getInt("Radius");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);

        compoundTag.putString("Title", title);
        compoundTag.putInt("Radius", radius);
    }

    public int getRadius() {
        return radius;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        markUpdated();
    }

    public void setRadius(int radius) {
        this.radius = radius;

        markUpdated();
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide() || title == null) return;

        BlockPos playerPos = Minecraft.getInstance().player.blockPosition();
        int blockHorizontalDistance = playerPos.distManhattan(pos.atY(playerPos.getY()));

        if (lastBlockPos != worldPosition && blockHorizontalDistance <= radius) {
            lastBlockPos = worldPosition;

            RegionTitleOverlay.show(title);
        }
    }
}
