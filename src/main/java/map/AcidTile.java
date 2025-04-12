package map;

import entities.GameEntity;

public class AcidTile extends Tile{
    int damageRate; // The rate at which the acid damages entities

    public AcidTile(int growthRate, int maxMycelium, Tekton parentTekton, int damageRate) {
        //super(growthRate, maxMycelium, parentTekton);
        super();
        this.damageRate = damageRate;
    }

    @Override
    public void addEntity(GameEntity entity) {
        // TODO Auto-generated method stub
        super.addEntity(entity);
    }

    public void damageEntities() {
        // Will implement later
    }

    @Override
    public void update() {
        // Will implement later
    }

}
