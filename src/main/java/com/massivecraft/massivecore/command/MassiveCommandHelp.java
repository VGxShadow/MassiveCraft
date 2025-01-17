package com.massivecraft.massivecore.command;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MassiveCommandHelp extends MassiveCommand
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	protected static MassiveCommandHelp i = new MassiveCommandHelp();
	public static MassiveCommandHelp get() { return i; }
	public MassiveCommandHelp()
	{
		// Aliases
		this.addAliases("?", "h", "help");
		
		// Parameters
		this.addParameter(Parameter.getPage());
		
		// Other
		this.setDesc("");
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{	
		// Args
		int page = this.readArg(); 
		
		// Get parent command
		if ( ! this.hasParent()) return;
		MassiveCommand parent = this.getParent();
		
		// Create Lines
		List<Mson> lines = new MassiveList<>();
		for (Object helpline : parent.getHelp())
		{
			lines.add(mson(Mson.parse("<a># "), helpline).color(ChatColor.YELLOW));
		}
		
		if (parent.hasParameterForIndex(0)) lines.add(parent.getTemplate(false, false, sender));
		
		for (MassiveCommand child : parent.getChildren())
		{
			if ( ! child.isVisibleTo(sender)) continue;
			lines.add(child.getTemplate(true, true, sender));
		}
		
		// Send Lines
		message(Txt.getPage(lines, page, "Help for command \"" + parent.getAliases().get(0) + "\"", this));
	}
	
	@Override
	public boolean isVisibleTo(CommandSender sender)
	{
		boolean visible = super.isVisibleTo(sender);
		if ( ! (this.hasParent() && visible)) return visible;
		
		int visibleSiblingCount = 0;
		for (MassiveCommand sibling : this.getParent().getChildren())
		{
			if (sibling instanceof MassiveCommandHelp) continue;
			if (sibling.isVisibleTo(sender)) visibleSiblingCount++;
		}
		
		int pageHeight = (sender instanceof Player) ? Txt.PAGEHEIGHT_PLAYER : Txt.PAGEHEIGHT_CONSOLE;
		return visibleSiblingCount > pageHeight;
	}
	
}
