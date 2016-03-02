package world;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;

public abstract class Region extends Area{
	protected Color color;
	protected double perimeter;
	protected int edges;
	public Region() {
		super();
		color = Color.black;
		edgeCalculation();
	}
	public Region(Shape s) {
		super(s);
		color = Color.black;
		edgeCalculation();
	}
	public Region(Shape s, Color c) {
		super(s);
		color = c;
		edgeCalculation();
	}
	public Color getColor() {
		return color;
	}
	public double approxSize() {
		return getBounds2D().getWidth() * getBounds2D().getHeight();
	}
	/**
	 * Calculates the number of edges and the perimeter of the Region. Make sure to call every time the Region is edited.
	 * @return perimeter.
	 */
	protected double edgeCalculation() {
//		if(this.isPolygonal()) {
//			
//		}
		double[] point = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double[] oldPoint = {0.0, 0.0};
		int type = 0;
//		double ret = 0.0;
		perimeter = 0.0;
		edges = 0;
		for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
			edges++;
			oldPoint[0] = point[0];
			oldPoint[1] = point[1];
			type = pi.currentSegment(point);
			if(perimeter >= 0.0) {
				switch (type) {
					case PathIterator.SEG_CLOSE:
						System.out.println("Error: close segment type found in this region");
//						perimeter = -1.0;
						break;
					case PathIterator.SEG_CUBICTO:
						System.out.println("Error: cubic segment type found in this region");
						perimeter = -1.0;
						break;
					case PathIterator.SEG_LINETO:
						perimeter += Math.sqrt((point[0] - oldPoint[0]) * (point[0] - oldPoint[0]) + (point[1] - oldPoint[1]) * (point[1] - oldPoint[1]));
						break;
					case PathIterator.SEG_MOVETO:
						System.out.println("Error: moveTo segment type found in this region");
//						perimeter = -1.0;
						break;
					case PathIterator.SEG_QUADTO:
						System.out.println("Error: quadratic segment type found in this region");
						perimeter = -1.0;
						break;
				}
			}
		}
		return perimeter;
	}
	/**
	 * Make sure to call edgeCalculation either every time this is run, or whenever the Region is edited.
	 * @return
	 */
	public int edges() {
		return edges;
	}
	/**
	 * Make sure to call edgeCalculation either every time this is run, or whenever the Region is edited.
	 * @return
	 */
	public double perimeter() {
		return perimeter;
	}
	public void edgePoint(double proportionPerimeter, double[] ret) {
		System.out.println("Finding edge point: perimeter: " + perimeter);
		double[] oldPoint = {0, 0};
		double[] point = {0, 0};
		double length = perimeter * proportionPerimeter;
		for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
			if(pi.currentSegment(point) != PathIterator.SEG_MOVETO) {
				length -= Math.sqrt((oldPoint[0] - point[0]) * (oldPoint[0] - point[0]) + (oldPoint[1] - point[1]) * (oldPoint[1] - point[1]));
				if(length < 0) {
					ret[0] = point[0] - oldPoint[0];
					ret[1] = point[1] - oldPoint[1];
					length += Math.sqrt(ret[0] * ret[0] + ret[1] * ret[1]);
					ret[0] *= length / Math.sqrt(ret[0] * ret[0] + ret[1] * ret[1]);
					ret[1] *= ret[0] / (point[0] - oldPoint[0]);
					ret[0] += oldPoint[0];
					ret[1] += oldPoint[1];
					break;
				}
			}
			oldPoint[0] = point[0];
			oldPoint[1] = point[1];
		}
	}
}
