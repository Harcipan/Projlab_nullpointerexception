package player;

import static use_cases.UseCase.printWrapper;

import java.util.ArrayList;
import java.util.List;

import entities.Insect;
import map.Tile;
import prototype.App;
import use_cases.UseCase;
import entities.Spore;
import use_cases.UseCase.*;

public class InsectPlayer extends Player{
    List<Insect> controlledInsects;
    int ID;

    public InsectPlayer() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing InsectPlayer as " + UseCase.logger.get(this), ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.controlledInsects = new ArrayList<>();
        this.ID = App.getInsectPlayers().size();
    }

    public int getID() {
        return ID;
    }

    public void addControlledInsect(Insect controlledInsect) {
        this.controlledInsects.add(controlledInsect);
        //System.out.printf("\na\n");
    }

    public List<Insect> getControlledInsects() {
        return controlledInsects;
    }

    /**
     * Move the first controlled insect to the tile
     * Overloaded method for convenience
     * @param tile
     */
    public void moveTo(Tile tile) {
        if (controlledInsects.isEmpty()) {
            printWrapper("No controlled insects available to move", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        moveTo(tile, controlledInsects.get(0));
    }

    /*
     * Move the insect to the tile
     * Check if the insect is controlled by this player
     * Check if the insect can move
     * Check if the tile is a neighbor of the insect
     * Check if the tile is on the same tekton as the insect or there is a bridge to the tile
     * If all checks pass, move the insect to the tile
     * 
     * currently bridges are not implemented
     * @param tile the tile to move to
     * @param controlledInsect the insect to move
     */
    public void moveTo(Tile tile, Insect controlledInsect) {
        //check if tile is valid
        // neighbors
        if(tile.isNeighbor(controlledInsect.getCurrentTile())){
            controlledInsect.step(tile);

        }
        else{
            printWrapper("The tile is not a neighbor of the insect", ArrowDirection.RIGHT, Indent.UNINDENT);
        }


        //removed for now, needs fix from creator (Zsiga)
        // UseCase.printWrapper(UseCase.logger.get(this)+".moveTo(" + UseCase.logger.get(tile)+")", ArrowDirection.RIGHT, Indent.INDENT);

        // //check if insect is ours to control
        // if(!controlledInsects.contains(controlledInsect)){
        //     printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
        //     return;
        // }
        // //check if insect can move
        // if(controlledInsect.getSpeed() == 0){
        //     printWrapper("The insect cannot move", ArrowDirection.RIGHT, Indent.UNINDENT);
        //     return;
        // }
        
        // // and same parent tekton
        // if(tile.getParentTekton() == controlledInsect.getCurrentTile().getParentTekton()){
        //     controlledInsect.step(tile);            
        // }
        // //or there is a mycelium bridge
        // else{
        //     if(controlledInsect.getCurrentTile().hasBridge(tile)){
        //         controlledInsect.step(tile);
        //     }
        //     else{
        //         printWrapper("The insect does not have a bridge to the tile", ArrowDirection.RIGHT, Indent.UNINDENT);
        //         return;
        //     }
        // }
        

    }

    public void eat(Spore s) {
        eat(s, controlledInsects.get(0));
    }

    public void eat(Spore s, Insect controlledInsect) {
        if(!controlledInsects.contains(controlledInsect)){
            printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        UseCase.printWrapper(UseCase.logger.get(this)+".eat(" + UseCase.logger.get(s)+")", ArrowDirection.RIGHT, Indent.INDENT);
        // Will implement later
        controlledInsect.eat(s);
    }

    public void cut(Tile tile) {
        cut(tile, controlledInsects.get(0));
    }
    
    public void cut(Tile tile, Insect controlledInsect) {
        if(!controlledInsects.contains(controlledInsect)){
            printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        UseCase.printWrapper(UseCase.logger.get(this)+".cut(" + UseCase.logger.get(tile)+")", ArrowDirection.RIGHT, Indent.INDENT);

        //check if insect is ours to control
        if(!controlledInsects.contains(controlledInsect)){
            printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        controlledInsect.cut(tile);
    }

    /*
     * Remove the insect from the list of controlled insects
     * This is used when the insect is no longer controlled by the player (its dead)
     */
    public void removeControlledInsect(Insect controlledInsect) {
        this.controlledInsects.remove(controlledInsect);
        
    }

    
}
