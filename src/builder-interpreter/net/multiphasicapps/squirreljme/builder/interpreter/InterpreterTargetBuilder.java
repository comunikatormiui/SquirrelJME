// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.builder.interpreter;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import net.multiphasicapps.squirreljme.builder.BuildConfig;
import net.multiphasicapps.squirreljme.builder.TargetBuilder;
import net.multiphasicapps.squirreljme.emulator.Emulator;
import net.multiphasicapps.squirreljme.emulator.interpreter.
	InterpreterEmulator;
import net.multiphasicapps.squirreljme.emulator.Volume;
import net.multiphasicapps.squirreljme.emulator.ZipFileVolume;
import net.multiphasicapps.squirreljme.exe.ExecutableOutput;
import net.multiphasicapps.squirreljme.exe.interpreter.
	InterpreterExecutableOutput;
import net.multiphasicapps.squirreljme.jit.base.JITException;
import net.multiphasicapps.squirreljme.jit.base.JITTriplet;
import net.multiphasicapps.squirreljme.jit.JITOutputConfig;
import net.multiphasicapps.zip.blockreader.ZipFile;
import net.multiphasicapps.zip.streamwriter.ZipStreamWriter;
import net.multiphasicapps.zip.ZipCompressionType;

/**
 * This is the builder which can target the interpreter.
 *
 * @since 2016/07/22
 */
public class InterpreterTargetBuilder
	extends TargetBuilder
{
	/**
	 * Initializes the interpreter target builder.
	 *
	 * @since 2016/07/22
	 */
	public InterpreterTargetBuilder()
	{
		super(false,
			"mips-32+i,big.squirreljme.interpreter",
			"SquirrelJME Test Interpreter");
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/07/25
	 */
	@Override
	public Emulator emulate(BuildConfig __conf, Path __p)
		throws IOException, NullPointerException
	{
		// Check
		if (__conf == null || __p == null)
			throw new NullPointerException("NARG");
		
		// The ZIP or other initialization could be bad
		Emulator rv = null;
		ZipFile zip = null;
		FileChannel fc = null;
		try
		{
			// Setup emulator
			rv = new InterpreterEmulator(null);
		
			// Setup the Zip file volume
			fc = FileChannel.open(__p, StandardOpenOption.READ);
			zip = ZipFile.open(fc);
			Volume zfv = new ZipFileVolume(zip);
			rv.mountVolume(Volume.CONTRIB_BINARIES, zfv);
			
			// Extra arguments?
			String[] emuargs = __conf.emulatorArguments();
			int ne = emuargs.length;
			String[] pass = new String[ne + 1];
			pass[0] = rv.resolvePath(zfv, __executableName(__conf));
			for (int i = 0; i < ne; i++)
				pass[i + 1] = emuargs[i];
		
			// Run the emulator
			rv.startProcess(null, pass);
		
			// Return it
			return rv;
		}
		
		// Failed
		catch (IOException|Error|RuntimeException e)
		{
			// Close the emulator
			if (rv != null)
				try
				{
					rv.close();
				}
				catch (IOException|Error|RuntimeException f)
				{
					e.addSuppressed(f);
				}
			
			// Close the ZIP
			if (zip != null)
				try
				{
					zip.close();
				}
				catch (IOException|Error|RuntimeException f)
				{
					e.addSuppressed(f);
				}
			
			// Close the channel
			if (fc != null)
				try
				{
					fc.close();
				}
				catch (IOException|Error|RuntimeException f)
				{
					e.addSuppressed(f);
				}
			
			// Toss
			throw e;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/07/23
	 */
	@Override
	public void linkBinary(ZipStreamWriter __zsw, String[] __names,
		InputStream[] __blobs, BuildConfig __conf, String[] __vmcp)
		throws JITException, IOException, NullPointerException
	{
		// Check
		if (__zsw == null || __names == null || __blobs == null ||
			__conf == null || __vmcp == null)
			throw new NullPointerException("NARG");
		
		// Create binary
		try (OutputStream os = __zsw.nextEntry(__executableName(__conf),
			ZipCompressionType.DEFAULT_COMPRESSION))
		{
			// Create executable output
			InterpreterExecutableOutput ieo =
				new InterpreterExecutableOutput();
			
			// Add standard properties
			addStandardSystemProperties(__conf, ieo);
			
			// Add VM paths
			addVirtualMachineClassPath(__conf, ieo, __vmcp);
			
			// Link the binary together
			ieo.linkBinary(os, __names, __blobs);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/07/22
	 */
	@Override
	public void outputConfig(JITOutputConfig __conf, BuildConfig __bc)
		throws NullPointerException
	{
		// Check
		if (__conf == null || __bc == null)
			throw new NullPointerException("NARG");
		
		// Nothing needs to be done
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/07/22
	 */
	@Override
	public boolean supportsConfig(BuildConfig __conf)
		throws NullPointerException
	{
		// Check
		if (__conf == null)
			throw new NullPointerException("NARG");
		
		// Interpreted MIPS
		JITTriplet triplet = __conf.triplet();
		return triplet.architecture().equals("mips") &&
			triplet.operatingSystem().equals("squirreljme") &&
			triplet.operatingSystemVariant().equals("interpreter");
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/07/23
	 */
	@Override
	public String targetPackageGroup(BuildConfig __conf)
		throws NullPointerException
	{
		// Check
		if (__conf == null)
			throw new NullPointerException("NARG");
		
		// Always just interpreter
		return "interpreter";
	}
	
	/**
	 * Returns the executable name.
	 *
	 * @param __conf The configuration.
	 * @return The name of the output executable.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/07/30
	 */
	private String __executableName(BuildConfig __conf)
		throws NullPointerException
	{
		// Check
		if (__conf == null)
			throw new NullPointerException("NARG");
		
		// Alternative is set?
		String rv = __conf.alternativeExecutableName();
		if (rv == null)
			return "squirreljme.int";
		return rv;
	}
}

