# LifeGiverPotionItem Implementation Plan

## Problem Analysis
The original issue was that the `getMaxUseTime(ItemStack)` method must override or implement a supertype method, but the file is currently empty. We need to create a complete implementation from scratch.

## Technical Requirements
- **Minecraft Version**: 1.21.1
- **Fabric API Version**: 0.116.4+1.21.1
- **Java Version**: 21
- **Functionality**: Consumable item that executes `/limitedlives add <drinking_player> -1` command

## Implementation Strategy

### 1. Class Structure
```java
public class LifeGiverPotionItem extends Item {
    // Constructor with proper settings
    // Override necessary methods for consumable behavior
}
```

### 2. Key Methods to Implement

#### `getMaxUseTime(ItemStack stack)`
- **Purpose**: Defines how long the item takes to consume
- **Return Value**: Time in ticks (20 ticks = 1 second)
- **Typical Values**: 32 ticks for potions, 16 ticks for food
- **Implementation**: `return 32;` (matches original code snippet)

#### `getUseAction(ItemStack stack)`
- **Purpose**: Defines the animation/action type when using the item
- **Return Value**: `UseAction.DRINK` for potion-like items
- **Implementation**: `return UseAction.DRINK;`

#### `finishUsing(ItemStack stack, World world, LivingEntity user)`
- **Purpose**: Called when the item consumption is completed
- **Functionality**: Execute the command and handle item consumption
- **Implementation**: 
  - Check if user is a player
  - Execute `/limitedlives add <player> -1` command
  - Consume the item (decrease stack size)
  - Return the modified stack

### 3. Command Execution Strategy
```java
if (user instanceof ServerPlayerEntity player) {
    ServerCommandSource commandSource = player.getCommandSource();
    String command = "limitedlives add " + player.getName().getString() + " -1";
    player.getServer().getCommandManager().executeWithPrefix(commandSource, command);
}
```

### 4. Required Imports
```java
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
```

### 5. Item Settings Configuration
```java
public LifeGiverPotionItem(Settings settings) {
    super(settings.maxCount(16)); // Stack size of 16
}
```

### 6. ModItems.java Update
Replace:
```java
public static final Item LIFE_GIVER = registerItem("life_giver", new Item(new Item.Settings()));
```

With:
```java
public static final Item LIFE_GIVER = registerItem("life_giver", 
    new LifeGiverPotionItem(new Item.Settings()));
```

## Error Handling Considerations
1. **Null Checks**: Ensure player and server are not null
2. **Command Validation**: Handle cases where the command might fail
3. **Server-Side Only**: Ensure command execution only happens on server side
4. **Stack Management**: Properly handle item consumption

## Testing Strategy
1. Compile the code to ensure no syntax errors
2. Test item registration in game
3. Test consumption animation and timing
4. Verify command execution with actual `/limitedlives` mod
5. Test edge cases (creative mode, invalid players, etc.)

## Files to Modify
1. `src/main/java/com/himine/item/custom/LifeGiverPotionItem.java` - Create new class
2. `src/main/java/com/himine/item/ModItems.java` - Update item registration

## Expected Outcome
- A fully functional consumable item that takes 32 ticks to consume
- Proper drinking animation during consumption
- Execution of `/limitedlives add <player> -1` command upon completion
- Proper item stack management (item is consumed after use)
- No compilation errors