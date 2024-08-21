package xueluoanping.teastory.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

public class VineEntity extends SyncedBlockEntity {
    public static final ModelProperty<Integer> AGE_PROPERTY = new ModelProperty<>();

    public static int MAX_AGE = 3;
    public static int MAX_DISTANCE = 7;
    private int age = 0;
    private int distance = 0;

    public VineEntity( BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.VINE_TYPE.get(), pos, state);
    }
    public VineEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.age = tag.getInt("age");
        this.distance = tag.getInt("distance");
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder().with(AGE_PROPERTY, getAge()).build();
        // return super.getModelData();
    }

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("age", getAge());
        tag.putInt("distance", getDistance());
        super.saveAdditional(tag);
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }

    public static int getMaxDistance() {
        return MAX_DISTANCE;
    }

    public int getAge() {
        return age;
    }


    public int getDistance() {
        return distance;
    }


    public void setAge(int age) {
        this.age = age;
        inventoryChanged();
    }

    public void setDistance(int distance) {
        this.distance = distance;
        inventoryChanged();
    }

    public void setAgeAndDistance(int age, int distance) {
        this.distance = distance;
        this.age = age;
        inventoryChanged();
    }
}
