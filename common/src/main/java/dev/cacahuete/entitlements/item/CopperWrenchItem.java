package dev.cacahuete.entitlements.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CopperWrenchItem extends Item {
    Mode mode;

    public CopperWrenchItem(Properties properties, Mode mode) {
        super(properties);

        this.mode = mode;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable("item.entitlements.copper_wrench");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack newItem = null;
        Component modeComponent = null;

        switch (mode) {
            case Default:
                newItem = new ItemStack(ItemRegister.COPPER_WRENCH_RADIUS.get());
                modeComponent = Component.translatable("item.entitlements.copper_wrench.mode.radius");
                break;
            case Radius:
                newItem = new ItemStack(ItemRegister.COPPER_WRENCH_TIME.get());
                modeComponent = Component.translatable("item.entitlements.copper_wrench.mode.time");
                break;
            case Time:
                newItem = new ItemStack(ItemRegister.COPPER_WRENCH.get());
                modeComponent = Component.translatable("item.entitlements.copper_wrench.mode.default");
                break;
        }

        player.displayClientMessage(Component.translatable("item.entitlements.copper_wrench.set_mode",
                modeComponent.getString()), true);

        return InteractionResultHolder.success(newItem);
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        Default,
        Radius,
        Time
    }
}
