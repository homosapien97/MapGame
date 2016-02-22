package main;

import java.awt.Color;
import java.awt.EventQueue;

import ui.RenderMode;
import ui.Window;
import world.City;
import world.Landmass;
import world.World;

public class Main{
	public static boolean debug = false;
	private static void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Window window = new Window(RenderMode.landmasses);
				window.setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		World.world.add(new City(500, 500, 25, Color.green));
//		World.world.add(new Landmass(500, 500, 600, 15));
//		World.world.add(new Landmass(500.0, 500.0, 600.0, 15, .6));
		long seed = (long)(Math.random() * Long.MAX_VALUE);
		World.world.add(new Landmass(500.0, 500.0, 6000.0, 1.0, 14, 5, 5429079965126446080l, true, -1));
		System.out.println("SEED: " + seed);
		createWindow();
		
//		FractalBlob fb = new FractalBlob();
//		System.out.println(fb);
	}
}
