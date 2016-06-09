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
 * This interface represents a standard memory pool which is used by a manager
 * to provide access to areas of memory.
 *
 * @since 2016/06/05
 */
@Deprecated
public interface MemoryPool
	extends AtomicOperations, ReadableMemory, WritableMemory
{
	/**
	 * Returns the base address and location of this memory pool.
	 *
	 * The value returned here must be treated as an unsigned value.
	 *
	 * @retrun The pool's base address, the handling of this value is not
	 * signed.
	 * @since 2016/06/08
	 */
	public abstract long baseAddress();
	
	/**
	 * Returns the number of bytes which are available in this pool.
	 *
	 * @return The number of available bytes.
	 * @since 2016/06/05
	 */
	public abstract long size();
}

