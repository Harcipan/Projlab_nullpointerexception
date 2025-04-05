package player;

import static use_cases.UseCase.printWrapper;

import java.util.ArrayList;
import java.util.List;

import entities.Insect;
import map.Tile;
import use_cases.UseCase;
import entities.Spore;
import use_cases.UseCase.*;

public class InsectPlayer extends Player{
    List<Insect> controlledInsects;

    public InsectPlayer() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing InsectPlayer as " + UseCase.logger.get(this), ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.controlledInsects = new ArrayList<>();
    }

    public void addControlledInsect(Insect controlledInsect) {
        this.controlledInsects.add(controlledInsect);
    }

    public List<Insect> getControlledInsects() {
        return controlledInsects;
    }

    public void moveTo(Tile tile){
        moveTo(tile, controlledInsects.get(0));
    }
    public void moveTo(Tile tile, Insect controlledInsect) {
        // Will implement later
        
        //check if insect is ours to control
        if(!controlledInsects.contains(controlledInsect)){
            printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        //check if insect is paralyzed
        System.out.println("Is the insect paralyzed? Y/N");
        String answer = System.console().readLine();
        if(answer.equalsIgnoreCase("Y")){
            printWrapper("The insect is paralyzed and cannot move, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        
        controlledInsect.step(tile);

    }

    public void eat(Spore s) {
        eat(s, controlledInsects.get(0));
    }

    public void eat(Spore s, Insect controlledInsect) {
        if(!controlledInsects.contains(controlledInsect)){
            printWrapper("The insect is not controlled by this player, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
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
        // Will implement later
        
        System.out.println("Can the insect cut? Y/N");
        String answer = System.console().readLine();
        if(answer.equalsIgnoreCase("N")){
            printWrapper("The insect cannot cut, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
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
