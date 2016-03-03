package utility;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import main.Main;
import world.Path;
import world.Region;
import world.World;

public class DeformedLine extends Path2D.Double{
	private Random rand;
	
	public DeformedLine(double jaggedness, double curviness, double length, Region region, int tries, long seed) {
		int originalTries = tries;
		rand = new Random(seed);
		double[] start = {0, 0};
		double lengthLeft = length;
		region.edgePoint(rand.nextDouble(), start);
//		System.out.println("Deformed line start point: " + start[0] + ", " + start[1]);
//		this.moveTo(start[0], start[1]);
//		this.lineTo(endX, endY);
		Path2D.Double newPath = new Path2D.Double();
		do {
			tries = originalTries;
			region.edgePoint(rand.nextDouble(), start);
			newPath.reset();
			newPath.moveTo(start[0], start[1]);
			double[] oldPoint = {start[0], start[1]};
			double[] mutPoint = {0.0, 0.0};
	//		for(int i = 0; i < deformations; i++) {
	//			for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
	//				if(pi.currentSegment(point) == PathIterator.SEG_MOVETO) {
	//					newPath.moveTo(point[0], point[1]);
	//				} else {
	//					getMutation(oldPoint, point, jaggedness, area, mutPoint);
	//					newPath.lineTo(mutPoint[0], mutPoint[1]);
	//					newPath.lineTo(point[0], point[1]);
	//				}
	//				oldPoint[0] = point[0];
	//				oldPoint[1] = point[1];
	//			}
	//		}
			double segLength = centerRand() * jaggedness;
			double theta = rand.nextDouble() * Math.PI * 2;
			while(lengthLeft > segLength && tries > 0) {
				mutPoint[0] = oldPoint[0] + Math.cos(theta);
				mutPoint[1] = oldPoint[1] + Math.sin(theta);
				if(region.contains(mutPoint[0], mutPoint[1])
						&&
						!intersectsSegment(newPath, oldPoint[0], oldPoint[1], mutPoint[0], mutPoint[1], false) 
						&&
						!region.intersectsSegment(oldPoint[0], oldPoint[1], mutPoint[0], mutPoint[1], true))
				{
					newPath.lineTo(mutPoint[0], mutPoint[1]);
					lengthLeft -= segLength;
					segLength = centerRand() * jaggedness;
					theta += (rand.nextDouble() - 0.5) * curviness;
					oldPoint[0] = mutPoint[0];
					oldPoint[1] = mutPoint[1];
					tries = originalTries;
				} else {
					tries--;
					System.out.println("DeformedLine creation error. Tries left: " + tries);
					theta = rand.nextDouble() * Math.PI * 2;
				}
			}
		} while (tries == 0);
//		newPath.closePath();
//		this.reset();
		this.append(newPath, false);
//		Rectangle2D r = this.getBounds2D();
//		this.transform(AffineTransform.getTranslateInstance(-r.getCenterX(), -r.getCenterY()));
//		this.transform(AffineTransform.getRotateInstance(rand.nextDouble() * Math.PI * 2));
		if(Main.debug) {
			World.world.add(new Path(newPath, Color.cyan));
		}
	}
	private double zeroCenterRand() {
		double ret = rand.nextDouble() - .5;
//		return ret * ret * ret * 8;
		return ret * ret * ret * 4;
	}
	private double centerRand() {
		double ret = rand.nextDouble();
		return ret * (4 * ret * ret - 6 * ret + 3);
	}
	/**
	 * TODO document
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
	/**
	 * TODO document
	 * @param path
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @return
	 */
	private static boolean intersectsSegment(Path2D.Double path, double ax, double ay, double bx, double by, boolean equality) {
		double sx = bx - ax;
		double sy = by - ay;
		double tx;
		double ty;
		double s;
		double t;
		double[] point = {0, 0};
		double[] oldPoint = {0, 0};
		PathIterator pi = path.getPathIterator(null);
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
