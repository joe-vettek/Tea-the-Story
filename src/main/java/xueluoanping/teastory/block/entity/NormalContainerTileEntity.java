package xueluoanping.teastory.block.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;



public abstract class NormalContainerTileEntity extends SyncedBlockEntity implements MenuProvider
{
    private boolean prepareForRemove = false;

    public NormalContainerTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }


    @Override
    public Component getDisplayName()
    {
        return Component.translatable("container.teastory." + BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(getType()).getPath());
    }

}
