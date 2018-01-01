// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.runtime.clsyscall;

/**
 * This represents the packet types which are sent between the clients.
 *
 * Packet types which are negative do not have a response value returned and
 * are essentially just events.
 *
 * @since 2018/01/01
 */
public interface PacketTypes
{
	/** The hello packet which indicates the remote side is alive. */
	public static final int HELLO =
		-1;
	
	/** This represents a result to a previous key which was sent. */
	public static final int RESPONSE_OKAY =
		-2;
	
	/** This represents a response which has failed. */
	public static final int RESPONSE_FAIL =
		-3;
	
	/** The client has been started (constructors are okay). */
	public static final int INITIALIZATION_COMPLETE =
		-4;
	
	/** Map service. */
	public static final int MAP_SERVICE =
		1;
}

