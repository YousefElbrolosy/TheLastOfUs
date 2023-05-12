package model.characters;

import exceptions.*;

public class Fighter extends Hero {

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {

		super.useSpecial();

	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		int x = this.getActionsAvailable();
		if (x > 0) {
			if (isSpecialAction()) {
				super.attack();

			}
			else{
				x--;
				this.setActionsAvailable(x);
				super.attack();

			}
		}
		else{
			throw new NotEnoughActionsException("no Enough actions avaliable");
		}
	}

}
