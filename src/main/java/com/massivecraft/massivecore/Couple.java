package com.massivecraft.massivecore;

import com.massivecraft.massivecore.util.MUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map.Entry;

public class Couple<A, B> implements Entry<A, B>, Cloneable, Serializable
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final transient long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// FIELDS: RAW
	// -------------------------------------------- //
	
	private final A first;
	public A getFirst() { return this.first; }
	
	private final B second;
	public B getSecond() { return this.second; }
	
	// -------------------------------------------- //
	// FIELDS: WITH
	// -------------------------------------------- //
	
	public Couple<A, B> withFirst(A first) { return valueOf(first, second); }
	
	public Couple<A, B> withSecond(B second) { return valueOf(first, second); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public Couple()
	{
		this(null, null);
	}
	
	public Couple(A first, B second)
	{
		this.first = first;
		this.second = second;
	}
	
	// -------------------------------------------- //
	// OVERRIDE: ENTRY
	// -------------------------------------------- //
	
	@Override
	public A getKey()
	{
		return this.first;
	}

	@Override
	public B getValue()
	{
		return this.second;
	}

	@Override
	public B setValue(B value)
	{
		throw new IllegalStateException("This entry is a couple which is immutable.");
	}
	
	// -------------------------------------------- //
	// FACTORY: VALUE OF
	// -------------------------------------------- //
	
	public static <A, B> Couple<A, B> valueOf(A first, B second)
	{
		return new Couple<>(first, second);
	}
	
	// -------------------------------------------- //
	// EQUALS
	// -------------------------------------------- //
	
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Couple<?, ?> that)) return false;
		return MUtil.equals(
			this.getFirst(), that.getFirst(),
			this.getSecond(), that.getSecond()
		);
	}

	// -------------------------------------------- //
	// CLONE
	// -------------------------------------------- //
	
	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	public Couple<A, B> clone()
	{
		return this;
	}
	
}
