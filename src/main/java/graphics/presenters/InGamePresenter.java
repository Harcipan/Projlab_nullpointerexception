package graphics.presenters;

import app.GameCoordinator;

/**
 * InGamePresenter acts as the Presenter in the MVP pattern for the in-game view.
 * It mediates between the game logic (model) and the InGameStrategy (view).
 */
public class InGamePresenter {
    private GameCoordinator coordinator;
    private int score = 0; // Example field, replace with actual game state as needed

    public InGamePresenter(GameCoordinator coordinator) {
        if (coordinator == null) {
            throw new IllegalArgumentException("GameCoordinator cannot be null");
        }
        this.coordinator = coordinator;
    }

    // Called by the view when the Pause button is clicked
    public void onPauseClicked() {
        System.out.println("InGamePresenter: Pause clicked");
        // TODO: Implement pause logic, e.g., coordinator.pauseGame();
    }

    // Called by the view when the Save button is clicked
    public void onSaveClicked() {
        System.out.println("InGamePresenter: Save clicked");
        // TODO: Implement save logic, e.g., coordinator.saveGame();
    }

    // Called by the view to get the current score
    public int getScore() {
        return score;
    }

    // Example: update score (could be called by the model or coordinator)
    public void setScore(int score) {
        this.score = score;
    }

    public GameCoordinator getCoordinator() {
        return coordinator;
    }

    public int getMapSize() {
        return coordinator.getMapSize();
    }
}
