package entities;

import map.Tile;
import use_cases.UseCase;

public class SplitSpore extends Spore {

    public SplitSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SplitSpore as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
    }

    public SplitSpore(int id, Tile currentTile, int nutrientValue, int lifetime) {
        super(id, currentTile, nutrientValue, lifetime, 0, 0);
    }

    /*
     * When an insect eats this spore, there will be another insect summoned.
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        //i.split();
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"splitspore_").append(id).append("\" : { \n");
        int lineValue = currentTile.getParentTekton().getMap().getWidth();
        int tileValue = currentTile.getX() + lineValue * currentTile.getY();
        sb.append("\t\"currentTile\": \"t").append(tileValue).append("\", \n");
        sb.append("\t\"isConsumed\": ").append(isConsumed).append("\", \n");
        sb.append("\t\"nutrientValue\": ").append(nutrientValue).append("\", \n");
        sb.append("\t\"lifetime\": ").append(lifetime).append("\", \n");
        sb.append("} \n");

        return sb.toString();    
    }
}
