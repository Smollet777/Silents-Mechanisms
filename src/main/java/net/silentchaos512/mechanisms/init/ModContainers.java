package net.silentchaos512.mechanisms.init;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.mechanisms.SilentMechanisms;
import net.silentchaos512.mechanisms.block.batterybox.BatteryBoxContainer;
import net.silentchaos512.mechanisms.block.batterybox.BatteryBoxScreen;
import net.silentchaos512.mechanisms.block.compressor.CompressorContainer;
import net.silentchaos512.mechanisms.block.compressor.CompressorScreen;
import net.silentchaos512.mechanisms.block.crusher.CrusherContainer;
import net.silentchaos512.mechanisms.block.crusher.CrusherScreen;
import net.silentchaos512.mechanisms.block.electricfurnace.ElectricFurnaceContainer;
import net.silentchaos512.mechanisms.block.electricfurnace.ElectricFurnaceScreen;
import net.silentchaos512.mechanisms.block.generator.CoalGeneratorContainer;
import net.silentchaos512.mechanisms.block.generator.CoalGeneratorScreen;

public class ModContainers {
    public static ContainerType<BatteryBoxContainer> batteryBox;
    public static ContainerType<CoalGeneratorContainer> coalGenerator;
    public static ContainerType<CompressorContainer> compressor;
    public static ContainerType<CrusherContainer> crusher;
    public static ContainerType<ElectricFurnaceContainer> electricFurnace;

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
        batteryBox = register("battery_box", BatteryBoxContainer::new);
        coalGenerator = register("coal_generator", CoalGeneratorContainer::new);
        compressor = register("compressor", CompressorContainer::new);
        crusher = register("crusher", CrusherContainer::new);
        electricFurnace = register("electric_furnace", ElectricFurnaceContainer::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(batteryBox, BatteryBoxScreen::new);
        ScreenManager.registerFactory(coalGenerator, CoalGeneratorScreen::new);
        ScreenManager.registerFactory(compressor, CompressorScreen::new);
        ScreenManager.registerFactory(crusher, CrusherScreen::new);
        ScreenManager.registerFactory(electricFurnace, ElectricFurnaceScreen::new);
    }

    private static <C extends Container> ContainerType<C> register(String name, ContainerType.IFactory<C> containerFactory) {
        ContainerType<C> type = new ContainerType<>(containerFactory);
        type.setRegistryName(SilentMechanisms.getId(name));
        ForgeRegistries.CONTAINERS.register(type);
        return type;
    }
}