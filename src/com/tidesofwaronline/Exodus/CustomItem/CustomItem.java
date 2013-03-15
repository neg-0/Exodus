package com.tidesofwaronline.Exodus.CustomItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tidesofwaronline.Exodus.CustomEnchantment.CustomEnchantment;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler.Tier;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler.Type;
import com.tidesofwaronline.Exodus.Player.Attunement;
import com.tidesofwaronline.Exodus.Util.MessageUtil;

@SerializableAs("CustomItem")
public class CustomItem extends ItemStack implements ConfigurationSerializable {

	public static class CustomItemBuilder {
		private int ID = 0;
		private String name = "Custom Item";
		private ChatColor color = ChatColor.WHITE;
		private Material material = Material.WOOD_SWORD;
		private Tier tier = Tier.Common;
		private Type type = Type.Sword;
		private int damageMin = 1;
		private int damageMax = 1;
		private ArrayList<CustomEnchantment> el = new ArrayList<CustomEnchantment>();
		private int levelreq = 0;
		private Attunement attunement = Attunement.WARRIOR;
		private int attunereq = 0;
		private boolean glow = false;
		private ArrayList<String> lore = new ArrayList<String>();

		CustomItemBuilder() {
		}

		public CustomItem build() {
			return new CustomItem(ID, name, color, material, tier, type,
					damageMin, damageMax, el, levelreq, attunement,
					attunereq, glow, lore);
		}

		public CustomItemBuilder withAttunement(Attunement attunement) {
			this.attunement = attunement;
			return this;
		}

		public CustomItemBuilder withAttuneReq(int attunereq) {
			this.attunereq = attunereq;
			return this;
		}

		public CustomItemBuilder withColor(ChatColor color) {
			this.color = color;
			return this;
		}

		public CustomItemBuilder withDamageMax(int damagemax) {
			this.damageMax = damagemax;
			return this;
		}

		public CustomItemBuilder withDamageMin(int damagemin) {
			this.damageMin = damagemin;
			return this;
		}

		public CustomItemBuilder withEnchantment(CustomEnchantment ce) {
			this.el.add(new CustomEnchantment(ce, 1));
			return this;
		}

		public CustomItemBuilder withEnchantment(CustomEnchantment ce, int l) {
			this.el.add(new CustomEnchantment(ce, l));
			return this;
		}

		public CustomItemBuilder withEnchantment(Enchantment enchantment) {
			this.el.add(new CustomEnchantment(enchantment, 1));
			return this;
		}
		
		public CustomItemBuilder withEnchantment(Enchantment enchantment,
				int level) {
			this.el.add(new CustomEnchantment(enchantment, level));
			return this;
		}

		public CustomItemBuilder withEnchantments(Collection<CustomEnchantment> ce) {
			this.el.addAll(ce);
			return this;
		}

		public CustomItemBuilder withID(int ID) {
			this.ID = ID;
			return this;
		}

		public CustomItemBuilder withLevelReq(int levelreq) {
			this.levelreq = levelreq;
			return this;
		}

		public CustomItemBuilder withLore(List<String> lore) {
			this.lore.addAll(lore);
			return this;
		}

		public CustomItemBuilder withLore(String lore) {
			if (lore.length() > 20 && lore.length() > this.name.length()) {
				withLore(MessageUtil.wrapText(lore, 20));
				return this;
			}
			this.lore.add(lore);
			return this;
		}

		public CustomItemBuilder withMaterial(Material material) {
			this.material = material;
			return this;
		}

		public CustomItemBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public CustomItemBuilder withTier(Tier tier) {
			this.tier = tier;
			return this;
		}

		public CustomItemBuilder withType(Type type) {
			this.type = type;
			return this;
		}
		
		public CustomItemBuilder withGlow() {
			this.glow = true;
			return this;
		}
	}

	static {
		ConfigurationSerialization
				.registerClass(CustomItem.class, "CustomItem");
	}

