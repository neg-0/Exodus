package com.tidesofwaronline.Exodus.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.map.MinecraftFont;

public class MessageUtil {

	public static void sendRawMessage(CommandSender sender, String message) {
		sender.sendMessage(message);
	}

	public static void sendColouredMessage(CommandSender sender, String message) {
		sender.sendMessage(addColour(message));
	}

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(format(message));
	}

	/**
	 * Logger
	 * 
	 * @param message Object being Logged.
	 */
	public static void log(Object message) {
		System.out.println(message);
	}

	/**
	 * Logger
	 * 
	 * @param message Object being Logged.
	 */
	public static void log(Level level, Object message) {
		Logger.getLogger("Minecraft").log(level, message.toString());
	}

	/**
	 * Logger
	 * 
	 * @param message Object being Logged.
	 */
	public static void logFormatted(Object message) {
		System.out.println(format(message.toString()));
	}

	/**
	 * Colour Formatting
	 * 
	 * @param string String being Formatted.
	 * @return Coloured String.
	 */
	public static String addColour(String string) {
		string = string.replace("`e", "").replace("`r", "\u00A7c")
				.replace("`R", "\u00A74").replace("`y", "\u00A7e")
				.replace("`Y", "\u00A76").replace("`g", "\u00A7a")
				.replace("`G", "\u00A72").replace("`a", "\u00A7b")
				.replace("`A", "\u00A73").replace("`b", "\u00A79")
				.replace("`B", "\u00A71").replace("`p", "\u00A7d")
				.replace("`P", "\u00A75").replace("`k", "\u00A70")
				.replace("`s", "\u00A77").replace("`S", "\u00A78")
				.replace("`w", "\u00A7f");

		string = string.replace("<r>", "").replace("<black>", "\u00A70")
				.replace("<navy>", "\u00A71").replace("<green>", "\u00A72")
				.replace("<teal>", "\u00A73").replace("<red>", "\u00A74")
				.replace("<purple>", "\u00A75").replace("<gold>", "\u00A76")
				.replace("<silver>", "\u00A77").replace("<gray>", "\u00A78")
				.replace("<blue>", "\u00A79").replace("<lime>", "\u00A7a")
				.replace("<aqua>", "\u00A7b").replace("<rose>", "\u00A7c")
				.replace("<pink>", "\u00A7d").replace("<yellow>", "\u00A7e")
				.replace("<white>", "\u00A7f");

		string = string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "\u00A7$2");

		string = string.replaceAll("(&([a-fk-orA-FK-OR0-9]))", "\u00A7$2");

		return string.replace("&&", "&");
	}

	/**
	 * Colour Removal
	 * 
	 * @param string String Colour is being removed from.
	 * @return DeColoured String.
	 */
	public static String removeColour(String string) {
		string = addColour(string);

		return string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "§z");
	}
	
	/**
	 * Colour Removal
	 * 
	 * @param string String Colour is being removed from.
	 * @return String with no Colour Code.
	 */
	public static String removeColourCode(String string) {
		string = addColour(string);

		return string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "");
	}

	/**
	 * Plugin Formatting
	 * 
	 * @param message Message being appended.
	 * @return Message appended to [MChat]
	 */
	public static String format(String message) {
		return addColour("&2[&4M&8Chat&2] &6" + message);
	}

	/**
	 * Plugin Formatting
	 * 
	 * @param message Message being appended.
	 * @return Message appended to name
	 */
	public static String format(String name, String message) {
		return addColour("&2[&4" + name + "&2] &6" + message);
	}

	public static List<String> wrapText (String text, int len)
	{
	  // return empty list for null text
	  if (text == null)
		  return Arrays.asList(new String());

	  // return text if len is zero or less
	  if (len <= 0) {
		  return Arrays.asList(new String[] {text});
	  }

	  // return text if less than length
	  if (text.length() <= len)
		  return Arrays.asList(new String[] {text});

	  char [] chars = text.toCharArray();
	  List<String> lines = new ArrayList<String>();
	  StringBuffer line = new StringBuffer();
	  StringBuffer word = new StringBuffer();

	  for (int i = 0; i < chars.length; i++) {
	    word.append(chars[i]);

	    if (chars[i] == ' ') {
	      if ((line.length() + word.length()) > len) {
	        lines.add(line.toString());
	        line.delete(0, line.length());
	      }

	      line.append(word);
	      word.delete(0, word.length());
	    }
	  }

	  // handle any extra chars in current word
	  if (word.length() > 0) {
	    if ((line.length() + word.length()) > len) {
	      lines.add(line.toString());
	      line.delete(0, line.length());
	    }
	    line.append(word);
	  }

	  // handle extra line
	  if (line.length() > 0) {
	    lines.add(line.toString());
	  }

	  return lines;
	}
	
	public static int getStringWidth(String str) {
		int width = 0;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (c == ' ') {
				width += 4;
			} else {
				width += MinecraftFont.Font.getChar(c).getWidth() + 1;
			}
		}
		return width;
	}

	public static int getStringWidthBold(String str) {
		int width = 0;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (c == ' ') {
				width += 4;
			} else {
				width += MinecraftFont.Font.getChar(c).getWidth() + 2;
			}
		}
		return width;
	}
	
	public static List<String> prependList(List<String> list, String append) {
		List<String> newlist = new ArrayList<String>();
		for (String s : list) {
			newlist.add(append + s);
		}
		return newlist;
	}
}