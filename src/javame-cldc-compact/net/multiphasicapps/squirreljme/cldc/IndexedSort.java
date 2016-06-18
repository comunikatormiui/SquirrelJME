// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.cldc;

/**
 * This class contains a special method which implements an in place merge
 * sort algorithm except that it works on a special int array of indices
 * instead of the original array. This permits
 *
 * @since 2016/06/18
 */
public final class IndexedSort
{
	/**
	 * Not used.
	 *
	 * @since 2016/06/18
	 */
	private IndexedSort()
	{
	}
	
	/**
	 * Sorts the given input indices using a special comparator of an unknown
	 * type. The result is a set of indices of the sorted input which is in the
	 * index order of the data that is sorted.
	 *
	 * @param <Q> Original data passed to the 
	 * @param __q The original data to sort.
	 * @param __from The inclusive starting index.
	 * @param __to The exclusive ending index.
	 * @param __comp The special comparator for the input data.
	 * @return The array of indices in their sorted order, the indices in the
	 * array start from {@code __from} and end at {@code __to}.
	 * @throws IllegalArgumentException If the from and/or to index are
	 * negative, or the to index is before the from index.
	 * @throws NullPointerException If no comparator was specified.
	 * @since 2016/06/18
	 */
	public static <Q> int[] sort(Q __q, int __from, int __to,
		IndexedComparator<Q> __comp)
		throws IllegalArgumentException, NullPointerException
	{
		// Check
		if (__comp == null)
			throw new NullPointerException("NARG");
		
		// {@squirreljme.error The from or to index to sort is either negative
		// or the to index is before the from index.}
		if (__from < 0 || __to < 0 || __to < __from)
			throw new IllegalArgumentException("ZZ0r");
		
		// Not sorting anything?
		int n = __to - __from;
		if (n == 0)
			return new int[0];
		
		// Only sorting a single element
		else if (n == 1)
			return new int[]{__from};
		
		// Comparing only two elements
		else if (n == 2)
		{
			// Get indices
			int ax = __from,
				bx = __from + 1;
			
			// Compare them
			int comp = __comp.compare(__q, ax, bx);
			
			// A is higher?
			if (comp > 0)
				return new int[]{bx, ax};
			
			// Otherwise keep the same
			else
				return new int[]{ax, bx};
		}
		
		// Setup target array with sorted index entries
		int rv[] = new int[n];
		for (int i = 0, j = __from; i < n; i++)
			rv[i] = j++;
		
		// Calculate the stack size, the number of divisions that would be
		// used, The stack hold low and high values.
		// Need an extra stack entry for the starting point
		int maxstack = ((Integer.numberOfTrailingZeros(
			Integer.highestOneBit(n)) + 1)) * 2;
		int[] stack = new int[maxstack];
		int at = 0;
		
		// The first entry in the stack is the fully sorted list
		stack[at++] = 0;
		stack[at++] = n;
		
		// The second entry is the left side of that sort
		stack[at++] = 0;
		stack[at++] = n >>> 1;
		
		// Perform an in place merge sort starting at the top region
		// If the stack ever gets only a single start/end pair then the
		// sort operation is complete.
		boolean mergeup = false;
		boolean maybemerge = false;
		for (; at >= 4;)
		{
			// Get the stack region before this one
			int befs = stack[at - 4],
				befe = stack[at - 3];
			int befn = befe - befs;
			
			// Get the stack region that is currently being looked at
			int nows = stack[at - 2],
				nowe = stack[at - 1];
			int nown = nowe - nows;
			
			System.err.printf(
				"DEBUG -- @%3d %c%c %3d:%3d (%3d) || %3d:%3d (%3d)%n",
				at, (mergeup ? '^' : ' '), (maybemerge ? 'M' : ' '),
				befs, befe, befn, nows, nowe, nown);
			
			// Maybe merge up?
			if (maybemerge)
			{
				// If the end is at the before end, merge up more
				if (nowe == befe)
					mergeup = true;
				
				// Otherwise switch to the right side
				else
				{
					stack[at - 2] = nowe;
					stack[at - 1] = befe;
				}
				
				// If not merging, continue
				maybemerge = false;
				if (!mergeup)
					continue;
			}
			
			// Perform a merge
			if (mergeup)
			{
				// Get the left side
				int lefs = befs,
					lefe = nows,
					lefn = lefe - lefs;
				
				// And the right side
				int rigs = nows,
					rige = nowe,
					rign = nown;
				
				// The full merge range
				int fuls = lefs,
					fule = rige,
					fuln = lefn + rign;
				
				System.err.printf(
					"DEBUG -- MERGE %3d:%3d (%3d) %3d:%3d (%3d) " +
					"--> %3d:%3d (%3d)%n",
					lefs, lefe, lefn, rigs, rige, rign, fuls, fule, fuln);
				
				// Remove stack entry
				at -= 2;
				
				// Maybe merge again, possibly
				mergeup = false;
				maybemerge = true;
				continue;
			}
			
			// Down to two entries? Sort them, either merge up if this is
			// the trailing end or decend into the second side if this is not.
			// Also a single entry, if odd
			if (nown <= 2)
			{
				// Only sort for two entries, this can happen if the
				// input array is of an odd size
				if (nown == 2)
					;
				
				/*if (true)
					throw new Error("TODO");*/
				
				// Merge up?
				if (befe == nowe)
					mergeup = true;
				
				// Sort the right side
				else
				{
					stack[at - 2] = nowe;
					stack[at - 1] = befe;
				}
			}
			
			// Otherwise, descend the left side
			else
			{
				stack[at++] = nows;
				stack[at++] = nows + (nown >>> 1);
			}
		}
		
		// Return the sorted result
		return rv;
	}
}

