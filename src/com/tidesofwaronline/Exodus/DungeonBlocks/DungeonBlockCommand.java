package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface DungeonBlockCommand {
	
	String syntax();
	String example();
	String description();
	
}
