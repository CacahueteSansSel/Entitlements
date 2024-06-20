package dev.cacahuete.entitlements.block.entity;

import com.sun.jna.platform.win32.WinBase;
import dev.cacahuete.entitlements.overlay.RegionTitleOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.BossEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RegionBroadcastBlockEntity extends BlockEntity {
    static BlockPos lastBlockPos;

    String title = "Untitled Area";
    int radius = 10;
    float displayTime = 5f;
    ItemStack displayItemStack;

    public RegionBroadcastBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegister.DISC_ENCODER.get(), blockPos, blockState);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);

        title = compoundTag.contains("Title") ? compoundTag.getString("Title") : "Untitled Area";
        radius = compoundTag.contains("Radius") ? compoundTag.getInt("Radius") : 10;
        displayTime = compoundTag.contains("DisplayTime") ? compoundTag.getFloat("DisplayTime") : 5f;
        if (compoundTag.contains("DisplayItem"))
            displayItemStack = ItemStack.of(compoundTag.getCompound("DisplayItem"));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putString("Title", title);
        compoundTag.putInt("Radius", radius);
        compoundTag.putFloat("DisplayTime", displayTime);

        if (displayItemStack != null) {
            CompoundTag itemTag = new CompoundTag();
            displayItemStack.save(itemTag);
            compoundTag.put("DisplayItem", itemTag);
        }
    }

    public int getRadius() {
        return radius;
    }

    public String getTitle() {
        return title;
    }

    public float getDisplayTime() {
        return displayTime;
    }

    public ItemStack getDisplayItemStack() {
        return displayItemStack;
    }

    public void setTitle(String title) {
        this.title = title;

        markUpdated();
    }

    public void setRadius(int radius) {
        this.radius = radius;

        markUpdated();
    }

    public void setDisplayTime(float displayTime) {
        this.displayTime = displayTime;
    }

    public void setDisplayItemStack(ItemStack displayItemStack) {
        this.displayItemStack = displayItemStack;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
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

            RegionTitleOverlay.show(title, displayTime);
        }

        if (lastBlockPos == worldPosition && blockHorizontalDistance > radius) {
            lastBlockPos = null;
        }
    }
}
