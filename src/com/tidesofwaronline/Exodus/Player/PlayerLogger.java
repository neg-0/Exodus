package com.tidesofwaronline.Exodus.Player;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Database.mysql.MySQL;


public class PlayerLogger implements Listener{
	
	Exodus exodus;
	MySQL mysql;
	Connection c;
	
	Queue<Event> queue = new LinkedList<Event>();
	
	public PlayerLogger(Exodus exodus) {
		this.exodus = exodus;
		mysql = new MySQL(exodus, "host.name", "port", "database", "user", "pass");
	}
	
	void openConnection() {
	    c = mysql.openConnection();
	}
	
	@EventHandler
	void logBlockBreak(BlockBreakEvent e) {
		queue.add(new Event(e.getPlayer().getName(), String.valueOf(e.getBlock().getTypeId())));
	}
	
	void UpdateEntry(Event e) {
		
	}
	
	final class Event {
		
		final String key, value;
		
		Event(final String key, final String value) {
			this.key = key;
			this.value = value;
		}
	}
	
	class DatabaseUpdater implements Runnable {
		
		boolean active = true;

		@Override
		public void run() {
			while(active) {
				queue.remove();
			}
		}
		
	}
}
