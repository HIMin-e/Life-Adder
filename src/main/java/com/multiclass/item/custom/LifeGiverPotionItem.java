package com.multiclass.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LifeGiverPotionItem extends Item {

    public LifeGiverPotionItem(Settings settings) {
        super(settings.maxCount(16)); // Stack size of 16
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        
        // Only execute on server side
        if (!world.isClient && user instanceof ServerPlayerEntity player) {
            try {
                // Create command source with server permissions (bypasses player permission checks)
                ServerCommandSource commandSource = player.getServer().getCommandSource()
                    .withSilent() // Don't send feedback to console
                    .withLevel(4) // Set permission level to 4 (operator level)
                    .withPosition(player.getPos()) // Set position context
                    .withWorld(player.getServerWorld()); // Set world context
                String command = "limitedlives add " + player.getName().getString() + " -1";
                
                // Execute the command with elevated permissions using the dispatcher
                player.getServer().getCommandManager().getDispatcher().execute(command, commandSource);
                
                // Consume the item (decrease stack size by 1)
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                
            } catch (Exception e) {
                // Log error if command execution fails
                System.err.println("Failed to execute limitedlives command: " + e.getMessage());
            }
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }
}