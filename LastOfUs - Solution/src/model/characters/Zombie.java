package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException {
		
		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[0].length; j++) {
				Point p1 = new Point(i, j);
				if ((isAdjacent(this.getLocation(), p1)) == true) {
					this.setTarget(getOccupiedHeroes(p1));
					super.attack();
				}
			}
		}

	}

	public static Hero getOccupiedHeroes(Point p) {
		int i = 0;
		while (i < Game.heroes.size()) {
			if (Game.heroes.get(i).getLocation() == p) {
				return Game.heroes.get(i);
			} else {
				i++;
			}
		}
		return null;

	}
}


