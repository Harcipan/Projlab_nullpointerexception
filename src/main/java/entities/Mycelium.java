package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import map.HealTile;
import map.Tile;
import player.FungusPlayer;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.*;

public class Mycelium extends Fungus implements Serializable{
    int maxHealth = 5; 
    FungusPlayer player = null; // The player that owns this mycelium
    List<FungusBody> connectedBodies = new ArrayList<FungusBody>();
    List<Mycelium> connectedMycelia = new ArrayList<Mycelium>();
    private boolean isDying;

    public Mycelium(int id, int health, Tile currentTile, FungusPlayer player) {
        super(id, health, currentTile);
        this.player = player;
        this.health = health;
        this.isDying = false;
        currentTile.addEntity(this);
        player.addMycelium(this);
    }

    public Mycelium()
    {
        super();
        replace(this);
        printWrapper("Initializing Mycelium as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
    }

    @Override
    public void update() {
        if(isDying){
            damage();
        }
    }

    // Reconnect with the mycelium network, recover health
    // Neighbor cells will be checked for mycelium
    public void connect(){
        if (searchConnection()) {
            health = maxHealth;
        }
    }

    /**
     * Detach the mycelium from the network
     */
    public void detach(){
        for (Mycelium myc: connectedMycelia) {
            myc.connectedMycelia.remove(this);
        }
        connectedMycelia.clear();
        connectedBodies.clear();
    }

    /**
     * Search for connections with other mycelium or fungus bodies
     * If a connection is found, the mycelium will be added to the list of connected mycelia
     * and the fungus body will be added to the list of connected bodies
     * Recursively search for connections in the neighboring tiles
     * @return true if a connection to a fungus body is found, false otherwise
     */
    private boolean searchConnection(){
        boolean found = false;
        List<Tile> neighbors = currentTile.getNeighbors();
        for (Mycelium myc: player.getMycelia()){
            if (myc != this && neighbors.contains(myc.getCurrentTile())) {
                addConnectedMycelium(myc);
            }
        }
        for (FungusBody fb: player.getFungusBodies()){
            if (neighbors.contains(fb.getCurrentTile())) {
                addConnectedBody(fb);
                found = true;
            }
        }
        return found;

    }

    /**
     * Add a mycelium to the list of connected mycelia
     * go through recursively to find all connected mycelia
     * @param myc the mycelium to add
     */
    private void addConnectedMycelium(Mycelium myc) {
        myc.addConnectedMycelium(this);
        if(connectedMycelia.contains(myc)) {
            return;
        }
        if(myc == this) {
            return;
        }
        connectedMycelia.add(myc);
        for (Mycelium m : myc.connectedMycelia) {
            if (!connectedMycelia.contains(m)) {
                addConnectedMycelium(m);
            }
        }
    }

    /**
     * Add a fungus body to the list of connected bodies
     * go through mycelia to update all connected bodies lists
     * @param fb the fungus body to add
     */
    private void addConnectedBody(FungusBody fb) {
        if(connectedBodies.contains(fb)) {
            return;
        }
        connectedBodies.add(fb);
        for (Mycelium m : connectedMycelia) {
            m.addConnectedBody(fb);
        }
    }

    @Override
    public void die() {
        detach();
        player.removeMycelium(this);
        currentTile.removeEntity(this);
    }

    @Override
    public void getCut() {
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".getCut()", ArrowDirection.RIGHT, Indent.INDENT);
        die();
        printWrapper("Mycelium: "+UseCase.logger.get(this)+".getCut()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    @Override
    public void damage() {
        health--;
        if(health <= 0) {
            System.out.printf("Mycelium at %d, %d health at zero %n", currentTile.getX(), currentTile.getY());
            die();
        }
    }

    @Override
    public void heal() {
        if (health < maxHealth) {
            health++;
        }
    }

    public void setIsDying(boolean state){
        isDying = state;
        //System.out.printf("Mycelium at %d, %d set to dying %n", currentTile.getX(), currentTile.getY());
    }

    public String serialize() {

        StringBuilder sb = new StringBuilder();
        sb.append("\"mycelium_").append(id).append("\": {\n");
        int tileValue = currentTile.getParentTekton().getTileId(currentTile);
        sb.append("\t\"currentTile\": t").append(tileValue).append(",\n");
        sb.append("\t\"health\": ").append(health).append(",\n");
        sb.append("\t\"isDying\": ").append(isDying).append("\n");
        sb.append("}");

        return sb.toString();
    }

    public FungusPlayer getPlayer() {
        if (player == null) {
            System.out.println("Mycelium has no player");
        }
        return player;
    }

    public void setPlayer(FungusPlayer player) {
        this.player = player;
    }
}