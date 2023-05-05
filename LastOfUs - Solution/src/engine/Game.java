package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class Game {
	
	public static Cell [][] map=new Cell[15][15] ;
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
	public static Point generatePoint(){
		int x=(int)(Math.random()*16);
		int y=(int)(Math.random()*16);
		Point res=new Point(x,y);
		return res;
	}
    public static void startGame(Hero h){
		int i1=0;
		while(i1<5){
			Vaccine v=new Vaccine();
			CollectibleCell c=new CollectibleCell(v);
			Point p= generatePoint();
			if (map[p.x][p.y]==null){
				
				map[p.x][p.y]=c;
			}else{
				while(map[p.x][p.y]!=null){
                    p= generatePoint();
					if (map[p.x][p.y]==null)
					    map[p.x][p.y]=c;
				}
			}
			i1++;
		}
		int i2=0;
		while(i2<5){
			Supply v=new Supply();
			CollectibleCell c=new CollectibleCell(v);
			Point p=generatePoint();
			if (map[p.x][p.y]==null){
				map[p.x][p.y]=c;
			}else{
				while(map[p.x][p.y]!=null){
                     p=generatePoint();
					if (map[p.x][p.y]==null)
					    map[p.x][p.y]=c;
				}
			}
			i2++;
		}
		int i3=0;
		while(i3<10){
			Zombie v=new Zombie();
			zombies.add(v);
			CharacterCell c=new CharacterCell(v);
			Point p=new Point();
			if (map[p.x][p.y]==null){
				
				map[p.x][p.y]=c;
			}else{
				while(map[p.x][p.y]!=null){
                    p=generatePoint();
					if (map[p.x][p.y]==null)
					    map[p.x][p.y]=c;
				}
			}
			i3++;
		}
	}

    
}
