package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.characters.Character;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	
	public static Cell [][] map  = new Cell [15][15] ;
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>(10);
	
	
	
		
	public static void loadHeroes(String filePath)  throws IOException {
		
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();
			
			
		}
		br.close();

		
		
	}
	public static void endTurn() throws NotEnoughActionsException, InvalidTargetException{
		//iterates through zombie list and makes each zombie attack if possible
		//is there a way to make it all at once?
		int i = 0; int j = 0;
		while(i<Game.zombies.size()){
			Game.zombies.get(i).attackZombie();
			i++;
		}
		//resetting each heroes maxActions
		while(j<Game.heroes.size()){
			Game.heroes.get(i).setActionsAvailable(Game.heroes.get(i).getMaxActions());
			//here I assumed initial target of each hero is null
			Game.heroes.get(i).setTarget(null);
			Game.heroes.get(i).setSpecialAction(false);
			j++;
		}
		//Updating map //setting visibility of each adjacent cell to each hero to true
		for(int k = 0; k<Game.heroes.size() ; k++){
			ArrayList<Point> adjPoints = getAdjacent(Game.heroes.get(k).getLocation());
			for(int y = 0; y<adjPoints.size(); y++){
				CharacterCell heroCell = (CharacterCell) Game.map[adjPoints.get(y).x][adjPoints.get(y).y];
				heroCell.setVisible(true);
			}
		}
		//spawning a Zombie Randomly on the map
		Zombie z = new Zombie();
		//Randomizing point
		Point p = notOccRandomPointGenerator();
		z.setLocation(p);
		Game.zombies.add(z);
		//instead of initialising a variable I won't use
		new CharacterCell(z);

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
	public static ArrayList <Point> getAdjacent(Point p){
		//Point[] adjPoints = new Point[8];
		//Should it be 0 or 8?
		ArrayList <Point> adjPoints =  new ArrayList<Point>(0);
		int x = p.x;
		int y = p.y;
		Point p1 = new Point(x+1, y);
		Point p2 = new Point(x-1, y);
		Point p3 = new Point(x+1, y+1);
		Point p4 = new Point(x+1, y-1);
		Point p5 = new Point(x-1, y+1);
		Point p6 = new Point(x-1, y-1);
		Point p7 = new Point(x, y-1);
		Point p8 = new Point(x, y+1);
		if(isAdjacent(p1, p)){
			adjPoints.add(p1);
		}
		if(isAdjacent(p2, p)){
			adjPoints.add(p2);
		}
		if(isAdjacent(p3, p)){
			adjPoints.add(p3);
		}
		if(isAdjacent(p4, p)){
			adjPoints.add(p4);
		}
		if(isAdjacent(p5, p)){
			adjPoints.add(p5);
		}
		if(isAdjacent(p6, p)){
			adjPoints.add(p6);
		}
		if(isAdjacent(p7, p)){
			adjPoints.add(p7);
		}
		if(isAdjacent(p8, p)){
			adjPoints.add(p8);
		}
		return adjPoints;
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

	
}
