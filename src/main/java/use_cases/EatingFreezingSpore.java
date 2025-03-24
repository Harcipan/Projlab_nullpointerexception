package use_cases;

import player.InsectPlayer;
import entities.FreezeSpore;
import entities.Insect;
import entities.SpeedUpSpore;
import entities.Spore;
import map.Tekton;
import map.Tile;

public class EatingFreezingSpore extends UseCase {
    public EatingFreezingSpore() {
        super(5, "Freezing spore affecting insect");
    }

    @Override
    public void execute() {
       //insect player is created
       UseCase.logger.put(null, "ip");
       InsectPlayer ip=new InsectPlayer();

       //tekton of tiles
       UseCase.logger.put(null, "tek");
       Tekton tek=new Tekton(1,1);
       //printWrapper("Tekton: tek",Direction.LEFT);


       //current tile cration
       UseCase.logger.put(null, "t");
       Tile t=new Tile();
       //printWrapper("Tile: currentTile",Direction.LEFT);
       
       //insect create
       UseCase.logger.put(null, "i");
       Insect i=new Insect();


       //Freezespore create
       UseCase.logger.put(null, "s");
       Spore s=new FreezeSpore();

       //add insect to insectplayer
       ip.setControlledInsect(i);

       //add tiles to tekton
       t.setParentTekton(tek);

       //add insect and spore to tile
       t.addEntity(s);
       t.addEntity(i);

       //insect eat spore
       ip.eat(s);
    }
}
