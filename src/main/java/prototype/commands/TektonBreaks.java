package prototype.commands;

import java.util.ArrayList;
import java.util.List;

import entities.GameEntity;
import entities.Mycelium;
import map.Tekton;
import map.Tile;
import prototype.*;

public class TektonBreaks extends Command {
    public TektonBreaks() {
        super("tekton_breaks", "Break the tectonic plate and cut all mycelium on it",
                "tekton_breaks <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(2, args.length))
            return false;
        if (isMapUninitialized())
            return false;

        Tekton tek = parseTekton(args[1], "Tectonic plate");
        if (tek == null)
            return false;

        List<Tekton> tl = tek.breakTekton();
        ArrayList<Mycelium> removableMycelia = new ArrayList<>();
        for (Tile t : tek.getTiles()) {
            for (GameEntity e : t.getEntities()) {
                if (e instanceof Mycelium) {
                    removableMycelia.add((Mycelium) e);
                }
            }
        }
        for (Mycelium mycelium : removableMycelia) {
            mycelium.die();
        }

        return false;
    }
}
