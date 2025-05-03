package graphics.presenters;

import app.GameCoordinator;
import app.PlayerInfo;
import java.util.List;

/**
 * InGamePresenter acts as the Presenter in the MVP pattern for the in-game view.
 * It mediates between the game logic (model) and the InGameStrategy (view).
 */
public class InGamePresenter {
    private GameCoordinator coordinator;

    public InGamePresenter(GameCoordinator coordinator) {
        if (coordinator == null) {
            throw new IllegalArgumentException("GameCoordinator cannot be null");
        }
        this.coordinator = coordinator;
    }

    public GameCoordinator getCoordinator() {
        return coordinator;
    }

    public int getMapSize() {
        return coordinator.getMapSize();
    }

    public int getHUDWidth() {
        return coordinator.getHUDWidth(); // Get the HUD width from the coordinator
    }

    public List<PlayerInfo> getPlayers() {
        return coordinator.getPlayers();
    }

    public int getCurrentTurn() {
        return coordinator.getCurrentTurn();
    }
}
