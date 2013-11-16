package com.tidesofwaronline.Exodus.Menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.tidesofwaronline.Exodus.Exodus;

public class DynamicMenu implements Listener {
	
	private final String title;
	private final int size;
	private OptionClickEventHandler handler;
	private Player owner;
	private DynamicIcon[] dynamicIcons;

	public Inventory inventory;
	
	public DynamicMenu(Player owner, String title, int rows, OptionClickEventHandler handler) {
		this.title = title;
		this.size = rows*9;
		this.handler = handler;
		this.owner = owner;
		Exodus.getPlugin().getServer().getPluginManager().registerEvents(this, Exodus.getPlugin());
		generate();
	}
	
	public DynamicIcon setIcon(final int position, final DynamicIcon itemIcon) {
		dynamicIcons[position] = itemIcon;
		return itemIcon;
	}
	
	public DynamicIcon getIcon(final int position) {
		return dynamicIcons[position];
	}
	
	public Inventory generate() {
		inventory = Bukkit.createInventory(owner, size, title);
		for (int i = 0; i < this.size; i++) {
			if (dynamicIcons[i] != null)
			inventory.setItem(i, dynamicIcons[i].getIcon());
		}
		return inventory;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(final InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(title)) { // If the click is in the stats menu
			event.setCancelled(true); //Cancel the pickup event
			final int slot = event.getRawSlot(); // Get the clicked slot
			if (slot >= 0 && slot < size && dynamicIcons[slot] != null) { // If player clicked an item
				final OptionClickEvent e = new OptionClickEvent(
						(Player) event.getWhoClicked(), slot, dynamicIcons[slot].getName(), getIcon(slot));
				handler.onOptionClick(e);
			}
		}
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
	}
	
	
	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private final Player player;
		private final int position;
		private final String name;
		private final DynamicIcon icon;

		public OptionClickEvent(final Player player, final int position,
				final String name, final DynamicIcon icon) {
			this.player = player;
			this.position = position;
			this.name = name;
			this.icon = icon;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}
		
		public DynamicIcon getIcon() {
			return icon;
		}

		public Inventory getInventory() {
			return inventory;
		}
	}
}

