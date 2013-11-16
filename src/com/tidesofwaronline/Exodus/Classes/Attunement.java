package com.tidesofwaronline.Exodus.Classes;

import java.util.ArrayList;
import java.util.List;

import com.tidesofwaronline.Exodus.Abilities.Ability;

public abstract class Attunement {
	
	List<Ability> activeAbilities = new ArrayList<Ability>();
	List<Ability> passiveAbilities = new ArrayList<Ability>();
}
