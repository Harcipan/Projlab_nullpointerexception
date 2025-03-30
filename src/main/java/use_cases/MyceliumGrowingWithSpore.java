package use_cases;

import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class MyceliumGrowingWithSpore extends UseCase {

    public MyceliumGrowingWithSpore() {
        super(10, "Mycelium growing with spore");
    }

    @Override
    public void execute() {
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);
        //create tekton
        UseCase.logger.put(null, "tek");
        Tekton tek=new Tekton(1,1);

        //create tile
        UseCase.logger.put(null, "t");
        Tile t=new Tile();
        
        //create ungus
        UseCase.logger.put(null, "fp");
        FungusPlayer fp=new FungusPlayer();

        //add tile to tekton
        t.setParentTekton(tek);

        fp.growMycelium(t);
    }
    
}
