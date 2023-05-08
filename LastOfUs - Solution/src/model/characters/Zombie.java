package model.characters;

import engine.Game;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	// public void onCharacterDeath(Zombie dead) {
	// 	// Handling when health reaches zero is done in other methods where Health is
	// 	// reached 0
	// 	if (dead.getCurrentHp() <= 0) {
	// 		dead.setLocation(null);
	// 	}
	// 	Game.zombies.remove(dead);

	// }
}
