package world;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import test.FractalBlob;

public class Landmass extends Region{
	private static final Color landmassColor = Color.orange;
	public Landmass(Area a) {
		super(a, landmassColor);
	}
	public Landmass(double x, double y, double scale, int deformations, boolean removeHoles) {
		super(generateLandmass(x, y, scale, deformations, 1.0));
		if(removeHoles) removeHoles();
	}
	public Landmass(double x, double y, double scale, int deformations, double jaggedness, boolean removeHoles) {
		super(generateLandmass(x, y, scale, deformations, jaggedness));
		if(removeHoles) removeHoles();
	}
	public Landmass(double x, double y, double scale, int deformations, double jaggedness, int polySides,long seed, boolean removeHoles) {
		super(generateLandmass(x, y, scale, deformations, jaggedness, polySides, seed));
		if(removeHoles) removeHoles();
	}
	private static Area generateLandmass(double x, double y, double scale, int deformations, double jaggedness) {
		FractalBlob ret = new FractalBlob(deformations, jaggedness);
		ret.transform(AffineTransform.getScaleInstance(scale, scale));
		ret.transform(AffineTransform.getTranslateInstance(x, y));
		return ret;
	}
	private static Area generateLandmass(double x, double y, double scale, int deformations, double jaggedness, int polySides, long seed) {
		FractalBlob ret = new FractalBlob(deformations, jaggedness, polySides, seed);
		ret.transform(AffineTransform.getScaleInstance(scale, scale));
		ret.transform(AffineTransform.getTranslateInstance(x, y));
		return ret;
	}
	private void removeHoles() {	//this doesn't really work as intended. Or at all, really.
		PathIterator pi = this.getPathIterator(null);
		Path2D.Double largest = new Path2D.Double();
		Path2D.Double temp = new Path2D.Double();
		double[] coords = {0,0};
		int segType = 0;
		for(; !pi.isDone(); pi.next()) {
			segType = pi.currentSegment(coords);
			if(segType == PathIterator.SEG_MOVETO) {
				System.out.println("Loop found at (" + coords[0] + "," + coords[1] + ")");
				if(largest.getBounds2D().getWidth() < temp.getBounds2D().getWidth()) {	//if width is larger, than height should be too
					System.out.println("Old width: " + largest.getBounds2D().getWidth());
					System.out.println("New width: " + temp.getBounds2D().getWidth());
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

}
