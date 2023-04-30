package model.characters;

import java.awt.Point;


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
		this.name=name;
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
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
		public void attack() throws InvalidTargetException, NotEnoughActionsException {
		Point targetLoc = getTarget().location;
		Point characterLoc = this.getLocation();
		if (!isAdjacent(targetLoc, characterLoc))
			throw new InvalidTargetException("Cannot attack this cell");
//		else {
//			if (this.getAttackDmg() >= this.target.getCurrentHp()) {
//				this.target.setCurrentHp(0);
//				this.target = null;
//			} else {
//				while (this.getCurrentHp() == 0 || this.target.getCurrentHp() == 0) {
//					this.setCurrentHp(this.getCurrentHp() - this.target.getAttackDmg());
//					this.target.setCurrentHp(this.target.getCurrentHp() - this.getAttackDmg());
//				}
//				if (this.target.getCurrentHp() == 0)
//					this.target = null;
//			}
//
//		} Haso da goz2 defend aktr 

		else {
			this.target.setCurrentHp(this.target.getCurrentHp()-this.getAttackDmg());
		}

	}


	public static boolean isAdjacent(Point point1, Point point2) {
		int x = point1.x + point1.y ;
		int y = point2.x + point2.y ;
		if (x-y==0 ||Math.abs(x-y)==1)
			return true;
		if(Math.abs(x-y)==2 && ((x==y)||(point1.x - point1.y)==(point2.x - point2.y)))
		return true;
		
		else
			return false;
	}
	public static void main(String[] args) {
		Point x = new Point(0,0);
		Point y = new Point(2,0);
		System.out.print(isAdjacent(x,y));
	}


}
