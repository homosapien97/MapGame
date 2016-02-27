package world;

public class Continent extends Region {
	private Landmass landmass;
	public Continent(double x, double y, double scale, int deformations, double jaggedness, int polySides, long seed) {
		landmass = new Landmass(x, y, scale, jaggedness, deformations, polySides, seed, true);
		add(landmass);
	}
	private void generateRivers() {
		
	}
	private void generateLakes() {
		
	}
	private void generateMountains() {
		
	}
	private void generateVolcanos() {
		
	}
	private void generateHills() {
		
	}
	private void generateIslands() {
		//If islands are below a certain size, don't bother giving them fancy terrain--choose either mountain, farmland, marsh, beach
		
	}
	private void generateFarmlands() {
		
	}
	private void generateMarshes() {
		
	}
	private void generateBeaches() {
		
	}
	private void generateBluffs() {
		
	}
	private void generatePlains() {
		
	}
	private void generateForests() {
		
	}
}
