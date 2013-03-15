package com.tidesofwaronline.Exodus.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class GUIWindow {

	private JFrame frmExodusSimulator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIWindow window = new GUIWindow();
					window.frmExodusSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExodusSimulator = new JFrame();
		frmExodusSimulator.setResizable(false);
		frmExodusSimulator.setTitle("Exodus Simulator");
		frmExodusSimulator.setBounds(100, 100, 770, 541);
		frmExodusSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 184, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frmExodusSimulator.getContentPane().setLayout(gridBagLayout);

		JPanel ControlPanel = new JPanel();
		ControlPanel.setBorder(new TitledBorder(null, "Controls",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_ControlPanel = new GridBagConstraints();
		gbc_ControlPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ControlPanel.fill = GridBagConstraints.BOTH;
		gbc_ControlPanel.gridx = 0;
		gbc_ControlPanel.gridy = 0;
		frmExodusSimulator.getContentPane().add(ControlPanel, gbc_ControlPanel);
		final TitledBorder playerLevelSliderBorder = new TitledBorder(null,
				"Player Level", TitledBorder.LEADING, TitledBorder.TOP, null,
				null);
		GridBagLayout gbl_ControlPanel = new GridBagLayout();
		gbl_ControlPanel.columnWidths = new int[] { 234, 0 };
		gbl_ControlPanel.rowHeights = new int[] { 224, 0, 0 };
		gbl_ControlPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_ControlPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		ControlPanel.setLayout(gbl_ControlPanel);

		JPanel Player1 = new JPanel();
		Player1.setBorder(new TitledBorder(null, "Player 1",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_Player1 = new GridBagConstraints();
		gbc_Player1.insets = new Insets(0, 0, 5, 0);
		gbc_Player1.fill = GridBagConstraints.BOTH;
		gbc_Player1.gridx = 0;
		gbc_Player1.gridy = 0;
		ControlPanel.add(Player1, gbc_Player1);
		GridBagLayout gbl_Player1 = new GridBagLayout();
		gbl_Player1.columnWidths = new int[] { 20, 0 };
		gbl_Player1.rowHeights = new int[] { 60, 41, 0 };
		gbl_Player1.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_Player1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		Player1.setLayout(gbl_Player1);

		JPanel playerLevelSlider = new JPanel();
		playerLevelSlider.setBorder(playerLevelSliderBorder);
		GridBagConstraints gbc_playerLevelSlider = new GridBagConstraints();
		gbc_playerLevelSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_playerLevelSlider.anchor = GridBagConstraints.NORTH;
		gbc_playerLevelSlider.insets = new Insets(0, 0, 5, 0);
		gbc_playerLevelSlider.gridx = 0;
		gbc_playerLevelSlider.gridy = 0;
		Player1.add(playerLevelSlider, gbc_playerLevelSlider);

		final JSlider playerLevel = new JSlider();
		playerLevel.setMinorTickSpacing(1);

		playerLevel.setPaintLabels(true);
		playerLevel.setPaintTicks(true);
		playerLevel.setMajorTickSpacing(10);
		playerLevelSlider.add(playerLevel);
		playerLevel.setToolTipText("The Player's level.");
		playerLevel.setValue(1);
		playerLevel.setMaximum(40);

		JPanel playerAttunements = new JPanel();
		playerAttunements.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Player Attunements",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		GridBagConstraints gbc_playerAttunements = new GridBagConstraints();
		gbc_playerAttunements.fill = GridBagConstraints.BOTH;
		gbc_playerAttunements.gridx = 0;
		gbc_playerAttunements.gridy = 1;
		Player1.add(playerAttunements, gbc_playerAttunements);
		GridBagLayout gbl_playerAttunements = new GridBagLayout();
		gbl_playerAttunements.columnWidths = new int[] { 20, 0, 0, 0, 0, 0, 0 };
		gbl_playerAttunements.rowHeights = new int[] { 41, 0 };
		gbl_playerAttunements.columnWeights = new double[] { 1.0, 1.0, 1.0,
				1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_playerAttunements.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		playerAttunements.setLayout(gbl_playerAttunements);

		JSpinner playerAttunementWarrior = new JSpinner();
		playerAttunementWarrior.setToolTipText("Warrior");
		GridBagConstraints gbc_playerAttunementWarrior = new GridBagConstraints();
		gbc_playerAttunementWarrior.insets = new Insets(0, 0, 0, 5);
		gbc_playerAttunementWarrior.gridx = 0;
		gbc_playerAttunementWarrior.gridy = 0;
		playerAttunements.add(playerAttunementWarrior,
				gbc_playerAttunementWarrior);

		JSpinner playerAttunementRogue = new JSpinner();
		playerAttunementRogue.setToolTipText("Rogue");
		GridBagConstraints gbc_playerAttunementRogue = new GridBagConstraints();
		gbc_playerAttunementRogue.insets = new Insets(0, 0, 0, 5);
		gbc_playerAttunementRogue.gridx = 1;
		gbc_playerAttunementRogue.gridy = 0;
		playerAttunements.add(playerAttunementRogue, gbc_playerAttunementRogue);

		JSpinner playerAttunementRanger = new JSpinner();
		playerAttunementRanger.setToolTipText("Ranger");
		GridBagConstraints gbc_playerAttunementRanger = new GridBagConstraints();
		gbc_playerAttunementRanger.insets = new Insets(0, 0, 0, 5);
		gbc_playerAttunementRanger.gridx = 2;
		gbc_playerAttunementRanger.gridy = 0;
		playerAttunements.add(playerAttunementRanger,
				gbc_playerAttunementRanger);

		JSpinner playerAttunementCleric = new JSpinner();
		playerAttunementCleric.setToolTipText("Cleric");
		GridBagConstraints gbc_playerAttunementCleric = new GridBagConstraints();
		gbc_playerAttunementCleric.insets = new Insets(0, 0, 0, 5);
		gbc_playerAttunementCleric.gridx = 3;
		gbc_playerAttunementCleric.gridy = 0;
		playerAttunements.add(playerAttunementCleric,
				gbc_playerAttunementCleric);

		JSpinner playerAttunementMage = new JSpinner();
		playerAttunementMage.setToolTipText("Mage");
		GridBagConstraints gbc_playerAttunementMage = new GridBagConstraints();
		gbc_playerAttunementMage.insets = new Insets(0, 0, 0, 5);
		gbc_playerAttunementMage.gridx = 4;
		gbc_playerAttunementMage.gridy = 0;
		playerAttunements.add(playerAttunementMage, gbc_playerAttunementMage);

		JSpinner playerAttunementWarlock = new JSpinner();
		playerAttunementWarlock.setToolTipText("Warlock");
		GridBagConstraints gbc_playerAttunementWarlock = new GridBagConstraints();
		gbc_playerAttunementWarlock.gridx = 5;
		gbc_playerAttunementWarlock.gridy = 0;
		playerAttunements.add(playerAttunementWarlock,
				gbc_playerAttunementWarlock);

		JPanel Player2 = new JPanel();
		Player2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Player 2",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		GridBagConstraints gbc_Player2 = new GridBagConstraints();
		gbc_Player2.fill = GridBagConstraints.BOTH;
		gbc_Player2.gridx = 0;
		gbc_Player2.gridy = 1;
		ControlPanel.add(Player2, gbc_Player2);
		GridBagLayout gbl_Player2 = new GridBagLayout();
		gbl_Player2.columnWidths = new int[] { 20 };
		gbl_Player2.rowHeights = new int[] { 60, 41 };
		gbl_Player2.columnWeights = new double[] { 0.0 };
		gbl_Player2.rowWeights = new double[] { 0.0, 0.0 };
		Player2.setLayout(gbl_Player2);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.anchor = GridBagConstraints.NORTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		Player2.add(panel_2, gbc_panel_2);

		JSlider slider = new JSlider();
		slider.setValue(1);
		slider.setToolTipText("The Player's level.");
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setMaximum(40);
		slider.setMajorTickSpacing(10);
		panel_2.add(slider);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Player Attunements",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		Player2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 20, 0, 0, 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 41 };
		gbl_panel_3.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
		gbl_panel_3.rowWeights = new double[] { 0.0 };
		panel_3.setLayout(gbl_panel_3);

		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("Warrior");
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 0;
		gbc_spinner.gridy = 0;
		panel_3.add(spinner, gbc_spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setToolTipText("Rogue");
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 0;
		panel_3.add(spinner_1, gbc_spinner_1);

		JSpinner spinner_2 = new JSpinner();
		spinner_2.setToolTipText("Ranger");
		GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
		gbc_spinner_2.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_2.gridx = 2;
		gbc_spinner_2.gridy = 0;
		panel_3.add(spinner_2, gbc_spinner_2);

		JSpinner spinner_3 = new JSpinner();
		spinner_3.setToolTipText("Cleric");
		GridBagConstraints gbc_spinner_3 = new GridBagConstraints();
		gbc_spinner_3.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_3.gridx = 3;
		gbc_spinner_3.gridy = 0;
		panel_3.add(spinner_3, gbc_spinner_3);

		JSpinner spinner_4 = new JSpinner();
		spinner_4.setToolTipText("Mage");
		GridBagConstraints gbc_spinner_4 = new GridBagConstraints();
		gbc_spinner_4.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_4.gridx = 4;
		gbc_spinner_4.gridy = 0;
		panel_3.add(spinner_4, gbc_spinner_4);

		JSpinner spinner_5 = new JSpinner();
		spinner_5.setToolTipText("Warlock");
		GridBagConstraints gbc_spinner_5 = new GridBagConstraints();
		gbc_spinner_5.gridx = 5;
		gbc_spinner_5.gridy = 0;
		panel_3.add(spinner_5, gbc_spinner_5);

		JPanel View = new JPanel();
		View.setBackground(UIManager.getColor("Viewport.background"));
		View.setForeground(Color.WHITE);
		GridBagConstraints gbc_View = new GridBagConstraints();
		gbc_View.gridheight = 2;
		gbc_View.fill = GridBagConstraints.BOTH;
		gbc_View.gridx = 1;
		gbc_View.gridy = 0;
		frmExodusSimulator.getContentPane().add(View, gbc_View);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frmExodusSimulator.getContentPane().add(panel, gbc_panel);

		JButton btnGo = new JButton("Go!");
		panel.add(btnGo);
	}

}
