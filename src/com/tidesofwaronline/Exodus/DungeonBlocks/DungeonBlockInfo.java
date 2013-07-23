package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface DungeonBlockInfo {

	public String name();
	public String material();
	public boolean hasInput();
	public boolean hasOutput();
	public String[] settings();
	public String description();
}
