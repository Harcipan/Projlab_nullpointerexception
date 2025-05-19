package map;

import entities.GameEntity;

public class AcidTile extends Tile{
    int damageRate; // The rate at which the acid damages entities (damage per tick)

    public AcidTile(int growthRate, int maxMycelium, Tekton parentTekton, int damageRate, int x, int y) {
        super(growthRate, maxMycelium, parentTekton,x,y);
        this.damageRate = damageRate;
    }


    private void damageEntities() {
        /*for (GameEntity ge : entities){
            for (int i = 0; i < damageRate; i++) {
                ge.damage(); // Call the damage method on each entity in the tile
            }
        }*/
        for(int i=0;i<entities.size();i++) {
            for (int j = 0; j < damageRate && i<entities.size(); j++) {
                entities.get(i).damage(); // Call the damage method on each entity in the tile
            }
        }
    }

    @Override
    public void update() {
        damageEntities(); // Call the damageEntities method to apply damage to entities
        super.update(); // Call the parent class's update method to handle other updates
    }

    @Override
    public String serialize(){
        StringBuilder sb = new StringBuilder();
        int lineValue = parentTekton.getMap().getWidth();
        int value = x + lineValue * y;

        int parentIndex = parentTekton.getMap().getTektons().indexOf(parentTekton);
        
        sb.append("\"at").append(value).append("\"").append(" : {\n");
        sb.append("\t \"parentTekton\" : T").append(parentIndex).append(", \n");
        sb.append("\t \"growthRate\" : ").append(growthRate).append(" \n");
        sb.append("\t \"damageRate\" : ").append(damageRate).append(" \n");
        
        sb.append("}");

        return sb.toString();
    }

}
