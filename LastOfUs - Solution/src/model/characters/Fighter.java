package model.characters;

import exceptions.*;

public class Fighter extends Hero {

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException, InvalidTargetException {
		super.useSpecial();

		this.attack();
	}

}
