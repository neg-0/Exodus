package net.milkycraft.Sample;

import net.milkycraft.Scheduler.PlayerTimer;
import net.milkycraft.Scheduler.Schedule;
import net.milkycraft.Scheduler.Scheduler;
import net.milkycraft.Scheduler.Time;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

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

public class SamplePlugin extends JavaPlugin implements Listener {

	/*
	 * This is just a sample plugin to get you familar with the methods
	 * Permissions, and most other stuff is just for example.
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {

	}

	/**
	 * Format loc.
	 * 
	 * @param loc
	 *            the loc
	 * @return the string
	 */
	public String formatLoc(Location loc) {
		return loc.getBlockX() + ", " + loc.getBlockY() + ", "
				+ loc.getBlockZ();
	}


	/*
	 * The time the player has to wait to say ass
	 */
	/**
	 * On chat.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.getMessage().equals("test")) {
			if (PlayerTimer.isCoolingDown(e.getPlayer().getName(), Time.EXTWO)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED
								+ "You need to wait "
								+ ChatColor.YELLOW
								+ PlayerTimer.getRemainingTime(e.getPlayer()
										.getName(), Time.EXTWO) + ChatColor.RED
								+ " seconds to say that!");
			} else {
				Schedule s = Scheduler.schedule(this, e.getPlayer().getName(), Time.EXTWO);
				Scheduler.schedulePlayerCooldown(s);
			}
		}
	}

	/**
	 * On place.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission("sampleplugin.bypass.playertimer")) {
			return;
		}
		if (e.getBlock().getType() == Material.STONE) {
			if (PlayerTimer.isCoolingDown(e.getPlayer().getName(), Time.EXONE)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED
								+ "You need to wait "
								+ ChatColor.YELLOW
								+ PlayerTimer.getRemainingTime(e.getPlayer()
										.getName(), Time.EXONE) + ChatColor.RED
								+ " seconds to place that stone");
			} else {
				Scheduler.schedulePlayerCooldown(Scheduler.schedule(this, e
						.getPlayer().getName(), Time.EXONE));
			}
		} else if (e.getBlock().getType() == Material.GLASS) {
			if (PlayerTimer.isCoolingDown(e.getPlayer().getName(), Time.EXTWO)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED
								+ "You need to wait "
								+ ChatColor.YELLOW
								+ PlayerTimer.getRemainingTime(e.getPlayer()
										.getName(), Time.EXTWO) + ChatColor.RED
								+ " seconds to place that glass");
			} else {
				Scheduler.schedulePlayerCooldown(Scheduler.schedule(this, e
						.getPlayer().getName(), Time.EXTWO));
			}
		} else {
			if (PlayerTimer
					.isCoolingDown(e.getPlayer().getName(), Time.EXTHREE)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED
								+ "You need to wait "
								+ ChatColor.YELLOW
								+ PlayerTimer.getRemainingTime(e.getPlayer()
										.getName(), Time.EXTHREE)
								+ ChatColor.RED + " seconds to place that");
			} else {
				Scheduler.schedulePlayerCooldown(Scheduler.schedule(this, e
						.getPlayer().getName(), Time.EXTHREE));
			}
		}
	}
}
