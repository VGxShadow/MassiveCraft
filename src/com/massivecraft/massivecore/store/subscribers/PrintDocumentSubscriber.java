package com.massivecraft.massivecore.store.subscribers;

import com.massivecraft.massivecore.xlib.bson.Document;

public class PrintDocumentSubscriber extends ConsumerSubscriber<Document>
{
	
	/**
	 * Construct a new instance
	 */
	public PrintDocumentSubscriber() {
		super(t -> System.out.println(t.toJson()));
	}

}
