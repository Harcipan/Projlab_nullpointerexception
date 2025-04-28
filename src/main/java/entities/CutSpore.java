package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/**
 * CutSpore is a spore that disables the cutting ability of an insect when eaten.
 * It is a subclass of Spore and inherits its properties and methods.
 */
public class CutSpore extends Spore {
    Tile formerParentTile;

    /**
     * Constructor for CutSpore
     * @param id the id of the spore
     * @param currentTile the tile where the spore is located
     * @param nutrientValue the nutrient value of the spore
     * @param lifetime the lifetime of the spore
     * @param effectTime the time the spore will have an effect on the insect
     * 
     */
    public CutSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, 0);
    }

    public CutSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing CutSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
    }

    /**
     * When an insect eats this spore, cutting ability is disabled
     * @param i the insect that eats the spore
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setCut(false);
        i.addSpore(this);
        formerParentTile = currentTile;
        currentTile.removeEntity(this);
    }

    /**
     * Restore cutting ability
     * @param i the insect we are removing the effect from
     */
    @Override
    public void removeEffect(Insect i) {
        i.setCut(true);
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"cutspore_").append(id).append("\": {\n");
        Tile parentTile = currentTile;
        if(parentTile == null){
            parentTile =  formerParentTile;
        }
        int lineValue = parentTile.getParentTekton().getMap().getWidth();
        int tileValue = parentTile.getX() + lineValue * parentTile.getY();
        sb.append("\t\"currentTile\": t").append(tileValue).append(",\n");
        sb.append("\t\"isConsumed\": ").append(isConsumed).append("\n");
        sb.append("}");

        return sb.toString();    
    }

}
