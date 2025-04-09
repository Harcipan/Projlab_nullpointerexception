package use_cases;

import entities.Mycelium;
import map.*;

public class DetachedMyceliumDies extends UseCase {

    public DetachedMyceliumDies() {//legyen az id azonos azzal ami az usecase id-ja
        super(11, "Detached Mycelium Dies");
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
        Tekton tek = new Tekton(1, 1, m );

        // add tekton to map
        m.addTekton(tek);

        // tile creation and parent tekton assignment in constructor
        UseCase.logger.put(null, "t");
        Tile t = new Tile();


        // mycelium creation and tile assignment in constructor

        System.out.println("Creating mycelium...");
        UseCase.logger.put(null, "myc");
        Mycelium myc = new Mycelium();
        //add tile to tekton
        t.setParentTekton(tek);

        // make the reference mutual
        t.addEntity(myc);

        // simulate a tick
        // this will call the mycelium's update method (health determined here)
        for (int i = 0; i < 5; i++) {
            myc.update();
        }

        // action: mycelium dies
        if (myc.getHealth() <= 0) {
            myc.die();
        }

        // remove mycelium from tile
        t.removeEntity(myc);

        m.tick();  // this will call the mycelium's detach method (connectivity determined here)
    }
}

