package app;

import graphics.PanelRenderer;
import graphics.presenters.*;
import graphics.strategies.*;
import map.Map;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameCoordinator {
    private JFrame mainFrame;
    private PanelRenderer panelRenderer;
    private Map gameMap;
    public void setFrame(JFrame frame) { this.mainFrame = frame; }
    public void setPanelRenderer(PanelRenderer renderer) { this.panelRenderer = renderer; }

    /**
     * Starts the application by showing the main menu.
     */
    public void initiateRepaint() {
        if (panelRenderer != null) {
            panelRenderer.repaint(); // Repaint the panel to reflect changes
            System.out.println("GameCoordinator: Repaint called on PanelRenderer.");
        } else {
            System.err.println("GameCoordinator Error: PanelRenderer is null. Cannot repaint.");
        }
    }

    public void startApplication() {
        showMainMenu(); // Delegate to showMainMenu for clarity
    }

    public void startGame() {
        // invoke the InGamePresenter and set the render strategy to InGameStrategy
        System.out.println("GameCoordinator: Starting game...");
        if (panelRenderer == null) {
            System.err.println("GameCoordinator Error: PanelRenderer is null. Cannot start game.");
            return;
        }
        // 1. Create the Presenter for the in-game view
        InGamePresenter inGamePresenter = new InGamePresenter(this);

        // 2. Create the Strategy (View implementation) for the in-game view
        InGameStrategy inGameStrategy = new InGameStrategy(inGamePresenter);

        // 3. Tell PanelRenderer to use the in-game strategy
        panelRenderer.setRenderStrategy(inGameStrategy);

        // Set the size of the game to 1024x1024
        panelRenderer.setPreferredSize(new Dimension(1024, 1024));

        // Always pack and center the frame to force resize
        if (mainFrame != null) {
            mainFrame.setPreferredSize(new Dimension(1024, 1024));
            mainFrame.setMinimumSize(new Dimension(1024, 1024));
            mainFrame.setMaximumSize(new Dimension(1024, 1024));
            mainFrame.setSize(1024, 1024);
            panelRenderer.revalidate();
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        }

        System.out.println("GameCoordinator: Switched to InGameRenderStrategy with size 1024x1024.");
    }

    public void showOptionsScreen() {
        // TODO: Implement options screen logic
    }

    public void exitApplication() {
        // TODO: Implement exit logic
    }

    public void showMainMenu() {

        System.out.println("GameCoordinator: Setting up Main Menu...");
        if (panelRenderer == null) {
            System.err.println("GameCoordinator Error: PanelRenderer is null. Cannot show main menu.");
            return;
        }
        // 1. Create the Presenter for the main menu
        MainMenuPresenter mainMenuPresenter = new MainMenuPresenter(this);

        // 2. Create the Strategy (View implementation) for the main menu
        MainMenuStrategy mainMenuStrategy = new MainMenuStrategy(mainMenuPresenter);

        // 3. Tell PanelRenderer to use the main menu strategy
        panelRenderer.setRenderStrategy(mainMenuStrategy);
        System.out.println("GameCoordinator: Switched to MainMenuRenderStrategy.");

        // Optional: Ensure frame is packed and visible if not already done
        if (mainFrame != null && !mainFrame.isVisible()) {
             mainFrame.pack();
             mainFrame.setLocationRelativeTo(null);
             mainFrame.setVisible(true);
        }
    }
     
    private void repaint() {
        if (panelRenderer != null) {
            panelRenderer.repaint(); // Repaint the panel to reflect changes
            System.out.println("GameCoordinator: Repaint called on PanelRenderer.");
        } else {
            System.err.println("GameCoordinator Error: PanelRenderer is null. Cannot repaint.");
        }
    }

    public void showNewGameSetupScreen() {
        System.out.println("GameCoordinator: Setting up New Game Setup Screen...");
        if (panelRenderer == null) {
            System.err.println("GameCoordinator Error: PanelRenderer is null. Cannot show new game setup screen.");
            return;
        }
        // 1. Create the Presenter for the new game setup
        NewGameSetupPresenter newGameSetupPresenter = new NewGameSetupPresenter(this);

        // 2. Create the Strategy (View implementation) for the new game setup
        NewGameSetupStrategy newGameSetupStrategy = new NewGameSetupStrategy(newGameSetupPresenter);

        // 3. Tell PanelRenderer to use the new game setup strategy
        panelRenderer.setRenderStrategy(newGameSetupStrategy);
        System.out.println("GameCoordinator: Switched to NewGameSetupStrategy.");
    }

    public int getMapSize() {
        
        int ret = -1;

        if (gameMap == null) {
            System.err.println("GameCoordinator Error: Game map is null. Cannot get map size.");
            return -1; // or some default value
        } 
        
        if (gameMap.getWidth() != gameMap.getHeight()) {
            System.err.println("GameCoordinator Error: Map is not square. Cannot get map size.");
            return -1; // or some default value
        } 
        
        if (gameMap.getWidth() != 64 && gameMap.getWidth() != 32) {
            System.err.println("GameCoordinator Error: Map size is not 32 or 64. Cannot get map size.");
            return -1; // or some default value
        } 
        
        if (gameMap != null) {
            ret =  gameMap.getWidth(); // Assuming width and height are the same for square maps
        } 
        
        return ret;
    }
}
