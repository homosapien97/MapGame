package ui;

import java.awt.Color;
import java.util.ArrayList;

import world.City;
import world.Landmass;

public class RenderMode extends ArrayList<Lens>{
	public static final RenderMode landmasses = new RenderMode();
	public static final RenderMode cities = new RenderMode();
	static {
		//cities:
		cities.add(new Lens(Landmass.class, Color.orange, true));
		cities.add(new Lens(City.class, null, true));
		
		//landmasses:
		landmasses.add(new Lens(Landmass.class, null, true));
	}

}