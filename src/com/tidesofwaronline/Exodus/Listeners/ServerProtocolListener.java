/**
 *
 * This software is part of the MaterialAPI
 *
 * This api allows plugin developers to create on a easy way custom
 * items with a custom id and recipes depending on them.
 *
 * MaterialAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * MaterialAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MaterialAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.tidesofwaronline.Exodus.Listeners;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExoPlayer.ExoGameMode;

/**
 * Hooking into the ProtocolLib to hide enchantments and the custom id int the
 * lore.
 */
public class ServerProtocolListener extends PacketAdapter {

	Exodus exodus;

	public ServerProtocolListener(Exodus plugin) {
		super(plugin, ConnectionSide.SERVER_SIDE, new Integer[] {
				Integer.valueOf(103), Integer.valueOf(104), Integer.valueOf(53) });
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
		
		

		this.exodus = plugin;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void onPacketSending(PacketEvent e) {
		PacketContainer packet = e.getPacket();

		try {
			switch (e.getPacketID()) {
			case 103: {
				if (ExoPlayer.getExodusPlayer(e.getPlayer()).isFilter()) {
					removeCustomId(packet.getItemModifier().read(0));
				}
				break;
			}

			case 104: {
				ItemStack[] elements = packet.getItemArrayModifier().read(0);

				for (int i = 0; i < elements.length; i++) {
					if (elements[i] != null) {
						if (ExoPlayer.getExodusPlayer(e.getPlayer()).isFilter()) {
							this.removeCustomId(elements[i]);
						}
					}
				}

				break;
			}
			
			case 53: {
				StructureModifier<Integer> sm = e.getPacket().getIntegers();
				final Location loc = new Location(e.getPlayer().getWorld(), sm.read(0), sm.read(1), sm.read(2));

				if (DungeonBlock.getDungeonBlock(loc) != null) {
					final ExoPlayer exoPlayer = ExoPlayer.getExodusPlayer(e.getPlayer());
					final DungeonBlock db = DungeonBlock.getDungeonBlock(loc);
					if (exoPlayer.getExoGameMode() == ExoGameMode.DBEDITOR) {
						if (db.isValid()) {
							sm.write(3, db.getMaterial().getId());
						} else {
							sm.write(3, 0);
						}
					} else {
						sm.write(3, 0);
					}
				}
				break;
			}
			}
		} catch (FieldAccessException ex) {
			this.getPlugin().getLogger()
					.log(Level.SEVERE, "Couldn't access field.", ex);
		}
	}

	/**
	 * Removing the lore from the item which contains the custom id.
	 * 
	 * @param item The itemstack.
	 */
	private void removeCustomId(ItemStack item) {
		if (item == null) {
			return;
		}
		
		boolean glow = false;
		

		ItemMeta m = item.getItemMeta();
		List<String> lore = m.getLore();

		if (m.hasEnchants()) {
			for (Enchantment ench : m.getEnchants().keySet()) {
				m.removeEnchant(ench);
			}

		}

		if (m.hasLore()) {
			for (int i = 0; i < lore.size(); i++) {
				if (lore.get(i).equalsIgnoreCase("Glow:true")) {
					glow = true;
				}
			}
			for (int i = 0; i < lore.size(); i++) {
				String t = lore.get(i);

				if (t.contains("CUSTOM_ITEM")) {
					
					CustomItem ci = CustomItemHandler.lookup(CustomItemHandler
							.getCustomId(item));
					if (ci == null) {
						Exodus.logger.info("Custom Item is null");
						return;
					}
					
					m.setLore(ci.getDisplayWindow());

				}
				
				
			}
		}		
		item.setItemMeta(m);
		
		item = MinecraftReflection.getBukkitItemStack(item);
		
		if (glow) {
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			compound.put(NbtFactory.ofList("ench"));
		}
	}
}