// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.jvm;

/**
 * This represents a single task which has information on what it is running
 * along with its ID and such.
 *
 * @since 2019/06/22
 */
public final class ClientTask
{
	/** The physical task ID. */
	public final int pid;
	
	/** The logical task ID. */
	public final int lid;
	
	/**
	 * Initializes the client task.
	 *
	 * @param __pid The PID.
	 * @param __lid The LID.
	 * @since 2019/06/22
	 */
	public ClientTask(int __pid, int __lid)
	{
		this.pid = __pid;
		this.lid = __lid;
	}
}

