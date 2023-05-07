package model.characters;

import java.awt.Point;
import java.util.ArrayList;
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
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	public Character getTarget() {
		return target;
		
	}
	//this is important for attack rule
	public void setTarget(Character target){
		
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
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		//check attack on newLogic
		if((this instanceof Hero && this.getTarget() instanceof Zombie)||(this instanceof Zombie && this.getTarget() instanceof Hero)){
			this.getTarget().setCurrentHp(this.target.getCurrentHp()-this.getAttackDmg());

			if (this.getTarget().getCurrentHp() <=0){
				this.getTarget().defend(this);
				this.getTarget().onCharacterDeath();
			}
			else{
				this.getTarget().defend(this);
			}
					
	}
	else throw new InvalidTargetException("Please select a valid target");
	}
	
	public void attackZombie() throws NotEnoughActionsException, InvalidTargetException{
			int x = this.getLocation().x;
			int y = this.getLocation().y;

			Point p1 = new Point(x+1, y);
			Point p2 = new Point(x-1, y);
			Point p3 = new Point(x+1, y+1);
			Point p4 = new Point(x+1, y-1);
			Point p5 = new Point(x-1, y+1);
			Point p6 = new Point(x-1, y-1);
			Point p7 = new Point(x, y-1);
			Point p8 = new Point(x, y+1);
			
			if((isOccupiedHeroes(p1))){
				if((isAdjacent(this.getLocation(), p1))==true){
						this.setTarget(getOccupiedHeroes(p1));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p2))){
				if((isAdjacent(this.getLocation(), p2))==true){
						this.setTarget(getOccupiedHeroes(p2));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p3))){
				if((isAdjacent(this.getLocation(), p3))==true){
						this.setTarget(getOccupiedHeroes(p3));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p4))){
				if((isAdjacent(this.getLocation(), p4))==true){
						this.setTarget(getOccupiedHeroes(p4));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p5))){
				if((isAdjacent(this.getLocation(), p5))==true){
						this.setTarget(getOccupiedHeroes(p5));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p6))){
				if((isAdjacent(this.getLocation(), p6))==true){
						this.setTarget(getOccupiedHeroes(p6));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p7))){
				if((isAdjacent(this.getLocation(), p7))==true){
						this.setTarget(getOccupiedHeroes(p7));
						this.attack();
				}
			}
			if((isOccupiedHeroes(p8))){
				if((isAdjacent(this.getLocation(), p8))==true){
						this.setTarget(getOccupiedHeroes(p8));
						this.attack();
				}
			}


	}
			
	
	public void defend(Character c) throws InvalidTargetException{

		this.setTarget(c);
		c.setCurrentHp(c.getCurrentHp()-this.getAttackDmg()/2);
		if (c.getCurrentHp() <=0 )
			c.onCharacterDeath();
	}
	
public static boolean isAdjacent(Point point1, Point point2) {
	if ((point1.x>=0)&&(point1.y>=0)&(point2.y>=0)&&(point2.y>=0)){
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
//here I am assuming that the Zombie generated randomly on the map
public void onCharacterDeath(){
	//called only for attack not for cure guaranteeing respawning of zombie upon death
	//make character cell empty? so that it can be used as a trap cell for example if 
	//turn ended?
	//I dont remove last since last may be a Zombie that has Health < MaxHealth
		//when accessing zombies out of the list? Is the actions done on them updated?
		//int length = Game.zombies.size();
		//missing setting Character cell to be null
		if(Game.map[(int) this.getLocation().getY()][(int) this.getLocation().getX()] instanceof CharacterCell){
			//this made sure that the location is same
			CharacterCell characterCell = new CharacterCell(this);
			//this makes it null
			characterCell.setCharacter(null);
			

		}
	if(this instanceof Zombie){
		
		
		Game.zombies.remove((Zombie)this);
		Zombie z = new Zombie();
		//Randomizing point
		Point p = notOccRandomPointGenerator();
		z.setLocation(p);
		Game.zombies.add(z);
		// does where I place it in the new zombies List matter?
		
		
		/*Start of first comment
		Does this mean that I have to check before every operation 
		that the zombie is in the list?
		Should I handle target to become null?*/
		/*Start of 2nd comment
		 Random Generation of new Full Healthed zombie in map
		 */
		
	}
	else{
		if(this instanceof Hero){
			//need to search for this and remove it specifically
			//do I type cast it to hero?
			Game.heroes.remove((Hero) this);

		}
	}
	// I think this line is useless because since they are not in the array
	//they won't be used and therefore their locations won't be used
	this.setLocation(null);
}
public Point notOccRandomPointGenerator(){
	//length of columns (no. of rows)
	int numberOfRows = Game.map[0].length; 
	//length of rows (no. of columns)
	int numberOfColumns = Game.map.length;
	//based on the Point(row,column)
 	Random r = new Random();
	int xNew = r.nextInt(numberOfRows);
	int yNew = r.nextInt(numberOfColumns);
	Point p = new Point();
	p.x = xNew;
	p.y = yNew;
	/* 
	if((isOccupiedZombies(p) && isOccupiedHeroes(p)) == false)*/
	//note that here x and y are inverted
	if(!((Game.map[(int) p.getY()][(int) p.getX()] instanceof CharacterCell)||
	(Game.map[(int) p.getY()][(int) p.getX()] instanceof TrapCell)||
	(Game.map[(int) p.getY()][(int) p.getX()] instanceof CollectibleCell)))
		return p;
	else 
		return notOccRandomPointGenerator();
}
public boolean isOccupiedZombies(Point p){
	int i = 0;
	while(i<Game.zombies.size()){
		if(Game.zombies.get(i).getLocation()==p){
			return true;
		}
		else{
			i++;
		}
	}
	return false;


}
//or isoccupied that check for if intance of character cell
public boolean isOccupiedHeroes(Point p){
	int i = 0;
	while(i<Game.heroes.size()){
		if(Game.heroes.get(i).getLocation()==p){
			return true;
		}
		else{
			i++;
		}
	}
	return false;


}	
public Character getOccupiedHeroes(Point p){
	int i = 0;
	while(i<Game.heroes.size()){
		if(Game.heroes.get(i).getLocation()==p){
			return Game.heroes.get(i);
		}
		else{
			i++;
		}
	}
	return null;


}	
}

//extends is for farafeer