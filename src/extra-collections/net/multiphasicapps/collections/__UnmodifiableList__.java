// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.collections;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/**
 * This is a list which cannot be modified.
 *
 * @param <V> The type of value the list stores.
 * @since 2016/03/03
 */
abstract class __UnmodifiableList__<V>
	extends AbstractList<V>
{
	/** The list to wrap. */
	protected final List<V> wrapped;	
	
	/**
	 * Initializes the list which cannot be modified.
	 *
	 * @param __l The list to wrap.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/03/03
	 */
	private __UnmodifiableList__(List<V> __l)
		throws NullPointerException
	{
		// Check
		if (__l == null)
			throw new NullPointerException("NARG");
		
		// Set
		wrapped = __l;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/03/03
	 */
	@Override
	public final V get(int __i)
	{
		return wrapped.get(__i);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/03/03
	 */
	@Override
	public final int size()
	{
		return wrapped.size();
	}
	
	/**
	 * This is a list which implements {@link RandomAccess} so that the sort
	 * and search operations do not result in an entire copy of the list
	 * before the operation is performed.
	 *
	 * @param <V> The type to contain.
	 * @since 2016/04/28
	 */
	static final class __Random__<V>
		extends __UnmodifiableList__<V>
		implements RandomAccess
	{
		/**
		 * Initializes the random access list.
		 *
		 * @param __l The list to wrap.
		 * @since 2016/04/28
		 */
		__Random__(List<V> __l)
		{
			super(__l);
		}
	}
	
	/**
	 * This is a list which does not implement {@link RandomAccess} and as
	 * such when sort or binary search is done, an intermediate array is used
	 * in place.
	 *
	 * @param <V> The type to contain.
	 * @since 2016/04/28
	 */
	static final class __Sequential__<V>
		extends __UnmodifiableList__<V>
	{
		/**
		 * Initializes the sequential access list.
		 *
		 * @param __l The list to wrap.
		 * @since 2016/04/28
		 */
		__Sequential__(List<V> __l)
		{
			super(__l);
		}
	}
}

