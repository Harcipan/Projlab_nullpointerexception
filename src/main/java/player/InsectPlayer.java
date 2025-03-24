package player;

import static use_cases.UseCase.printWrapper;

import entities.Insect;
import map.Tile;
import use_cases.UseCase;
import entities.Spore;
import use_cases.UseCase.*;

public class InsectPlayer extends Player{
    Insect controlledInsect;

    public InsectPlayer() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing InsectPlayer as " + UseCase.logger.get(this), ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.controlledInsect = null;
        UseCase.printWrapper("InsectPlayer: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public void setControlledInsect(Insect controlledInsect) {
        UseCase.printWrapper(UseCase.logger.get(this)+".setControlledInsect(" + UseCase.logger.get(controlledInsect)+")", ArrowDirection.RIGHT, Indent.KEEP);
        this.controlledInsect = controlledInsect;
        UseCase.printWrapper(UseCase.logger.get(this)+".setControlledInsect()", ArrowDirection.LEFT, Indent.KEEP);
    }

    public void moveTo(Tile tile) {
        // Will implement later
        UseCase.printWrapper(UseCase.logger.get(this)+".moveTo(" + UseCase.logger.get(tile)+")", ArrowDirection.RIGHT, Indent.INDENT);

        System.out.println("Is the insect paralyzed? Y/N");
        String answer = System.console().readLine();
        if(answer.equalsIgnoreCase("Y")){
            printWrapper("The insect is paralyzed and cannot move, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        
        controlledInsect.step(tile);

        UseCase.printWrapper(UseCase.logger.get(this)+".moveTo()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void eat(Spore s){
        // Will implement later
        UseCase.printWrapper(UseCase.logger.get(this)+".eat(" + UseCase.logger.get(s)+")", ArrowDirection.RIGHT, Indent.INDENT);
        controlledInsect.eat(s);
        UseCase.printWrapper(UseCase.logger.get(this)+".eat()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void cut(Tile tile){
        // Will implement later
        UseCase.printWrapper(UseCase.logger.get(this)+".cut(" + UseCase.logger.get(tile)+")", ArrowDirection.RIGHT, Indent.INDENT);
        
        System.out.println("Can the insect cut? Y/N");
        String answer = System.console().readLine();
        if(answer.equalsIgnoreCase("N")){
            printWrapper("The insect cannot cut, end of use-case", ArrowDirection.RIGHT, Indent.UNINDENT);
            return;
        }
        controlledInsect.cut(tile);
        UseCase.printWrapper(UseCase.logger.get(this)+".cut()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    
}
