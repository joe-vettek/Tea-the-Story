package cloud.lemonslice.teastory.block.craft;


import cloud.lemonslice.teastory.blockentity.WoodenBarrelTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import cloud.lemonslice.teastory.tag.NormalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;

public class WoodenBarrelBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE;

    public WoodenBarrelBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }



    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            var te = worldIn.getBlockEntity(pos);
            if (FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(player.getItemInHand(handIn), 1)).isPresent()) {
                return te.getCapability(ForgeCapabilities.FLUID_HANDLER, hit.getDirection()).map(fluidTank ->
                {
                    FluidUtil.interactWithFluidHandler(player, handIn, fluidTank);
                    return InteractionResult.SUCCESS;
                }).orElse(InteractionResult.FAIL);
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        var te = worldIn.getBlockEntity(pos);
        if (te instanceof WoodenBarrelTileEntity) {
            int i = ((WoodenBarrelTileEntity) te).getFluidAmount();
            float f = pos.getY() + 0.0625F + 0.875F * i / 2000;
            if (!worldIn.isClientSide()) {
                if (entityIn.fireImmune()) {
                    if (((WoodenBarrelTileEntity) te).getFluid().is(FluidTags.WATER) && i > 250 && entityIn.getBlockY() <= f) {
                        entityIn.extinguishFire();
                    }
                } else if (entityIn instanceof ItemEntity && ((WoodenBarrelTileEntity) te).getFluid() == Fluids.WATER) {
                    ItemStack item = ((ItemEntity) entityIn).getItem();
                    if (item.is(NormalTags.Items.CROPS_RICE)) {
                        ((ItemEntity) entityIn).setItem(new ItemStack(ItemRegister.WASHED_RICE.get(), item.getCount()));
                    }
                }
            }
        }
    }

    static {
        VoxelShape outer = VoxelShapeHelper.createVoxelShape(1, 0, 1, 14, 16, 14);
        VoxelShape inner = VoxelShapeHelper.createVoxelShape(2, 1, 2, 12, 15, 12);
        SHAPE = Shapes.join(outer, inner, BooleanOp.NOT_SAME);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntityTypeRegistry.WOODEN_BARREL_TYPE.get().create(blockPos, blockState);
    }
}
