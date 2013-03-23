package com.tidesofwaronline.Exodus.Config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;

public class XMLUpdater extends Thread {
	
	File fXmlFile = XMLLoader.getFile();
	long lastModified = fXmlFile.lastModified();

	@Override
	public void run() {
		
		Exodus.logger.info("Exodus XMLUpdater loaded and running.");
		
		try {
			while (true) {
				if (fXmlFile.lastModified() != lastModified) {
					Exodus.logger.info("Exodus XML file modified. Waiting 10 seconds.");
					Thread.sleep(10000);
					Exodus.logger.info("Parsing new XML file.");
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.isOp()) {
							p.sendMessage("Parsing new XML file.");
						}
					}
					XMLLoader.parse();
					lastModified = fXmlFile.lastModified();
				}
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
