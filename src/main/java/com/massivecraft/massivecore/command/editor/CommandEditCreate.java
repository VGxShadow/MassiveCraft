package com.massivecraft.massivecore.command.editor;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementEditorPropertyCreated;
import org.jetbrains.annotations.NotNull;

public class CommandEditCreate<O, V> extends CommandEditAbstract<O, V>
{	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CommandEditCreate(@NotNull EditSettings<O> settings, @NotNull Property<O, V> property)
	{
		// Super	
		super(settings, property, true);
		
		// Aliases
		String alias = this.createCommandAlias();
		this.setAliases(alias);
		
		// Desc
		this.setDesc(alias + " " + this.getPropertyName());
		
		// Requirements
		this.addRequirements(RequirementEditorPropertyCreated.get(false));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		V newInstance = this.getValueType().createNewInstance();
		this.attemptSet(newInstance);
	}
	
}
