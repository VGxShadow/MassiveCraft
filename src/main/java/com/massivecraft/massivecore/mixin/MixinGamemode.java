package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

public class MixinGamemode extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinGamemode d = new MixinGamemode();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinGamemode i = d;
	@Contract(pure = true)
	public static MixinGamemode get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public GameMode getGamemode(Object playerObject)
	{
		Player player = IdUtil.getPlayer(playerObject);
		if (player == null) return null;
		
		return player.getGameMode();
	}

	public void setGamemode(Object playerObject, GameMode gameMode)
	{
		Player player = IdUtil.getPlayer(playerObject);
		if (player == null) return;
		
		player.setGameMode(gameMode);
	}

}
