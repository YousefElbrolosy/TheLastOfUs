package model.characters;

import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
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
		if (this.getTarget() instanceof Hero)
		    throw new InvalidTargetException();
		
			for(int i=0;i<15;i++){
				for(int j=0;j<15;j++){
				Game.map[i][j].setVisible(true);	
			}
		}
			
			
			super.attack();
	}
	
	

	
}
