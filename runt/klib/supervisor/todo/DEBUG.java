// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package todo;

import cc.squirreljme.jvm.Assembly;
import cc.squirreljme.jvm.SystemCallError;
import cc.squirreljme.jvm.SystemCallIndex;

/**
 * Contains basic debug output printing.
 *
 * @since 2019/05/26
 */
public final class DEBUG
{
	/**
	 * Not used.
	 *
	 * @since 2019/05/26
	 */
	private DEBUG()
	{
	}
	
	/**
	 * Prints a debug note.
	 *
	 * @param __fmt The string format, compatible with Java except that it
	 * is very limited in the formats it supports.
	 * @param __args Arguments to the note.
	 * @since 2019/05/26
	 */
	public static final void note(String __fmt, Object... __args)
	{
		// Get the pipe descriptor for standard error, ignore if it fails
		int fd = Assembly.sysCallV(SystemCallIndex.PD_OF_STDERR);
		if (SystemCallError.getError(SystemCallIndex.PD_OF_STDERR) != 0)
			return;
		
		// Print char by char to the console
		for (int i = 0, n = __fmt.length(); i < n; i++)
		{
			// Read character here
			char c = __fmt.charAt(i);
			
			// Single byte sequence
			if (c <= 0x7F)
			{
				// Forward
				Assembly.sysCall(SystemCallIndex.PD_WRITE_BYTE,
					fd, c & 0xFF);
			}
			
			// Double byte sequence
			else
			{
				// Forward
				Assembly.sysCall(SystemCallIndex.PD_WRITE_BYTE,
					fd, (c >>> 6) | 0b1100_0000);
				Assembly.sysCall(SystemCallIndex.PD_WRITE_BYTE,
					fd, (c & 0b111111));
			}
		}
		
		// End with newline sequence
		Assembly.sysCall(SystemCallIndex.PD_WRITE_BYTE, fd, '\n');
	}
}
