package entities;

import java.util.ArrayList;
import java.util.List;

import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.*;

public class Mycelium extends Fungus{
    boolean isDying = false;
    List<FungusBody> connectedBodies = new ArrayList<FungusBody>();

    /*public Mycelium(int health, Tile currentTile) {
        super(health, currentTile);
    }*/

    public Mycelium()
    {
        super();
        replace(this);
        printWrapper("Initializing Mycelium as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        printWrapper("Mycelium: "+UseCase.logger.get(this),ArrowDirection.LEFT);
    }

    // Reconnect with the mycelium network, recover health
    // Neighbor cells will be checked for mycelium
    public void connect(){
        // Will implement later
    }

    // Disconnect from the mycelium network, lose health
    public void detach(){
        // Will implement later
    }

    // Check if the mycelium has been connected to a body
    private boolean searchConnection(){
        // Will implement later
        return false;
    }
}
