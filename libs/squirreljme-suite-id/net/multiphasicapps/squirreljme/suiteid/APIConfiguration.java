// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.suiteid;

/**
 * This represents a configuration such as CLDC which specifies which base
 * classes are available.
 *
 * @since 2016/12/14
 */
public final class APIConfiguration
	extends API
{
	/**
	 * Initializes the constant in name and version form.
	 *
	 * @param __n
	 * @throws IllegalArgumentException If the name and version form is not
	 * valid.
	 * @since 2016/12/14
	 */
	APIConfiguration(String __n)
		throws IllegalArgumentException
	{
		super(__n);
	}
	
	/**
	 * Initializes the constant using the given name and version.
	 *
	 * @param __n The API name.
	 * @param __v The API version.
	 * @throws IllegalArgumentException If the arguments are not valid.
	 * @since 2016/12/14
	 */
	APIConfiguration(String __n, MidletVersion __v)
		throws IllegalArgumentException
	{
		super(__n, __v);
	}
}

