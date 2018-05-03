// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.javac.syntax.expr;

/**
 * This is just an expression one syntax which contains a single expression
 * two syntax and has no ternary operation.
 *
 * @since 2018/05/03
 */
public final class PlainExpressionOneSyntax
	implements ExpressionOneSyntax
{
	/** The expression used. */
	protected final ExpressionTwoSyntax expression;
	
	/**
	 * Initializes the plain syntax.
	 *
	 * @param __expr The expression used.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/05/03
	 */
	public PlainExpressionOneSyntax(ExpressionTwoSyntax __expr)
		throws NullPointerException
	{
		if (__expr == null)
			throw new NullPointerException("NARG");
		
		this.expression = __expr;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/05/03
	 */
	@Override
	public final boolean equals(Object __o)
	{
		throw new todo.TODO();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/05/03
	 */
	@Override
	public final int hashCode()
	{
		throw new todo.TODO();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/05/03
	 */
	@Override
	public final String toString()
	{
		throw new todo.TODO();
	}
}
