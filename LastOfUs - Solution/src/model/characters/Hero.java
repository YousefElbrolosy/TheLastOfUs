package model.characters;

import java.awt.Point;
import java.util.ArrayList;

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

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
		this.specialAction = false;
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

	// public void attack() throws InvalidTargetException, NotEnoughActionsException {
	// 	int x = this.getActionsAvailable();
	// 	if(this.getTarget()!=null){
	// 		if (this.getTarget() instanceof Zombie) {
	// 			if (!(this instanceof Fighter && isSpecialAction())) {
	// 				if (x > 0)
	// 					this.setActionsAvailable(--x);
	// 				else
	// 					throw new NotEnoughActionsException("No actions avaliable");
	// 			}
	// 			super.attack();
	// 		} else
	// 			throw new InvalidTargetException("Invalid target");
	// 	}
	// 	else
	// 		throw new InvalidTargetException("Invalid target");
	// }
	public void attack()throws InvalidTargetException,NotEnoughActionsException{
		if(this.getTarget()==null){
			throw new InvalidTargetException("Invalid target");
		}
		if(!(this.getTarget() instanceof Zombie))
			throw new InvalidTargetException("Invalid Target");
		else{
			super.attack();
		}
	}

	public void useSpecial() throws NotEnoughActionsException, NoAvailableResourcesException, InvalidTargetException {
		ArrayList<Supply> s = this.getSupplyInventory();
		if (s.isEmpty())
			throw new NoAvailableResourcesException("No supplies");
		if (this.isSpecialAction())
			throw new NoAvailableResourcesException("Already true");
		else
			this.setSpecialAction(true);
		s.get(s.size() - 1).use(this);

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
						int trapDmg = ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
						int curr = this.getCurrentHp() - trapDmg;
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
						int trapDmg = ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
						int curr = this.getCurrentHp() - trapDmg;
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
						int trapDmg = ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
						int curr = this.getCurrentHp() - trapDmg;
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
						int trapDmg = ((TrapCell) (Game.map[loc.x][loc.y])).getTrapDamage();
						int curr = this.getCurrentHp() - trapDmg;
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
			if (this.getCurrentHp() > 0) {
				Game.map[this.getLocation().x][this.getLocation().y].setVisible(true);
				for (int i = 0; i < Game.map.length; i++) {
					for (int j = 0; j < Game.map[i].length; j++) {
						if (isAdjacent(this.getLocation(), new Point(i, j))) {

							Game.map[i][j].setVisible(true);
						}
					}

				}
			} else
				this.onCharacterDeath();

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

	public void cure() throws InvalidTargetException,
			NoAvailableResourcesException, NotEnoughActionsException {
		if (Game.availableHeroes.size() <= 0) {
			throw new NoAvailableResourcesException("no heroes");
		}
		if (this.getVaccineInventory().size() > 0) {
			if (this.getTarget() instanceof Zombie) {
				if (this.getActionsAvailable() > 0) {
					if (isAdjacent(this.getLocation(), this.getTarget().getLocation())) {
						Vaccine v = this.getVaccineInventory().get(0);
						v.use(this);
						int x = this.getActionsAvailable();
						this.setActionsAvailable(--x);
					} else
						throw new InvalidTargetException("Invalid Target");
				} else
					throw new NotEnoughActionsException("No enough actions avaliable");

			} else
				throw new InvalidTargetException("Invalid target");
		} else
			throw new NoAvailableResourcesException("No vaccines available");
	}

}
