package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

public class MixinKick extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinKick d = new MixinKick();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinKick i = d;
	@Contract(pure = true)
	public static MixinKick get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public boolean kick(Object senderObject)
	{
		return this.kick(senderObject, null);
	}
	
	public boolean kick(Object senderObject, String message)
	{
		Player player = IdUtil.getPlayer(senderObject);
		if (player == null) return false;
		player.kickPlayer(message);
		return true;
	}

}
