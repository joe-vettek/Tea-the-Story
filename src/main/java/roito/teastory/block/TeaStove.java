package roito.teastory.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import roito.teastory.TeaStory;
import roito.teastory.inventory.GuiElementLoader;
import roito.teastory.tileentity.TileEntityTeaStove;

public class TeaStove extends BlockContainer implements ITileEntityProvider
{
	private final boolean isBurning;
	private static boolean keepInventory;

	public TeaStove(float lightLevel, String name, boolean isBurning)
	{
		super(Material.ROCK);
		this.setHardness(3.5F);
		this.setSoundType(SoundType.STONE);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(TeaStory.MODID, name));
		this.setLightLevel(lightLevel);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.isBurning = isBurning;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTeaStove();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public ArrayList getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune)
	{
		ArrayList drops = new ArrayList();
		drops.add(new ItemStack(BlockLoader.tea_stove, 1));
		return drops;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			int id = GuiElementLoader.GUI_TEASTOVE;
			playerIn.openGui(TeaStory.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (this.isBurning)
		{
			EnumFacing enumfacing = stateIn.getValue(FACING);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			switch (enumfacing)
			{
			case WEST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case EAST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case NORTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case SOUTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			}
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + 1.1D, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1 + 0.8D, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int facing = state.getValue(FACING).getHorizontalIndex();
		return facing;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
		items.add(new ItemStack(BlockLoader.tea_stove));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
		return new ItemStack(BlockLoader.tea_stove);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
		IBlockState origin = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!keepInventory)
		{
			TileEntityTeaStove te = (TileEntityTeaStove) worldIn.getTileEntity(pos);
			IItemHandler inventory0 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			IItemHandler inventory1 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
			IItemHandler inventory3 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

			for (int i = inventory0.getSlots() - 1; i >= 0; --i)
			{
				if (inventory0.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, inventory0.getStackInSlot(i));
					((IItemHandlerModifiable) inventory0).setStackInSlot(i, ItemStack.EMPTY);
				}
			}

			for (int i = inventory1.getSlots() - 1; i >= 0; --i)
			{
				if (inventory1.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, inventory1.getStackInSlot(i));
					((IItemHandlerModifiable) inventory1).setStackInSlot(i, ItemStack.EMPTY);
				}
			}

			for (int i = inventory3.getSlots() - 1; i >= 0; --i)
			{
				if (inventory3.getStackInSlot(i) != ItemStack.EMPTY)
				{
					Block.spawnAsEntity(worldIn, pos, inventory3.getStackInSlot(i));
					((IItemHandlerModifiable) inventory3).setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	public static void setState(boolean active, World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		keepInventory = true;

		if (active)
		{
			worldIn.setBlockState(pos, BlockLoader.lit_tea_stove.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)));
		}
		else
		{
			worldIn.setBlockState(pos, BlockLoader.tea_stove.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)));
		}

		keepInventory = false;

		if (tileentity != null)
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
}
