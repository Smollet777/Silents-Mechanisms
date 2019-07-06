package net.silentchaos512.mechanisms.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.mechanisms.SilentMechanisms;
import net.silentchaos512.mechanisms.item.BatteryItem;
import net.silentchaos512.mechanisms.item.CraftingItems;
import net.silentchaos512.mechanisms.item.DebugItem;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {
    public static BatteryItem battery;

    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

        battery = register("battery", new BatteryItem());

        Arrays.stream(CraftingItems.values()).forEach(c -> register(c.getName(), c.asItem()));

        register("debug_item", new DebugItem());
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = SilentMechanisms.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
