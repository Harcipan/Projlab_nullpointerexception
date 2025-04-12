package map;

public class MonoTile extends Tile{

    public MonoTile(int growthRate, Tekton parentTekton){
        super(growthRate, 1, parentTekton);
    }

    public boolean isTaken() {
        return myceliumSpace == 0;
    }
}
