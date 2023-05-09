package model.characters;

import java.util.ArrayList;


import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
	}
    public void useSpecial() throws NoAvailableResourcesException,NotEnoughActionsException,InvalidTargetException{
		ArrayList<Supply>x=this.getSupplyInventory();
		if(x.isEmpty())
		   throw new NoAvailableResourcesException();
        if (this.getActionsAvailable()==0||this.isSpecialAction()==false)
		  throw new NotEnoughActionsException();
		if (this.getTarget() instanceof Hero)
	      throw new InvalidTargetException();
	setSpecialTurn(true);
	super.attack();
	}
	

	
	
	
	

}
