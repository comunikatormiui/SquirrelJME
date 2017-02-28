// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.rms;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import javax.microedition.midlet.MIDlet;
import net.multiphasicapps.squirreljme.midlet.ActiveMidlet;
import net.multiphasicapps.squirreljme.suiteid.MidletSuiteName;
import net.multiphasicapps.squirreljme.suiteid.MidletSuiteVendor;
import net.multiphasicapps.squirreljme.unsafe.SquirrelJME;

/**
 * This is the base class for a manager which provides access to a record
 * store.
 *
 * @since 2017/02/27
 */
public abstract class RecordClusterManager
{
	/** The current owner. */
	private static volatile Reference<RecordStoreOwner> _THIS_OWNER;
	
	/**
	 * Opens the cluster manager for the given suite.
	 *
	 * @param __o The suite to open the cluster for, if it is already open
	 * then the existing cluster will be returned.
	 * @throws NullPointerException On null arguments.
	 * @since 2017/02/27
	 */
	public final RecordCluster open(RecordStoreOwner __o)
		throws NullPointerException
	{
		// Check
		if (__o == null)
			throw new NullPointerException("NARG");
		
		throw new Error("TODO");
	}
	
	/**
	 * Returns the owner for the current MIDlet.
	 *
	 * @return The owner for the current midlet.
	 * @since 2017/02/28
	 */
	public static RecordStoreOwner thisOwner()
	{
		Reference<RecordStoreOwner> ref = _THIS_OWNER;
		RecordStoreOwner rv;
		
		// Cache?
		if (ref == null || null == (rv = ref.get()))
		{
			// Need to build the MIDlet name
			MIDlet mid = ActiveMidlet.get();
			
			// {@squirreljme.error DC02 Could not get the name and/or
			// vendor of the current MIDlet}
			String name = mid.getAppProperty("midlet-name"),
				vend = mid.getAppProperty("midlet-vendor");
			if (name == null || vend == null)
				throw new RuntimeException("DC02");
			
			// Set
			_THIS_OWNER = new WeakReference<>((rv = new RecordStoreOwner(
				new MidletSuiteName(name), new MidletSuiteVendor(vend))));
		}
		
		return rv;
	}
}

