package com.massivecraft.massivecore.engine;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.MassiveCoreBukkitCommand;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class EngineMassiveCoreCommandRegistration extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final EngineMassiveCoreCommandRegistration i = new EngineMassiveCoreCommandRegistration();
	@Contract(pure = true)
	public static EngineMassiveCoreCommandRegistration get() { return i; }
	public EngineMassiveCoreCommandRegistration()
	{
		long interval = Long.getLong("MassiveCoreCommandRegistrationPeriod", 20L * 60L * 60L); // default to every hour
		this.setPeriod(interval);
		
		Bukkit.getScheduler().runTaskLater(
			MassiveCore.get(),
			EngineMassiveCoreCommandRegistration::updateRegistrations,
			100
		);
	}
	
	// -------------------------------------------- //
	// TASK
	// -------------------------------------------- //
	
	@Override
	public void run()
	{
		updateRegistrations();
	}
	
	// -------------------------------------------- //
	// UPDATE REGISTRATIONS
	// -------------------------------------------- //
	
	public static void updateRegistrations()
	{
		// Step #1: Hack into Bukkit and get the SimpleCommandMap and it's knownCommands.
		SimpleCommandMap simpleCommandMap = getSimpleCommandMap();
		Map<String, Command> knownCommands = getSimpleCommandMapDotKnownCommands(simpleCommandMap);
		
		// Step #2: Create a "name --> target" map that contains the MassiveCommands that /should/ be registered in Bukkit. 
		Map<String, MassiveCommand> nameTargets = new HashMap<>();
		// For each MassiveCommand that is supposed to be registered ...
		for (MassiveCommand massiveCommand : MassiveCommand.getAllInstances())
		{
			// ... and for each of it's aliases ...
			for (String alias : massiveCommand.getAliases())
			{
				// ... that aren't null ...
				if (alias == null) continue;
				
				// ... clean the alias ...
				alias = alias.trim().toLowerCase();
				
				// ... and put it in the map.
				// NOTE: In case the same alias is used by many commands the overwrite occurs here!
				nameTargets.put(alias, massiveCommand);
			}
		}
		
		// Step #3: Ensure the nameTargets created in Step #2 are registered in Bukkit.
		// For each nameTarget entry ...
		for (Entry<String, MassiveCommand> entry : nameTargets.entrySet())
		{
			String name = entry.getKey();
			MassiveCommand target = entry.getValue();
			
			// ... find the current command registered in Bukkit under that name (if any) ...
			Command current = knownCommands.get(name);
			MassiveCommand massiveCurrent = getMassiveCommand(current);
			
			// ... and if the current command is not the target ...
			// NOTE: We do this check since it's important we don't create new MassiveCoreBukkitCommands unless required.
			// NOTE: Before I implemented this check I caused a memory leak in tandem with Spigots timings system.
			if (target == massiveCurrent) continue;
			
			// ... unregister the current command if there is one ...
			if (current != null)
			{
				knownCommands.remove(name);
				current.unregister(simpleCommandMap);
			}
			
			// ... create a new MassiveCoreBukkitCommand ...
			MassiveCoreBukkitCommand command = new MassiveCoreBukkitCommand(name, target);
			
			// ... and finally register it.
			Plugin plugin = command.getPlugin();
			String pluginName = plugin.getName();
			simpleCommandMap.register(pluginName, command);
		}
		
		// Step #4: Remove/Unregister MassiveCommands from Bukkit that are but should not be that any longer. 
		// For each known command ...
		Iterator<Entry<String, Command>> iter = knownCommands.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Command> entry = iter.next();
			String name = entry.getKey();
			Command command = entry.getValue();
			
			// ... that is a MassiveCoreBukkitCommand ...
			MassiveCommand massiveCommand = getMassiveCommand(command);
			if (massiveCommand == null) continue;
			
			// ... and not a target ...
			if (nameTargets.containsKey(name)) continue;
			
			// ... unregister it.
			command.unregister(simpleCommandMap);
			iter.remove();
		}
		syncCommands();
	}
	
	// -------------------------------------------- //
	// GETTERS
	// -------------------------------------------- //
	
	protected static Field SERVER_DOT_COMMAND_MAP = ReflectionUtil.getField(Bukkit.getServer().getClass(), "commandMap");
	public static SimpleCommandMap getSimpleCommandMap()
	{
		Server server = Bukkit.getServer();
		return ReflectionUtil.getField(SERVER_DOT_COMMAND_MAP, server);
	}
	
	protected static Field SIMPLE_COMMAND_MAP_DOT_KNOWN_COMMANDS = ReflectionUtil.getField(SimpleCommandMap.class, "knownCommands");
	public static Map<String, Command> getSimpleCommandMapDotKnownCommands(SimpleCommandMap simpleCommandMap)
	{
		return ReflectionUtil.getField(SIMPLE_COMMAND_MAP_DOT_KNOWN_COMMANDS, simpleCommandMap);
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	@Contract("null -> null")
	public static MassiveCommand getMassiveCommand(Command command)
	{
		if (command == null) return null;
		if (!(command instanceof MassiveCoreBukkitCommand mcbc)) return null;
		return mcbc.getMassiveCommand();
	}
	
	// -------------------------------------------- //
	// 1.13 SYNC COMMANDS
	// -------------------------------------------- //
	
	private static Method CRAFTSERVER_SYNC_COMMANDS = null;
	private static boolean syncCommandsFailed = false;
	private static Method getSyncCommandMethod() {
		if (CRAFTSERVER_SYNC_COMMANDS != null || syncCommandsFailed) return CRAFTSERVER_SYNC_COMMANDS;
		Class<?> clazz = Bukkit.getServer().getClass();
		try {
			CRAFTSERVER_SYNC_COMMANDS = ReflectionUtil.getMethod(clazz, "syncCommands");
		} catch (Exception ex) {
			syncCommandsFailed = true;
		}
		return CRAFTSERVER_SYNC_COMMANDS;
	}
	private static void syncCommands() {
		Method sync = getSyncCommandMethod();
		if (sync == null) return;
		ReflectionUtil.invokeMethod(sync, Bukkit.getServer());
	}

}
