package com.tidesofwaronline.Exodus.Config;

import java.io.File;

public class XMLUpdater extends Thread {
	
	File fXmlFile = new File(
			"E:\\Documents\\Tides of War\\Exodus\\Exodus\\Exodus.xml");
	long lastModified = fXmlFile.lastModified();

	@Override
	public void run() {
		
		try {
			while (true) {
				System.out.println("Checking for updated XML");
				if (fXmlFile.lastModified() != lastModified) {
					System.out.println("File Modified");
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
