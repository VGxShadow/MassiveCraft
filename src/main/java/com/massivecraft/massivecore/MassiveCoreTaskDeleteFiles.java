package com.massivecraft.massivecore;

import java.io.File;

public class MassiveCoreTaskDeleteFiles implements Runnable
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MassiveCoreTaskDeleteFiles i = new MassiveCoreTaskDeleteFiles();
	public static MassiveCoreTaskDeleteFiles get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void run()
	{
		// Some people got null pointers
		if (MassiveCoreMConf.get() == null) throw new NullPointerException("mconf");
		if (MassiveCoreMConf.get().deleteFiles == null) throw new NullPointerException("deleteFiles");

		for (String deleteFile : MassiveCoreMConf.get().deleteFiles)
		{
			File file = new File(deleteFile);
			file.delete();
		}
	}
	
}
