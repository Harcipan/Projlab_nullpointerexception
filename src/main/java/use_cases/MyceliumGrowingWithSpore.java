package use_cases;

import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class MyceliumGrowingWithSpore extends UseCase {

    public MyceliumGrowingWithSpore() {
        super(9, "Mycelium growing with spore");
    }

    @Override
    public void execute() {
        //create tekton
        Tekton tek=new Tekton(1,1);

        //create tile
        Tile t=new Tile();
        
        //create fungus
        FungusPlayer fp=new FungusPlayer();

        //add tile to tekton
        t.setParentTekton(tek);

        fp.growMycelium(t);
    }
    
}
