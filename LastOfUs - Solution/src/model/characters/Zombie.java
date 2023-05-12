package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException {	
		this.setTarget(setForMe(this.getLocation()));
		super.attack();
		

	}

	public static Hero setForMe(Point x){
		for(int i =0 ; i<Game.map.length;i++){
			for(int j =0;j<Game.map[0].length;j++){
			if(isAdjacent(new Point(i,j), x))	
				if(Game.map[i][j] instanceof CharacterCell){
					if(((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero)	
							return (Hero) ((CharacterCell)Game.map[i][j]).getCharacter();
				}
			}
		}
		return null;
	}


}


