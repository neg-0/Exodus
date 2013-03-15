package com.tidesofwaronline.Exodus.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

	private static final long serialVersionUID = -382704924909844493L;

	public GUI() {
		
		this.setSize(1024, 768);
		this.setTitle("Exodus GUI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public class Panel extends JPanel {

		private static final long serialVersionUID = -6020268014214860857L;
		
		public Panel() {
		}
		
	}
}