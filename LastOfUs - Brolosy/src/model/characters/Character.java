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
	
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		//check attack on newLogic
		if(isAdjacent(this.getLocation(), this.getTarget().getLocation())){
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
		else throw new InvalidTargetException("Please select a valid target");
	}

	public void defend(Character c) throws InvalidTargetException{

		this.setTarget(c);
		c.setCurrentHp(c.getCurrentHp()-this.getAttackDmg()/2);
		if (c.getCurrentHp() <=0)
			c.onCharacterDeath();
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
		//if(Game.map[(int) this.getLocation().getY()][(int) this.getLocation().getX()] instanceof CharacterCell){
		//this made sure that the location is same
		CharacterCell characterCell = (CharacterCell) Game.map[this.getLocation().x][this.getLocation().y];
		characterCell.setCharacter(null);

			
		if(this instanceof Zombie){
			
			
			Game.zombies.remove((Zombie)this);
			Zombie z = new Zombie();
			//Randomizing point
			Point p = notOccRandomPointGenerator();
			z.setLocation(p);
			Game.zombies.add(z);
			//instead of initialising a variable I won't use
			new CharacterCell(z);

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

	public static Point notOccRandomPointGenerator(){
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
		if(!(((Game.map[(int) p.getX()][(int) p.getY()] instanceof CharacterCell)&& (((CharacterCell) (Game.map[(int) p.getX()][(int) p.getY()])).getCharacter()!=null))||
		(Game.map[(int) p.getX()][(int) p.getY()] instanceof TrapCell)||
		(Game.map[(int) p.getX()][(int) p.getY()] instanceof CollectibleCell)))
			return p;
		else 
			return notOccRandomPointGenerator();
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

	public static boolean isOccupied(Point p) {
		if (Game.map[p.x][p.y] instanceof CharacterCell) {
			if (((CharacterCell) Game.map[p.x][p.y]).getCharacter() == null)
				return false;
			else
				return true;

		}
		return false;

	}
}
