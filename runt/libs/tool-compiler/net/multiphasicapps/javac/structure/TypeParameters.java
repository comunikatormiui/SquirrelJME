// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.javac.structure;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import net.multiphasicapps.javac.token.BufferedTokenSource;
import net.multiphasicapps.javac.token.Token;
import net.multiphasicapps.javac.token.TokenSource;
import net.multiphasicapps.javac.token.TokenType;

/**
 * This represents multiple type parameters.
 *
 * @since 2018/04/30
 */
public final class TypeParameters
{
	/** Type parameters used. */
	private final TypeParameter[] _params;
	
	/**
	 * Initializes the type parameters.
	 *
	 * @param __t The types to use.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/04/30
	 */
	public TypeParameters(TypeParameter... __t)
		throws NullPointerException
	{
		this(Arrays.<TypeParameter>asList((__t == null ? new TypeParameter[0] :
			__t)));
	}
	
	/**
	 * Initializes the type parameters.
	 *
	 * @param __t The types to use.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/04/30
	 */
	public TypeParameters(Iterable<TypeParameter> __t)
		throws NullPointerException
	{
		if (__t == null)
			throw new NullPointerException("NARG");
		
		List<TypeParameter> params = new ArrayList<>();
		for (TypeParameter t : __t)
		{
			if (__t == null)
				throw new NullPointerException("NARG");
			
			params.add(t);
		}
		
		this._params = params.<TypeParameter>toArray(
			new TypeParameter[params.size()]);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/04/28
	 */
	@Override
	public final boolean equals(Object __o)
	{
		throw new todo.TODO();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/04/28
	 */
	@Override
	public final int hashCode()
	{
		throw new todo.TODO();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/04/28
	 */
	@Override
	public final String toString()
	{
		throw new todo.TODO();
	}
	
	/**
	 * Parses a list of multiple type parameters.
	 *
	 * @param __in The input tokens.
	 * @return The parsed type parameters.
	 * @throws NullPointerException On null arguments.
	 * @throws StructureParseException If the type parameters could not be
	 * parsed.
	 * @since 2018/04/24
	 */
	public static TypeParameters parse(BufferedTokenSource __in)
		throws NullPointerException, StructureParseException
	{
		if (__in == null)
			throw new NullPointerException("NARG");
		
		throw new todo.TODO();
	}
}
