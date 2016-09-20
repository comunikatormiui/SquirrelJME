// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.javac.base;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * This interface is used for obtaining files needed for the compiler input.
 *
 * All requested file names are treated as if they were named accordingly to
 * ZIP files.
 *
 * @since 2016/09/18
 */
public interface CompilerInput
{
	/**
	 * Opens the file associated with the input name.
	 *
	 * @param __src If {@code true} then obtain source code, otherwise a
	 * class file is requested.
	 * @param __name The name of the file.
	 * @return The stream for the file data.
	 * @throws IOException On read errors.
	 * @throws NoSuchFileException If the file does not exist.
	 * @since 2016/09/19
	 */
	public abstract InputStream input(boolean __src, String __name)
		throws IOException, NoSuchFileException;
	
	/**
	 * Lists all files which are visible to this input.
	 *
	 * @param __src If {@code true} then only the source path is to be
	 * considered, otherwise the class path should be read.
	 * @return An iterable over every file which is available.
	 * @throws IOException On read errors.
	 * @since 2016/09/19
	 */
	public abstract Iterable<String> list(boolean __src)
		throws IOException;
}

