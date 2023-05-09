package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.*;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import engine.*;


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


		public void move(Direction d) throws MovementException, NotEnoughActionsException, NoAvailableResourcesException {
			int z = this.getActionsAvailable();
			if (z < 1)
				throw new NotEnoughActionsException("No enough actions avaliable");
			else if (d.equals(Direction.UP) || d.equals(Direction.RIGHT) || d.equals(Direction.DOWN)
					|| d.equals(Direction.LEFT)) {
				if (d.equals(Direction.UP)) {
					int x = (this.getLocation().x) + 1;
					int y = this.getLocation().y;
					if (x > 14 || super.isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
								Game.map[loc.x][loc.y] = new CharacterCell(null);
							}
	
							else {
								this.setCurrentHp(curr);
								Game.map[loc.x][loc.y] = new CharacterCell(this);
	
							}
						}
						if (Game.map[loc.x][loc.y] instanceof CollectibleCell) {
							Collectible collect = ((CollectibleCell) Game.map[loc.x][loc.y]).getCollectible();
							collect.pickUp(this);
							Game.map[loc.x][loc.y] = new CharacterCell(this);
						}
						Game.map[loc.x][loc.y] = new CharacterCell(this);
	
					}
	
				}
				if (d.equals(Direction.DOWN)) {
					int x = (this.getLocation().x) - 1;
					int y = this.getLocation().y;
					if (x < 0 || super.isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
								Game.map[loc.x][loc.y] = new CharacterCell(null);
							}
	
							else {
								this.setCurrentHp(curr);
								Game.map[loc.x][loc.y] = new CharacterCell(this);
	
							}
						}
						if (Game.map[loc.x][loc.y] instanceof CollectibleCell) {
							Collectible collect = ((CollectibleCell) Game.map[loc.x][loc.y]).getCollectible();
							collect.pickUp(this);
							Game.map[loc.x][loc.y] = new CharacterCell(this);
						}
						Game.map[loc.x][loc.y] = new CharacterCell(this);
	
					}
	
				}
	
				if (d.equals(Direction.LEFT)) {
					int x = (this.getLocation().x);
					int y = (this.getLocation().y) - 1;
					if (y < 0 || super.isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
								Game.map[loc.x][loc.y] = new CharacterCell(null);
							}
	
							else {
								this.setCurrentHp(curr);
								Game.map[loc.x][loc.y] = new CharacterCell(this);
	
							}
						}
						if (Game.map[loc.x][loc.y] instanceof CollectibleCell) {
							Collectible collect = ((CollectibleCell) Game.map[loc.x][loc.y]).getCollectible();
							collect.pickUp(this);
							Game.map[loc.x][loc.y] = new CharacterCell(this);
						}
						Game.map[loc.x][loc.y] = new CharacterCell(this);
	
					}
	
				}
	
				if (d.equals(Direction.RIGHT)) {
					int x = (this.getLocation().x);
					int y = (this.getLocation().y) + 1;
					if (y > 14 || super.isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
								Game.map[loc.x][loc.y] = new CharacterCell(null);
							}
	
							else {
								this.setCurrentHp(curr);
								Game.map[loc.x][loc.y] = new CharacterCell(this);
	
							}
						}
						if (Game.map[loc.x][loc.y] instanceof CollectibleCell) {
							Collectible collect = ((CollectibleCell) Game.map[loc.x][loc.y]).getCollectible();
							collect.pickUp(this);
							Game.map[loc.x][loc.y] = new CharacterCell(this);
						}
						Game.map[loc.x][loc.y] = new CharacterCell(this);
					}
	
				}
	
				for (int i = 0; i < Game.map.length; i++) {
					for (int j = 0; j < Game.map[i].length; j++) {
						if (isAdjacent(this.getLocation(), new Point(i, j))) {
							Game.map[i][j].setVisible(true);
						}
					}
	
				}
			}
	
		}
		
		public boolean isTrapCell(Point p) {

			return (Game.map[p.x][p.y] instanceof TrapCell) ? true : false;
	
		}

		public static boolean isAdjacent(Point point1, Point point2) {
			if ((point1.x>=0)&&(point1.y>=0)&&(point2.y>=0)&&(point2.y>=0)){
					int x =(point2.x-point1.x);
					int y = (point2.y-point1.y);
					double d = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
					if(d==1 || d==Math.sqrt(2))
						return true; 
					else
						return false;
			}
			else{
				return false;
			}
		}

		
	
}
