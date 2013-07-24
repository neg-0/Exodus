package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DungeonBlockInfo {

	public String name();
	public String material();
	public boolean hasInput();
	public boolean hasOutput();
	public String description();
}
