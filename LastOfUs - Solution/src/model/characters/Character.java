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
		if (!isAdjacent(targetLoc, characterLoc) || this.getTarget() == null || !(this.getTarget() instanceof Zombie))
			throw new InvalidTargetException("Cannot attack this cell");

		else {
			this.target.setCurrentHp(this.target.getCurrentHp() - this.getAttackDmg());
			if (this.target.getCurrentHp() <= 0) {
				this.target.onCharacterDeath();
			}
			else{
				this.target.defend(this);
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
			Game.map[loc.x][loc.y] = new CharacterCell(null);
			Game.zombies.remove(this);
			Zombie z = new Zombie();
			Point p = notOccRandomPointGenerator();
			z.setLocation(p);
			Game.zombies.add(z);
			Game.map[p.x][p.y] = new CharacterCell(z);

		} else  {
				this.setLocation(null);
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

	// public void attackZombie() throws NotEnoughActionsException,
	// InvalidTargetException {
	// int x = this.getLocation().x;
	// int y = this.getLocation().y;

	// Point p1 = new Point(x + 1, y);
	// Point p2 = new Point(x - 1, y);
	// Point p3 = new Point(x + 1, y + 1);
	// Point p4 = new Point(x + 1, y - 1);
	// Point p5 = new Point(x - 1, y + 1);
	// Point p6 = new Point(x - 1, y - 1);
	// Point p7 = new Point(x, y - 1);
	// Point p8 = new Point(x, y + 1);

	// if ((isOccupiedHeroes(p1))) {
	// if ((isAdjacent(this.getLocation(), p1)) == true) {
	// this.setTarget(getOccupiedHeroes(p1));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p2))) {
	// if ((isAdjacent(this.getLocation(), p2)) == true) {
	// this.setTarget(getOccupiedHeroes(p2));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p3))) {
	// if ((isAdjacent(this.getLocation(), p3)) == true) {
	// this.setTarget(getOccupiedHeroes(p3));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p4))) {
	// if ((isAdjacent(this.getLocation(), p4)) == true) {
	// this.setTarget(getOccupiedHeroes(p4));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p5))) {
	// if ((isAdjacent(this.getLocation(), p5)) == true) {
	// this.setTarget(getOccupiedHeroes(p5));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p6))) {
	// if ((isAdjacent(this.getLocation(), p6)) == true) {
	// this.setTarget(getOccupiedHeroes(p6));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p7))) {
	// if ((isAdjacent(this.getLocation(), p7)) == true) {
	// this.setTarget(getOccupiedHeroes(p7));
	// this.attack();
	// }
	// }
	// if ((isOccupiedHeroes(p8))) {
	// if ((isAdjacent(this.getLocation(), p8)) == true) {
	// this.setTarget(getOccupiedHeroes(p8));
	// this.attack();
	// }
	// }

	// }

	public static ArrayList<Point> getAdjacent(Point p) {
		// Point[] adjPoints = new Point[8];
		ArrayList<Point> adjPoints = new ArrayList<Point>(0);
		int x = p.x;
		int y = p.y;
		Point p1 = new Point(x + 1, y);
		Point p2 = new Point(x - 1, y);
		Point p3 = new Point(x + 1, y + 1);
		Point p4 = new Point(x + 1, y - 1);
		Point p5 = new Point(x - 1, y + 1);
		Point p6 = new Point(x - 1, y - 1);
		Point p7 = new Point(x, y - 1);
		Point p8 = new Point(x, y + 1);
		if (isAdjacent(p1, p)) {
			adjPoints.add(p1);
		}
		if (isAdjacent(p2, p)) {
			adjPoints.add(p2);
		}
		if (isAdjacent(p3, p)) {
			adjPoints.add(p3);
		}
		if (isAdjacent(p4, p)) {
			adjPoints.add(p4);
		}
		if (isAdjacent(p5, p)) {
			adjPoints.add(p5);
		}
		if (isAdjacent(p6, p)) {
			adjPoints.add(p6);
		}
		if (isAdjacent(p7, p)) {
			adjPoints.add(p7);
		}
		if (isAdjacent(p8, p)) {
			adjPoints.add(p8);
		}
		return adjPoints;
	}

	// or isoccupied that check for if intance of character cell
	public static Character getOccupiedHeroes(Point p) {
		int i = 0;
		while (i < Game.heroes.size()) {
			if (Game.heroes.get(i).getLocation() == p) {
				return Game.heroes.get(i);
			} else {
				i++;
			}
		}
		return null;

	}
}

// //this is important for attack rule
// public void setTarget(Character target)throws InvalidTargetException{
// // the reason I removed the condition that both must not be heroes is that
// // A medic can set another hero as its target to heal it
// if(target != this){
// if(isAdjacent(this.getLocation(), target.getLocation())){
// if(this instanceof Hero){
// if(target instanceof Zombie){
// this.target = target;
// System.out.print("Now target of Hero is Zombie");
// }
// else throw new InvalidTargetException("Please Select a valid Target");
// }
// }
// else throw new InvalidTargetException("Please Select a valid Target");
// }
// else throw new InvalidTargetException("Please Select a valid Target");

// //this.target = target;
// // I think target is set by if there is a character in the cell you want to
// move to
// // if last move was to the left then left is target when attacking
// // if last move was to the left and there is a zombie in front of you
// // as well as a zombie to the right of that zombie
// // priority of target goes to the one right in front first
// //then if death set target to diagonally adjacent right, then diagonally
// adjacent left?
// // or when I kill A zombie I take its place? no
// /*

// summing up, always kill the zombie right in front of you, if you went left
// imagine
// that the character looked left and found a zombie in front of him
// that's priority 1
// priority 2 if he didn't find an immediantly adjacent zombie tackle right
// diagonal
// before left diagonal
// */
// }

// public String getName() {
// return name;
// }

// public Point getLocation() {
// return location;
// }

// public void setLocation(Point location) {
// this.location = location;
// }

// public int getMaxHp() {
// return maxHp;
// }

// public int getCurrentHp() {
// return currentHp;
// }

// public void setCurrentHp(int currentHp) {
// if(currentHp < 0)
// this.currentHp = 0;
// else if(currentHp > maxHp)
// this.currentHp = maxHp;
// else
// this.currentHp = currentHp;
// }

// public int getAttackDmg() {
// return attackDmg;
// }
// public void attack() throws NotEnoughActionsException, InvalidTargetException
// {
// //check attack on newLogic
// this.getTarget().setCurrentHp(this.target.getCurrentHp()-this.getAttackDmg());

// if (this.getTarget().getCurrentHp() <=0){
// this.getTarget().defend(this);
// this.getTarget().onCharacterDeath();
// }
// else{
// this.getTarget().defend(this);
// }

// }

// public void defend(Character c) throws InvalidTargetException{

// this.setTarget(c);
// c.setCurrentHp(c.getCurrentHp()-this.getAttackDmg()/2);
// if (c.getCurrentHp() <=0 )
// c.onCharacterDeath();
// }

// /*
// public void attack(Character target) throws NotEnoughActionsException,
// InvalidTargetException{
// Hero h = (Hero) this;

// int actions_available = h.getActionsAvailable();
// //if ((this == h && target == z)||(this == z && target == h)){
// //if (this!=target){ (equivilant as above)
// if((this.getAdjacency(target) == true) && (this!=target)){
// if(this.getTarget() == target){
// // does zombie has actions available?
// // missing handling special ability if this was fighter and ability is used
// // should I immplement attack seperately in each sub class? why implement it
// in character in
// // 1st place?
// if (this == h){
// if(actions_available>0){
// actions_available--;
// }
// else{
// throw new NotEnoughActionsException("There aren't enough actions");
// }
// }
// int currHp = target.getCurrentHp();
// int newHp = currHp - this.getAttackDmg();
// target.setCurrentHp(newHp);
// }
// }
// else{
// throw new InvalidTargetException("Invalid target");
// }

// }
// */
// /*

// */

// //here I am assuming that the Zombie generated randomly on the map
// public void onCharacterDeath(){
// //called only for attack not for cure guaranteeing respawning of zombie upon
// death
// //make character cell empty? so that it can be used as a trap cell for
// example if
// //turn ended?
// if(this instanceof Zombie){
// //I dont remove last since last may be a Zombie that has Health < MaxHealth
// //when accessing zombies out of the list? Is the actions done on them
// updated?
// //int length = Game.zombies.size();
// //missing setting Character cell to be null
// if(Game.map[(int) this.getLocation().getX()][(int) this.getLocation().getY()]
// instanceof CharacterCell){
// //this made sure that the location is same
// CharacterCell characterCell = new CharacterCell(this);
// //this makes it null
// characterCell.setCharacter(null);

// }

// Game.zombies.remove((Zombie)this);
// Zombie z = new Zombie();
// //Randomizing point
// Point p = notOccRandomPointGenerator();
// z.setLocation(p);
// Game.zombies.add(z);
// // does where I place it in the new zombies List matter?

// /*Start of first comment
// Does this mean that I have to check before every operation
// that the zombie is in the list?
// Should I handle target to become null?*/
// /*Start of 2nd comment
// Random Generation of new Full Healthed zombie in map
// */

// }
// else{
// if(this instanceof Hero){
// //need to search for this and remove it specifically
// //do I type cast it to hero?
// Game.heroes.remove((Hero) this);

// }
// }
// // I think this line is useless because since they are not in the array
// //they won't be used and therefore their locations won't be used
// this.setLocation(null);
// }
// public Point notOccRandomPointGenerator(){
// //length of columns (no. of rows)
// int numberOfRows = Game.map[0].length;
// //length of rows (no. of columns)
// int numberOfColumns = Game.map.length;
// //based on the Point(row,column)
// Random r = new Random();
// int xNew = r.nextInt(numberOfRows);
// int yNew = r.nextInt(numberOfColumns);
// Point p = new Point();
// p.x = xNew;
// p.y = yNew;
// if((isOccupiedZombies(p) && isOccupiedHeroes(p)) == false)
// return p;
// else
// return notOccRandomPointGenerator();
// }
// public boolean isOccupiedZombies(Point p){
// /*Zombie z = new Zombie();
// z.setLocation(p);
// if(Game.zombies.contains(z)){
// return true;
// }*/
// int i = 0;
// while(i<Game.zombies.size()){
// if(Game.zombies.get(i).getLocation()==p){
// return true;
// }
// else{
// i++;
// }
// }
// return false;

// }
// public boolean isOccupiedHeroes(Point p){
// /*Zombie z = new Zombie();
// z.setLocation(p);
// if(Game.zombies.contains(z)){
// return true;
// }*/
// int i = 0;
// while(i<Game.heroes.size()){
// if(Game.heroes.get(i).getLocation()==p){
// return true;
// }
// else{
// i++;
// }
// }
// return false;

// }
// /*
// public void onCharacterDeath(Character dead){
// //Handling when health reaches zero is done in other methods where Health is
// reached 0
// if (dead.getCurrentHp()<=0){
// dead.setLocation(null);
// }
// }
// */

// }

// //extends is for farafeer