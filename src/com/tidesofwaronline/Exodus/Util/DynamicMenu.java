package com.tidesofwaronline.Exodus.Util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DynamicMenu implements Listener {
	
	private final String title;
	private final int size;
	private OptionClickEventHandler handler;
	private Player owner;
	private DynamicIcon[] dynamicIcons;

	public Inventory inventory;
	
	public DynamicMenu(Player owner, String title, int rows,
			OptionClickEventHandler handler, Plugin plugin) {
		this.title = title;
		this.size = rows*9;
		this.handler = handler;
		this.owner = owner;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		generate();
	}
	
	public DynamicIcon setIcon(final int position, final ItemStack icon) {
		DynamicIcon newicon = new DynamicIcon(icon);
		dynamicIcons[position] = newicon;
		return newicon;
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
						(Player) event.getWhoClicked(), slot, dynamicIcons[slot].getName());
				handler.onOptionClick(e);
			}
		}
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
	}
	
	public class DynamicIcon {
		
		String name;
		List<String> lore;
		ItemStack icon;
		
		public DynamicIcon(ItemStack icon) {
			this.icon = icon;
			this.name = icon.getType().name();
			update();
		}
		
		public DynamicIcon setName(String name) {
			this.name = name;
			return this;
		}
		
		public DynamicIcon setLore(List<String> lore) {
			this.lore = lore;
			return this;
		}
		
		public ItemStack getIcon() {
			return icon;
		}
		
		public String getName() {
			return name;
		}
		
		public DynamicIcon update() {
			icon.getItemMeta().setDisplayName(this.name);
			icon.getItemMeta().setLore(this.lore);
			return this;
		}
		
	}
	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private final Player player;
		private final int position;
		private final String name;

		public OptionClickEvent(final Player player, final int position,
				final String name) {
			this.player = player;
			this.position = position;
			this.name = name;
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

		public Inventory getInventory() {
			return inventory;
		}
	}
}

