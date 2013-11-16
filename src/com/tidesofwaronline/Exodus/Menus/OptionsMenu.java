package com.tidesofwaronline.Exodus.Menus;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class OptionsMenu extends DynamicMenu {

	public OptionsMenu(ExoPlayer exoPlayer) {
		super(exoPlayer.getPlayer(), exoPlayer.getPlayer().getDisplayName() + "' s Options", 4,
		new DynamicMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(final DynamicMenu.OptionClickEvent event) {
				click(event);
			}
		});
	}

	protected static void click(OptionClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}
