package map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    List<Tekton> tektons;

    public Map() {
        tektons = new ArrayList<>();
    }

    public void addTekton(Tekton tekton) {
        tektons.add(tekton);
    }

    public List<Tekton> getTektons() {
        return tektons;
    }

    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }
}
