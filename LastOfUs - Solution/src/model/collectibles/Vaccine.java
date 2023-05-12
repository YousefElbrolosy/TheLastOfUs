package model.collectibles;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.*;
import model.world.CharacterCell;

public class Vaccine implements Collectible {

	public Vaccine() {

	}

	public void pickUp(Hero h) {
		ArrayList<Vaccine> vaccineInventory = h.getVaccineInventory();
		vaccineInventory.add(this);
		h.setVaccineInventory(vaccineInventory);
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		ArrayList<Vaccine>z=h.getVaccineInventory();
		z.remove(this);
		h.setVaccineInventory(z);

		Hero hero = Game.availableHeroes.get(0);
		Game.availableHeroes.remove(hero);
		Game.heroes.add(hero);
		Point point = h.getTarget().getLocation();
		hero.setLocation(point);
		CharacterCell zombieCell = (CharacterCell) Game.map[point.x][point.y];
		zombieCell.setCharacter(hero);
		Game.zombies.remove(h.getTarget());
		h.setTarget(null);

	}
}
