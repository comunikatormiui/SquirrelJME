// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.lcdui;

import cc.squirreljme.runtime.cldc.service.ServiceAccessor;
import cc.squirreljme.runtime.cldc.service.ServiceCaller;
import cc.squirreljme.runtime.cldc.service.ServiceClientProvider;

/**
 * This is used to provide access to the service caller for use in the
 * LCDUI code to interact with the server.
 *
 * @since 2018/03/16
 */
public final class LcdServiceCall
{
	/** Lock for the service accessor. */
	private static final Object _LOCK =
		new Object();
	
	/** The local caller interface. */
	private static volatile ServiceCaller _CALLER;
	
	/** Local caller. */
	protected final ServiceCaller caller;
	
	/**
	 * Instances of this class do nothing.
	 *
	 * @param __c The caller to use.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/03/16
	 */
	LcdServiceCall(ServiceCaller __c)
		throws NullPointerException
	{
		if (__c == null)
			throw new NullPointerException("NARG");
		
		this.caller = __c;
	}
	
	/**
	 * Returns the service caller.
	 *
	 * @return The service caller.
	 * @since 2018/03/16
	 */
	public static final ServiceCaller caller()
	{
		ServiceCaller rv = LcdServiceCall._CALLER;
		if (rv == null)
			synchronized (LcdServiceCall._LOCK)
			{
				rv = LcdServiceCall._CALLER;
				if (rv == null)
				{
					_CALLER = (rv = ServiceAccessor.<LcdServiceCall>service(
						LcdServiceCall.class).caller);
				}
			}
			
		return null;
	}
	
	/**
	 * This is used to provide access to the `ServiceCaller` instance used to
	 * send events to the server.
	 *
	 * @since 2018/03/15
	 */
	public static final class Provider
		implements ServiceClientProvider
	{
		/**
		 * {@inheritDoc}
		 * @since 2018/03/15
		 */
		@Override
		public final Object initializeClient(ServiceCaller __c)
			throws NullPointerException
		{
			if (__c == null)
				throw new NullPointerException("NARG");
			
			// Just use the wrapper which returns the caller
			return new LcdServiceCall(__c);
		}
	}
}

