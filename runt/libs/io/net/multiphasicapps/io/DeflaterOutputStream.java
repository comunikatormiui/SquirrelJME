// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is used to compress to standard deflate streams.
 *
 * Associated standards:
 * {@link https://www.ietf.org/rfc/rfc1951.txt}.
 *
 * This class is not thread safe.
 *
 * @since 2018/11/10
 */
public class DeflaterOutputStream
	extends OutputStream
	implements CompressionStream
{
	/** Stream to write compressed data to. */
	protected final OutputStream out;
	
	/** The block size to compress for. */
	private final int _blocksize;
	
	/** The bytes to process first. */
	private final byte[] _fill;
	
	/** The number of bytes in the fill. */
	private int _fillbytes;
	
	/** Has this been closed? */
	private boolean _closed;
	
	/** Compressed bytes. */
	private long _ncompressed;
	
	/** Uncompressed bytes. */
	private long _nuncompressed;
	
	/** The temporary bits for output. */
	private int _wout;
	
	/** The number to bits available to the output. */
	private int _wbits;
	
	/**
	 * Initializes the deflation stream.
	 *
	 * @param __os The output stream.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/11/10
	 */
	public DeflaterOutputStream(OutputStream __os)
		throws NullPointerException
	{
		this(__os, CompressionLevel.DEFAULT);
	}
	
	/**
	 * Initializes the deflation stream.
	 *
	 * @param __os The output stream.
	 * @param __cl The compression level to use.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/11/10
	 */
	public DeflaterOutputStream(OutputStream __os, CompressionLevel __cl)
		throws NullPointerException
	{
		if (__os == null || __cl == null)
			throw new NullPointerException("NARG");
		
		this.out = __os;
		
		// Process data by blocks for efficiency
		int blocksize = __cl.blockSize();
		this._fill = new byte[blocksize];
		this._blocksize = blocksize;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final void close()
		throws IOException
	{
		// Only close once!
		if (!this._closed)
		{
			// Is closed
			this._closed = true;
			
			// Process the file if there is any
			if (this._fillbytes > 0)
				this.__processFill();
			
			// Make sure the output is flushed first
			this.flush();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final long compressedBytes()
	{
		return this._ncompressed;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final void flush()
		throws IOException
	{
		// Flush all the bits
		this.__bitFlush();
		
		// Then flush the stream itself
		this.out.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final long uncompressedBytes()
	{
		return this._nuncompressed;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final void write(int __b)
		throws IOException
	{
		// Just forward write call since it is easier
		this.write(new byte[]{(byte)__b}, 0, 1);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2018/11/10
	 */
	@Override
	public final void write(byte[] __b, int __o, int __l)
		throws IOException
	{
		// Check
		if (__b == null)
			throw new NullPointerException("NARG");
		if (__o < 0 || __l < 0 || (__o + __l) > __b.length)
			throw new ArrayIndexOutOfBoundsException("AIOB");
		
		byte[] fill = this._fill;
		int blocksize = this._blocksize,
			fillbytes = this._fillbytes;
		
		// Write into the fill buffer, but do fill in chunks since that is
		// more optimized
		boolean addedfill = false;
		while (__l > 0)
		{
			// We can only fit so many bytes in the fill before it is full
			int leftinfill = Math.min(blocksize - fillbytes, __l);
			
			// Copy bytes into the fill
			for (int i = 0; i < leftinfill; i++)
				fill[fillbytes++] = __b[__o++];
			
			// Length is dropped by the fill
			__l -= leftinfill;
			
			// Process entire block of bytes
			if (fillbytes == blocksize)
			{
				// Need to store the number of bytes written before we
				// continue
				this._fillbytes = fillbytes;
				this.__processFill();
				
				// Since the entire fill was drained, this would have been
				// reset
				fillbytes = 0;
			}
			
			// Fill would have been added, so need to say that there are
			// bytes in here currently
			else
				addedfill = true;
		}
		
		// Bytes were added to the fill, record those
		if (addedfill)
			this._fillbytes = fillbytes;
	}
	
	/**
	 * Flushes the input bits to the output stream.
	 *
	 * @throws IOException On write errors.
	 * @since 2018/11/01
	 */
	final void __bitFlush()
		throws IOException
	{
		// Only write if there are enough bits to write
		int wbits = this._wbits;
		if (wbits >= 8)
		{
			OutputStream out = this.out;
			int wout = this._wout;
			long ncompressed = this._ncompressed;
			
			// Send to the output
			while (wbits >= 8)
			{
				// Send to output
				out.write(wout & 0xFF);
				
				// Clip down
				wout >>>= 8;
				wbits -= 8;
				
				// Single byte was written
				ncompressed++;
			}
			
			// Store new values
			this._wbits = wbits;
			this._wout = wout;
			this._ncompressed = ncompressed;
		}
	}
	
	/**
	 * Writes the specified bits to the output.
	 *
	 * @param __v The value to write.
	 * @param __n The number of bits to store.
	 * @param __msb Is the most significant bit first?
	 * @throws IOException On write errors.
	 * @since 2018/11/10
	 */
	final void __bitOut(int __v, int __n, boolean __msb)
		throws IOException
	{
		throw new todo.TODO();
	}
	
	/**
	 * Pads the output bits the given number.
	 *
	 * @param __n The number of bits to pad to.
	 * @throws IOException On write errors.
	 * @since 2018/11/10
	 */
	final void __bitPad(int __n)
		throws IOException
	{
		throw new todo.TODO();
	}
	
	/**
	 * Processes the bytes which are in the fill buffer.
	 *
	 * @throws IOException On write errors.
	 * @since 2018/11/10
	 */
	final void __processFill()
		throws IOException
	{
		// Get fill parameters
		byte[] fill = this._fill;
		int fillbytes = this._fillbytes;
		
		// Debug
		todo.DEBUG.note("Processing %d bytes...", fillbytes);
		
		throw new todo.TODO();
		
		// private final int _blocksize;
		// private final byte[] _fill;
		// private int _fillbytes;
	}
}

