package model.collectibles;

import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.*;
import model.world.CharacterCell;

public class Vaccine implements Collectible {

	public Vaccine() {

	}

	public void pickUp(Hero h) {
		// Vaccine s = new Vaccine();
		ArrayList<Vaccine> vaccineInventory = h.getVaccineInventory();
		vaccineInventory.add(this);
		h.setVaccineInventory(vaccineInventory);
	}

	public void use(Hero h) throws NoAvailableResourcesException {

		Hero x = Game.availableHeroes.get(0);
		Game.availableHeroes.remove(x);
		Game.heroes.add(x);
		x.setLocation(h.getTarget().getLocation());
		CharacterCell zombieCell = (CharacterCell) Game.map[h.getTarget().getLocation().x][h
				.getTarget().getLocation().y];
		zombieCell.setCharacter(x);
		Game.zombies.remove(h.getTarget());
		h.getTarget().setLocation(null);
		// modifying Points and Array
		h.setTarget(null);

	}
}
