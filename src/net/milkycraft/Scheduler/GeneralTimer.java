package net.milkycraft.Scheduler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// TODO: Auto-generated Javadoc
/*	Copyright (c) 2012, Nick Porillo milkywayz@mail.com
 *
 *	Permission to use, copy, modify, and/or distribute this software for any purpose 
 *  with or without fee is hereby granted, provided that the above copyright notice 
 *  and this permission notice appear in all copies.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE 
 *	INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE 
 *  FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 *	OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, 
 *  ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

public class GeneralTimer implements Runnable, Timer {

	public static Set<Schedule> gtask = new HashSet<Schedule>();
	public static Iterator<Schedule> gitr = gtask.iterator();
	
	public static void update() {
		while (gitr.hasNext()) {
			final Schedule s = gitr.next();
			if (s.getEndTime() <= System.currentTimeMillis()) {
				gitr.remove();
			}
		}
	}

	/**
	 * Gets the long left.
	 * 
	 * @param timer
	 *            the timer
	 * @return the long left
	 */
	public static long getLongLeft(final Time timer) {
		long time;
		long secs = 0L;
		while (gitr.hasNext()) {
			final Schedule s = gitr.next();
			if (s.getDuration().equals(timer)) {
				time = timer.getMinecraftLong()
						- (s.getTime() - System.currentTimeMillis());
				secs = timer.getInt() - time / 1000;
			}
		}
		return secs;
	}

	/**
	 * Gets the remaining time.
	 * 
	 * @param timer
	 *            the timer
	 * @return the remaining time
	 */
	public static int getRemainingTime(final Time timer) {
		int time;
		int secs = 0;
		final Iterator<Schedule> itr = gtask.iterator();
		while (itr.hasNext()) {
			final Schedule s = itr.next();
			if (s.getDuration().equals(timer)) {
				time = (int) (timer.getMinecraftLong() - (s.getTime() - System
						.currentTimeMillis()));
				secs = timer.getInt() - time / 1000;
			}
		}
		// Make sure the remaining time is between 0 and the timer's length
		if (secs < 0 || secs > timer.getInt()) {
			update();
			return 0;
		}
		return secs;
	}


	/**
	 * Checks if is cooling down.
	 * 
	 * @param time
	 *            the time
	 * @return the boolean
	 */
	public static boolean isCoolingDown(final Time time) {
		while (gitr.hasNext()) {
			final Schedule s = gitr.next();
			if (s.getDuration().equals(time)) {
				return true;
			}
		}
		return false;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		update();
	}

}
