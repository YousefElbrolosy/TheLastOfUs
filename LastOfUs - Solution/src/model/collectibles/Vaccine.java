package model.collectibles;

import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.*;

public class Vaccine implements Collectible {

	public Vaccine() {
		
	}

	public void pickUp(Hero h){
		// Vaccine s = new Vaccine();
		ArrayList<Vaccine> vaccineInventory = h.getVaccineInventory();
		vaccineInventory.add(this);
		h.setVaccineInventory(vaccineInventory);
	}
	public void use(Hero h) throws NoAvailableResourcesException {
			
			ArrayList<Vaccine> vaccineInventory = h.getVaccineInventory();
			if (!vaccineInventory.isEmpty()){
			int length = vaccineInventory.size();
			vaccineInventory.remove(length-1);
			h.setVaccineInventory(vaccineInventory);
			}
			else{
				throw new NoAvailableResourcesException("no space");
			}
	}
}
