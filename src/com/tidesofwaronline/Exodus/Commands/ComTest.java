package com.tidesofwaronline.Exodus.Commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;

public class ComTest extends Command {

	public ComTest(CommandPackage comPackage) {
		
		Player player = comPackage.getPlayer();
		
		try {
		 FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
		 ObjectOutputStream save = new ObjectOutputStream(saveFile);
		
		Collection<DungeonBlock> list = DungeonBlock.getDungeonBlocks(player.getPlayer().getWorld());
		
		save.writeObject(list);
		save.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
