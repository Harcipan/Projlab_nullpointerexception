package graphics.presenters;

import app.GameCoordinator; // Import the GameCoordinator class
import prototype.App;

/* This class acts as an intermediary between the GUI and the application logic.
 * The purpose is to follow the MVP (Model-View-Presenter) pattern, where this class serves as the Presenter.
 * The reason to use this so it is decoupled from the GUI and can be tested independently, but still aligns to Swing's inability to bind data natively.
 * The Strategy pattern takes the role of the View, where the concrete strategy is the actual GUI. (e.g. MainMenuRenderStrategy where user interactions are handled)
 * */

public class MainMenuPresenter {

    private GameCoordinator coordinator; // Field to hold the coordinator reference

    // Constructor to receive the coordinator
    public MainMenuPresenter(GameCoordinator coordinator) {
        if (coordinator == null) {
            throw new IllegalArgumentException("GameCoordinator cannot be null");
        }
        this.coordinator = coordinator;
    }

    // --- Actions triggered BY the View ---

    public void onStartGameClicked() {
        System.out.println("MainMenuPresenter: Handling 'Start Game' action.");
        // Delegate the actual game starting logic to the coordinator/model
        coordinator.showNewGameSetupScreen();
    }

    public void onTesterClicked() {
        System.out.println("MainMenuPresenter: Handling 'Tester' action.");
        // run app in a different thread
        Thread testerThread = new Thread(() -> {
            App app = new App();
            app.run(); // Assuming run() is non-static
        });
        testerThread.start();
    }

    public void onExitClicked() {
        System.out.println("MainMenuPresenter: Handling 'Exit' action.");
        // Delegate exiting to the coordinator
        coordinator.exitApplication();
    }

    public void onLoadGameClicked() {
        System.out.println("MainMenuPresenter: Handling 'Load Game' action.");
        // Delegate loading game logic to the coordinator
        coordinator.showLoadGameScreen();
    }
}
