package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NmsItemStackTooltip117R1 extends NmsItemStackTooltip
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltip117R1 i = new NmsItemStackTooltip117R1();
	public static NmsItemStackTooltip117R1 get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// org.bukkit.craftbukkit.inventory.CraftItemStack
	private Class<?> classCraftItemStack;
	
	// net.minecraft.world.item.ItemStack
	private Class<?> classNmsItemStack;
	
	// net.minecraft.serverNBTTagCompound
	private Class<?> classNmsNbtTagCompound;
	
	// org.bukkit.craftbukkit.inventory.CraftItemStack#asNmsCopy(net.minecraft.server.ItemStack)
	private Method methodCraftItemStackAsNmsCopy;
	
	// net.minecraft.server.ItemStack#save(net.minecraft.serverNBTTagCompound)
	private Method methodNmsItemStackSave;
	
	// -------------------------------------------- //
	// PROVOKE
	// -------------------------------------------- //
	
	@Override
	public Class<ArmorStand> provoke() throws Throwable
	{
		// Demand 1.8
		// The rich chat system with clickables and tooltips were added in Minecraft 1.8.
		// At Minecraft 1.7 the reflection in setup will succeed.
		// The returned String is however an inferior and incompatible version.
		return ArmorStand.class;
	}
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classCraftItemStack = PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftItemStack");
		this.classNmsItemStack = PackageType.MINECRAFT_WORLD_ITEM.getClass("ItemStack");
		this.classNmsNbtTagCompound = PackageType.MINECRAFT_NBT.getClass("NBTTagCompound");
		
		this.methodCraftItemStackAsNmsCopy = ReflectionUtil.getMethod(this.classCraftItemStack, "asNMSCopy", ItemStack.class);
		this.methodNmsItemStackSave = ReflectionUtil.getMethod(this.classNmsItemStack, "save", classNmsNbtTagCompound);
	}
	
	// -------------------------------------------- //
	// TOOLTIP
	// -------------------------------------------- //
	
	@Override
	public String getNbtStringTooltip(ItemStack item)
	{
		Object nmsItemStack = ReflectionUtil.invokeMethod(this.methodCraftItemStackAsNmsCopy, null, item);
		Object nbtTagCompound = ReflectionUtil.newInstance(this.classNmsNbtTagCompound);
		nbtTagCompound = ReflectionUtil.invokeMethod(this.methodNmsItemStackSave, nmsItemStack, nbtTagCompound);
		return nbtTagCompound.toString();
	}
	
}