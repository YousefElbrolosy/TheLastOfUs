package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.*;

public class Game {

	public static Cell[][] map = new Cell[15][15];
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();

	public static void loadHeroes(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero = null;
			switch (content[1]) {
				case "FIGH":
					hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
				case "MED":
					hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
				case "EXP":
					hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
			}
			availableHeroes.add(hero);
			line = br.readLine();

		}
		br.close();

	}

	public static Point generatePoint() {
		Random random = new Random();
		int x = random.nextInt(15);
		int y = random.nextInt(15);
		Point res = new Point(x, y);
		return res;
	}

	public static boolean Occupied(Point p) {
		if (Game.map[p.x][p.y] instanceof CharacterCell) {
			if (((CharacterCell) Game.map[p.x][p.y]).getCharacter() == null)
				return false;
			else
				return true;

		} else if (Game.map[p.x][p.y] instanceof TrapCell)
			return true;
		else if (Game.map[p.x][p.y] instanceof CollectibleCell)
			return true;
		else
			return false;

	}

	public static void startGame(Hero h) {
		// map = new Cell[15][15];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				map[i][j] = new CharacterCell(null);

			}
		}
		int i1 = 0;
		while (i1 < 5) {
			Vaccine v = new Vaccine();
			CollectibleCell c = new CollectibleCell(v);
			Point p = generatePoint();
		if (!Occupied(p)) {
				//if (!(Game.map[p.x][p.y] instanceof CollectibleCell|| Game.map[p.x][p.y] instanceof TrapCell||(Game.map[p.x][p.y] instanceof CharacterCell) )){
				map[p.x][p.y] = c;
			} else {
				while (Occupied(p)) {
					p = generatePoint();
				}
				map[p.x][p.y] = c;
			}
			i1++;
		}
		

		int i2 = 0;
		while (i2 < 5) {
			Supply v = new Supply();
			CollectibleCell c = new CollectibleCell(v);
			Point p = generatePoint();
			if (!Occupied(p)) {
				map[p.x][p.y] = c;			
			} else {
				while (Occupied(p)) {
					p = generatePoint();

				}
				map[p.x][p.y] = c;
			}
			i2++;
		}

		int i3 = 0;
		while (i3 < 10) {
			Zombie v = new Zombie();
			zombies.add(v);
			CharacterCell c = new CharacterCell(v);
			Point p = generatePoint();
			if (!Occupied(p)) {

				map[p.x][p.y] = c;

			} else {
				while (Occupied(p)) {
					p = generatePoint();
				}
				map[p.x][p.y] = c;
			}
			v.setLocation(p);

			i3++;
		}

		int i4 = 0;
		while (i4 < 5) {
			TrapCell v = new TrapCell();
			Point p = generatePoint();
			if (!Occupied(p)) {
				map[p.x][p.y] = v;
			} else {
				while (Occupied(p)) {
					p = generatePoint();

				}
				map[p.x][p.y] = v;
			}
			i4++;
		}

		map[0][0] = new CharacterCell(h);
		map[0][0].setVisible(true);
		heroes.add(h);
		availableHeroes.remove(h);
		h.setLocation(new Point(0, 0));
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (isAdjacent(h.getLocation(), new Point(i, j))) {
					map[i][j].setVisible(true);
				}
			}
		}
	}



	public static boolean checkWin() {
		int hero = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell z = map[i][j];
				if (z instanceof CollectibleCell) {
					Collectible x = ((CollectibleCell) map[i][j]).getCollectible();
					if (x instanceof Vaccine) {
						return false;
					}
				}
				if (z instanceof CharacterCell) {
					Character x = ((CharacterCell) map[i][j]).getCharacter();
					if (x instanceof Hero) {
						hero++;
						if (((Hero) x).getVaccineInventory().size() != 0)
							return false;
					}

				}

			}

		}
		if (hero < 5)
			return false;
		else
			return true;
	}

	public static boolean checkGameOver() {
		boolean flagvacc = true;
		boolean flagused = true;
		boolean flagheroes = true;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell z = map[i][j];
				if (z instanceof CollectibleCell) {
					Collectible x = ((CollectibleCell) map[i][j]).getCollectible();
					if (x instanceof Vaccine) {
						flagvacc = false;
					}
				}
			}
		}
		for (int i = 0; i < heroes.size(); i++) {
			if (heroes.get(i).getVaccineInventory().size() != 0)
				flagused = false;
		}
		if (availableHeroes.size() != 0 || heroes.size() != 0)
			flagheroes = false;
		return (flagvacc && flagused) || flagheroes;

	}

	public static void endTurn()
			throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException {
		// iterates through zombie list and makes each zombie attack if possible
		// is there a way to make it all at once?
		int i = 0;
		int j = 0;
		for (Zombie zombie : zombies) {
			Hero x = setForMe(zombie.getLocation());
			if (x != null) {
				zombie.setTarget(x);
				zombie.attack();
			}

		}

		// resetting each heroes maxActions and SpecialActions
		while (j < Game.heroes.size()) {
			Game.heroes.get(j).setActionsAvailable(Game.heroes.get(j).getMaxActions());
			// here I assumed initial target of each hero is null
			Game.heroes.get(j).setTarget(null);
			Game.heroes.get(j).setSpecialAction(false);
			j++;
		}

		for (i = 0; i < Game.map.length; i++) {
			for (j = 0; j < Game.map[i].length; j++) {

				Game.map[i][j].setVisible(false);
			}
		}

		// Updating map //setting visibility of each adjacent cell to each hero to true

		for (i = 0; i < Game.map.length; i++) {
			for (j = 0; j < Game.map[i].length; j++) {
				if (Game.map[i][j] instanceof CharacterCell) {
					Character z = ((CharacterCell) Game.map[i][j]).getCharacter();
					if (z instanceof Hero) {
						setAdjacent(z.getLocation());
					}

				}

			}

		}
		for (i = 0; i < zombies.size(); i++) {
			zombies.get(i).setTarget(null);
		}
		// spawning a Zombie Randomly on the map
		Zombie z = new Zombie();
		// Randomizing point
		Point p = notOccRandomPointGenerator();
		z.setLocation(p);
		Game.zombies.add(z);
		// instead of initialising a variable I won't use
		Game.map[p.x][p.y] = new CharacterCell(z);
	}

	// set visiblity to all adjacent cells and the location of the hero
	public static void setAdjacent(Point p) {
		Game.map[p.x][p.y].setVisible(true);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (isAdjacent(p, new Point(i, j)))
					Game.map[i][j].setVisible(true);
			}
		}
	}

	public static boolean isOccupiedHeroes(Point p) {
		int i = 0;
		while (i < Game.heroes.size()) {
			if (Game.heroes.get(i).getLocation() == p) {
				return true;
			} else {
				i++;
			}
		}
		return false;

	}

	public static Point notOccRandomPointGenerator() {
		// length of columns (no. of rows)
		int numberOfRows = Game.map[0].length;
		// length of rows (no. of columns)
		int numberOfColumns = Game.map.length;
		// based on the Point(row,column)
		Random r = new Random();
		int xNew = r.nextInt(numberOfRows);
		int yNew = r.nextInt(numberOfColumns);
		Point p = new Point();
		p.x = xNew;
		p.y = yNew;

		// note that here x and y are inverted
		if (!(((Game.map[(int) p.getX()][(int) p.getY()] instanceof CharacterCell)
				&& (((CharacterCell) (Game.map[(int) p.getX()][(int) p.getY()])).getCharacter() != null)) ||
				(Game.map[(int) p.getX()][(int) p.getY()] instanceof TrapCell) ||
				(Game.map[(int) p.getX()][(int) p.getY()] instanceof CollectibleCell)))
			return p;
		else
			return notOccRandomPointGenerator();
	}

	public static Hero setForMe(Point x) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (isAdjacent(new Point(i, j), x))
					if (map[i][j] instanceof CharacterCell) {
						if (((CharacterCell) map[i][j]).getCharacter() instanceof Hero)
							return (Hero) ((CharacterCell) map[i][j]).getCharacter();
					}
			}
		}
		return null;
	}

	public static boolean isAdjacent(Point point1, Point point2) {
		if ((point1.x >= 0) && (point1.y >= 0) && (point2.y >= 0) && (point2.y >= 0)) {
			int x = (point2.x - point1.x);
			int y = (point2.y - point1.y);
			double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			if (d == 1 || d == Math.sqrt(2))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

}
