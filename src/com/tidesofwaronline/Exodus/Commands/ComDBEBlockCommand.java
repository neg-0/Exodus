package com.tidesofwaronline.Exodus.Commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlockCommand;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComDBEBlockCommand {

	public ComDBEBlockCommand(ExoPlayer exop, String message) {
		
		Player p = exop.getPlayer();
		
		String[] split = message.split(" ");
		String command = split[0];
		String[] arguments = Arrays.copyOfRange(split, 1, split.length);
		
		Method[] methods = exop.getEditingBlock().getClass().getMethods();

		for (Method m : methods) {
			if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(command)) {
				try {
					
					Object o;
					
					if (m.getParameterTypes().length > 0) {
						o = m.invoke(exop.getEditingBlock(), new Object[] { arguments });
					} else {
						o = m.invoke(exop.getEditingBlock());
					}
										
					if (m.getReturnType() == String.class) {
						p.sendMessage(o.toString());
					} else if (m.getReturnType() == boolean.class && Boolean.parseBoolean(o.toString())) {
						p.sendMessage("Done!");
					}
					
					return;
				} catch (IllegalArgumentException e) {
					p.sendMessage("Invalid number of arguments");
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
		
		if (command.equalsIgnoreCase("exit")) {
			p.sendMessage("No longer editing " + exop.getEditingBlock().toString());
			exop.setEditingBlock(null);
		} else {
			p.sendMessage("�7Command \"" + command + "\" not valid!");
		}
	}

}
