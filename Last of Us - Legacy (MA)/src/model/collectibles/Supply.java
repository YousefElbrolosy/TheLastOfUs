package model.collectibles;

import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.*;

public class Supply implements Collectible  {

	

	
	public Supply() {
		
	}
	
	public void pickUp(Hero h){
		//	Supply s = new Supply();
			ArrayList<Supply> supplyInventory = h.getSupplyInventory();
			supplyInventory.add(this);
			h.setSupplyInventory(supplyInventory);


	}
	public void use(Hero h) throws NoAvailableResourcesException{
		ArrayList<Supply> supplyInventory = h.getSupplyInventory();
		if (!supplyInventory.isEmpty()){
		int length = supplyInventory.size();
		supplyInventory.remove(length-1);
		h.setSupplyInventory(supplyInventory);
		}
		else{
			throw new NoAvailableResourcesException("no space");
		}
	}
	
		
		

}
