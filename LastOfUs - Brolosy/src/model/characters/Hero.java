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


		public void move(Direction d) throws MovementException, NotEnoughActionsException, NoAvailableResourcesException {
			int z = this.getActionsAvailable();
			if (z < 1)
				throw new NotEnoughActionsException("No enough actions avaliable");
			else if (d.equals(Direction.UP) || d.equals(Direction.RIGHT) || d.equals(Direction.DOWN)
					|| d.equals(Direction.LEFT)) {
				if (d.equals(Direction.UP)) {
					int x = (this.getLocation().x) + 1;
					int y = this.getLocation().y;
					if (x > 14 || isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						Game.map[x - 1][y] = new CharacterCell(null);
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
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
					if (x < 0 || isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						Game.map[x + 1][y] = new CharacterCell(null);
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
	
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
					if (y < 0 || isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						Game.map[x][y + 1] = new CharacterCell(null);
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
	
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
					if (y > 14 || isOccupied(new Point(x, y)))
						throw new MovementException("Invalid move");
					else {
						Game.map[x][y - 1] = new CharacterCell(null);
						this.setLocation(new Point(x, y));
						this.setActionsAvailable(--z);
						Point loc = this.getLocation();
						if (isTrapCell(loc)) {
							int curr = this.getCurrentHp() - ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
							if (curr <= 0) {
								this.onCharacterDeath();
	
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



		public void cure() throws InvalidTargetException, NoAvailableResourcesException, NotEnoughActionsException {
			if (this.getVaccineInventory().size() > 0) {
				if (this.getTarget() instanceof Zombie) {
					if (this.getActionsAvailable() > 0) {
						if (isAdjacent(this.getLocation(), this.getTarget().getLocation())) {
							Vaccine v = getVaccineInventory().get(getVaccineInventory().size() - 1);
							// Mechanics of curing
							int y = Game.availableHeroes.size();
							Random r = new Random();
							int yRand = r.nextInt(y);
							Hero h = Game.availableHeroes.get(yRand);
							Game.heroes.add(h);
							h.setLocation(this.getTarget().getLocation());
							CharacterCell zombieCell = (CharacterCell) Game.map[this.getTarget().getLocation().x][this.getTarget().getLocation().y];
							zombieCell.setCharacter(h);
							Game.zombies.remove(this.getTarget());
							this.getTarget().setLocation(null);
							// modifying Points and Array
							int x = this.getActionsAvailable();
							this.setActionsAvailable(--x);
							v.use(this);
						} else
							throw new InvalidTargetException("Invalid Target");
					} else
						throw new NotEnoughActionsException("No enough actions avaliable");
		
				} else
					throw new InvalidTargetException("Invalid target");
			} else
				throw new NoAvailableResourcesException("No vaccines available");
		}

	
		public void useSpecial() throws NotEnoughActionsException, NoAvailableResourcesException,InvalidTargetException {
			ArrayList<Supply>s=this.getSupplyInventory();
			if (this.getTarget() instanceof Zombie)
			   throw new InvalidTargetException();
			if(s.isEmpty())
			   throw new NoAvailableResourcesException();
			if (this.getActionsAvailable()==0||this.isSpecialAction()==false)
			  throw new NotEnoughActionsException();
			s.get(s.size()-1).use(this);
			
			
	
		}

		
}
