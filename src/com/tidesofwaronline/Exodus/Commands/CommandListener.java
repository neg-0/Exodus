package com.tidesofwaronline.Exodus.Commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class CommandListener implements CommandExecutor {

	private final Exodus plugin;
	CommandPackage comPackage;

	public CommandListener(final Exodus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, String[] args) {
		
		Player player = null;
		ExoPlayer exoPlayer = null;
		
		if (sender instanceof Player) {
			player = (Player) sender;
			exoPlayer = ExoPlayer.getExodusPlayer(player);
		}
		
		if (command.getName().equalsIgnoreCase("stats")) {
			exoPlayer.openStatsMenu();
		} else if (command.getName().equalsIgnoreCase("exo")) {
			if (player.hasPermission("exodus.admin") || player.isOp()) {
				
				String subCommand = null;
								
				if (args.length > 0) {
					subCommand = args[0];
					args = Arrays.copyOfRange(args, 1, args.length);
				}
				
				comPackage = new CommandPackage(plugin, player, exoPlayer, args);
				
				if (subCommand == null) {
					new ComHelp(comPackage);
					return true;
				}
				
				try {
					Class<?> clazz = Class.forName(this.getClass().getPackage().getName() + ".Com" + WordUtils.capitalize(subCommand));
					Constructor<?> con = clazz.getConstructor(CommandPackage.class);
					con.newInstance(comPackage);
					return true;
				} catch (ClassNotFoundException e) {
					player.sendMessage("Command not found: " + subCommand);
					return true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else {
				player.sendMessage("You do not have permission for this command!");
			}
		} else { 
			comPackage = new CommandPackage(plugin, player, exoPlayer, args);
			if (command.getName().equalsIgnoreCase("party")) {
				new ComParty(comPackage);
			} else if (command.getName().equalsIgnoreCase("guild")) {
				new ComGuild(comPackage);
			} else if (command.getName().equalsIgnoreCase("dbe")) {
				new ComDBE(comPackage);
			} else if (command.getName().equalsIgnoreCase("gift")) {
				new ComGift(comPackage);
			}
		}
		return true;
	}
}
