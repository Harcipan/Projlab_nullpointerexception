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
        InsectPlayer ip=new InsectPlayer();

        //tekton of tiles
        Tekton tek=new Tekton(1,1);
        //printWrapper("Tekton: tek",Direction.LEFT);


        //current tile cration
        Tile t=new Tile();
        //printWrapper("Tile: currentTile",Direction.LEFT);
        
        //insect create
        Insect i=new Insect();

        //Speedupspore create
        Spore s=new FreezeSpore();

        //insect eat spore
        ip.eat(s);
    }
}
