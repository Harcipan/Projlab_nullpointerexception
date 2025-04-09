package use_cases;

import player.InsectPlayer;
import entities.Insect;
import entities.SpeedUpSpore;
import entities.Spore;
import map.Tekton;
import map.Tile;

public class EatingSpeedupSpore extends UseCase {
    public EatingSpeedupSpore() {
        super(4, "Speedup spore affecting insect");
    }

    @Override
    public void execute() {
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);
        //insect player is created
        UseCase.logger.put(null, "ip");
        InsectPlayer ip=new InsectPlayer();

        //tekton of tiles
        UseCase.logger.put(null, "tek");
        Tekton tek=new Tekton(1,1, null);
        //printWrapper("Tekton: tek",Direction.LEFT);


        //current tile cration
        UseCase.logger.put(null, "t");
        Tile t=new Tile();
        //printWrapper("Tile: currentTile",Direction.LEFT);
        
        //insect create
        UseCase.logger.put(null, "i");
        Insect i=new Insect(1, t, ip);


        //Speedupspore create
        UseCase.logger.put(null, "s");
        Spore s=new SpeedUpSpore();

        //add insect to insectplayer
        ip.addControlledInsect(i);

        //add tiles to tekton
        t.setParentTekton(tek);

        //add insect and spore to tile
        t.addEntity(s);
        t.addEntity(i);

        //insect eat spore
        ip.eat(s);
    }
}
