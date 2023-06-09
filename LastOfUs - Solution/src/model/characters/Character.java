package model.characters;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.*;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	public Character() {
	}

	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if (currentHp < 0)
			this.currentHp = 0;
		else if (currentHp > maxHp)
			this.currentHp = maxHp;
		else
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public static boolean isAdjacent(Point point1, Point point2) {
		if ((point1.x >= 0) && (point1.y >= 0) && (point2.y >= 0) && (point2.y >= 0)) {
			int x = (point2.x - point1.x);
			int y = (point2.y - point1.y);
			double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			if (d == 1 || d == Math.sqrt(2))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	

	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if (this.getTarget() == null) {
			throw new InvalidTargetException("Invalid Target1");
		}
		if ((this instanceof Hero && this.getTarget() instanceof Hero)) {
			throw new InvalidTargetException("Please select a valid target");
		}
		
		if (!(isAdjacent(this.getLocation(), this.getTarget().getLocation()))) {
			throw new InvalidTargetException("Please select a valid target");
		} 
		
		else {
			this.getTarget().setCurrentHp(this.target.getCurrentHp() - this.getAttackDmg());
			this.getTarget().defend(this);

			if (this.getTarget().getCurrentHp() <= 0) {
				
				this.getTarget().onCharacterDeath();
			} 
			

		}

	}

	public void defend(Character c) {
		this.setTarget(c);

		c.setCurrentHp(c.getCurrentHp() - (this.getAttackDmg() / 2));
		if (c.getCurrentHp() <= 0)
			c.onCharacterDeath();

	}

	public void onCharacterDeath() {
		Point loc = this.getLocation();
		Game.map[loc.x][loc.y] = new CharacterCell(null);
		// ((CharacterCell)Game.map[loc.x][loc.y]).setCharacter(null);;

		if (this instanceof Zombie) {
			Game.zombies.remove(this);
			Zombie z = new Zombie();
			Point p = notOccRandomPointGenerator();
			z.setLocation(p);
			Game.zombies.add(z);
			Game.map[p.x][p.y] = new CharacterCell(z);

		} else {
			Game.heroes.remove(this);
		}
	}

	public static Point notOccRandomPointGenerator() {
		Random r = new Random();
		int x = r.nextInt(15);
		int y = r.nextInt(15);
		Point p = new Point(x, y);

		if (!Occupied(p))
			return p;
		else
			return notOccRandomPointGenerator();
	}

	public static boolean Occupied(Point p) {
		if (Game.map[p.x][p.y] instanceof CharacterCell) {
			if (((CharacterCell) Game.map[p.x][p.y]).getCharacter() == null)
				return false;
			else
				return true;

		} else if (Game.map[p.x][p.y] instanceof TrapCell)
			return true;
		else if (Game.map[p.x][p.y] instanceof CollectibleCell)
			return true;
		else
			return false;

	}
}
