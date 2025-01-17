package com.massivecraft.massivecore.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PeriodUtil
{
	// -------------------------------------------- //
	// MILLIS STORE
	// -------------------------------------------- //
	
	private static final Map<Object, Long> objectToMillis = new HashMap<>();
	
	public static long getMillis(Object object)
	{
		Long ret = objectToMillis.get(object);
		if (ret == null) ret = 0L;
		return ret;
	}
	
	public static void setMillis(Object object, @Nullable Long millis)
	{
		if (millis == null || millis == 0)
		{
			objectToMillis.remove(object);
		}
		else
		{
			objectToMillis.put(object, millis);
		}
	}
	
	// -------------------------------------------- //
	// TICKS STORE
	// -------------------------------------------- //
	
	private static final Map<Object, Long> objectToTicks = new HashMap<>();
	
	public static long getTicks(Object object)
	{
		Long ret = objectToTicks.get(object);
		if (ret == null) ret = 0L;
		return ret;
	}
	
	public static void setTicks(Object object, @Nullable Long ticks)
	{
		if (ticks == null || ticks == 0)
		{
			objectToTicks.remove(object);
		}
		else
		{
			objectToTicks.put(object, ticks);
		}
	}
	
	// -------------------------------------------- //
	// RANDOM SIMPLE
	// -------------------------------------------- //
	
	@Contract(pure = true)
	public static long getPeriod(long length, long now)
	{
		return now / length;
	}
	
	public static long getPeriod(long length)
	{
		return getPeriod(length, System.currentTimeMillis());
	}
	
	public static long getLastPeriod(Object object, long length)
	{
		return getPeriod(length, getMillis(object));
	}
	
	public static boolean isNewPeriod(Object object, long length, long now)
	{
		// If the ticks are the same we are fine!
		long currentTicks = Bukkit.getWorlds().get(0).getTime();
		long lastTicks = getTicks(object);
		if (currentTicks == lastTicks) return true;
		
		// Otherwise period must be new!
		long currentPeriod = getPeriod(length, now);
		long lastPeriod = getLastPeriod(object, length);
		if (currentPeriod == lastPeriod) return false;
		
		// And then we log data
		setTicks(object, currentTicks);
		setMillis(object, now);
		
		return true;
	}
	
	public static boolean isNewPeriod(Object object, long length)
	{
		return isNewPeriod(object, length, System.currentTimeMillis());
	}
	
}
