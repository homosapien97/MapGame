package world;

import java.util.ArrayList;
import java.util.HashMap;

public class World extends HashMap<Class<? extends Region>, ArrayList<Region> >{
	public static World world = new World();
	public boolean add(Region r) {
		Class<? extends Region> c = r.getClass();
		ArrayList<Region> l;
		if(this.get(c) == null) {
			l = new ArrayList<Region>();
			boolean ret = l.add(r);
			this.put(c, l);
			return ret;
		} else {
			return this.get(c).add(r);
		}
	}
}
