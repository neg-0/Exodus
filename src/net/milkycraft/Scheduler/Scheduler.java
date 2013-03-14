package net.milkycraft.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

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

public class Scheduler {

	/**
	 * Create an instance of Schedule Reference main class for plugin Define a
	 * playername for cooldown to affect Define a Time for the cooldown *Since
	 * it deals with bukkit methods. Strickly synchronous.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param player
	 *            the player
	 * @param time
	 *            the time
	 * @return an instanceof Schedule
	 */
	public static Schedule schedule(final Plugin plugin, final String player,
			final Time time) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
				new PlayerTimer(), time.getMinecraftLong());
		return new Schedule(player, time, System.currentTimeMillis());
	}

	/**
	 * Schedules a new general cooldown Uses the schedule passed along for
	 * information.
	 * 
	 * @param schedule
	 *            the schedule
	 */
	public static void scheduleGeneralCooldown(final Schedule schedule) {
		if (schedule.getPlayerName() == null) {
			GeneralTimer.gtask.add(schedule);
		}
	}

	/**
	 * Schedules a new player cooldown Uses the Schedule passed along for
	 * information schedulePlayerCooldown(new schedule(this, "milkywayz",
	 * Time.derp);
	 * 
	 * @param schedule
	 *            the schedule
	 */
	public static void schedulePlayerCooldown(final Schedule schedule) {
		System.out.println("Added " + schedule.getPlayerName() + " to ptask " + schedule.getEndTime() );
		PlayerTimer.ptask.add(schedule);
	}
}
