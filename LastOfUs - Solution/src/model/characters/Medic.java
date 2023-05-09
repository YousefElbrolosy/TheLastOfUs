package model.characters;

import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
	}
	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException,InvalidTargetException {
		if (this.getTarget() instanceof Zombie)
		   throw new InvalidTargetException();
		ArrayList<Supply>x=this.getSupplyInventory();
		if(x.isEmpty())
		   throw new NoAvailableResourcesException();
        if (this.getActionsAvailable()==0||this.isSpecialAction()==false)
		  throw new NotEnoughActionsException();
		if (this.getCurrentHp() < this.getMaxHp()) {
			this.setCurrentHp(this.getMaxHp());
		} else {
			this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
		}
		
		
		super.attack();
	}


}
