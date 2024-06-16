package dev.cacahuete.entitlements.item;

import dev.cacahuete.entitlements.block.BlockInteraction;
import dev.cacahuete.entitlements.block.DescriptionBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptionBlockItem extends BlockItem {
    public DescriptionBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        DescriptionBlock descBlock = (DescriptionBlock)getBlock();
        list.add(descBlock.getDescription().withStyle(ChatFormatting.GRAY));

        list.add(Component.empty());

        list.add(Component.literal("When placed In World:").withStyle(ChatFormatting.GRAY));
        for (BlockInteraction interaction : descBlock.getInteractions()) {
            list.add(Component.literal("[" + interaction.getPlayerInputAction() + "]").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(" to " + interaction.getIntendedResult()).withStyle(ChatFormatting.GRAY)));
        }
    }
}
