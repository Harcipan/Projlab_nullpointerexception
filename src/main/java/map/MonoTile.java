package map;

public class MonoTile extends Tile{

    public MonoTile(int growthRate, Tekton parentTekton){
        super(growthRate, 1, parentTekton);
    }

    public boolean isTaken() {
        return myceliumSpace == 0;
    }

    @Override
    public String serialize(){
        StringBuilder sb = new StringBuilder();
        int lineValue = parentTekton.getMap().getWidth();
        int value = x + lineValue * y;

        int parentIndex = parentTekton.getMap().getTektons().indexOf(parentTekton);
        
        sb.append("\"mt").append(value).append("\"").append(" : {\n");
        sb.append("\t \"parentTekton\" : T").append(parentIndex).append(", \n");
        sb.append("\t \"growthRate\" : ").append(growthRate).append(" \n");
        sb.append("\t \"isTaken\" : ").append(isTaken()).append(" \n");
        sb.append("}");

        return sb.toString();
    }
}
