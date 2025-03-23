package use_cases;

import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class MyceliumGrowing extends UseCase {

    public MyceliumGrowing() {
        super(8, "Mycelium growing");
    }

    @Override
    public void execute() {
        //create tekton
        Tekton tek=new Tekton(1,1);

        //create tile
        Tile t=new Tile();
        
        //create ungus
        FungusPlayer fp=new FungusPlayer();

        //add tile to tekton
        t.setParentTekton(tek);

        fp.growMycelium(t);
    }
    
}
