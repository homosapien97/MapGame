package world;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;

public class Region extends Area{
	private Color color;
	public Region() {
		super();
		color = Color.black;
	}
	public Region(Shape s) {
		super(s);
		color = Color.black;
	}
	public Region(Shape s, Color c) {
		super(s);
		color = c;
	}
	public Color getColor() {
		return color;
	}
}
