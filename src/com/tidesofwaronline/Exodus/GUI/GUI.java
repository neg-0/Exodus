package com.tidesofwaronline.Exodus.GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame {

	private static final long serialVersionUID = -382704924909844493L;

	public GUI() {

		this.setSize(1024, 768);
		this.setTitle("Exodus GUI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		this.add(new Panel());
	}

	public class Panel extends JPanel {

		private static final long serialVersionUID = -6020268014214860857L;

		public Panel() {
			this.setSize(128, 768);
			this.setLocation(0, 0);
			this.setVisible(true);

			this.add(new JLabel("Control Panel"));
		}

	}
}