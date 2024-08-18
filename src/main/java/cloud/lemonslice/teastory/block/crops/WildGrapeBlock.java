package cloud.lemonslice.teastory.block.crops;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import xueluoanping.teastory.BlockRegister;

import java.util.List;

public class WildGrapeBlock extends BushBlock
{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public WildGrapeBlock(BlockBehaviour.Properties pProperties)
    {
        super(pProperties);

    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }



    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder)
    {
        List<ItemStack> list = Lists.newArrayList();
        list.add(new ItemStack(BlockRegister.GRAPES.get(), builder.getLevel().getRandom().nextInt(4) + 1));
        return list;
    }
}
