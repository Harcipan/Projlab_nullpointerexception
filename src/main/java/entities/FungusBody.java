package entities;

import static use_cases.UseCase.replace;

import map.Tile;
import player.FungusPlayer;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * FungusBody is a subclass of Fungus that represents the body of a fungus.
 * It is responsible for growing mycelium and managing the spore charge.
 */
public class FungusBody extends Fungus{
    private static final int MAX_SPORE_CHARGE = 100; // Maximum spore charge
    private static final int CHARGE_PER_TICK = 1; // Spore charge increase per tick
    private FungusPlayer player = null; // The player that owns this fungus body
    private int sporeCharge = 0;

    /*
     * Old constructor for FungusBody used in use-case simulation
     * @deprecated
     */
    @Deprecated
    public FungusBody(int health, int initialSporeCharge, Tile currentTile){
        super(health, currentTile);
        this.sporeCharge = initialSporeCharge;
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }
    public FungusBody(int health,  Tile currentTile, FungusPlayer player) {
        super(health, currentTile);
        this.player = player;
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }
    @Override
    public void update(){
        sporeCharge+= CHARGE_PER_TICK;
        if (sporeCharge > MAX_SPORE_CHARGE) {
            sporeCharge = MAX_SPORE_CHARGE;
        }
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
        // later
    }

    @Override
    /*
     * Grow mycelium on the selected tile
     * It is assumed that the tile has been validated
     * @param neighbor the tile to grow mycelium on
     */
    public void growMycelium(Tile neighbor) {
        Mycelium m = new Mycelium(1, neighbor, this.player);
        m.connectedBodies.add(this);
        m.connect();
    }

    /*
     * Grow a body on the selected tile
     * It is assumed that the tile has been validated (highly unlikely but possible)
     * @param neighbor the tile to grow a body on
     */
    @Override
    public void growBody(Tile neighbor) {
        new FungusBody(1, neighbor, this.player);
    }





}
