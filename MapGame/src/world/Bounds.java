package world;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Bounds extends Region{
	private static final Color boundsColor = Color.green;
	
	
	public Bounds(Rectangle2D r) {
		super(r, boundsColor);
	}
}
