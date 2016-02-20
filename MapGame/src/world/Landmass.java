package world;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import test.FractalBlob;

public class Landmass extends Region{
	private static final Color landmassColor = Color.orange;
	public Landmass(Area a) {
		super(a, landmassColor);
	}
//	public Landmass(double x, double y, double r, int d) {
//		super(generateLandmass(x, y, r, d), landmassColor);
//	}
	public Landmass(double x, double y, double scale, int deformations) {
		super(generateLandmass(x, y, scale, deformations, 1.0));
	}
	public Landmass(double x, double y, double scale, int deformations, double jaggedness) {
		super(generateLandmass(x, y, scale, deformations, jaggedness));
	}
	public Landmass(double x, double y, double scale, int deformations, double jaggedness, int polySides) {
		super(generateLandmass(x, y, scale, deformations, jaggedness, polySides));
	}
//	private static Area generateLandmass(double x, double y, double scale, int deformations) {
//		FractalBlob ret = new FractalBlob(deformations);
//		ret.transform(AffineTransform.getScaleInstance(scale, scale));
//		ret.transform(AffineTransform.getTranslateInstance(x, y));
//		return ret;
//	}
	private static Area generateLandmass(double x, double y, double scale, int deformations, double jaggedness) {
		FractalBlob ret = new FractalBlob(deformations, jaggedness);
		ret.transform(AffineTransform.getScaleInstance(scale, scale));
		ret.transform(AffineTransform.getTranslateInstance(x, y));
		return ret;
	}
	private static Area generateLandmass(double x, double y, double scale, int deformations, double jaggedness, int polySides) {
		FractalBlob ret = new FractalBlob(deformations, jaggedness, polySides);
		ret.transform(AffineTransform.getScaleInstance(scale, scale));
		ret.transform(AffineTransform.getTranslateInstance(x, y));
		return ret;
	}
//	private static Area generateLandmass(double x, double y, double r, int d) {
//		Area ret = new Area();
//		Area temp;
//		Area temp2;
//		Path2D p;
//		double x0;
//		double y0;
//		double radius;
//		double theta;
//		for(int i = 0; i < d * 2; i++) {
//			p = new Path2D.Double(Path2D.WIND_NON_ZERO, d + 1);
//			radius = r * Math.random() * Math.random();
//			theta = 2 * Math.PI * Math.random();
//			x0 = radius * Math.cos(theta);
//			y0 = radius * Math.sin(theta);
//			p.moveTo(x0, y0);
//			if(i % 2 == 0) {
//				for(int j = 0; j < d; j++) {
//					
//				}
//			}
//		}
//	}
//	private static Area generateLandmass(double x, double y, double w, double h, int d) {
//		System.out.println("Generating Landmass");
//		Area ret = new Area();
//		Area temp;
//		Area temp2;
//		Path2D p;
//		double x0;
//		double y0;
//		for(int i = 0; i < d; i++) {
//			p = new Path2D.Double(Path2D.WIND_NON_ZERO, d + 1);
//			x0 = x + w * Math.random();
//			y0 = y + h * Math.random();
//			p.moveTo(x0,y0);
//			for(int j = 0; j < d; j++) {
//				p.curveTo(x + w * Math.random(), y + w * Math.random(), x + w * Math.random(), y + w * Math.random(), x + w * Math.random(), y + w * Math.random());
//			}
//			p.curveTo(x + w * Math.random(), y + w * Math.random(), x + w * Math.random(), y + w * Math.random(), x0, y0);
//			temp = new Area(p);
//			System.out.println(temp.isEmpty());
//			temp2 = (Area) temp.clone();
//			if(!ret.isEmpty()) {
//				temp2.intersect(ret);
//			}
//			if(!temp2.isEmpty()) {
//				System.out.println("Path successfully added!");
//				ret.add(temp);
//			} else {
//				System.out.println("Areas do not intersect!");
//				i--;
//			}
//		}
//		return ret;
//	}
}
