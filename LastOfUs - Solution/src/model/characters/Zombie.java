package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException {
		if (this.getTarget() instanceof Zombie){
			throw new InvalidTargetException();
		}
		
		super.attack();

	}
	public boolean isOccupied(Point p) {
		if (Game.map[p.x][p.y] instanceof CharacterCell) {
			if (((CharacterCell) Game.map[p.x][p.y]).getCharacter() == null)
				return false;
			else
				return true;

		}
		return false;

	}

// 	public void attackZombie() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException{
// 		int x = this.getLocation().x;
// 		int y = this.getLocation().y;

// 		Point p1 = new Point(x+1, y);
// 		Point p2 = new Point(x-1, y);
// 		Point p3 = new Point(x+1, y+1);
// 		Point p4 = new Point(x+1, y-1);
// 		Point p5 = new Point(x-1, y+1);
// 		Point p6 = new Point(x-1, y-1);
// 		Point p7 = new Point(x, y-1);
// 		Point p8 = new Point(x, y+1);
// 	/* 
// 		for(int i = 0; i<Game.map.length;i++){
// 			for(int j = 0; j<Game.map[0].length;j++){
// 				Point p1 = new Point(i, j);
// 				if((isAdjacent(z.getLocation(), p1))==true){
// 					z.setTarget(getOccupiedHeroes(p1));
// 					z.attack();
// 				}
// 			}
// 		} 
// */	
// 		if((isOccupiedHeroes(p1))){
// 			if((isAdjacent(this.getLocation(), p1))==true){
// 					this.setTarget(getOccupiedHeroes(p1));
// 					this.attack();
// 			}
// 		}
// 		else if((isOccupiedHeroes(p2))){
// 				if((isAdjacent(this.getLocation(), p2))==true){
// 						this.setTarget(getOccupiedHeroes(p2));
// 						this.attack();
// 				}
// 			}
// 			else if((isOccupiedHeroes(p3))){
// 					if((isAdjacent(this.getLocation(), p3))==true){
// 							this.setTarget(getOccupiedHeroes(p3));
// 							this.attack();
// 					}
// 			}
// 			else if((isOccupiedHeroes(p4))){
// 					if((isAdjacent(this.getLocation(), p4))==true){
// 							this.setTarget(getOccupiedHeroes(p4));
// 							this.attack();
// 					}
// 			}
// 			else if((isOccupiedHeroes(p5))){
// 					if((isAdjacent(this.getLocation(), p5))==true){
// 							this.setTarget(getOccupiedHeroes(p5));
// 							this.attack();
// 					}
// 			}
// 			else if((isOccupiedHeroes(p6))){
// 				if((isAdjacent(this.getLocation(), p6))==true){
// 						this.setTarget(getOccupiedHeroes(p6));
// 						this.attack();
// 				}
// 			}
// 			else if((isOccupiedHeroes(p7))){
// 					if((isAdjacent(this.getLocation(), p7))==true){
// 							this.setTarget(getOccupiedHeroes(p7));
// 							this.attack();
// 					}
// 			}
// 			else if((isOccupiedHeroes(p8))){
// 					if((isAdjacent(this.getLocation(), p8))==true){
// 							this.setTarget(getOccupiedHeroes(p8));
// 							this.attack();
// 					}
// 			}

// }
/* 
public static boolean isOccupiedHeroes(Point p){
	int i = 0;
	while(i++<Game.heroes.size()){
		if(Game.heroes.get(i).getLocation()==p){
			return true;
		}
		
	}
	return false;


}	
public static Hero getOccupiedHeroes(Point p){
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
*/
}


