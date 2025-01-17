package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.SenderPresence;
import com.massivecraft.massivecore.SenderType;
import com.massivecraft.massivecore.command.type.sender.TypeSenderEntity;
import com.massivecraft.massivecore.command.type.sender.TypeSenderId;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SenderColl<E extends SenderEntity<E>> extends Coll<E> implements SenderIdSource
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public SenderColl(String id, Class<E> entityClass, Db db, MassivePlugin plugin)
	{	
		super(id, entityClass, db, plugin);
		this.setCreative(true);
		this.setLowercasing(true);
	}
	
	public SenderColl(String id)
	{	
		this(id, null, null, null);
	}
	
	public SenderColl()
	{	
		this(null, null, null, null);
	}

	// -------------------------------------------- //
	// STACK TRACEABILITY
	// -------------------------------------------- //
	
	@Override
	public void onTick()
	{
		super.onTick();
	}
	
	// -------------------------------------------- //
	// OVERRIDE: Coll
	// -------------------------------------------- //
	
	@Override
	public E get(Object oid)
	{
		if (MUtil.isNpc(oid)) return null;
		return super.get(oid);
	}
	
	@Override
	public E get(Object oid, boolean creative)
	{
		if (MUtil.isNpc(oid)) return null;
		return super.get(oid, creative);
	}
	
	@Override
	public String fixId(Object oid)
	{
		// A null oid should always return null.
		if (oid == null) return null;
		
		String ret = null;
		
		if (oid instanceof String)
		{
			ret = (String)oid;
		}
		else if (oid.getClass() == this.entityClass)
		{
			ret = ((Entity<?>) oid).getId();
		}
		
		if (ret == null)
		{
			// Always lower case.
			ret = IdUtil.getId(oid);
		}
		
		if (this.isLowercasing() && ret != null)
		{
			ret = ret.toLowerCase();
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// OVERRIDE: SenderIdSource
	// -------------------------------------------- //
	
	@Override
	public Collection<Collection<String>> getSenderIdCollections()
	{
		List<Collection<String>> ret = new ArrayList<>();
		
		ret.add(this.getIds());
		
		// For creative collections we must add all known ids.
		// You could say the corresponding entities latently exist in the collection because it's creative.
		if (this.isCreative())
		{
			ret.add(IdUtil.getIds(SenderPresence.ANY, SenderType.ANY));
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// ARGUMENT READERS
	// -------------------------------------------- //
	
	public TypeSenderEntity<E> getTypeEntity()
	{
		return TypeSenderEntity.get(this);
	}
	
	public TypeSenderEntity<E> getTypeEntity(SenderPresence presence)
	{
		return TypeSenderEntity.get(this, presence);
	}
	
	public TypeSenderId getTypeId()
	{
		return TypeSenderId.get(this);
	}
	
	public TypeSenderId getTypeId(SenderPresence presence)
	{
		return TypeSenderId.get(this, presence);
	}
	
	// -------------------------------------------- //
	// GET ALL ONLINE / OFFLINE
	// -------------------------------------------- //
	
	public static final Predicate<SenderEntity<?>> PREDICATE_ONLINE = SenderEntity::isOnline;
	
	public static final Predicate<SenderEntity<?>> PREDICATE_OFFLINE = SenderEntity::isOffline;
	
	public Collection<E> getAllOnline()
	{
		return this.getAll(PREDICATE_ONLINE);
	}
	
	public Collection<E> getAllOffline()
	{
		return this.getAll(PREDICATE_OFFLINE);
	}
	
	// -------------------------------------------- //
	// SENDER REFERENCE MANAGEMENT
	// -------------------------------------------- //
	
	protected void setSenderReference(String senderId, CommandSender sender)
	{
		 E senderEntity = this.get(senderId, false);
		 if (senderEntity == null) return;
		 senderEntity.sender = sender;
		 senderEntity.senderInitiated = true;
	}
	
	public static void setSenderReferences(String senderId, CommandSender sender)
	{
		for (Coll<?> coll : Coll.getInstances())
		{
			if (!(coll instanceof SenderColl<?> senderColl)) continue;
			senderColl.setSenderReference(senderId, sender);
		}
	}
	
	// -------------------------------------------- //
	// CLEAN
	// -------------------------------------------- //

	// Must be overriden if they want to use it.
	public long getCleanInactivityToleranceMillis()
	{
		return -1;
	}
	
}
