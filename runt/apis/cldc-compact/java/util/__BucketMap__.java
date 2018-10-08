// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package java.util;

/**
 * This is a bucket map which acts as the raw internal hash table
 * implementation.
 *
 * @see HashMap
 * @see HashSet
 * @see LinkedHashMap
 * @see LinkedHashSet
 * @param <K> The key type.
 * @param <V> The value type.
 * @since 2018/10/07
 */
final class __BucketMap__<K, V>
{
	/** The default capacity. */
	static final int _DEFAULT_CAPACITY =
		16;
	
	/** The default load factor. */
	static final float _DEFAULT_LOAD =
		0.75F;
	
	/** The load factor. */
	protected final float loadfactor;
	
	/** The entry chains for each element. */
	__Chain__[] _buckets;
	
	/** The hashcode divisor for buckets. */
	int _bucketdiv;
	
	/** The number of elements in the map. */
	int _size;
	
	/**
	 * Initializes the map with the default capacity and load factor.
	 *
	 * @since 2018/10/07
	 */
	__BucketMap__()
	{
		this(__BucketMap__._DEFAULT_CAPACITY, __BucketMap__._DEFAULT_LOAD);
	}
	
	/**
	 * Initializes the map with the given capacity and the default load factor.
	 *
	 * @param __cap The capacity used.
	 * @throws IllegalArgumentException If the capacity is negative.
	 * @since 2018/10/07
	 */
	__BucketMap__(int __cap)
	{
		this(__cap, __BucketMap__._DEFAULT_LOAD);
	}
	
	/**
	 * Initializes the map with the given capacity and load factor.
	 *
	 * @param __cap The capacity used.
	 * @param __load The load factor used.
	 * @throws IllegalArgumentException If the capacity is negative or the
	 * load factor is not positive.
	 * @since 2018/10/07
	 */
	__BucketMap__(int __cap, float __load)
		throws IllegalArgumentException
	{
		// {@squirreljme.error ZZ2b The initial capacity of the map cannot be
		// negative.}
		if (__cap < 0)
			throw new IllegalArgumentException("ZZ2b");
		
		// {@squirreljme.error ZZ2c The load factor must be a positive value.}
		if (__load <= 0.0F)
			throw new IllegalArgumentException("ZZ2c");
		
		this.loadfactor = __load;
		this._buckets = new __Bucket__[__cap];
		this._bucketdiv = __cap;
	}
	
	/**
	 * Returns the chain that the hashed object is within for the bucket.
	 *
	 * @param __hash The hash to locate the bucket for.
	 * @return The bucket for the given hash.
	 * @since 2018/10/07
	 */
	public __BucketMap__.__Chain__ put(Object __k)
	{
		// Determine the slot the bucket would be in
		int slot = __hash % this._bucketdiv;
		
		// Return that bucket
		return this._buckets[slot];
	}
	
	/**
	 * Chained elements which represent multiple entries.
	 *
	 * @since 2018/10/07
	 */
	static final class __Chain__
	{
	}
}
