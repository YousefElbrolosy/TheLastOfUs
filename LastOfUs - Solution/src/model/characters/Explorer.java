package model.characters;

import engine.Game;
import exceptions.*;

public class Explorer extends Hero {

	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		super.useSpecial();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Game.map[i][j].setVisible(true);
			}
		}
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		int x = this.getActionsAvailable();
		if (x > 0) {

			x--;
			this.setActionsAvailable(x);
			super.attack();

		} else {
			throw new NotEnoughActionsException("no Enough actions avaliable");
		}
	}

}
