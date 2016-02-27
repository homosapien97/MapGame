package ui;

import java.awt.Color;
import java.util.ArrayList;

import world.Bounds;
import world.City;
import world.Landmass;
import world.Path;
import world.River;

public class RenderMode extends ArrayList<Lens>{
	public static final RenderMode landmasses = new RenderMode();
	public static final RenderMode cities = new RenderMode();
	public static final RenderMode debug = new RenderMode();
	public static final RenderMode bodiesOfWater = new RenderMode();
	static {
		//cities:
		cities.add(new Lens(Landmass.class, Color.orange, true));
		cities.add(new Lens(River.class, Color.blue, true));
		cities.add(new Lens(City.class, null, true));
		
		//landmasses:
		landmasses.add(new Lens(Landmass.class, null, true));
		
		//debug:
		debug.add(new Lens(Landmass.class, null, false));
		debug.add(new Lens(City.class, null, false));
		debug.add(new Lens(Bounds.class, null, false));
		debug.add(new Lens(Path.class, null, false));
		debug.add(new Lens(City.class, null, false));
	}

}