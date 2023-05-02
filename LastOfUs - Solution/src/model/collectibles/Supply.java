package model.collectibles;

import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;



public class Supply implements Collectible {

	

	
	public Supply() {
		
	}
	public void pickUp(Hero h){
		Supply n=new Supply();
		ArrayList<Supply>s=h.getSupplyInventory();
		s.add(n);
		h.setSupplyInventory(s);
	}
	public void use(Hero h){
	

	
		
		

}
}
