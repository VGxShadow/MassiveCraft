package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import org.bukkit.persistence.PersistentDataContainer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPersistentData116R3P extends NmsPersistentData
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsPersistentData116R3P i = new NmsPersistentData116R3P();
	public static NmsPersistentData116R3P get () { return i; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private Class<?> classNBTTagCompound;
	
	private Class<?> classCraftNBTTagConfigSerializer;
	
	private Class<?> classCraftPersistentDataContainer;
	
	private Method serializePersistentData;
	private Method deserializeNBT;
	
	private Method putAllPersistentData;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classNBTTagCompound = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
		
		this.classCraftNBTTagConfigSerializer = PackageType.CRAFTBUKKIT_UTIL.getClass("CraftNBTTagConfigSerializer");
		
		this.classCraftPersistentDataContainer = PackageType.CRAFTBUKKIT_PERSISTENCE.getClass("CraftPersistentDataContainer");
		
		this.serializePersistentData = ReflectionUtils.getMethod(classCraftPersistentDataContainer, "serialize");
		this.deserializeNBT = ReflectionUtils.getMethod(classCraftNBTTagConfigSerializer, "deserialize", Object.class);
		
		this.putAllPersistentData = ReflectionUtils.getMethod(classCraftPersistentDataContainer, "putAll", classNBTTagCompound);
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPersistentData(PersistentDataContainer persistentDataContainer) {
		try
		{
			return (Map<String, Object>) serializePersistentData.invoke(persistentDataContainer);
		}
		catch (Exception ignored) {
			MassiveCore.get().log(Level.WARNING, "Failed to getPersistentData");
			return null;
		}
	}
	
	@Override
	public void setPersistentData(PersistentDataContainer persistentDataContainer, Map<String, Object> data) {
		try
		{
			Object deserialized = deserializeNBT.invoke(persistentDataContainer, data);
			putAllPersistentData.invoke(persistentDataContainer, deserialized);
		}
		catch (Exception ignored) {
			MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData");
		}
	}
	
}
