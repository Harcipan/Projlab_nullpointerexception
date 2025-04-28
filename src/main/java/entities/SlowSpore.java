package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * Spore that slows down the insect that eats it
 * effectValue must be less than 100 (its a percentage)
 */
public class SlowSpore extends Spore {
    Tile formerParentTile;

    public SlowSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }
    public SlowSpore(int effectValue){
        super();
        this.effectValue = effectValue;
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SlowSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
    }

    /*
     * apply speed reduction to the insect
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setSpeedPercent(effectValue);
        i.addSpore(this);
        formerParentTile = currentTile;
        currentTile.removeEntity(this);
    }

    /*
     * Restore insect speed to 0 (applies default speed)
     */
    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"slowspore_").append(id).append("\": {\n");
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
