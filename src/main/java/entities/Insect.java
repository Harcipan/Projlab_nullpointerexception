package entities;

import java.util.ArrayList;
import java.util.List;

import map.*;
import player.*;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * Insect is a class that represents an insect in the game.
 */
public class Insect extends GameEntity {
    int speed;                  // The speed of the insect
    boolean canCut;             // Whether the insect can cut through mycelium
    InsectPlayer controlledBy;  // The player that controls the insect
    List<Spore> underInfluence; //The spores that are currently affecting the insect

    // Temporary here, should be deleted after changing other methods
    public Insect(int id, Tile currentTile, InsectPlayer player) {
        super(id, currentTile);
        this.speed = 100;
        this.canCut = true;
        controlledBy = player;
        controlledBy.addControlledInsect(this);
        underInfluence = new ArrayList<Spore>();
    }

    public Insect(int id, Tile currentTile, InsectPlayer player, int speed, boolean canCut) {
        super(id, currentTile);
        this.speed = speed;
        this.canCut = canCut;
        controlledBy = player;
        controlledBy.addControlledInsect(this);
        underInfluence = new ArrayList<Spore>();
    }

    /*
     * Split the insect
     * Creates another insect controlled by the same player
     * The new insect will be placed in the same tile as the original insect
     */
    public Insect split() {
        Insect newInsect = new Insect(100+id, getCurrentTile(), controlledBy, speed, canCut);
        //controlledBy.addControlledInsect(newInsect);
        return newInsect;
    }

    /*
     * Kill the insect
     * Remove the insect from the tile and the player
     */
    public void die() {
        controlledBy.removeControlledInsect(this);
        currentTile.removeEntity(this);
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

        setCut(true);
    }

    /*
     * Eat the spore and update the score of the player
     * Adds spore to affecting list
     * @param target the spore to eat
     */
    public void eat(Spore target) {
        controlledBy.updateScore(target.getNutrientValue());
        UseCase.printWrapper(UseCase.logger.get(this)+".eat(" + UseCase.logger.get(target)+")", ArrowDirection.RIGHT, Indent.INDENT);
        target.getEaten(this);
        UseCase.printWrapper(UseCase.logger.get(this)+".eat()", ArrowDirection.LEFT, Indent.UNINDENT);
        //controlledBy.updateScore(target.getNutrientValue());
        //target.getEaten(this);
        if (target instanceof SplitSpore) {
            split();
        }
    }

    /*
     * Move the insect to the target tile
     * Assumes the target tile is valid
     * @param target the tile to move to
     */
    public void step(Tile target) {
        //UseCase.printWrapper(UseCase.logger.get(this)+".step(" + UseCase.logger.get(target)+")", ArrowDirection.RIGHT, Indent.INDENT);
        currentTile.removeEntity(this);
        target.addEntity(this);
    }

    /*
     * Remove the mycelium in the target tile
     * Assumes the target tile is valid
     * @param target the tile to cut
     */
    public void cut(Tile target) {
        //UseCase.printWrapper(UseCase.logger.get(this)+".cut(" + UseCase.logger.get(target)+")", ArrowDirection.RIGHT, Indent.INDENT);
        if (!canCut) {
            return;
        }
        target.getEntities();
        for (GameEntity ge : target.getEntities()) {
            ge.getCut();
        }
        for(int i=0; i<target.getEntities().size();i++) {
            target.getEntities().get(i).getCut();
        }
    }

    /*
     * Add a spore to the list of spores affecting the insect
     */
    public void addSpore(Spore spore){
        underInfluence.add(spore);
    }

    public List<Spore> getUnderInfluence(){
        return underInfluence;
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
     * Get the speed of the insect
     */
    public int getSpeed() {
        return speed;
    }

    /*
     * set cutting ability
     */
    public void setCut(boolean canCut) {
        this.canCut = canCut;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"insect_").append(id).append("\": {\n");

        //int lineValue = currentTile.getParentTekton().getMap().getWidth();
        //int tileValue = currentTile.getX() + lineValue * currentTile.getY();
        int tileValue = currentTile.getParentTekton().getTileId(currentTile);
        sb.append("\t\"currentTile\": \"t").append(tileValue).append("\",\n");
        sb.append("\t\"speed\": ").append(speed).append(",\n");
        sb.append("\t\"canCut\": ").append(canCut).append(",\n");
        sb.append("}");

        return sb.toString();
    }
}
