package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * Spore that speeds up the insect
 * effectValue must be greater than 100 (its a percentage)
 */
public class SpeedUpSpore extends Spore {
    /*public SpeedUpSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }*/

    public SpeedUpSpore(int effectValue) {
        super();
        this.effectValue = effectValue;
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SpeedUpSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("SpeedUpSpore: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    /*
     * apply speed increase to the insect
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
