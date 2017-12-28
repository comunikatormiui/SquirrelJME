// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.runtime.javase;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import net.multiphasicapps.squirreljme.runtime.cldc.SystemCall;
import net.multiphasicapps.squirreljme.runtime.cldc.SystemCaller;
import net.multiphasicapps.squirreljme.runtime.kernel.Kernel;
import net.multiphasicapps.squirreljme.runtime.kernel.KernelTask;
import net.multiphasicapps.squirreljme.runtime.kernel.syscall.DirectCaller;

/**
 * This initializes the SquirrelJME CLDC run-time interfaces and provides a
 * bridge so that the run-time functions properly on non-SquirrelJME Java VMs.
 *
 * @since 2017/12/07
 */
public class Main
{
	/**
	 * Wrapped main entry point.
	 *
	 * @param __args Program arguments, these are forwarded.
	 * @throws Throwable On any kind of throwable.
	 * @since 2017/12/07
	 */
	public static void main(String... __args)
		throws Throwable
	{
		// These are launch parameters which are used by the actual Java SE
		// wrappers to spawn new tasks
		String clientmain = System.getProperty(
			"net.multiphasicapps.squirreljme.runtime.javase.clientmain");
		boolean isclient = (clientmain != null);
		
		// Initialize the run-time which sets up the SquirrelJME specific
		// APIs
		__initializeRunTime(isclient);
		
		// The client just uses the specified main class
		String mainclassname;
		if (isclient)
			mainclassname = clientmain;
		
		// Determines the class name via manifest
		else
			mainclassname = __mainClassByManifest();
		
		// Exceptions generated as of the result of the method call are
		// wrapped so they must be unwrapped
		try
		{
			// {@squirreljme.error AF03 The main method is not static.}
			Method mainmethod = Class.forName(mainclassname).
				getMethod("main", String[].class);
			if ((mainmethod.getModifiers() & Modifier.STATIC) == 0)
				throw new RuntimeException("AF03");
			
			// Call it
			mainmethod.invoke(null, new Object[]{__args});
		}
		
		// Completely hide call exceptions
		catch (InvocationTargetException e)
		{
			Throwable c = e.getCause();
			if (c != null)
				throw c;
			else
				throw e;
		}
	}
	
	/**
	 * Initializes the run-time.
	 *
	 * @param __client If {@code true} then it is initialized for the client.
	 * @throws Throwable On any throwable.
	 * @since 2017/12/07
	 */
	private static void __initializeRunTime(boolean __client)
		throws Throwable
	{
		// Clients use a bi-directional bridge on top of the standard
		// input and output streams to interact with the system
		SystemCaller syscaller;
		if (__client)
		{
			throw new todo.TODO();
		}
		
		// The server uses the actual kernel
		else
		{
			KernelTask[] kerneltask = new KernelTask[1];
			Kernel kernel = new JavaKernel(kerneltask);
			syscaller = new DirectCaller(kernel, kerneltask[0]);
		}
		
		// Need to obtain the interface field so that it is initialized
		Field callerfield = SystemCall.class.getDeclaredField("_CALLER");
		
		// There is an internal modifiers field which needs to be cleared so
		// that the data can be accessed as such
		Field modifiersfield = Field.class.getDeclaredField("modifiers");
		modifiersfield.setAccessible(true);
		
		// Remember the old modifiers and clear the final field
		int oldmods = modifiersfield.getInt(callerfield);
		modifiersfield.setInt(callerfield,
			callerfield.getModifiers() & ~Modifier.FINAL);
		
		// It is final so it must be settable
		callerfield.setAccessible(true);
		
		// Set the interface used to interact with the kernel
		callerfield.set(null, syscaller);
		
		// Protect everything again
		modifiersfield.setInt(callerfield, oldmods);
		modifiersfield.setAccessible(false);
		callerfield.setAccessible(false);
	}
	
	/**
	 * Returns the main class obtained by the manifest.
	 *
	 * @return The main manifest class.
	 * @throws Throwable On any throwable
	 * @since 2017/12/07
	 */
	private static String __mainClassByManifest()
		throws Throwable
	{
		// Determine the main class to actually call using the copied
		// manifest
		try (InputStream is = Main.class.getResourceAsStream(
			"/SQUIRRELJME-BOOTSTRAP.MF"))
		{
			// {@squirreljme.error AF01 No manifest is available?}
			if (is == null)
				throw new RuntimeException("AF01");
		
			// {@squirreljme.error AF02 No main class is available?}
			String mainclassname = new Manifest(is).getMainAttributes().
				getValue("Main-Class");
			if (mainclassname == null || mainclassname.isEmpty())
				throw new RuntimeException("AF02");
			return mainclassname;
		}
	}
}

