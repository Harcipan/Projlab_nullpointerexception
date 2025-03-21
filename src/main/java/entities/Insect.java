package entities;

import java.util.ArrayList;
import java.util.List;

import map.*;
import player.*;

public class Insect extends GameEntity {
    int speed;  // The speed of the insect
    boolean canCut; // Whether the insect can cut through mycelium
    Player controlledBy; // The player that controls the insect
    List<Spore> underInfluence; //The spores that are affecting the insect

    public Insect(int id, Tile currentTile, Player player) {
        super(id, currentTile);
        speed = 100;
        canCut = true;
        controlledBy = player;
        underInfluence = new ArrayList<Spore>();
    }

    @Override
    public void update() {
        // Decrease effect times and remove expired spores
        for (Spore spore : underInfluence) {
            spore.update();
            if(spore.getEffectTime() <= 0) {
                spore.removeEffect(this);
                underInfluence.remove(spore);
            }
        }
    }

    public void eat(Spore target) {
        controlledBy.updateScore(target.getNutrientValue());
        target.getEaten(this);
    }

    public void step(Tile target) {
        // Will implement later
    }

    public void cut(Tile target) {
        // Will implement later
    }

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

    public void setCut(boolean canCut) {
        this.canCut = canCut;
    }




}
