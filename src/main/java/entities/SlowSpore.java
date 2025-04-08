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
    public SlowSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }
    public SlowSpore(){
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SlowSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("SlowSpore: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    /*
     * apply speed reduction to the insect
     */
    @Override
    public void getEaten(Insect i) {
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten(" + UseCase.logger.get(i)+")", ArrowDirection.RIGHT, Indent.INDENT);
        isConsumed = true;
        i.setSpeedPercent(effectValue);
        i.addSpore(this);
        currentTile.removeEntity(this);
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    /*
     * Restore insect speed to 0 (applies default speed)
     */
    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }

}
