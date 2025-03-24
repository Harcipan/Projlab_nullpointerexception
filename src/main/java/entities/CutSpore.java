package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

public class CutSpore extends Spore {
    /*public CutSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }*/

    public CutSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing CutSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("CutSpore: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    /*
     * When an insect eats this spore, cutting ability is disabled
     */
    @Override
    public void getEaten(Insect i) {
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten(" + UseCase.logger.get(i)+")", ArrowDirection.RIGHT, Indent.INDENT);
        isConsumed = true;
        i.setCut(false);
        i.addSpore(this);
        currentTile.removeEntity(this);
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    /*
     * Restore cutting ability
     */
    @Override
    public void removeEffect(Insect i) {
        i.setCut(true);
    }

}
