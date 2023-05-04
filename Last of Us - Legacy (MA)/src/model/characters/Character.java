package model.characters;

import java.awt.Point;

import exceptions.InvalidTargetException;
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
	//this is important for attack rule
	public void setTarget(Character target)throws InvalidTargetException{
		if(target != this){
			if(isAdjacent(location, target.getLocation())){
				if(this instanceof Zombie){
					if(target instanceof Hero){
						this.target = target;
						System.out.print("Now target of zombie is hero");
					}
					else throw new InvalidTargetException("Please Select a valid Target");
				}
			}
			else throw new InvalidTargetException("Please Select a valid Target");
		}
		else throw new InvalidTargetException("Please Select a valid Target");


		//this.target = target;
		// I think target is set by if there is a character in the cell you want to move to
		// if last move was to the left then left is target when  attacking
		// if last move was to the left and there is a zombie in front of you 
		// as well as a zombie to the right of that zombie
		// priority of target goes to the one right in front first
		//then if death set target to diagonally adjacent right, then diagonally adjacent left?
		// or when I kill A zombie I take its place? no
		/*

		 summing up, always kill the zombie right in front of you, if you went left imagine
		 that the character looked left and found a zombie in front of him
		 that's priority 1 
		 priority 2 if he didn't find an immediantly adjacent zombie tackle right diagonal
		 before left diagonal
		*/
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
	public void attack(Character target) throws NotEnoughActionsException, InvalidTargetException{
		Hero h = (Hero) this;
		
		int actions_available = h.getActionsAvailable();
	//if ((this == h && target == z)||(this == z && target == h)){
	//if (this!=target){ (equivilant as above)	
	if((this.getAdjacency(target) == true) && (this!=target)){
		if(this.getTarget() == target){
// does zombie has actions available?
// missing handling special ability if this was fighter and ability is used
// should I immplement attack seperately in each sub class? why implement it in character in 
// 1st place?
			if (this == h){
				if(actions_available>0){
					actions_available--;
				}
				else{
					throw new NotEnoughActionsException("There aren't enough actions");
				}
			}
			int currHp = target.getCurrentHp();
			int newHp = currHp - this.getAttackDmg();
			target.setCurrentHp(newHp);
		}
	}
	else{
		throw new InvalidTargetException("Invalid target");
	}	

	}
	/* 
	public boolean getAdjacency(Character target){
		// is it better to use | instead of || ?
		if((location.getX() == target.getLocation().getX())
		| (location.getY() == target.getLocation().getY())
		| ( (location.getX() + location.getY()) ==
		(target.getLocation().getX() + target.getLocation().getY()) )
		| ( (location.getX() - location.getY()) ==
		(target.getLocation().getX() - target.getLocation().getY()) )
		){
			return true;
			// Hi baskat Sameh Sakr
		}
		else
			return false;
			
	}
*/
public static boolean isAdjacent(Point point1, Point point2) {
	int x =(point2.x-point1.x);
	int y = (point2.y-point1.y);
	double d = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	if(d==1 || d==Math.sqrt(2))
		return true;
	else 
		return false ; 
}


public void onCharacterDeath(Character dead){
	//Handling when health reaches zero is done in other methods where Health is reached 0
	if (dead.getCurrentHp()<=0){
		dead.setLocation(null);
	}
}








	
	
}

//extends is for farafeer