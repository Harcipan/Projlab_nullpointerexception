package entities;

import static use_cases.UseCase.replace;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

public class FungusBody extends Fungus{
    private int sporeCharge = 0;

    public FungusBody(int health, int initialSporeCharge, Tile currentTile) {
        //super(health, currentTile);
        super();
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        this.sporeCharge = initialSporeCharge;
    }

    public void decrementSporeCharge() {
        this.sporeCharge--;
    }

    public void decrementSporeCharge(int amount) {
        this.sporeCharge -= amount;
    }

    public int getSporeCharge() {
        return this.sporeCharge;
    }

    public void sporeCloud() {

    }





}
