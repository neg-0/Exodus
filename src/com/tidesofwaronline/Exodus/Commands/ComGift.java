package com.tidesofwaronline.Exodus.Commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.GiftManager;
import com.tidesofwaronline.Exodus.Util.ItemUtil;

public class ComGift extends Command {

	public ComGift(CommandPackage comPackage) {
		String[] args = comPackage.getArgs();
		Player player = comPackage.getPlayer();
		
		if (args.length > 0) {
			String command = args[0];
			args = Arrays.copyOfRange(args, 1, args.length);
			
			if (command.equalsIgnoreCase("give") && player.isOp()) {
				if (args.length > 0) {
					Player giveTo = Bukkit.getPlayer(args[0]);
					if (giveTo != null) {
						
						ItemStack itemStack = ItemUtil.getItemFromString(args[1]);
						
						if (itemStack != null) {
							if (args.length > 2) {
								try {
									int amount = Integer.parseInt(args[2]);
									itemStack.setAmount(amount);
								} catch (NumberFormatException e) {
									player.sendMessage("Argument " + args[2] + " must be a number!");
								}
							}
							GiftManager.addGift(giveTo.getName(), itemStack);
							if (itemStack instanceof CustomItem) {
								player.sendMessage("Giving " + giveTo.getName() + " " + itemStack.getAmount() + " " + ((CustomItem) itemStack).getName());
							} else {
								player.sendMessage("Giving " + giveTo.getName() + " " + itemStack.getAmount() + " " + itemStack.getType().name());
							}
						
						} else {
							player.sendMessage("Unknown Item.");
						}
					}
					
				} else {
					player.sendMessage("Syntax: /gift give <Player> <Item> (Quantity)");
				}
			} else if (command.equalsIgnoreCase("claim")) {
				GiftManager.claimGifts(player);
			} else if (command.equalsIgnoreCase("list")) {
				player.sendMessage(args[0] + " has " + GiftManager.getGifts(args[0]).size() + " gifts waiting:");
				for (ItemStack i : GiftManager.getGifts(args[0])) {
					if (i instanceof CustomItem) {
						player.sendMessage(((CustomItem) i).getName());
					} else {
						player.sendMessage(i.getType().name());
					}
				}
			}
		} else {
			if (GiftManager.hasGifts(player)) {
				player.sendMessage("You have some gifts available! Type '/gift claim' to receive them!");
			} else {
				player.sendMessage("You have no gifts to claim!");
			}
		}
	}
}
