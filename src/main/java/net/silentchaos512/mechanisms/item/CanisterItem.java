package net.silentchaos512.mechanisms.item;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.mechanisms.SilentMechanisms;
import net.silentchaos512.mechanisms.api.IFluidContainer;
import net.silentchaos512.mechanisms.util.TextUtil;

import javax.annotation.Nullable;
import java.util.Objects;

public class CanisterItem extends Item implements IFluidContainer {
    public CanisterItem() {
        super(new Properties().group(SilentMechanisms.ITEM_GROUP).maxStackSize(64));
        addPropertyOverride(SilentMechanisms.getId("fluid_level"), (stack, world, entity) -> getFluid(stack).getAmount());
    }

    public ItemStack getStack(@Nullable Fluid fluid) {
        ItemStack result = new ItemStack(this);
        if (fluid != null) {
            ResourceLocation fluidId = Objects.requireNonNull(fluid.getRegistryName());
            result.getOrCreateTag().putString("CanisterFluid", fluidId.toString());
        }
        return result;
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        if (!(stack.getItem() instanceof CanisterItem)) {
            return FluidStack.EMPTY;
        }
        ResourceLocation fluidId = ResourceLocation.tryCreate(stack.getOrCreateTag().getString("CanisterFluid"));
        if (fluidId == null) {
            return FluidStack.EMPTY;
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid == null) {
            return FluidStack.EMPTY;
        }
        return new FluidStack(fluid, 1000);
    }

    @Override
    public ItemStack fillWithFluid(ItemStack empty, FluidStack fluid) {
        return getStack(fluid.getFluid());
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        FluidStack fluid = getFluid(stack);
        ITextComponent fluidText = fluid.isEmpty() ? TextUtil.translate("misc", "empty") : fluid.getDisplayName();
        return new TranslationTextComponent(this.getTranslationKey(), fluidText);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(itemStack.getItem());
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            items.add(getStack(null));
            ForgeRegistries.FLUIDS.getValues().stream()
                    .filter(f -> f.isSource(f.getDefaultState()))
                    .map(this::getStack)
                    .forEach(items::add);
        }
    }
}
