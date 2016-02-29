package utility;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class DeformedLine extends Path2D.Double{
	private Random rand;

	public DeformedLine(double jaggedness, int deformations, long seed) {
		rand = new Random(seed);
		this.moveTo(0, 0);
		this.lineTo(1, 0);
		Path2D.Double newPath = new Path2D.Double();
		int lineType;
		double[] oldPoint = {0, 0};
		double[] mutPoint = {0, 0};
		double[] point = {0, 0};
		for(int i = 0; i < deformations; i++) {
			for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
				lineType = pi.currentSegment(point);
				if(lineType == PathIterator.SEG_MOVETO) {
					newPath.moveTo(point[0], point[1]);
				} else {
					getMutation(oldPoint, point, jaggedness , mutPoint);
					newPath.lineTo(mutPoint[0], mutPoint[1]);
					newPath.lineTo(point[0], point[1]);
				}
				oldPoint[0] = point[0];
				oldPoint[1] = point[1];
			}
		}
//		newPath.closePath();
		this.reset();
		this.append(newPath, false);
		Rectangle2D r = this.getBounds2D();
		this.transform(AffineTransform.getTranslateInstance(-r.getCenterX(), -r.getCenterY()));
		this.transform(AffineTransform.getRotateInstance(rand.nextDouble() * Math.PI * 2));
	}
	
	public DeformedLine(double jaggedness, int deformations, long seed, double startX, double startY, double endX, double endY, Area area) {
		rand = new Random(seed);
		this.moveTo(startX, startY);
		this.lineTo(endX, endY);
		Path2D.Double newPath = new Path2D.Double();
		double[] oldPoint = {startX, startY};
		double[] mutPoint = {startX, startY};
		double[] point = {startX, startY};
		for(int i = 0; i < deformations; i++) {
			for(PathIterator pi = this.getPathIterator(null); !pi.isDone(); pi.next()) {
				if(pi.currentSegment(point) == PathIterator.SEG_MOVETO) {
					newPath.moveTo(point[0], point[1]);
				} else {
					do {
						getMutation(oldPoint, point, jaggedness, area, mutPoint);
					} while(!area.contains(mutPoint[0], mutPoint[1]));
					newPath.lineTo(mutPoint[0], mutPoint[1]);
					newPath.lineTo(point[0], point[1]);
				}
				oldPoint[0] = point[0];
				oldPoint[1] = point[1];
			}
		}
//		newPath.closePath();
		this.reset();
		this.append(newPath, false);
		Rectangle2D r = this.getBounds2D();
		this.transform(AffineTransform.getTranslateInstance(-r.getCenterX(), -r.getCenterY()));
		this.transform(AffineTransform.getRotateInstance(rand.nextDouble() * Math.PI * 2));
	}
	
	private void getMutation(double[] a, double[] b, double jaggedness, double[] ret) {
//		double[] ret = new double[] {0,0};
		double dx = b[0] - a[0];
		double dy = b[1] - a[1];
		if(dx == 0) {
			ret[0] = 0;
//			ret[1] = dy * Math.random();
//			ret[1] = dy * rand.nextDouble();
			ret[1] = dy * centerRand();
//			ret[1] = dy / 2;
		} else {
//			ret[0] = dx * Math.random();
//			ret[0] = dx * rand.nextDouble();
			ret[0] = dx * centerRand();
//			ret[0] = dx / 2;
			ret[1] = ret[0] * dy/dx;
		}
		
//		dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * Math.random();
		if(dy == 0) {
//			dy = dx * Math.random() / 2 * jaggedness;
//			dy = dx * rand.nextDouble() / 2 * jaggedness;
//			dy = dx * (rand.nextDouble() - .5) * jaggedness;
			dy = dx * zeroCenterRand() * jaggedness;
			dx = 0;
		} else {
			double minv = -dx/dy;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * Math.random() / 2 * jaggedness;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * rand.nextDouble() / 2 * jaggedness;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * (rand.nextDouble() - .5) * jaggedness;
			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * zeroCenterRand() * jaggedness;
			dy = minv * dx;
		}
		ret[0] += dx + a[0];
		ret[1] += dy + a[1];
//		return ret;
	}
	private void getMutation(double[] a, double[] b, double jaggedness, Area area, double[] ret) {
//		double[] ret = new double[] {0,0};
		double dx = b[0] - a[0];
		double dy = b[1] - a[1];
		if(dx == 0) {
			ret[0] = 0;
//			ret[1] = dy * Math.random();
//			ret[1] = dy * rand.nextDouble();
			ret[1] = dy * centerRand();
//			ret[1] = dy / 2;
		} else {
//			ret[0] = dx * Math.random();
//			ret[0] = dx * rand.nextDouble();
			ret[0] = dx * centerRand();
//			ret[0] = dx / 2;
			ret[1] = ret[0] * dy/dx;
		}
		
//		dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * Math.random();
		do {
			if(dy == 0) {
	//			dy = dx * Math.random() / 2 * jaggedness;
	//			dy = dx * rand.nextDouble() / 2 * jaggedness;
	//			dy = dx * (rand.nextDouble() - .5) * jaggedness;
				dy = dx * zeroCenterRand() * jaggedness;
				dx = 0;
			} else {
				double minv = -dx/dy;
	//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * Math.random() / 2 * jaggedness;
	//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * rand.nextDouble() / 2 * jaggedness;
	//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * (rand.nextDouble() - .5) * jaggedness;
				dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * zeroCenterRand() * jaggedness;
				dy = minv * dx;
			}
		} while(!area.contains(ret[0] + dx + a[0], ret[1] + dy + a[1]));
		ret[0] += dx + a[0];
		ret[1] += dy + a[1];
//		return ret;
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
}
