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
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.GridLayout;

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
		gridBagLayout.columnWidths = new int[] { 234, 0, 0 };
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
		gbl_ControlPanel.rowHeights = new int[] { 224, 0 };
		gbl_ControlPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_ControlPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		ControlPanel.setLayout(gbl_ControlPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		ControlPanel.add(scrollPane, gbc_scrollPane);

		JPanel Player1 = new JPanel();
		scrollPane.setViewportView(Player1);
		Player1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Player 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		gbl_playerAttunements.columnWidths = new int[] { 20, 0, 0, 0 };
		gbl_playerAttunements.rowHeights = new int[] { 41, 0, 0 };
		gbl_playerAttunements.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_playerAttunements.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		playerAttunements.setLayout(gbl_playerAttunements);

		JSpinner playerAttunementWarrior = new JSpinner();
		playerAttunementWarrior.setModel(new SpinnerNumberModel(0, 0, 64, 1));
		playerAttunementWarrior.setToolTipText("Warrior");
		GridBagConstraints gbc_playerAttunementWarrior = new GridBagConstraints();
		gbc_playerAttunementWarrior.insets = new Insets(0, 0, 5, 5);
		gbc_playerAttunementWarrior.gridx = 0;
		gbc_playerAttunementWarrior.gridy = 0;
		playerAttunements.add(playerAttunementWarrior,
				gbc_playerAttunementWarrior);

		JSpinner playerAttunementRogue = new JSpinner();
		playerAttunementRogue.setModel(new SpinnerNumberModel(0, 0, 64, 1));
		playerAttunementRogue.setToolTipText("Rogue");
		GridBagConstraints gbc_playerAttunementRogue = new GridBagConstraints();
		gbc_playerAttunementRogue.insets = new Insets(0, 0, 5, 5);
		gbc_playerAttunementRogue.gridx = 1;
		gbc_playerAttunementRogue.gridy = 0;
		playerAttunements.add(playerAttunementRogue, gbc_playerAttunementRogue);

		JSpinner playerAttunementRanger = new JSpinner();
		playerAttunementRanger.setModel(new SpinnerNumberModel(0, 0, 64, 1));
		playerAttunementRanger.setToolTipText("Ranger");
		GridBagConstraints gbc_playerAttunementRanger = new GridBagConstraints();
		gbc_playerAttunementRanger.insets = new Insets(0, 0, 5, 0);
		gbc_playerAttunementRanger.gridx = 2;
		gbc_playerAttunementRanger.gridy = 0;
		playerAttunements.add(playerAttunementRanger,
				gbc_playerAttunementRanger);
		
				JSpinner playerAttunementCleric = new JSpinner();
				playerAttunementCleric.setModel(new SpinnerNumberModel(0, 0, 64, 1));
				playerAttunementCleric.setToolTipText("Cleric");
				GridBagConstraints gbc_playerAttunementCleric = new GridBagConstraints();
				gbc_playerAttunementCleric.insets = new Insets(0, 0, 0, 5);
				gbc_playerAttunementCleric.gridx = 0;
				gbc_playerAttunementCleric.gridy = 1;
				playerAttunements.add(playerAttunementCleric,
						gbc_playerAttunementCleric);
				
						JSpinner playerAttunementMage = new JSpinner();
						playerAttunementMage.setModel(new SpinnerNumberModel(0, 0, 64, 1));
						playerAttunementMage.setToolTipText("Mage");
						GridBagConstraints gbc_playerAttunementMage = new GridBagConstraints();
						gbc_playerAttunementMage.insets = new Insets(0, 0, 0, 5);
						gbc_playerAttunementMage.gridx = 1;
						gbc_playerAttunementMage.gridy = 1;
						playerAttunements.add(playerAttunementMage, gbc_playerAttunementMage);
						
								JSpinner playerAttunementWarlock = new JSpinner();
								playerAttunementWarlock.setModel(new SpinnerNumberModel(0, 0, 64, 1));
								playerAttunementWarlock.setToolTipText("Warlock");
								GridBagConstraints gbc_playerAttunementWarlock = new GridBagConstraints();
								gbc_playerAttunementWarlock.gridx = 2;
								gbc_playerAttunementWarlock.gridy = 1;
								playerAttunements.add(playerAttunementWarlock,
										gbc_playerAttunementWarlock);

		JPanel View = new JPanel();
		View.setBackground(UIManager.getColor("Viewport.background"));
		View.setForeground(Color.WHITE);
		GridBagConstraints gbc_View = new GridBagConstraints();
		gbc_View.gridheight = 2;
		gbc_View.fill = GridBagConstraints.BOTH;
		gbc_View.gridx = 1;
		gbc_View.gridy = 0;
		frmExodusSimulator.getContentPane().add(View, gbc_View);
		View.setLayout(new GridLayout(1, 1, 16, 16));
		
		JPanel Battlefield = new JPanel();
		Battlefield.setBounds(new Rectangle(0, 0, 500, 500));
		Battlefield.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Battlefield.setBackground(Color.WHITE);
		View.add(Battlefield);
		Battlefield.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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
