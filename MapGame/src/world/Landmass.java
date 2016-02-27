package world;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

import main.Main;
import utility.FractalBlob;

public class Landmass extends Region{
	private static final Color landmassColor = Color.orange;
	public Landmass(Area a) {
		super(a, landmassColor);
	}
	public Landmass(double x, double y, double scale, double jaggedness, int deformations, int polySides, long seed, boolean removeHoles, double smoothRadius) {
		super(generateLandmass(x, y, scale, jaggedness, deformations, polySides, seed));
		if(removeHoles) {
			removeHoles();
		}
		smooth(smoothRadius);
	}
	private static Area generateLandmass(double x, double y, double scale, double jaggedness, int deformations, int polySides, long seed) {
		FractalBlob ret = new FractalBlob(deformations, jaggedness, polySides, seed);
		ret.transform(AffineTransform.getScaleInstance(scale, scale));
		ret.transform(AffineTransform.getTranslateInstance(x, y));
		return ret;
	}
	private void removeHoles() {
		PathIterator pi = this.getPathIterator(null);
		Path2D.Double largest = new Path2D.Double();
		Path2D.Double temp = new Path2D.Double();
		double[] coords = {0,0};
		int segType = 0;
		for(; !pi.isDone(); pi.next()) {
			segType = pi.currentSegment(coords);
			if(segType != PathIterator.SEG_LINETO) {
				if(Main.debug) {
					World.world.add(new Path(temp, new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256))));
//					World.world.add(new Bounds(temp.getBounds2D()));
				}
				if(largest.getBounds2D().getWidth() * largest.getBounds2D().getHeight() < temp.getBounds2D().getWidth() * temp.getBounds2D().getHeight()) {
					largest = temp;
				}
				temp = new Path2D.Double();
				temp.moveTo(coords[0], coords[1]);
			} else {
				temp.lineTo(coords[0], coords[1]);
			}
		}
		this.reset();				//I believe it is more efficient to reset the area before adding largest, because the edges will be identical, but it won't have to do any overlap math.
		this.add(new Area(largest));
	}
	private void smooth(double radius) {
		if(radius > 0) {
			Area smoother = new Area();
			double[] old = {0,0};
			double[] current = {0,0};
			Area stroke;
			for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
				if(pi.currentSegment(current) != PathIterator.SEG_MOVETO) {
					System.out.println("Smoothing (" + current[0] + ", " + current[1] + ")");
					smoother.add(new Area(new Ellipse2D.Double(current[0] - radius, current[1] - radius, 2 * radius, 2 * radius)));
					stroke = new Area(new Rectangle2D.Double(0, -radius, Math.sqrt((current[0] - old[0]) * (current[0] - old[0]) + (current[1] - old[1]) * (current[1] - old[1])), 2 * radius));
					stroke.transform(AffineTransform.getRotateInstance(current[0] - old[0] , current[1] - old[1] ));
					stroke.transform(AffineTransform.getTranslateInstance(old[0], old[1]));
					smoother.add(stroke);
				}
				old[0] = current[0];
				old[1] = current[1];
			}
			add(smoother);
			removeHoles();
		}
	}

}
