package model.characters;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import java.util.ArrayList;
import java.util.Random;

import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;


public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
	
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}

		
	


		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}

		

		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}





		public void setVaccineInventory(ArrayList<Vaccine> vaccineInventory) {
			this.vaccineInventory = vaccineInventory;
		}





		public void setSupplyInventory(ArrayList<Supply> supplyInventory) {
			this.supplyInventory = supplyInventory;
		}


public void attack() throws InvalidTargetException, NotEnoughActionsException {
	if(this.getActionsAvailable()>=1) {
		super.attack();
		int x = this.getActionsAvailable();
		this.setActionsAvailable(--x);
	}else 
		throw new NotEnoughActionsException("No enough actions avaliable");
	
	
}
public void cure()throws InvalidTargetException,NoAvailableResourcesException{
	if(this.getVaccineInventory().size()>0){	
		if(this.getTarget()instanceof Zombie){	
			if(this.getActionsAvailable()>0){
				if(isAdjacent(this.getLocation(),this.getTarget().getLocation())){
					Vaccine v = getVaccineInventory().get(getVaccineInventory().size()-1);
					//Mechanics of curing
					int y = Game.availableHeroes.size();
					Random r = new Random();
					int yRand = r.nextInt(y);
					Hero h = Game.availableHeroes.get(yRand);
					h.setLocation(this.getTarget().getLocation());
					CharacterCell zombieCell = (CharacterCell) Game.map[this.getTarget().getLocation().x][this.getTarget().getLocation().y];
					zombieCell.setCharacter(h);
					Game.zombies.remove(this.getTarget());
					this.getTarget().setLocation(null);
					//modifying Points and Array
					int x = this.getActionsAvailable();
					this.setActionsAvailable(x--);
					v.use(this);
				}
			}
		}
	}
}
}
