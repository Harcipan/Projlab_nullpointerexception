package entities;

import java.util.ArrayList;
import java.util.List;

import map.HealTile;
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
    }

    @Override
    public void update() {

        searchConnection();
        // if it has at least one body, reset health
        if(!connectedBodies.isEmpty()) {
            health = maxHealth;
        } else if (this.getCurrentTile() instanceof HealTile) {
            this.heal();
        }
        else {
            health--;
        }
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
        health--;
        if(health <= 0) {
            detach();
            // remove from tile
            currentTile.removeEntity(this);
        }
    }

    @Override
    public void heal() {
        if (health < maxHealth) {
            health++;
        }
    }
}