// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.classfile.register;

/**
 * Temporary instruction.
 *
 * @since 2019/03/16
 */
final class __TempInstruction__
{
	/** The index of this instruction. */
	protected final int index;
	
	/** The operation. */
	protected final int op;
	
	/** The arguments. */
	final Object[] _args;
	
	/**
	 * Initializes the temporary instruction.
	 *
	 * @param __dx The instruction index.
	 * @param __op The operation.
	 * @param __args The arguments.
	 * @since 2019/03/17
	 */
	__TempInstruction__(int __dx, int __op, Object... __args)
	{
		this.index = __dx;
		this.op = __op;
		this._args = (__args == null ? new Object[0] : __args.clone());
	}
}
