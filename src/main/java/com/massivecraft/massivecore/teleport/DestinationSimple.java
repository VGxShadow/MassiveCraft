package com.massivecraft.massivecore.teleport;

import com.massivecraft.massivecore.ps.PS;

import java.io.Serial;

public class DestinationSimple extends DestinationAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected PS ps;
	public void setPs(PS ps) { this.ps = ps; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public DestinationSimple()
	{
		this(null, null);
	}
	
	public DestinationSimple(PS ps)
	{
		this(ps, null);
	}
	
	public DestinationSimple(PS ps, String desc)
	{
		this.ps = ps;
		this.desc = desc;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public PS getPsInner()
	{
		return this.ps;
	}

}
