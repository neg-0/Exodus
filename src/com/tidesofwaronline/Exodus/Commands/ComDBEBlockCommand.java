package com.tidesofwaronline.Exodus.Commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockCommand;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComDBEBlockCommand {

	public ComDBEBlockCommand(CommandPackage cp) {
		
		Player player = cp.getPlayer();
		ExoPlayer exoPlayer = cp.getExoPlayer();
		
		String[] split = cp.getArgs()[0].split(" ");
		String command = split[0];
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		
		Method[] methods = exoPlayer.getEditingBlock().getClass().getMethods();

		for (Method m : methods) {
			if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(command)) {
				try {
					
					Object o;
					
					if (m.getParameterTypes().length > 0) {
						o = m.invoke(exoPlayer.getEditingBlock(), new Object[] { new CommandPackage(null, player, exoPlayer, arguments) });
					} else {
						o = m.invoke(exoPlayer.getEditingBlock());
					}
															
					if (m.getReturnType() == String.class) {
						player.sendMessage(o.toString());
					} else if (m.getReturnType() == boolean.class && Boolean.parseBoolean(o.toString())) {
						player.sendMessage("Done!");
					} else if (m.getReturnType() == List.class) {
						@SuppressWarnings("unchecked")
						List<String> output = (List<String>) o;
						player.sendMessage(output.toArray(new String[output.size()]));
					}
					
					return;
				} catch (IllegalArgumentException e) {
					player.sendMessage("Invalid number of arguments");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		player.sendMessage("§7Command \"" + command + "\" not valid!");
	}
}
