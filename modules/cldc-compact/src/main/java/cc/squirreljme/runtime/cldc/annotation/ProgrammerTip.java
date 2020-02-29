// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.cldc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a tip to the programmer (along with the developers of SquirrelJME)
 * so things can be remembered easier.
 *
 * @since 2018/10/29
 */
@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.CONSTRUCTOR, ElementType.FIELD,
	ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE,
	ElementType.PARAMETER, ElementType.TYPE})
public @interface ProgrammerTip
{
	/** @return The note to be stored. */
	String value() default "";
}

