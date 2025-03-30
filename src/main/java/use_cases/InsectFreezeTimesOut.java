package use_cases;

import entities.FreezeSpore;
import entities.Insect;
import map.Map;
import map.Tekton;
import map.Tile;
import player.InsectPlayer;


// bro goddamn initialize your environment next time
// ps. this is not even implemented!
public class InsectFreezeTimesOut extends UseCase {
    public InsectFreezeTimesOut()
    {
        super(7, "Insect freeze times out");
    }

    @Override
    public void execute() {
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // create map
        UseCase.logger.put(null, "m");
        Map m = new Map();

        // create tekton
        Tekton tek = new Tekton(1, 1);

        // create tile
        Tile t = new Tile(1, 1, tek);

        // mutual referencing
        t.setParentTekton(tek);
        tek.addTile(t);

        // create insectPlayer
        InsectPlayer ip = new InsectPlayer();

        // create insect
        Insect i = new Insect(2, t, ip);

        // create FreezeSpore
        FreezeSpore fs = new FreezeSpore();
        fs.setTile(t);
        t.addEntity(fs);

        // insect eats FreezeSpore

    }


}
