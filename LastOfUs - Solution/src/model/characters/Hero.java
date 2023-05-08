package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import model.collectibles.Supply;
import model.collectibles.Vaccine;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;

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
		// n2esly hewar en ana a5ly el cells visible el 2bleh w hwa rayhla w deh i think en ehna mafrood n5ly mn el awl el hero yb2a visible kol el adjacent cells el hwaleh w msh 3aref ezay mafrood n access hewar el cell w hya asln abstract 

		
		public void move(Direction d) throws MovementException,NotEnoughActionsException{
			int z = this.getActionsAvailable();
			if (z<1)
				throw new NotEnoughActionsException("No enough actions avaliable");
			else if (d.equals(Direction.UP) || d.equals(Direction.RIGHT)||d.equals(Direction.DOWN)||d.equals(Direction.LEFT) ) {
				if(d.equals(Direction.UP)){
					int x=(this.getLocation().x)-1;
					int y = this.getLocation().y;
					if (x<0)
						throw new MovementException("Invalid move");
					else{
						this.setLocation(new Point (x,y));
						this.setActionsAvailable(--z);
					}
					
				}
				if(d.equals(Direction.DOWN)){
					int x=(this.getLocation().x)+1;
					int y = this.getLocation().y;
					if (x>15)
						throw new MovementException("Invalid move");
					else{
						this.setLocation(new Point (x,y));
						this.setActionsAvailable(--z);
					}
					

				}

				if(d.equals(Direction.LEFT)){
					int x=(this.getLocation().x);
					int y = (this.getLocation().y)-1;
					if (y<0)
						throw new MovementException("Invalid move");
					else{
						this.setLocation(new Point (x,y));
						this.setActionsAvailable(--z);
						}
				
				}

				if(d.equals(Direction.RIGHT)){
					int x=(this.getLocation().x);
					int y = (this.getLocation().y)+1;
					if (y>15)
						throw new MovementException("Invalid move");
					else{
						this.setLocation(new Point (x,y));
						this.setActionsAvailable(--z);
						}


				}
				else
					throw new MovementException("Invalid move");

			}
			
		}
			
	
}
