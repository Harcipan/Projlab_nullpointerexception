package use_cases;

import entities.Insect;
import entities.Mycelium;
import map.Tekton;
import map.Tile;
import player.InsectPlayer;

public class InsectCutMycelium extends UseCase {

    public InsectCutMycelium() {
        super(2, "Insect cuts Mycelium");
    }

    @Override
    public void execute() {
        //insect player is created
        InsectPlayer ip=new InsectPlayer();

        //tekton of tiles
        Tekton tek=new Tekton(1,1);
        //printWrapper("Tekton: tek",Direction.LEFT);


        //current tile cration
        Tile t=new Tile();
        //printWrapper("Tile: currentTile",Direction.LEFT);
        
        //mycelium created
        Mycelium tm=new Mycelium();

        //insect created
        Insect i=new Insect();

        //add tile to the tekton
        t.setParentTekton(tek);

        t.addEntity(tm);
        t.addEntity(i);

        ip.setControlledInsect(i);

        ip.cut(t);
    }
    
}
