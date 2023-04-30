package model.collectibles;

import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

public class Vaccine implements Collectible {

	public Vaccine() {

	}

	public void pickUp(Hero h) {
		ArrayList<Vaccine> x = h.getVaccineInventory();
		x.add(this);
		h.setVaccineInventory(x);
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		ArrayList<Vaccine> x = h.getVaccineInventory();
		if (x.size() > 0) {
			x.remove(x.size() - 1);
			h.setVaccineInventory(x);

		}
		else
			throw new NoAvailableResourcesException("No enough vaccines in the inventory");

	}

}
