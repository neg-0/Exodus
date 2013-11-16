package com.tidesofwaronline.Exodus.Menus;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class StatsMenu extends DynamicMenu {
	
	static DynamicIcon[][] icons = new DynamicIcon[6][9];
	ExoPlayer exoPlayer;
	String title;
	
	public StatsMenu(ExoPlayer exoPlayer) {
		super(exoPlayer.getPlayer(), exoPlayer.getPlayer().getDisplayName() + "' s Stats", 4,
		new DynamicMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(final DynamicMenu.OptionClickEvent event) {
				click(event);
			}
		});
		
		super.setIcon(2, new ItemIcon(ItemIcon.IconType.HEAD, exoPlayer));
		super.setIcon(20, new ItemIcon(ItemIcon.IconType.CHEST, exoPlayer));
		super.setIcon(29, new ItemIcon(ItemIcon.IconType.LEGS, exoPlayer));
		super.setIcon(38, new ItemIcon(ItemIcon.IconType.FEET, exoPlayer));
		
		super.setIcon(19, new ItemIcon(ItemIcon.IconType.MELEE, exoPlayer));
		super.setIcon(21, new ItemIcon(ItemIcon.IconType.UNIQUE, exoPlayer));
		
		super.setIcon(28, new ItemIcon(ItemIcon.IconType.TRINKET, exoPlayer));
		super.setIcon(37, new ItemIcon(ItemIcon.IconType.RING1, exoPlayer));
		super.setIcon(39, new ItemIcon(ItemIcon.IconType.RING2, exoPlayer));
		
		super.setIcon(23, new MenuIcon(MenuIcon.IconType.PLAYER_INFO, exoPlayer));
		super.setIcon(24, new MenuIcon(MenuIcon.IconType.SPELLBOOK, exoPlayer));
		super.setIcon(25, new MenuIcon(MenuIcon.IconType.HEARTHSTONE, exoPlayer));
		super.setIcon(32, new MenuIcon(MenuIcon.IconType.REPUTATION, exoPlayer));
		super.setIcon(33, new MenuIcon(MenuIcon.IconType.OPTIONS, exoPlayer));
	}

	protected static void click(OptionClickEvent event) {
		
		if (event.getIcon() instanceof MenuIcon) {
			switch(((MenuIcon)event.getIcon()).getIconType()) {
			case PLAYER_INFO: {
				break;
			}
			case SPELLBOOK: {
				break;
			}
			case HEARTHSTONE: {
				break;
			}
			case REPUTATION: {
				break;
			}
			case OPTIONS: {
				break;
			}
			}
		}
		
	}
}
