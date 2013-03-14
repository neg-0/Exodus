package net.milkycraft.Scheduler;

import java.util.HashSet;
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

public class PlayerTimer implements Runnable, Timer {
	public static Set<Schedule> ptask = new HashSet<Schedule>();

	/**
	 * Returns the remaining time on the cooldown / delay Always returns an
	 * integer - About 95% accurate. Negatives no longer possible as they will
	 * return 0 and possible cause of the negative will be fixed
	 * 
	 * @param name
	 *            the name
	 * @param timer
	 *            the timer
	 * @return Seconds
	 */
	public static int getRemainingTime(final String name, final Time timer) {
		int time;
		int secs = 0;
		for (Schedule s : PlayerTimer.ptask) {
			if (s.getPlayerName() == name && s.getDuration() == timer) {
				time = (int) Math.floor((timer.getMinecraftLong() - (s.getTime() - System
						.currentTimeMillis())));
				secs = timer.getInt() - time / 1000;
			}
		}
		// Make sure the remaining time is between 0 and the timer's length
		if (secs <= 0) {
			update();
			return 0;
		}
		return secs;
	}

	/**
	 * Checks if is cooling down.
	 * 
	 * @param player
	 *            the player
	 * @param time
	 *            the time
	 * @return the boolean
	 */
	public static boolean isCoolingDown(final String player, final Time time) {
		for (Schedule s : ptask) {
			if (s.getPlayerName().equals(player)
					&& s.getDuration().getMinecraftLong()
							.equals(time.getMinecraftLong())) {
				return true;
			}
		}
		return false;
	}

	public static void update() {
		for (Schedule s : ptask) {
			if (s.getEndTime() <= System.currentTimeMillis()) {
				ptask.remove(s);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		debug("Update called");
		update();
	}

	public static void debug(final String log) {
		if (DEBUG) {
			System.out.println(log);
		}
	}
}
