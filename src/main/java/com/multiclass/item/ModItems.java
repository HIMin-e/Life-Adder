package com.multiclass.item;

import com.multiclass.MultiClass;
import com.multiclass.item.custom.LifeGiverPotionItem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SOUL_PIECE = registerItem("soul_piece", new Item(new Item.Settings()));
    public static final Item GLOWING_SHELL = registerItem("glowing_shell", new Item(new Item.Settings()));
    public static final Item ANCIENT_INGOT = registerItem("ancient_ingot", new Item(new Item.Settings()));
    public static final Item LIFE_GIVER = registerItem("life_giver", new LifeGiverPotionItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MultiClass.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MultiClass.LOGGER.info("Registering Mod Items for " + MultiClass.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(enteries -> {
            enteries.add(SOUL_PIECE);
            enteries.add(GLOWING_SHELL);
            enteries.add(ANCIENT_INGOT);
            enteries.add(LIFE_GIVER);
        });
    }
}