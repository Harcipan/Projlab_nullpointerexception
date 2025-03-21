package entities;

<<<<<<< HEAD
import map.Tile;

=======
>>>>>>> 682187e326a4ae59cdee63ff95aeb6cb4ebcf6c7
public class Mycelium extends Fungus{
    boolean isDying = false;
    List<FungusBody> connectedBodies = new ArrayList<FungusBody>();

    public Mycelium(int health, Tile currentTile) {
        super(health, currentTile);
    }

    // Reconnect with the mycelium network, recover health
    // Neighbor cells will be checked for mycelium
    public void connect(){
        // Will implement later
    }

    // Disconnect from the mycelium network, lose health
    public void detach(){
        // Will implement later
    }

    // Check if the mycelium has been connected to a body
    private boolean searchConnection(){
        // Will implement later
        return false;
    }



    
}
