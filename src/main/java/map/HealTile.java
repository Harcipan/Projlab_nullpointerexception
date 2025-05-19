package map;

import entities.*;

public class HealTile extends Tile{
    public HealTile(int growthRate, int maxMycelium, Tekton parentTekton, int x, int y){
        super(growthRate, maxMycelium, parentTekton,x, y);
    }

    private void healEntities() {
        for (GameEntity ge : entities) {
            ge.heal(); // Call the heal method on each entity in the tile
        }
    }

    @Override
    public void update() {
        healEntities(); // Call the healEntities method to apply healing to entities
        super.update(); // Call the parent class's update method to handle other updates
    }

    @Override
    public String serialize(){
        StringBuilder sb = new StringBuilder();
        int lineValue = parentTekton.getMap().getWidth();
        int value = x + lineValue * y;

        int parentIndex = parentTekton.getMap().getTektons().indexOf(parentTekton);
        
        sb.append("\"ht").append(value).append("\"").append(" : {\n");
        sb.append("\t \"parentTekton\" : T").append(parentIndex).append(", \n");
        sb.append("\t \"growthRate\" : ").append(growthRate).append(" \n");        
        sb.append("}");

        return sb.toString();
    }


}
