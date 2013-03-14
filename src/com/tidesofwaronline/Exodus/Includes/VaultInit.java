package com.tidesofwaronline.Exodus.Includes;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.tidesofwaronline.Exodus.Exodus;

public class VaultInit extends Exodus {

	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;

	@SuppressWarnings("unused")
	private boolean setupPermissions() {
		final RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	@SuppressWarnings("unused")
	private boolean setupChat() {
		final RegisteredServiceProvider<Chat> chatProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	@SuppressWarnings("unused")
	private boolean setupEconomy() {
		final RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}
