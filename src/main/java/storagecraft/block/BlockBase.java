package storagecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import storagecraft.StorageCraft;
import storagecraft.tile.TileBase;
import storagecraft.util.InventoryUtils;

public abstract class BlockBase extends Block {
	private String name;

	public BlockBase(String name) {
		super(Material.rock);

		this.name = name;

		setCreativeTab(StorageCraft.TAB);
		setBlockTextureName("storagecraft:" + name);
	}

	@Override
	public String getUnlocalizedName() {
		return "block." + StorageCraft.ID + ":" + name;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);

		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileBase) {
			((TileBase) tile).setDirection(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entityLiving)));
		}
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof IInventory) {
			InventoryUtils.dropInventory(world, (IInventory) tile, x, y, z);
		}

		super.onBlockPreDestroy(world, x, y, z, meta);
	}
}
