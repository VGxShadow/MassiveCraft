package com.massivecraft.massivecore.engine;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCoreAfterPlayerRespawn;
import com.massivecraft.massivecore.event.EventMassiveCoreAfterPlayerTeleport;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerUpdate;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This event triggers the EventMassiveCorePlayerUpdate on every block change.
 * It also runs it in reset mode rather than update mode upon world change.
 */
public class EngineMassiveCorePlayerUpdate extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final EngineMassiveCorePlayerUpdate i = new EngineMassiveCorePlayerUpdate();
	@Contract(pure = true)
	public static EngineMassiveCorePlayerUpdate get() { return i; }
	
	// -------------------------------------------- //
	// UPDATE
	// -------------------------------------------- //
	
	public static void update(Player player, boolean current)
	{
		// If this player is actually a player and not an NPC ...
		if (MUtil.isntPlayer(player)) return;
		
		// ... and the player is alive ...
		if (player.isDead()) return;
		
		// ... store data for no cheat plus bug fix ...
		if (player.isFlying())
		{
			setLastFlyActive(player, System.currentTimeMillis());
		}
		
		// ... then trigger an update.
		EventMassiveCorePlayerUpdate.run(player, current);
	}
	
	// NOTE: Can't be cancelled
	@EventHandler(priority = EventPriority.LOWEST)
	public void update(@NotNull PlayerJoinEvent event)
	{
		update(event.getPlayer(), true);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void update(@NotNull EventMassiveCoreAfterPlayerTeleport event)
	{
		update(event.getPlayer(), true);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void update(@NotNull EventMassiveCoreAfterPlayerRespawn event)
	{
		update(event.getPlayer(), true);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void update(@NotNull PlayerChangedWorldEvent event)
	{
		update(event.getPlayer(), true);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void update(@NotNull PlayerMoveEvent event)
	{
		// Only on block change!
		if (MUtil.isSameBlock(event)) return;
		
		update(event.getPlayer(), true);
	}
	
	// -------------------------------------------- //
	// LAST FLY ACTIVE
	// -------------------------------------------- //
	
	public static Map<UUID, Long> idToLastFlyActive = new HashMap<>();
	
	public static Long getLastFlyActive(@NotNull Player player)
	{
		return idToLastFlyActive.get(player.getUniqueId());
	}
	
	public static void setLastFlyActive(@NotNull Player player, Long millis)
	{
		idToLastFlyActive.put(player.getUniqueId(), millis);
	}
	
	public static boolean isFlyActiveRecently(@NotNull Player player)
	{
		Long lastActive = getLastFlyActive(player);
		if (lastActive == null) return false;
		return (System.currentTimeMillis() - lastActive < 2000);
	}
	
	// -------------------------------------------- //
	// FIX NO CHEAT PLUS BUG
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void fixNoCheatPlusBug(@NotNull EntityDamageEvent event)
	{
		// If a player ...
		if (MUtil.isntPlayer(event.getEntity())) return;
		Player player = (Player)event.getEntity();
		
		// ... is taking fall damage ...
		if (event.getCause() != DamageCause.FALL) return;
		
		// ... after recently flying ...
		if (!isFlyActiveRecently(player)) return;
		
		// ... cancel the event.
		event.setCancelled(true);
	}
	
}
