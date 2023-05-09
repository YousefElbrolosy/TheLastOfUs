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
		if (this.getTarget() instanceof Hero)
	      throw new InvalidTargetException();
	    super.useSpecial();
	}
	

	
	
	
	

}