	public static CustomItemBuilder customItemBuilder() {
		return new CustomItemBuilder();

	}

	private int ID = 0;
	private ItemMeta meta;	
	private String name = "Custom Item";
	private ChatColor color = ChatColor.WHITE;
	private Material material = Material.WOOD_SWORD;
	private Tier tier = Tier.Common;
	private Type type = Type.Sword;
	private int damageMin = 1;
	private int damageMax = 1;
	private List<CustomEnchantment> el = new ArrayList<CustomEnchantment>();
	private int levelreq = 0;
	private Attunement attunement = Attunement.WARRIOR;
	private int attunereq = 0;
	private boolean glow = false;
	private List<String> lore = new ArrayList<String>();

	List<String> displayWindow; //The printed lore

	Random random = new Random();

	public CustomItem(CustomItem i) {
		this.ID = i.ID;
		this.meta = i.meta;
		this.name = i.name;
		this.color = i.color;
		this.tier = i.tier;
		this.type = i.type;
		this.damageMin = i.damageMin;
		this.damageMax = i.damageMax;
		this.el.addAll(i.el);
		this.levelreq = i.levelreq;
		this.attunement = i.attunement;
		this.attunereq = i.attunereq;
		this.glow = i.glow;
		this.lore.addAll(i.lore);

		build();
		CustomItemHandler.register(this);
	}

	public CustomItem(int i) {
		super(i);
		this.material = Material.getMaterial(i);
		CustomItemHandler.register(this);
	}

	public CustomItem(int ID, String name, ChatColor color, Material material,
			Tier tier, Type type, int damagemin, int damagemax,
			List<CustomEnchantment> el, int levelreq,
			Attunement attunement, int attunereq, boolean glow, List<String> lore) {
		super(material);

		if (ID != 0) {
			this.ID = ID;
		} else {
			this.ID = this.hashCode();
		}

		this.material = material;
		this.name = name;
		this.color = color;
		this.meta = this.getItemMeta();
		this.setTier(tier);
		this.type = type;
		this.setDamageMin(damagemin);
		this.setDamageMax(damagemax);
		this.getEl().addAll(el);
		this.setLevelreq(levelreq);
		this.setAttunement(attunement);
		this.setAttunereq(attunereq);
		this.glow = glow;
		this.setLore(lore);
		
		CustomItemHandler.register(this);

		this.getDamage(); //Set random damage

		build();
		//updateLore();
	}

	public CustomItem(ItemStack item) {
		super(item);

		if (CustomItemHandler.isCustomItem(item)) {
			List<String> list = item.getItemMeta().getLore();

			if (!list.get(0).equalsIgnoreCase("CUSTOM_ITEM")) {
				return;
			}

			this.meta = this.getItemMeta();
			this.material = item.getType();
			this.name = MessageUtil
					.removeColourCode(this.meta.getDisplayName());

			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);

				if (s.contains("ID:")) {
					this.ID = Integer.valueOf(s.replace("ID:", "")).intValue();
				}

				if (s.contains("Color:")) {
					this.color = ChatColor.valueOf(s.replace("Color:", ""));
				}

				if (s.contains("Tier:")) {
					this.tier = Enum
							.valueOf(Tier.class, s.replace("Tier:", ""));
				}

				if (s.contains("Type:")) {
					this.type = Enum
							.valueOf(Type.class, s.replace("Type:", ""));
				}

				if (s.contains("dMin:")) {
					this.damageMin = Integer.valueOf(s.replace("dMin:", ""))
							.intValue();
				}

				if (s.contains("dMax:")) {
					this.damageMax = Integer.valueOf(s.replace("dMax:", ""))
							.intValue();
				}

				if (s.contains("Ench:")) {
					this.addEnchantment(CustomEnchantment.valueOf(s.replace("Ench:", "")));
				}

				if (s.contains("Lvl:")) {
					this.levelreq = Integer.valueOf(s.replace("Lvl:", ""))
							.intValue();
				}

				if (s.contains("Att:")) {
					this.attunement = Enum.valueOf(Attunement.class,
							s.replace("Att:", ""));
				}

				if (s.contains("Atr:")) {
					this.attunereq = Integer.valueOf(s.replace("Atr:", ""))
							.intValue();
				}
				
				if (s.contains("Glow:true")) {
					this.glow = true;
				}

				if (s.contains("Lore:")) {
					this.lore.add(s.replace("Lore:", ""));
				}
			}

