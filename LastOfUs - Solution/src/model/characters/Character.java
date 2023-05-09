package model.characters;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

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

	// check if the characters are adjacent and returns boolean
	public static boolean isAdjacent(Point point1, Point point2) {
		int x = (point2.x - point1.x);
		int y = (point2.y - point1.y);
		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (d == 1 || d == Math.sqrt(2))
			return true;
		else
			return false;
	}

	// attacking method
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		Point targetLoc = getTarget().location;
		Point characterLoc = this.getLocation();
		if (!isAdjacent(targetLoc, characterLoc) || this.getTarget() == null ||!(this.getTarget() instanceof Zombie))
			throw new InvalidTargetException("Cannot attack this cell");

		else {
			this.getTarget().setCurrentHp(this.target.getCurrentHp() - this.getAttackDmg());
			if (this.getTarget().getCurrentHp() <= 0) {
				this.getTarget().defend(this);
				this.getTarget().onCharacterDeath();
			}
			else{
				this.getTarget().defend(this);
			}
		}

	}

	// Defend method lw 2rena satr b satr fl example el hwa katbo fa dah el mafrood
	// yt3ml
	// ya3ny hwa 2l en el hero will attack el target tmmam w ba3deen hayn2s meno el
	// attack dmg w dah elana 3amlto f attack then el target hy defend w 2l hay3ml
	// eh fl defend 2l eno hy set el target bta3 el taregt yb2a el hero w y attack
	// 3aleh b nos el attack dmg

	public void defend(Character c) {

		this.setTarget(c);
		c.setCurrentHp(c.getCurrentHp() - this.getAttackDmg() / 2);
		if (c.getCurrentHp() <= 0)
			c.onCharacterDeath();

	}

	public void onCharacterDeath() {
		Point loc = this.getLocation();
		if (this instanceof Zombie) {
			((CharacterCell)Game.map[loc.x][loc.y]).setCharacter(null);;
			Game.zombies.remove(this);
			Zombie z = new Zombie();
			Point p = notOccRandomPointGenerator();
			z.setLocation(p);
			Game.zombies.add(z);
			Game.map[p.x][p.y] = new CharacterCell(z);

		} else  {
				Game.map[loc.x][loc.y] = new CharacterCell(null);
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