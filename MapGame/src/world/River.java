package world;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import utility.DeformedLine;

public class River extends Region{
	private static final Color riverColor = Color.blue;
	Landmass landmass;
	DeformedLine line;
	
//	public River(double jaggedness, int deformations, long seed, double startX, double startY, double endX, double endY, Landmass landmass, double radius) {
//		super(new Area(), riverColor);
//		line = new DeformedLine(jaggedness, deformations, seed, startX, startY, endX, endY, landmass);
//		this.landmass = landmass;
//		this.smooth(radius);
//	}
//	public River(double jaggedness, int deformations, long seed, Random rand, Landmass landmass, double radius) {
//		super(new Area(), riverColor);
//		double[] start = {0, 0};
//		double[] end = {0, 0};
//		landmass.randomInteriorPoint(rand, start);
//		landmass.edgePoint(rand.nextDouble(), end);
//		line = new DeformedLine(jaggedness, deformations, seed, start[0], start[1], end[0], end[1], landmass);
//		this.landmass = landmass;
//		this.smooth(radius);
//	}
	public River(double jaggedness, double curviness, double length, double radius, Landmass landmass, int tries, long seed) {
		super(new Area(), riverColor);
		this.line = new DeformedLine(jaggedness, curviness, length, landmass, tries, seed);
		this.landmass = landmass;
		this.smooth(radius);
	}
	private void smooth(double radius) {
		if(radius > 0) {
			System.out.println("Smoothing");
			Area smoother = new Area();
			double[] old = {0,0};
			double[] current = {0,0};
			Area stroke;
			for(PathIterator pi = line.getPathIterator(null); !pi.isDone(); pi.next()) {
				if(pi.currentSegment(current) != PathIterator.SEG_MOVETO) {
//					System.out.println("Smoothing (" + current[0] + ", " + current[1] + ")");
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
		}
	}
}
