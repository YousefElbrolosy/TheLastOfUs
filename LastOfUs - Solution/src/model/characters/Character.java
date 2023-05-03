package model.characters;

import java.awt.Point;
import java.lang.Math;

import exceptions.MovementException;
import exceptions.NotEnoughActionsException;

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

	// check if the characters are adjacent and returns boolean 
	public static boolean isAdjacent(Point point1, Point point2) {
		int x =(point2.x-point1.x);
		int y = (point2.y-point1.y);
		double d = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		if(d==1 || d==Math.sqrt(2))
			return true;
		else 
			return false ; 
	}

	// attacking method 
	public void attack() throws NotEnoughActionsException, MovementException {
		Point targetLoc = getTarget().location;
		Point characterLoc = this.getLocation();
		if (!isAdjacent(targetLoc, characterLoc))
			throw new MovementException("Cannot attack this cell");
		else {
			// this.target.setCurrentHp(this.target.getCurrentHp()-this.getAttackDmg());
			if (this.target.getCurrentHp() ==0)

				this.target.onCharacterDeath();

			else if (this.getCurrentHp() ==0 )

				this.onCharacterDeath();
			else

				this.target.defend(this);
	}

	}
	
	// Defend method 
	public void defend(Character target){
		target.setCurrentHp(target.getCurrentHp()-this.getAttackDmg());
		this.setCurrentHp(this.getCurrentHp()- (this.target.getAttackDmg()/2) );
	}


	// On character Death method 
	public void onCharacterDeath(){
		//Handling when health reaches zero is done in other methods where Health is reached 0
		// if (this.target.getCurrentHp()<=0){
		// 	this.target.setLocation(null) ;
		// }
	}

	
}
