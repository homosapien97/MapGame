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
	/**
	 * TODO document. Use at own risk if region is not polygonal.
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @return
	 */
	public boolean intersectsSegment(double ax, double ay, double bx, double by, boolean equality) {
		//credit: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
		//I'm too lazy to figure out the most efficient way to do this by myself
		double sx = bx - ax;
		double sy = by - ay;
		double tx;
		double ty;
		double s;
		double t;
		double[] point = {0, 0};
		double[] oldPoint = {0, 0};
		PathIterator pi = this.getPathIterator(null);
		pi.currentSegment(oldPoint);
		pi.next();
		for(; !pi.isDone(); pi.next()) {
			pi.currentSegment(point);
			tx = point[0] - oldPoint[0];
			ty = point[1] - oldPoint[1];
			s = (sx * (ay - oldPoint[1]) - sy * (ax - oldPoint[0])) / (-tx * sy + ty * sx);
			t = (tx * (ay - oldPoint[1]) - ty * (ax - oldPoint[0])) / (-tx * sy + ty * sx);
			if(equality && (s >= 0 && s <= 1 && t >= 0 && t <= 1) || !equality && (s > 0 && s < 1 && t > 0 && t < 1)) return true;	//WARNING: NOT TRUE INTERSECTION. IF JUST TOUCHING, WILL RETURN FALSE.
			oldPoint[0] = point[0];
			oldPoint[1] = point[1];
		}
		return false;
	}
	public boolean intersectsSegment(double ax, double ay, double bx, double by, double tolerance) {
		double sx = bx - ax;
		double sy = by - ay;
		double tx;
		double ty;
		double s;
		double t;
		double[] point = {0, 0};
		double[] oldPoint = {0, 0};
		PathIterator pi = this.getPathIterator(null);
		pi.currentSegment(oldPoint);
		pi.next();
		for(; !pi.isDone(); pi.next()) {
			pi.currentSegment(point);
			tx = point[0] - oldPoint[0];
			ty = point[1] - oldPoint[1];
			s = (sx * (ay - oldPoint[1]) - sy * (ax - oldPoint[0])) / (-tx * sy + ty * sx);
			t = (tx * (ay - oldPoint[1]) - ty * (ax - oldPoint[0])) / (-tx * sy + ty * sx);
			if(s > -tolerance && s < 1 + tolerance && t > -tolerance && t < 1 + tolerance) return true;	//WARNING: NOT TRUE INTERSECTION. IF JUST TOUCHING, WILL RETURN FALSE.
			oldPoint[0] = point[0];
			oldPoint[1] = point[1];
		}
		return false;
	}
}
