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
public class Time {

	/*
	 * Define the time delays you need here.
	 * This way they are accurate to the tenth of a second
	 */
	public final static Time EXONE = new Time(5.0);
	public final static Time EXTHREE = new Time(15.0);
	public final static Time EXTWO = new Time(15L);


	private double time;
	/**
	 * Instantiates a new time.
	 * 
	 * @param time
	 *            the time
	 */
	public Time(final Double time) {
		this.time = time;
	}
	
	public Time(final long timr) {
		this.time = Double.valueOf(timr);
	}


	/**
	 * Gets the int.
	 * 
	 * @return the int
	 */
	public Integer getInt() {
		return (int) time ;
	}

	/**
	 * 
	 * @return the minecraft long
	 */
	public Long getMinecraftLong() {
		return (long) (time * 20);
	}

	/**
	 * Gets the nano.
	 * 
	 * @return the nano
	 */
	public Long getNano() {
		return (long) (time * 1000000000);
	}

	/**
	 * Gets the real long.
	 * 
	 * @return the real long
	 */
	public Long getRealLong() {
		return (long) (time * 1000);
	}
}
