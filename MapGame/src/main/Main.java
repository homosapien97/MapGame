package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Random;

import ui.RenderMode;
import ui.Window;
import world.City;
import world.Landmass;
import world.River;
import world.World;

public class Main{
	public static boolean debug = false;
	private static void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Window window = new Window(RenderMode.cities);
				window.setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		World.world.add(new City(0, 0, 100, Color.green));
//		World.world.add(new Landmass(500, 500, 600, 15));
//		World.world.add(new Landmass(500.0, 500.0, 600.0, 15, .6));
		long seed = (long)(Math.random() * Long.MAX_VALUE);
		Landmass landmass = new Landmass(0.0, 0.0, 6000.0, 7.0, 14, 2, seed, true);
//		River river = new River(1.0, 8, seed, new Random(seed), landmass, 1.0);
		River river = new River(1000.0, .2, 800000.0, 8.0, landmass, 800, seed);

		World.world.add(landmass);
//		World.world.add(river);
		System.out.println("SEED: " + seed);
		createWindow();
		
//		FractalBlob fb = new FractalBlob();
//		System.out.println(fb);
//		3426.3100017539355, -25367.76780383857
	}
}
// good seeds:
// (500.0, 500.0, 6000.0, 1.0, 16, 3, 2100112869506769920l, true, -1)
// (500.0, 500.0, 6000.0, 7.0, 15, 6, 4085470830717436928l, true, -1)
// (500.0, 500.0, 6000.0, 8.0, 16, 2, 2100112869506769920l, true, -1)