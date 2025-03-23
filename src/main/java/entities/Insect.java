package entities;

import java.util.ArrayList;
import java.util.List;

import map.*;
import player.*;
import use_cases.UseCase;
import use_cases.UseCase.Direction;

public class Insect extends GameEntity {
    int speed;  // The speed of the insect
    boolean canCut; // Whether the insect can cut through mycelium
    Player controlledBy; // The player that controls the insect
    List<Spore> underInfluence; //The spores that are affecting the insect

    /*public Insect(int id, Tile currentTile, Player player) {
        super(id, currentTile);
        speed = 100;
        canCut = true;
        controlledBy = player;
        underInfluence = new ArrayList<Spore>();
    }*/

    public Insect()
    {
        super();
        UseCase.printWrapper("Insect",Direction.RIGHT);
    }

    /*
     * Update the insect
     * Decrease effect times and remove expired spores
     */
    @Override
    public void update() {
        for (Spore spore : underInfluence) {
            spore.update();
            if(spore.getEffectTime() <= 0) {
                spore.removeEffect(this);
                underInfluence.remove(spore);
            }
        }
    }

    /*
     * Eat the spore and update the score of the player
     * Adds spore to affecting list
     */
    public void eat(Spore target) {
        //controlledBy.updateScore(target.getNutrientValue());
        target.getEaten(this);
    }

    /*
     * Move the insect to the target tile
     * If the target tile is not adjacent, the insect will not move
     */
    public void step(Tile target) {
        // Will implement later
        currentTile.removeEntity(this);
        target.addEntity(this);
    }

    /*
     * Remove the mycelium in the target tile
     */
    public void cut(Tile target) {
        // Will implement later
        
    }

    /*
     * Add a spore to the list of spores affecting the insect
     */
    public void addSpore(Spore spore){
        underInfluence.add(spore);
    }

    /*
     * Set the speed of the insect to a percentage of the original speed
     * If the percentage is 0, the speed is reset to 100 (default speed)
     * For freezing use negative percentage
     */
    public void setSpeedPercent(int percent) {
        if(percent == 0) {
            // Reset speed to 100
            speed = 100;
            return;
        }
        speed = speed * percent / 100;
        if(speed < 0) {
            speed = 0;
        }
    }

    /*
     * set cutting ability
     */
    public void setCut(boolean canCut) {
        this.canCut = canCut;
    }




}
