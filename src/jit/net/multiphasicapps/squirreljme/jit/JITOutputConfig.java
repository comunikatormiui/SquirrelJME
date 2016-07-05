// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit;

import java.util.Objects;

/**
 * This is used to configure the input and output of a JIT operation. This
 * is used as a singular source for configuration to simplify passing of
 * arguments along with the potential of adding more details when required.
 *
 * @since 2016/07/04
 */
public final class JITOutputConfig
{
	/** Internal consistency lock. */
	protected final Object lock =
		new Object();
	
	/** The triplet to target. */
	private volatile JITTriplet _triplet;
	
	/**
	 * Initializes a blank configuration.
	 *
	 * @since 2016/07/04
	 */
	public JITOutputConfig()
	{
	}
	
	/**
	 * Returns the triplet to target.
	 *
	 * @return The target triplet, or {@code null} if it was never set.
	 * @since 2016/07/05
	 */
	public JITTriplet getTriplet()
	{
		return this._triplet;
	}
	
	/**
	 * Returns an immutable copy of the current configuration.
	 *
	 * @return An immutable copy of the configuration.
	 * @throws IllegalArgumentException If required configuration settings
	 * were not set.
	 * @since 2016/07/05
	 */
	public Immutable immutable()
		throws IllegalArgumentException
	{
		// Lock
		synchronized (this.lock)
		{
			return new Immutable(this);
		}
	}
	
	/**
	 * Sets the triplet to target.
	 *
	 * @param __t The triplet to target.
	 * @return The old triplet or {@code null} if it was not set.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/07/05
	 */
	public JITTriplet setTriplet(JITTriplet __t)
		throws NullPointerException
	{
		// Check
		if (__t == null)
			throw new NullPointerException("NARG");
		
		// Lock
		synchronized (this.lock)
		{
			JITTriplet rv = this._triplet;
			this._triplet = __t;
			return rv;
		}
	}
	
	/**
	 * This is an immutable copy of an output configuration which does not
	 * change so that it may safely be used by the {@link JITOutputFactory}
	 * without worrying about state changes.
	 *
	 * @since 2016/07/04
	 */
	public static final class Immutable
	{
		/** The target triplet. */
		protected final JITTriplet triplet;
		
		/**
		 * Initializes an immutable configuration which does not change.
		 *
		 * @param __joc The base configuration.
		 * @throws IllegalArgumentException If required options were not set.
		 * @throws NullPointerException On null arguments.
		 * @since 2016/07/04
		 */
		private Immutable(JITOutputConfig __joc)
			throws IllegalArgumentException, NullPointerException
		{
			// Lock
			if (__joc == null)
				throw new NullPointerException("NARG");
			
			// Lock
			synchronized (__joc.lock)
			{
				// {@squirreljme.error ED09 A triplet was never set.}
				JITTriplet triplet = __joc._triplet;
				if (triplet == null)
					throw new IllegalArgumentException("ED09");
				this.triplet = triplet;
			}
		}
		
		/**
		 * Returns the triplet to target.
		 *
		 * @return The target triplet.
		 * @since 2016/07/05
		 */
		public final JITTriplet triplet()
		{
			return this.triplet;
		}
	}
}

