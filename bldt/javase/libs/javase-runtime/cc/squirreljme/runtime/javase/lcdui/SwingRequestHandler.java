// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.javase.lcdui;

import cc.squirreljme.runtime.lcdui.server.LcdRequest;
import cc.squirreljme.runtime.lcdui.server.LcdRequestHandler;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 * This is the request handler for Swing which allows requests to be pumped
 * into the Swing event loop since it is required that everything execute
 * there.
 *
 * @since 2018/03/17
 */
public class SwingRequestHandler
	implements LcdRequestHandler
{
	/**
	 * {@inheritDoc}
	 * @since 2018/03/17
	 */
	@Override
	public void invokeLater(LcdRequest __r)
		throws NullPointerException
	{
		if (__r == null)
			throw new NullPointerException("NARG");
		
		SwingUtilities.invokeLater(__r);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/03/17
	 */
	@Override
	public <R> R invokeNow(Class<R> __cl, LcdRequest __r)
		throws InterruptedException, NullPointerException
	{
		if (__cl == null || __r == null)
			throw new NullPointerException("NARG");
		
		// If this is the event dispatching thread then invoke the request
		// as is becuase it could be a request to return the size of a
		// property or similar
		if (SwingUtilities.isEventDispatchThread())
		{
			__r.run();
			return __r.<R>result(__cl);
		}
		
		// Run it
		try
		{
			SwingUtilities.invokeAndWait(__r);
		}
		
		// Retoss this so the caller stops
		catch (InterruptedException e)
		{
			throw e;
		}
		
		// Failed to execute
		catch (InvocationTargetException e)
		{
			Throwable t = e.getCause();
			if (t instanceof RuntimeException)
				throw (RuntimeException)t;
			else if (t instanceof Error)
				throw (Error)t;
			
			// {@squirreljme.error AF05 Threw another exception while waiting
			// for a request to happen.}
			else
				throw new RuntimeException("AF05", e);
		}
		
		// Return the result of it
		return __r.<R>result(__cl);
	}
}

