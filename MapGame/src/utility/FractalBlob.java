package utility;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class FractalBlob extends Area{
	private Random rand;
	private ArrayList<double[]> points = new ArrayList<double[]>();
	
	public FractalBlob(int deformations, double jaggedness, int polySides, long seed) {
		rand = new Random(seed);
		points = regularUnitPolygon(polySides);
		Path2D.Double p2d = new Path2D.Double();
		ArrayList<double[]> newPoints = new ArrayList<double[]>();
		for(int j = 0; j < deformations; j++) {
			for(int i = 0; i < points.size(); i++) {
				newPoints.add(points.get(i));
				newPoints.add(getMutation(points.get(i), points.get((i + 1) % points.size()), jaggedness / (j + 1)));
			}
			
			points.clear();
			points.addAll(newPoints);
			newPoints.clear();
//			System.out.println(points.size());
		}
		double[] temp = points.get(0);
		p2d.moveTo(temp[0], temp[1]);
		for(int i = 1; i < points.size(); i++) {
			temp = points.get(i);
			p2d.lineTo(temp[0], temp[1]);
//			temp = getMutation(temp, points.get((i + 1) % points.size()), jaggedness);	//might as well add in an extra set of deformations because we're iterating thru again.
//			p2d.lineTo(temp[0], temp[1]);
		}
		p2d.closePath();
		Rectangle2D r = p2d.getBounds2D();
		p2d.transform(AffineTransform.getTranslateInstance(-r.getCenterX(), -r.getCenterY()));
		p2d.transform(AffineTransform.getRotateInstance(rand.nextDouble() * Math.PI * 2));
//		for(PathIterator pi = p2d.getPathIterator(null); !pi.isDone(); pi.next()) {
//			pi.currentSegment(temp);
//			System.out.println("(" + temp[0] + "," + temp[1] + ")");
//		}
//		System.out.println(p2d);
		Area a = new Area(p2d);
		this.add(a);
	}
	
	private double[] getMutation(double[] a, double[] b, double jaggedness) {
		double[] ret = new double[] {0,0};
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
			dy = dx * (rand.nextDouble() - .5) * jaggedness;
//			dy = dx * zeroCenterRand() / 2 * jaggedness;
			dx = 0;
		} else {
			double minv = -dx/dy;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * Math.random() / 2 * jaggedness;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * rand.nextDouble() / 2 * jaggedness;
			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * (rand.nextDouble() - .5) * jaggedness;
//			dx = Math.sqrt((dx * dx + dy * dy) / (minv * minv + 1)) * zeroCenterRand() / 2 * jaggedness;
			dy = minv * dx;
		}
		ret[0] += dx + a[0];
		ret[1] += dy + a[1];
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(double[] p : points) {
			ret += "(" + p[0] + "," + p[1] + ")\n";
		}
		return ret;
	}
	
	private static ArrayList<double[]> regularUnitPolygon(int n) {
		ArrayList<double[]> ret = new ArrayList<double[]>();
		ret.add(new double[] {0, 0});
//		double dTheta = Math.PI - (n - 1) * Math.PI / n;
		double dTheta = 2 * Math.PI / n;
		double[] temp;
		for(int i = 0; i < n - 1; i++) {
			temp = ret.get(ret.size() - 1);
			ret.add(new double[] {temp[0] + Math.cos(i * dTheta), temp[1] + Math.sin(i * dTheta)});
		}
		for(double[] d : ret) {
			System.out.println("pol:" + d[0] + "," + d[1]);
		}
		return ret;
	}
//	private double zeroCenterRand() {
//		double ret = rand.nextDouble() - .5;
//		return ret * ret * ret * 8;
//	}
	private double centerRand() {
		double ret = rand.nextDouble();
		return ret * (4 * ret * ret - 6 * ret + 3);
	}
}
