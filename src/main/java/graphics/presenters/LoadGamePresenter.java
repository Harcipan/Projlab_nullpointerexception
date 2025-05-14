package graphics.presenters;

import app.GameCoordinator;

public class LoadGamePresenter {
    private GameCoordinator coordinator;

    public LoadGamePresenter(GameCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public GameCoordinator getCoordinator() {
        return coordinator;
    }

    public void onLoadSaveClicked(String saveName) {
        // Placeholder: implement actual loading logic
        System.out.println("[LoadGamePresenter] Load requested for: " + saveName);
        // coordinator.loadGame(saveName); // Uncomment when implemented
    }

    public void onBackToMainMenuClicked() {
        if (coordinator != null) {
            coordinator.showMainMenu();
        }
    }
}
