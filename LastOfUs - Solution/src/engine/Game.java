package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.*;

public class Game {

	public static Cell[][] map;
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

	public static void startGame(Hero h) {
		map=new Cell[15][15];
		int i1 = 0;
		while (i1 < 5) {
			Vaccine v = new Vaccine();
			CollectibleCell c = new CollectibleCell(v);
			Point p = generatePoint();
			if (map[p.x][p.y] == null) {

				map[p.x][p.y] = c;
			} else {
				while (map[p.x][p.y] != null) {
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
			if (map[p.x][p.y] == null) {
				map[p.x][p.y] = c;
			} else {
				while (map[p.x][p.y] != null) {
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
			if (map[p.x][p.y] == null) {

				map[p.x][p.y] = c;
			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();
				}
				map[p.x][p.y] = c;
			}
			i3++;
		}
		
		int i4 = 0;
		while (i4 < 5) {
			TrapCell v = new TrapCell();
			Point p = generatePoint();
			if (map[p.x][p.y] == null) {
				map[p.x][p.y] = v;
			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();

				}
				map[p.x][p.y] = v;
			}
			i4++;
		}
		heroes.add(h);
		availableHeroes.remove(h);	
		h.setLocation(new Point(0, 0));
		// for(int i =0;i<map.length;i++){
		// 	for (int j =0;j<map[i].length;j++){
		// 		if(map[i][j] instanceof CharacterCell){
		// 			Character characater = ((CharacterCell) map[i][j]).getCharacter();
				

		// 			if(isAdjacent(characater.getLocation(),new Point(i, j))){
		// 				map[i][j].setVisible(true);
		// 		}
		// 	}
		// }

		// }
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
	

	public static boolean isAdjacent(Point point1, Point point2) {
	int x = (point2.x - point1.x);
	int y = (point2.y - point1.y);
	double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	if (d == 1 || d == Math.sqrt(2))
		return true;
	else
		return false;
}
}

