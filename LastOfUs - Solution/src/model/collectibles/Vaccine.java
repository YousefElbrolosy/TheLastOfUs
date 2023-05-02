package model.collectibles;

import java.util.ArrayList;

import model.characters.Hero;



public class Vaccine implements Collectible {

	public Vaccine() {
		
	}
	public void pickUp(Hero h){
		Vaccine n=new Vaccine();
		ArrayList<Vaccine>s=h.getVaccineInventory();
		s.add(n);
		h.setVaccineInventory(s);
	}
    public void use(Hero h){
    	ArrayList<Vaccine>s=h.getVaccineInventory();
		if(s.size()>0)   
    	    s.remove(s.size()-1);
		h.setVaccineInventory(s);
	}

}