			build();
			CustomItemHandler.register(this);
		}
	}

	@SuppressWarnings("unchecked")
	public CustomItem(Map<String, Object> map) {
		super(Material.STONE);
		this.ID = (Integer) map.get("ID");
		this.name = (String) map.get("Name");
		this.color = ChatColor.valueOf(map.get("Color").toString());
		this.material = Material.valueOf(map.get("Material").toString());
		this.tier = Tier.valueOf(map.get("Tier").toString());
		this.type = Type.valueOf(map.get("Type").toString());
		this.damageMin = (Integer) map.get("damageMin");
		this.damageMax = (Integer) map.get("damageMax");
		int i = 0;
		while (map.containsKey("Enchantments." + i)) {
			this.el.add(CustomEnchantment.valueOf(map.get("Enchantments." + i).toString()));
			i++;
		}
		this.levelreq = (Integer) map.get("LevelReq");
		this.attunement = Attunement.valueOf(map.get("Attunement").toString());
		this.attunereq = (Integer) map.get("AttunementLevel");
		this.glow = (Boolean) map.get("Glow");
		this.lore = (ArrayList<String>) map.get("Lore");

		build();
		CustomItemHandler.register(this);

	}

	public CustomItem(Material mat, int i) {
		super(mat, i);
		this.material = mat;
		CustomItemHandler.register(this);
	}

	public void addEnchantment(CustomEnchantment e) {
		el.add(e);
	}

	public void addEnchantments(List<CustomEnchantment> el) {
		this.getEl().addAll(el);
	}

	public void build() {
		this.setType(this.material);
		
		for (CustomEnchantment cei : this.getEl()) {
			if (cei.getEnchantment() != null) {
				this.addUnsafeEnchantment(Enchantment.getById(cei.getEnchantment().getId()), cei.getLevel());
			}
			
			if (cei.getCustomEnchantment() != null) {
				//this.addUnsafeEnchantment(Enchantment., level)
			}
		}
		
		if (this.getTypeId() != 0) {
			this.meta = this.getItemMeta();
			if (this.color != null) {
				this.meta.setDisplayName(this.color + this.name);
			} else {
				this.meta.setDisplayName(this.name);
			}
			this.setItemMeta(this.meta);
		}
		
		writeStats();
	}

	public Attunement getAttunement() {
		return attunement;
	}

	public int getAttunereq() {
		return attunereq;
	}

	public final ChatColor getColor() {
		return color;
	}

	public int getDamage() {
		return Math
				.round(getDamageMin()
						+ random.nextInt(this.getDamageMax()
								- this.getDamageMin() + 1));
	}

	public int getDamageMax() {
		return damageMax;
	}

	public int getDamageMin() {
		return damageMin;
	}

	public List<String> getDisplayWindow() {

		List<String> printLore = new ArrayList<String>();

		printLore.add(ChatColor.WHITE + this.getTier().toString() + " - "
				+ this.getItemType());
		printLore.add(ChatColor.WHITE + "" + this.getDamageMin() + " - "
				+ this.getDamageMax() + " Damage");

		if (!this.getEl().isEmpty()) {
			for (CustomEnchantment cei : this.getEl()) {
				printLore.add(ChatColor.DARK_GREEN + cei.display());
			}
		}

		if (this.getLevelreq() != 0) {
			printLore.add("Requires level " + this.getLevelreq());
		}

		if (this.getAttunereq() != 0) {
			printLore.add("Requires " + this.getAttunement() + " "
					+ this.getAttunereq());
		}

		if (!this.getLore().isEmpty()) {
			List<String> setlore = new ArrayList<String>(this.getLore());
			if (this.getLore().size() == 1) {
				setlore.set(0, "\"" + this.getLore().get(0) + "\"");
			} else {
				int last = this.getLore().size() - 1;
				setlore.set(0, "\"" + this.getLore().get(0));
				setlore.set(last, this.getLore().get(last) + "\"");
			}
			setlore = MessageUtil.prependList(setlore,
					ChatColor.YELLOW.toString());
			printLore.addAll(setlore);
		}

		return printLore;
	}

	public List<CustomEnchantment> getEl() {
		return el;
	}

	public Integer getID() {
		if (this.ID == 0) {
			this.ID = this.hashCode();
		}
		return this.ID;
	}

	public Type getItemType() {
		return type;
	}

	public int getLevelreq() {
		return levelreq;
	}

	public List<String> getLore() {
		return lore;
	}

	public Tier getTier() {
		return tier;
	}

	//Called when damaging a entity.
	public void onHit(Player player, LivingEntity entity) {
		for (CustomEnchantment ce : el) {
			if (ce.getCustomEnchantment() != null)
			ce.getCustomEnchantment().onHit(player, entity);
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", this.ID);
		map.put("Durability", this.getDurability());
		map.put("Name", this.name);
		map.put("Color", this.color.name());
		map.put("Material", this.material.name());
		map.put("Tier", this.getTier().toString());
		map.put("Type", this.type.toString());
		map.put("damageMin", this.getDamageMin());
		map.put("damageMax", this.getDamageMax());
		for (int i = 0; i < this.getEl().size(); i++) {
			map.put("Enchantments." + i, this.getEl().get(i).toString());	
		}
		map.put("LevelReq", this.getLevelreq());
		map.put("Attunement", this.getAttunement().toString());
		map.put("AttunementLevel", this.getAttunereq());
		map.put("Glow", this.isGlow());
		map.put("Lore", this.getLore());
		return map;
	}

	public void setAttunement(Attunement attunement) {
		this.attunement = attunement;
	}

	public void setAttunereq(int attunereq) {
		this.attunereq = attunereq;
	}

	public final void setColor(ChatColor color) {
		this.color = color;
		this.meta.setDisplayName(this.color + this.name);
		this.setItemMeta(this.meta);
	}

	public void setDamageMax(int damageMax) {
		this.damageMax = damageMax;
	}

	public void setDamageMin(int damageMin) {
		this.damageMin = damageMin;
	}

	public void setEl(List<CustomEnchantment> el) {
		this.el.clear();
		this.el.addAll(el);
	}

	public void setItemType(Type type) {
		this.type = type;
	}

	public void setLevelreq(int levelreq) {
		this.levelreq = levelreq;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public void setMaxDamage(int max) {
		this.setDamageMax(max);
	}

	public void setMinDamage(int min) {
		this.setDamageMin(min);
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	private void writeStats() {

		List<String> lore = new ArrayList<String>();

		lore.add("CUSTOM_ITEM");
		lore.add("ID:" + this.getID());
		lore.add("Color:" + getColor().name());
		lore.add("Tier:" + getTier().toString());
		lore.add("Type:" + type.toString());
		lore.add("dMin:" + getDamageMin());
		lore.add("dMax:" + getDamageMax());
		for (CustomEnchantment ei : getEl()) {
			lore.add("Ench:" + ei.toString());
		}
		lore.add("Lvl:" + String.valueOf(getLevelreq()));
		lore.add("Att:" + getAttunement().toString());
		lore.add("Atr:" + String.valueOf(getAttunereq()));
		lore.add("Glow:" + this.isGlow());
		for (String s : this.getLore()) {
			lore.add("Lore:" + s);
		}

		this.meta.setLore(lore);
		this.setItemMeta(this.meta);
	}

	public final boolean isGlow() {
		return glow;
	}

	public final void setGlow(boolean glow) {
		this.glow = glow;
	}
}