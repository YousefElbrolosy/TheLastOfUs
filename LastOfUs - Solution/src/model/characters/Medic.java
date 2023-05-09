package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

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
		} else {
			this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
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

}
