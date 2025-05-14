package graphics.presenters;

import app.GameCoordinator;
import entities.FungusBody;
import entities.Insect;
import map.Tile;
import player.FungusPlayer;
import player.InsectPlayer;
import player.Player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * InGamePresenter acts as the Presenter in the MVP pattern for the in-game view.
 * It mediates between the game logic (model) and the InGameStrategy (view).
 */
public class InGamePresenter {
    private GameCoordinator coordinator;

    // Placement phase state
    private boolean placementPhase = true;
    private int placingPlayerIndex = 0;
    private Point placementHover = null;

    public InGamePresenter(GameCoordinator coordinator) {
        if (coordinator == null) {
            throw new IllegalArgumentException("GameCoordinator cannot be null");
        }
        this.coordinator = coordinator;
    }

    /*
     * This is against the MVP pattern
     * @deprecated
     */
    @Deprecated
    public GameCoordinator getCoordinator() {
        return coordinator;
    }

    public List<FungusPlayer> getFungusPlayers() {
        List<Player> players = coordinator.getPlayers();
        List<FungusPlayer> fungusPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player instanceof FungusPlayer) {
                fungusPlayers.add((FungusPlayer) player);
            }
        }
        return fungusPlayers;
    }

    public List<InsectPlayer> getInsectPlayers() {
        List<Player> players = coordinator.getPlayers();
        List<InsectPlayer> insectPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player instanceof InsectPlayer) {
                insectPlayers.add((InsectPlayer) player);
            }
        }
        return insectPlayers;
    }

    public int getMapSize() {
        return coordinator.getMapSize();
    }

    public int getHUDWidth() {
        return coordinator.getHUDWidth(); // Get the HUD width from the coordinator
    }

    public List<Player> getPlayers() {
        return coordinator.getPlayers();
    }

    public Player getCurrentPlayer(){
        List<Player> players = getPlayers();
        int currentTurn = getCurrentTurn();
        return players.get(currentTurn % players.size());
    }

    public int getCurrentTurn() {
        return coordinator.getCurrentTurn();
    }

    public boolean isPlacementPhase() {
        return placementPhase;
    }

    public void setPlacementPhase(boolean phase) {
        this.placementPhase = phase;
    }

    public int getPlacingPlayerIndex() {
        return placingPlayerIndex;
    }

    public void setPlacingPlayerIndex(int idx) {
        this.placingPlayerIndex = idx;
    }

    public Point getPlacementHover() {
        return placementHover;
    }

    public void setPlacementHover(Point p) {
        this.placementHover = p;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= coordinator.getGameMap().getWidth() || y >= coordinator.getGameMap().getHeight()) {
            return null;
        }
        return coordinator.getGameMap().getTile(x, y);
    }
}
