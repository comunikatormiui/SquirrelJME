// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.kernel;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This is the SquirrelJME kernel which uses a kernel interface to interface
 * with the native system, this class manages processes and threads within
 * the virtual machine.
 *
 * @since 2016/10/31
 */
public final class Kernel
{
	/** The minimum number of permissible cycles. */
	public static final int MIN_CYCLES =
		16;
	
	/** The thread execution model used. */
	protected final ThreadingExecutionModel threadexecmodel;
	
	/** Threads. */
	final List<KernelThread> _threads =
		new LinkedList<>();
	
	/**
	 * Initializes the kernel using the given set of parameters.
	 *
	 * @param __kb The builder used for the kernel.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/10/31
	 */
	Kernel(KernelBuilder __kb)
		throws NullPointerException
	{
		// Check
		if (__kb == null)
			throw new NullPointerException("NARG");
		
		throw new Error("TODO");
	}
	
	/**
	 * Runs the kernel loop.
	 *
	 * @return {@code true} if the kernel has not yet exited.
	 * @throws InterruptedException If the loop operation was interrupted.
	 * @since 2016/10/31
	 */
	public final boolean run()
		throws InterruptedException
	{
		throw new Error("TODO");
	}
}

