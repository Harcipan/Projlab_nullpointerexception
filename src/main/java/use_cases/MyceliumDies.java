package use_cases;

import entities.Mycelium;
import map.*;

public class MyceliumDies extends UseCase {

    public MyceliumDies() {//legyen az id azonos azzal ami az usecase id-ja
        super(10, "Mycelium Dies");
    }

    @Override
    public void execute() {

        //scene initialization
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // map creation
        
        UseCase.logger.put(null, "m");
        Map m = new Map();

        // tekton creation
        UseCase.logger.put(null, "tek");
        Tekton tek = new Tekton(1, 1);

        // add tekton to map
        m.addTekton(tek);

        // tile creation and parent tekton assignment in constructor
        UseCase.logger.put(null, "t");
        Tile t = new Tile();

        // mycelium creation and tile assignment in constructor

        System.out.println("Creating mycelium...");
        UseCase.logger.put(null, "myc");
        Mycelium myc = new Mycelium();
        myc.setTile(t);
        // make the reference mutual
        t.addEntity(myc);

        // action: mycelium dies
        // this is a placeholder for the actual action TODO

        // remove mycelium from tile
        t.removeEntity(myc);

        m.tick();  // this will call the mycelium's detach method (connectivity determined here)
    }
}

