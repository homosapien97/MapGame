package ui;

import javax.swing.JFrame;

public class Window extends JFrame{
	private Surface surface;
	public Window(RenderMode rm) {
		surface = new Surface(rm);
		this.add(surface);
		this.setTitle("MapGame");
		this.setExtendedState(MAXIMIZED_BOTH);
//		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public Window(Surface s) {
		surface = s;
		this.add(s);
		this.setTitle("MapGame");
		this.setExtendedState(MAXIMIZED_BOTH);
//		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
