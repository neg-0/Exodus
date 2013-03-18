package com.tidesofwaronline.Exodus.Config;

import java.io.File;

import com.tidesofwaronline.Exodus.Exodus;

public class XMLUpdater extends Thread {
	
	File fXmlFile = new File(
			"plugins/Exodus/Exodus.xml");
	long lastModified = fXmlFile.lastModified();

	@Override
	public void run() {
		
		try {
			while (true) {
				if (fXmlFile.lastModified() != lastModified) {
					Exodus.logger.info("Exodus XML file modified. Reloading and Reparsing.");
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
