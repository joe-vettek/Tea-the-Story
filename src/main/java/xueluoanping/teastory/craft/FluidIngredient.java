package xueluoanping.teastory.craft;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
// import com.simibubi.create.Create;
// import com.simibubi.create.foundation.fluid.FluidHelper;
// import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FluidIngredient implements Predicate<FluidStack> {
    // Strange Empty but we need to found why
    public static final FluidIngredient EMPTY = FluidIngredient.fromFluidStack(FluidStack.EMPTY);
    public List<FluidStack> matchingFluidStacks;
    protected int amountRequired;
    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    public FluidIngredient() {
    }

    public static FluidIngredient fromTag(TagKey<Fluid> tag, int amount) {
        FluidIngredient.FluidTagIngredient ingredient = new FluidIngredient.FluidTagIngredient();
        ingredient.tag = tag;
        ingredient.amountRequired = amount;
        return ingredient;
    }

    public static FluidIngredient fromFluid(Fluid fluid, int amount) {
        FluidIngredient.FluidStackIngredient ingredient = new FluidIngredient.FluidStackIngredient();
        ingredient.fluid = fluid;
        ingredient.amountRequired = amount;
        ingredient.fixFlowing();
        return ingredient;
    }

    public static FluidIngredient fromFluidStack(FluidStack fluidStack) {
        FluidIngredient.FluidStackIngredient ingredient = new FluidIngredient.FluidStackIngredient();
        ingredient.fluid = fluidStack.getFluid();
        ingredient.amountRequired = fluidStack.getAmount();
        ingredient.fixFlowing();
        if (fluidStack.hasTag()) {
            ingredient.tagToMatch = fluidStack.getTag();
        }

        return ingredient;
    }

    protected abstract boolean testInternal(FluidStack var1);

    protected abstract void readInternal(FriendlyByteBuf var1);

    protected abstract void writeInternal(FriendlyByteBuf var1);

    protected abstract void readInternal(JsonObject var1);

    protected abstract void writeInternal(JsonObject var1);

    protected abstract List<FluidStack> determineMatchingFluidStacks();

    public int getRequiredAmount() {
        return this.amountRequired;
    }

    public List<FluidStack> getMatchingFluidStacks() {
        return this.matchingFluidStacks != null ? this.matchingFluidStacks : (this.matchingFluidStacks = this.determineMatchingFluidStacks());
    }

    public boolean test(FluidStack t) {
        if (t == null) {
            throw new IllegalArgumentException("FluidStack cannot be null");
        } else {
            return this.testInternal(t);
        }
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this instanceof FluidTagIngredient);
        buffer.writeVarInt(this.amountRequired);
        this.writeInternal(buffer);
    }

    public static FluidIngredient read(FriendlyByteBuf buffer) {
        boolean isTagIngredient = buffer.readBoolean();
        FluidIngredient ingredient = isTagIngredient ? new FluidIngredient.FluidTagIngredient() : new FluidIngredient.FluidStackIngredient();
        ((FluidIngredient)ingredient).amountRequired = buffer.readVarInt();
        ((FluidIngredient)ingredient).readInternal(buffer);
        return (FluidIngredient)ingredient;
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        this.writeInternal(json);
        json.addProperty("amount", this.amountRequired);
        return json;
    }

    public static boolean isFluidIngredient(@Nullable JsonElement je) {
        if (je != null && !je.isJsonNull()) {
            if (!je.isJsonObject()) {
                return false;
            } else {
                JsonObject json = je.getAsJsonObject();
                if (json.has("fluidTag")) {
                    return true;
                } else {
                    return json.has("fluid");
                }
            }
        } else {
            return false;
        }
    }

    public static FluidIngredient deserialize(@Nullable JsonElement je) {
        if (!isFluidIngredient(je)) {
            throw new JsonSyntaxException("Invalid fluid ingredient: " + Objects.toString(je));
        } else {
            JsonObject json = je.getAsJsonObject();
            FluidIngredient ingredient = json.has("fluidTag") ? new FluidIngredient.FluidTagIngredient() : new FluidIngredient.FluidStackIngredient();
            ((FluidIngredient)ingredient).readInternal(json);
            if (!json.has("amount")) {
                throw new JsonSyntaxException("Fluid ingredient has to define an amount");
            } else {
                ((FluidIngredient)ingredient).amountRequired = GsonHelper.getAsInt(json, "amount");
                return (FluidIngredient)ingredient;
            }
        }
    }

    public static class FluidTagIngredient extends FluidIngredient {
        protected TagKey<Fluid> tag;

        public FluidTagIngredient() {
        }

        protected boolean testInternal(FluidStack t) {
            if (this.tag == null) {
                Iterator var2 = this.getMatchingFluidStacks().iterator();

                FluidStack accepted;
                do {
                    if (!var2.hasNext()) {
                        return false;
                    }

                    accepted = (FluidStack)var2.next();
                } while(!accepted.getFluid().isSame(t.getFluid()));

                return true;
            } else {
                return t.getFluid().is(this.tag);
            }
        }

        protected void readInternal(FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            this.matchingFluidStacks = new ArrayList(size);

            for(int i = 0; i < size; ++i) {
                this.matchingFluidStacks.add(buffer.readFluidStack());
            }

        }

        protected void writeInternal(FriendlyByteBuf buffer) {
            List<FluidStack> matchingFluidStacks = this.getMatchingFluidStacks();
            buffer.writeVarInt(matchingFluidStacks.size());
            Stream<FluidStack> var10000 = matchingFluidStacks.stream();
            Objects.requireNonNull(buffer);
            var10000.forEach(buffer::writeFluidStack);
        }

        protected void readInternal(JsonObject json) {
            ResourceLocation name = new ResourceLocation(GsonHelper.getAsString(json, "fluidTag"));
            this.tag = FluidTags.create(name);
        }

        protected void writeInternal(JsonObject json) {
            json.addProperty("fluidTag", this.tag.location().toString());
        }

        protected List<FluidStack> determineMatchingFluidStacks() {
            return (List)ForgeRegistries.FLUIDS.tags().getTag(this.tag).stream().map((f) -> {
                return f instanceof FlowingFluid ? ((FlowingFluid)f).getSource() : f;
            }).distinct().map((f) -> {
                return new FluidStack(f, this.amountRequired);
            }).collect(Collectors.toList());
        }
    }

    public static class FluidStackIngredient extends FluidIngredient {
        protected Fluid fluid;
        protected CompoundTag tagToMatch = new CompoundTag();

        public FluidStackIngredient() {
        }

        void fixFlowing() {
            if (this.fluid instanceof FlowingFluid) {
                this.fluid = ((FlowingFluid)this.fluid).getSource();
            }

        }

        protected boolean testInternal(FluidStack t) {
            if (!t.getFluid().isSame(this.fluid)) {
                return false;
            } else if (this.tagToMatch.isEmpty()) {
                return true;
            } else {
                CompoundTag tag = t.getOrCreateTag();
                return tag.copy().merge(this.tagToMatch).equals(tag);
            }
        }

        protected void readInternal(FriendlyByteBuf buffer) {
            this.fluid = (Fluid)buffer.readRegistryId();
            this.tagToMatch = buffer.readNbt();
        }

        protected void writeInternal(FriendlyByteBuf buffer) {
            buffer.writeRegistryId(ForgeRegistries.FLUIDS, this.fluid);
            buffer.writeNbt(this.tagToMatch);
        }

        protected void readInternal(JsonObject json) {
            FluidStack stack = deserializeFluidStack(json);
            this.fluid = stack.getFluid();
            this.tagToMatch = stack.getOrCreateTag();
        }

        protected void writeInternal(JsonObject json) {
            json.addProperty("fluid", BuiltInRegistries.FLUID.getKey(this.fluid).toString());
            if(tagToMatch!=null&&!tagToMatch.isEmpty())
            json.add("nbt", JsonParser.parseString(this.tagToMatch.toString()));
        }

        protected List<FluidStack> determineMatchingFluidStacks() {
            return ImmutableList.of(this.tagToMatch.isEmpty() ? new FluidStack(this.fluid, this.amountRequired) : new FluidStack(this.fluid, this.amountRequired, this.tagToMatch));
        }
    }

    public static FluidStack deserializeFluidStack(JsonObject json) {
        ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
        Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(id);
        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid '" + id + "'");
        } else {
            int amount = GsonHelper.getAsInt(json, "amount");
            FluidStack stack = new FluidStack(fluid, amount);
            if (!json.has("nbt")) {
                return stack;
            } else {
                try {
                    JsonElement element = json.get("nbt");
                    stack.setTag(TagParser.parseTag(element.isJsonObject() ? GSON.toJson(element) : GsonHelper.convertToString(element, "nbt")));
                } catch (CommandSyntaxException var6) {
                    CommandSyntaxException e = var6;
                    e.printStackTrace();
                }

                return stack;
            }
        }
    }

}
