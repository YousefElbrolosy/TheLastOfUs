package model.collectibles;

import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

public class Supply implements Collectible {

	public Supply() {

	}

	public void pickUp(Hero h) {
		ArrayList<Supply> x = h.getSupplyInventory();
		x.add(this);
		h.setSupplyInventory(x);
		
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		
			ArrayList<Supply> x = h.getSupplyInventory();
			if(x.size()>0) {
				x.remove(x.size() - 1);
				h.setSupplyInventory(x);
			}
			
			else
				throw new NoAvailableResourcesException("No enough supplies in the inventory");
		
		
		
		

	}
}
