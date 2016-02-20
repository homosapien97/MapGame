package world;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class City extends Region{
	private static final Color cityColor = Color.red;
	private double population;
	private double density;
	
	
	public City(double x, double y, double r, Color c) {
		super(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r), c);
	}
	public City(Area a, Color c) {
		super(a, c);
	}
}
