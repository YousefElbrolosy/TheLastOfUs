package model.characters;

import java.awt.Point;

import exceptions.*;

public class Medic extends Hero {
	// Heal amount attribute - quiz idea

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException, InvalidTargetException {

		if (this.getTarget() instanceof Zombie)
			throw new InvalidTargetException();
		if (!isAdjacent(this.getLocation(), this.getTarget().getLocation()))
			throw new InvalidTargetException(getName());
		super.useSpecial();

		if (this.getCurrentHp() < this.getMaxHp()) {
			this.setCurrentHp(this.getMaxHp());
			this.setSpecialAction(false);
		} else {
			this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
			this.setSpecialAction(false);

		}

	}

	public static boolean isAdjacent(Point point1, Point point2) {
		int x = (point2.x - point1.x);
		int y = (point2.y - point1.y);
		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (d == 1 || d == Math.sqrt(2))
			return true;
		else
			return false;
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
