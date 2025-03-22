package use_cases;

import player.InsectPlayer;
import entities.Insect;
import map.Tekton;
import map.Tile;

public class InsectMove extends UseCase {
    
    public InsectMove() {
        super(1, "Insect player moving to tile");
    }

    @Override
    public void execute() {
        //insect Player is created
        InsectPlayer ip=new InsectPlayer();
        printWrapper("InsectPlayer: i",Direction.LEFT);

        //tekton of tiles
        Tekton tek=new Tekton(1,1);
        printWrapper("Tekton: tek",Direction.LEFT);


        //current tile cration
        Tile currentTile=new Tile();
        printWrapper("Tile: currentTile",Direction.LEFT);
        
        //target tile creation
        Tile targetTile=new Tile();
        printWrapper("Tile: targetTile",Direction.LEFT);

        //insect creation
        Insect i=new Insect();
        printWrapper("Insect: i",Direction.LEFT);

        //add tiles to tekton
        currentTile.setParentTekton(tek);
        printWrapper("currentTile.setParentTekton()",Direction.LEFT);
        targetTile.setParentTekton(tek);

        
        //add insect to tile
        currentTile.addEntity(i);

        //add insect to insectplayer
        ip.setControlledInsect(i);


        //insect steps
        ip.moveTo(targetTile);
    }
}
