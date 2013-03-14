package net.milkycraft.Scheduler;

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

public class Schedule {

	private final Time dur;
	private long endtime;
	private final String playername;
	private final long time;
	private long lien;

	/**
	 * Instantiates a new schedule.
	 * 
	 * @param playername
	 *            the playername
	 * @param dur
	 *            the dur
	 * @param time
	 *            the time
	 * @param taskid
	 *            the taskid
	 * @param async
	 *            the async
	 */
	public Schedule(final String playername, final Time dur, final Long time) {
		this.playername = playername;
		this.time = time;
		this.dur = dur;
		this.lien = 25L; // Default to 50L
		setEndTime(time + dur.getRealLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.milkycraft.Scheduler.ScheduledTask#getDuration()
	 */
	public Time getDuration() {
		return dur;
	}

	/**
	 * Gets the time when the schedule ends.
	 * 
	 * @return the end time
	 */
	public long getEndTime() {
		long realend = endtime - this.lien;
		return realend;
	}

	/**
	 * Gets the player name.
	 * 
	 * @return the player name
	 */
	public String getPlayerName() {
		return playername;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the time when the schedule ends.
	 * 
	 * @param endtime
	 *            the new end time
	 */
	public void setEndTime(final Long endtime) {
		this.endtime = endtime;
	}
	
	public void setLienency(long i) {
		if(lien > 1000L || lien < 20L) {
			return;
		}
		this.lien = i;
	}
}
