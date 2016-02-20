package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import test.FractalBlob;
import ui.Window;
import world.City;
import world.Landmass;
import world.World;

public class Main extends JFrame{
	private static void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Window window = new Window();
				window.setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		World.world.add(new City(500, 500, 1, Color.green));
//		World.world.add(new Landmass(500, 500, 600, 15));
//		World.world.add(new Landmass(500.0, 500.0, 600.0, 15, .6));
		World.world.add(new Landmass(500.0, 500.0, 600.0, 13, .9, 5, 335, false));
		createWindow();
		
//		FractalBlob fb = new FractalBlob();
//		System.out.println(fb);
	}
}
