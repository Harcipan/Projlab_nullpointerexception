package use_cases;

import player.InsectPlayer;

import java.util.HashMap;

import entities.Insect;
import map.Tekton;
import map.Tile;

public class InsectMove extends UseCase {
    
    public InsectMove() {
        super(1, "Insect player moving to tile");
    }

    @Override
    public void execute() {
        
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        //insect Player is created
        UseCase.logger.put(null, "ip");
        InsectPlayer ip=new InsectPlayer();

        //tekton of tiles
        UseCase.logger.put(null, "tek");
        Tekton tek=new Tekton(1,1);


        //current tile cration
        UseCase.logger.put(null, "currentTile");
        Tile currentTile=new Tile();
        
        //target tile creation
        UseCase.logger.put(null, "targetTile");
        Tile targetTile=new Tile();

        //insect creation
        UseCase.logger.put(null, "i");
        Insect i=new Insect();

        //add tiles to tekton
        currentTile.setParentTekton(tek);
        targetTile.setParentTekton(tek);

        
        //add insect to tile
        currentTile.addEntity(i);

        //add insect to insectplayer
        ip.setControlledInsect(i);


        //insect steps
        ip.moveTo(targetTile);
    }
}
