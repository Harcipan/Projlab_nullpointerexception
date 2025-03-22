package use_cases;

import entities.Mycelium;
import map.*;

public class MyceliumDies extends UseCase {

    public MyceliumDies() {//legyen az id azonos azzal ami az usecase id-ja
        super(1, "Mycelium Dies");
    }

    @Override
    public void execute() {

        //scene initialization
        System.out.println("Initializing scene...");

        // map creation
        System.out.println("Creating map...");
        Map m = new Map();

        // tekton creation
        System.out.println("Creating tekton...");
        Tekton tek = new Tekton(1, 1);

        // add tekton to map
        m.addTekton(tek);

        // tile creation and parent tekton assignment in constructor
        System.out.println("Creating tile...");
        Tile t = new Tile();

        // mycelium creation and tile assignment in constructor
        System.out.println("Creating mycelium...");
        Mycelium myc = new Mycelium(1, t);

        // make the reference mutual
        t.addEntity(myc);

        // action: mycelium dies
        System.out.println("Mycelium dies...");

        // remove mycelium from tile
        t.removeEntity(myc);

        m.tick();  // this will call the mycelium's detach method (connectivity determined here)
    }
}

