package ui;

import java.awt.Color;

import world.Region;

public class Lens {
	public final Class<? extends Region> class_;
	public final Color color;
	public final boolean fill;
	
	public Lens(Class<? extends Region> class_, Color color, boolean fill) {
		this.class_ = class_;
		this.color = color;
		this.fill = fill;
	} 
}
