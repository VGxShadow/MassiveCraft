package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

public class MixinDisplayName extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinDisplayName d = new MixinDisplayName();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinDisplayName i = d;
	@Contract(pure = true)
	public static MixinDisplayName get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public final static ChatColor DEFAULT_COLOR = ChatColor.WHITE;
	
	public Mson getDisplayNameMson(Object senderObject, Object watcherObject)
	{
		String displayName = this.getDisplayName(senderObject, watcherObject);
		if (displayName == null) return null;
		return Mson.fromParsedMessage(displayName);
	}
	
	public String getDisplayName(Object senderObject, Object watcherObject)
	{
		String senderId = IdUtil.getId(senderObject);
		if (senderId == null) return null;
		
		// Ret
		String ret = null;
		
		// Bukkit
		Player player = IdUtil.getPlayer(senderObject);
		if (player != null)
		{
			ret = player.getDisplayName();
		}
		
		// Fixed Name
		if (ret == null)
		{
			ret = IdUtil.getName(senderObject);
		}
		
		// Id Fallback
		if (ret == null)
		{
			ret = senderId;
		}
		
		// Ensure Colored
		if (ChatColor.stripColor(ret).equals(ret))
		{
			ret = DEFAULT_COLOR.toString() + ret;
		}
		
		return ret;
	}

}
