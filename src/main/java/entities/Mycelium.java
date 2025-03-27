package entities;

import java.util.ArrayList;
import java.util.List;

import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.*;

public class Mycelium extends Fungus{
    int maxHealth = 5; 
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

    @Override
    public void update() {

        printWrapper("Mycelium: "+UseCase.logger.get(this)+".update()", ArrowDirection.RIGHT, Indent.INDENT);
        searchConnection();
        // if it has at least one body, reset health
        if(!connectedBodies.isEmpty()) {
            health = maxHealth;
        }
        else {
            health--;
        }
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".update()", ArrowDirection.LEFT, Indent.UNINDENT);
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

    @Override
    public void damage() {
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".damage()", ArrowDirection.RIGHT, Indent.INDENT);
        health--;
        if(health <= 0) {
            detach();
            // remove from tile
            currentTile.removeEntity(this);
        }
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".damage()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    @Override
    public void heal() {
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".heal()", ArrowDirection.RIGHT, Indent.INDENT);
        health = maxHealth;
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".heal()", ArrowDirection.LEFT, Indent.UNINDENT);
}
}