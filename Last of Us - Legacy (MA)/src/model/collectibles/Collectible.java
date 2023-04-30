package model.collectibles;
import exceptions.NoAvailableResourcesException;
import model.characters.*;

public interface Collectible  {
    void pickUp(Hero h) throws NoAvailableResourcesException ;
    void use(Hero h) throws NoAvailableResourcesException;

}
