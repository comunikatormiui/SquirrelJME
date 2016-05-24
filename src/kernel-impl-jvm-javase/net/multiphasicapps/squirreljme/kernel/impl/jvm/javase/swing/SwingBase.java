// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.kernel.impl.jvm.javase.swing;

import java.lang.ref.Reference;
import java.util.Objects;
import net.multiphasicapps.squirreljme.ui.PIBase;
import net.multiphasicapps.squirreljme.ui.UIBase;
import net.multiphasicapps.squirreljme.ui.UIException;

/**
 * This is the base class for all swing based elements.
 *
 * @since 2016/05/23
 */
public abstract class SwingBase
	implements PIBase
{
	/** The locking object to use. */
	protected final Object lock;
	
	/** The owning manager. */
	protected final SwingManager manager;
	
	/** The external element. */
	protected final Reference<? extends UIBase> external;
	
	/**
	 * Intiailizes the swing base.
	 *
	 * @param __sm The owning manager.
	 * @param __ext The external element.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/05/23
	 */
	public SwingBase(SwingManager __sm, Reference<? extends UIBase> __ext)
		throws NullPointerException
	{
		// Check
		if (__sm == null || __ext == null)
			throw new NullPointerException("NARG");
		
		// Set
		this.manager = __sm;
		this.external = __ext;
		this.lock = __sm.lock();
	}
	
	/**
	 * Sub-classes must call the super method.
	 *
	 * {@inheritDoc}
	 * @since 2016/05/23
	 */
	@Override
	public void cleanup()
		throws UIException
	{
		throw new Error("TODO");
	}
	
	/**
	 * Returns the external element associated with this base.
	 *
	 * @param __cl The class type to cast to.
	 * @return The external element this internal one points to, or
	 * {@code null} if it was cleaned up and garbage collected.
	 * @throws ClassCastException If the class type is not correct.
	 * @throws NullPointerException On null arguments.
	 */
	public final <U extends UIBase> U external(Class<U> __cl)
		throws ClassCastException, NullPointerException
	{
		return __cl.cast(external.get());
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/05/23
	 */
	@Override
	public final SwingManager platformManager()
		throws UIException
	{
		return this.manager;
	}
}

