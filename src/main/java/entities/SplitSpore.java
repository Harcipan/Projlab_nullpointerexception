package entities;

import use_cases.UseCase;

public class SplitSpore extends Spore {

    public SplitSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SplitSpore as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
    }

    /*
     * When an insect eats this spore, there will be another insect summoned. The summoning process should happen outside of this class
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.addSpore(this);
        currentTile.removeEntity(this);
    }
}
