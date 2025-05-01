package graphics.presenters;

import app.GameCoordinator;
// Import any necessary classes for game settings (create if needed)
// import app.GameSettings;

public class NewGameSetupPresenter {

    private GameCoordinator coordinator; // To hold the settings being configured

    public NewGameSetupPresenter(GameCoordinator coordinator) {
        this.coordinator = coordinator;
        // this.currentSettings = new GameSettings(); // Initialize default settings
        System.out.println("NewGameSetupPresenter created.");
    }

    // --- Methods called by the View (NewGameSetupStrategy) ---
    public void onSettingChanged(String settingName, Object value) {
        System.out.println("NewGameSetupPresenter: Setting '" + settingName + "' changed to " + value);
        // TODO: Update currentSettings based on the change
        // e.g., if (settingName.equals("playerType")) currentSettings.setPlayerType((PlayerType)value);
    }

    public void onConfirmSetupClicked() {
        System.out.println("NewGameSetupPresenter: Confirm Setup clicked.");
        // TODO: Validate currentSettings if necessary
        // Tell coordinator to start the game with the chosen settings
        // coordinator.startGame(currentSettings);
        System.out.println("NewGameSetupPresenter: Requesting game start (actual settings TBD).");
        coordinator.startGame(); // Using parameterless startGame for now
    }

    public void onBackToMainMenuClicked() {
        System.out.println("NewGameSetupPresenter: Back to Main Menu clicked.");
        coordinator.showMainMenu();
    }

    // --- Methods to provide data TO the View ---
    // public GameSettings getCurrentSettings() { return currentSettings; }
}