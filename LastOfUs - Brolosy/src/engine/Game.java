package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.*;

public class Game {
	
	public static Cell [][] map ;
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	
	
	
		
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

	public static Point generatePoint() {
		Random random = new Random();
		int x = random.nextInt(15);
		int y = random.nextInt(15);
		Point res = new Point(x, y);
		return res;
	}

	public static void startGame(Hero h) {
		map = new Cell[15][15];
		int i1 = 0;
		while (i1 < 5) {
			Vaccine v = new Vaccine();
			CollectibleCell c = new CollectibleCell(v);
			Point p = generatePoint();
			if (map[p.x][p.y] == null) {

				map[p.x][p.y] = c;
			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();
				}
				map[p.x][p.y] = c;
			}
			i1++;
		}
		int i2 = 0;
		while (i2 < 5) {
			Supply v = new Supply();
			CollectibleCell c = new CollectibleCell(v);
			Point p = generatePoint();
			if (map[p.x][p.y] == null) {
				map[p.x][p.y] = c;
			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();

				}
				map[p.x][p.y] = c;
			}
			i2++;
		}

		int i3 = 0;
		while (i3 < 10) {
			Zombie v = new Zombie();
			zombies.add(v);
			CharacterCell c = new CharacterCell(v);
			Point p = generatePoint();
			Point loc;
			if (map[p.x][p.y] == null) {

				map[p.x][p.y] = c;
				loc = new Point(p.x, p.y);

			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();
				}
				map[p.x][p.y] = c;
				loc = new Point(p.x, p.y);
			}
			v.setLocation(loc);

			i3++;
		}

		int i4 = 0;
		while (i4 < 5) {
			TrapCell v = new TrapCell();
			Point p = generatePoint();
			if (map[p.x][p.y] == null) {
				map[p.x][p.y] = v;
			} else {
				while (map[p.x][p.y] != null) {
					p = generatePoint();

				}
				map[p.x][p.y] = v;
			}
			i4++;
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == null) {
					map[i][j] = new CharacterCell(null);
				}
			}
		}

		map[0][0] = new CharacterCell(h);
		map[0][0].setVisible(true);
		heroes.add(h);
		availableHeroes.remove(h);
		h.setLocation(new Point(0, 0));
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (isAdjacent(h.getLocation(), new Point(i, j))) {
					map[i][j].setVisible(true);
				}
			}
		}
	}

	public static boolean checkWin() {
		int hero = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell z = map[i][j];
				if (z instanceof CollectibleCell) {
					Collectible x = ((CollectibleCell) map[i][j]).getCollectible();
					if (x instanceof Vaccine) {
						return false;
					}
				}
				if (z instanceof CharacterCell) {
					Character x = ((CharacterCell) map[i][j]).getCharacter();
					if (x instanceof Hero) {
						hero++;
						if (((Hero) x).getVaccineInventory().size() != 0)
							return false;
					}

				}

			}

		}
		if (hero < 5)
			return false;
		else
			return true;
	}

	public static boolean checkGameOver() {
		boolean flagvacc = true;
		boolean flagused = true;
		boolean flagheroes = true;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell z = map[i][j];
				if (z instanceof CollectibleCell) {
					Collectible x = ((CollectibleCell) map[i][j]).getCollectible();
					if (x instanceof Vaccine) {
						flagvacc = false;
					}
				}
			}
		}
		for (int i = 0; i < heroes.size(); i++) {
			if (heroes.get(i).getVaccineInventory().size() != 0)
				flagused = false;
		}
		if (availableHeroes.size() != 0 || heroes.size() != 0)
			flagheroes = false;
		return (flagvacc && flagused) || flagheroes;

	}

	public static void endTurn() throws NotEnoughActionsException, InvalidTargetException{
		//iterates through zombie list and makes each zombie attack if possible
		//is there a way to make it all at once?
		int i = 0; int j = 0;
		while(i<Game.zombies.size()){
			attackZombie(Game.zombies.get(i));
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
		//setting visibility of whole map to false
		int x = 0;
		int y = 0;
		//x denotes no of rows and y denotes no of columns
		while(x<Game.map.length){
				Cell cell = Game.map[x][y];
				cell.setVisible(false);
				x++;
		}
		while(y<Game.map[0].length){
			Cell cell = Game.map[x-1][y];
			cell.setVisible(false);
			y++;
		}
			
		
		//Updating map //setting visibility of each adjacent cell to each hero to true
		for(int k = 0; k<Game.heroes.size() ; k++){
			ArrayList<Point> adjPoints = getAdjacent(Game.heroes.get(k).getLocation());
			for(y = 0; y<adjPoints.size(); y++){
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
	public static void attackZombie(Zombie z) throws NotEnoughActionsException, InvalidTargetException{
		// int x = z.getLocation().x;
		// int y = z.getLocation().y;
/* 
		Point p1 = new Point(x+1, y);
		Point p2 = new Point(x-1, y);
		Point p3 = new Point(x+1, y+1);
		Point p4 = new Point(x+1, y-1);
		Point p5 = new Point(x-1, y+1);
		Point p6 = new Point(x-1, y-1);
		Point p7 = new Point(x, y-1);
		Point p8 = new Point(x, y+1);
		*/
		for(int i = 0; i<Game.map.length;i++){
			for(int j = 0; j<Game.map[0].length;j++){
				Point p1 = new Point(i, j);
				if((isAdjacent(z.getLocation(), p1))==true){
					z.setTarget(getOccupiedHeroes(p1));
					z.attack();
				}
			}
		} 
/* 		
		if((isOccupiedHeroes(p1))){
			if((isAdjacent(z.getLocation(), p1))==true){
					z.setTarget(getOccupiedHeroes(p1));
					z.attack();
			}
		}
		else if((isOccupiedHeroes(p2))){
				if((isAdjacent(z.getLocation(), p2))==true){
						z.setTarget(getOccupiedHeroes(p2));
						z.attack();
				}
			}
			else if((isOccupiedHeroes(p3))){
					if((isAdjacent(z.getLocation(), p3))==true){
							z.setTarget(getOccupiedHeroes(p3));
							z.attack();
					}
			}
			else if((isOccupiedHeroes(p4))){
					if((isAdjacent(z.getLocation(), p4))==true){
							z.setTarget(getOccupiedHeroes(p4));
							z.attack();
					}
			}
			else if((isOccupiedHeroes(p5))){
					if((isAdjacent(z.getLocation(), p5))==true){
							z.setTarget(getOccupiedHeroes(p5));
							z.attack();
					}
			}
			else if((isOccupiedHeroes(p6))){
				if((isAdjacent(z.getLocation(), p6))==true){
						z.setTarget(getOccupiedHeroes(p6));
						z.attack();
				}
			}
			else if((isOccupiedHeroes(p7))){
					if((isAdjacent(z.getLocation(), p7))==true){
							z.setTarget(getOccupiedHeroes(p7));
							z.attack();
					}
			}
			else if((isOccupiedHeroes(p8))){
					if((isAdjacent(z.getLocation(), p8))==true){
							z.setTarget(getOccupiedHeroes(p8));
							z.attack();
					}
			}
*/
}

	public static boolean isOccupiedHeroes(Point p){
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

		
}
