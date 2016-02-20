package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

import world.Region;
import world.World;

public class Surface extends JPanel implements MouseWheelListener, MouseListener {
	private Graphics2D g2d;
	private AffineTransform t = new AffineTransform();
	private RenderMode rm;
	private ArrayList<? extends Region> temp;
	public Surface() {
		super();
		rm = RenderMode.cities;
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	private void drawHUD(Graphics2D g) {
//		g.draw(...
//		g.drawGlyphVector(g, x, y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.transform(t);
//		System.out.println("Center X: " + (getWidth() / 2 - t.getTranslateX() / t.getScaleX()));
//		System.out.println("Center Y: " + (getHeight() / 2 - t.getTranslateY() / t.getScaleY()));
//		System.out.println("Center X: " + (getWidth() / 2 - t.getTranslateX()) / t.getScaleX());
//		System.out.println("Center Y: " + (getHeight() / 2 - t.getTranslateY()) / t.getScaleY());
		System.out.println("Center X: " + centerX());
		System.out.println("Center Y: " + centerY());
		for(Lens l : rm) {
			temp = World.world.get(l.class_);
			if(temp != null) {
				if(l.color != null) {
					g2d.setColor(l.color);
					if(l.fill) {
						for(Region r : temp) {
							g2d.fill(r);
						}
					} else {
						for(Region r : temp) {
							g2d.draw(r);
						}
					}
				} else {
					if(l.fill) {
						for(Region r : temp) {
							g2d.setColor(r.getColor());
							g2d.fill(r);
						}
					} else {
						for(Region r: temp) {
							g2d.setColor(r.getColor());
							g2d.draw(r);
						}
					}
				}
			}
		}
		drawHUD(g2d);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
//		System.out.println("Zooming");
		double zoom = Math.pow(1.5, -e.getWheelRotation());
		System.out.println("Zooming: " + zoom);
//		t.concatenate(AffineTransform.getTranslateInstance(this.getWidth() / 2 - x, this.getHeight() / 2 - y));

//		t.concatenate(AffineTransform.getTranslateInstance(hw - hw * zoom, hh - hh * zoom));
//		t.concatenate(AffineTransform.getScaleInstance(zoom, zoom));
//		t.concatenate(AffineTransform.getTranslateInstance(
//				centerX() - worldX(e.getX()), 
//				centerY() - worldY(e.getY())
//				));
		t.scale(zoom, zoom);
		double x = worldX(e.getX());
		double y = worldY(e.getY());
		t.translate(x - zoom * x, y - zoom * y);
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked: (" + worldX(e.getX()) + "," + worldY(e.getY()) + ")");
//		t.concatenate(AffineTransform.getTranslateInstance((e.getX() - t.getTranslateX()) / t.getScaleX(), (e.getY() - t.getTranslateY()) / t.getScaleY()));
		t.concatenate(AffineTransform.getTranslateInstance(
				centerX() - worldX(e.getX()), 
				centerY() - worldY(e.getY())
				));
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Entered");
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Exited");
	}
	private double pressX = 0;
	private double pressY = 0;
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON2) {
			System.out.println("b2 pressed");
			pressX = worldX(e.getX());
			pressY = worldY(e.getY());
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON2) {
			System.out.println("b2 released");
			System.out.println("dx: " + (e.getX() - pressX));
			System.out.println("dy: " + (e.getY() - pressY));
			t.translate(worldX(e.getX()) - pressX, worldY(e.getY()) - pressY);
		}
		repaint();
	}
	
	/**
	 * The world x coordinate at the center of the screen
	 * @return the x coordinate of the world shown at center screen
	 */
	private double centerX() {
		return (getWidth() / 2 - t.getTranslateX()) / t.getScaleX();
	}
	/**
	 * The world y coordinate at the center of the screen
	 * @return the y coordinate of the world shown at center screen
	 */
	private double centerY() {
		return (getHeight() / 2 - t.getTranslateY()) / t.getScaleY();
	}
	/**
	 * The world x coordinate given a screen (click) coordinate
	 * @param screenX the x coordinate on the screen
	 * @return the world x coordinate
	 */
	private double worldX(double screenX) {
		return (screenX - t.getTranslateX()) / t.getScaleX();
	}
	/**
	 * The world y coordinate given a screen (click) coordinate
	 * @param screenX the y coordinate on the screen
	 * @return the world y coordinate
	 */
	private double worldY(double screenY) {
		return (screenY - t.getTranslateY()) / t.getScaleY();
	}
}
