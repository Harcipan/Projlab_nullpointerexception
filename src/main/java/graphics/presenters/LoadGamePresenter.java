package graphics.presenters;

import java.io.IOException;
import java.util.List;

import app.GameCoordinator;

public class LoadGamePresenter {
    private GameCoordinator coordinator;

    public LoadGamePresenter(GameCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public GameCoordinator getCoordinator() {
        return coordinator;
    }

    public List<String> getAllSaveNames() throws IOException{
        return coordinator.getAllSaveNames();
    }

    public void onLoadSaveClicked(String saveName) {
        System.out.println("[LoadGamePresenter] Load requested for: " + saveName);
        coordinator.loadGame(saveName);
    }

    public void onBackToMainMenuClicked() {
        if (coordinator != null) {
            coordinator.showMainMenu();
        }
    }
}
