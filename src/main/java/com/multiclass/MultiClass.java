package com.multiclass;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.multiclass.item.ModItems;

public class MultiClass implements ModInitializer {
	public static final String MOD_ID = "multiclass";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

	}
}