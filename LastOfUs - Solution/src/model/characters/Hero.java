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
	private static boolean specialTurn=false;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
		this.specialAction = false;
	}
    public boolean isSpecialTurn(){
		return specialTurn;
	}
	public void setSpecialTurn(boolean s){
		specialTurn=s;
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

	public void attack() throws InvalidTargetException, NotEnoughActionsException,NoAvailableResourcesException {
		ArrayList<Supply>y=this.getSupplyInventory();
		
			y.get(y.size()-1).use(this);
			super.attack();
			int x = this.getActionsAvailable();
        	if(!(this instanceof Fighter && isSpecialTurn()==true)){
				this.setActionsAvailable(x--);
			}
	}
	// n2esly hewar en ana a5ly el cells visible el 2bleh w hwa rayhla w deh i think
	// en ehna mafrood n5ly mn el awl el hero yb2a visible kol el adjacent cells el
	// hwaleh w msh 3aref ezay mafrood n access hewar el cell w hya asln abstract

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
				if (x < 0 || isOccupied(new Point(x, y)))
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
				if (y < 0 || isOccupied(new Point(x, y)))
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
				if (y > 14 || isOccupied(new Point(x, y)))
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

	public static boolean isAdjacent(Point point1, Point point2) {
		int x = (point2.x - point1.x);
		int y = (point2.y - point1.y);
		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (d == 1 || d == Math.sqrt(2))
			return true;
		else
			return false;
	}

	// or isoccupied that check for if instance of character cell
	public boolean isOccupied(Point p) {
		if (Game.map[p.x][p.y] instanceof CharacterCell) {
			if (((CharacterCell) Game.map[p.x][p.y]).getCharacter() == null)
				return false;
			else
				return true;

		}
		return false;

	}

	public boolean isTrapCell(Point p) {

		return (Game.map[p.x][p.y] instanceof TrapCell) ? true : false;

	}

	public void cure() throws InvalidTargetException, NoAvailableResourcesException {
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
						h.setLocation(this.getTarget().getLocation());
						CharacterCell zombieCell = (CharacterCell) Game.map[this.getTarget().getLocation().x][this
								.getTarget().getLocation().y];
						zombieCell.setCharacter(h);
						Game.zombies.remove(this.getTarget());
						this.getTarget().setLocation(null);
						// modifying Points and Array
						int x = this.getActionsAvailable();
						this.setActionsAvailable(x--);
						v.use(this);
					}
				}
			}
		}
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
