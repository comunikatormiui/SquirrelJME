// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.memory;

/**
 * This interface is used to implement atomic operations.
 *
 * @since 2016/06/08
 */
@Deprecated
public interface AtomicOperations
{
	/**
	 * Atomically compares the given address with the desired value and if it
	 * matches the given value then its value is changed.
	 *
	 * @param __addr The address to operate on.
	 * @param __d The desired value.
	 * @param __v The value at this address is the desired value then it
	 * is set to this value.
	 * @return {@code true} if the value matched and was adjusted, otherwise
	 * {@code false} is returned if the value did not match.
	 * @throws MemoryIOException If the operation could not be performed.
	 * @since 2016/06/08
	 */
	public abstract boolean compareAndSetInt(long __addr, int __d, int __v)
		throws MemoryIOException;
	
	/**
	 * Atomically reads a value at a given location and adds a value to it and
	 * returns the old value. 
	 *
	 * @param __addr The address to operate on.
	 * @param __v The value to add.
	 * @return The old value.
	 * @throws MemoryIOException If the operation could not be performed.
	 * @since 2016/06/08
	 */
	public abstract int fetchAndAddInt(long __addr, int __v)
		throws MemoryIOException;
}

