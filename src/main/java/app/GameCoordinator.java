package app;

import graphics.PanelRenderer;
import graphics.presenters.*;
import graphics.strategies.*;
import map.Map;
import map.Tekton;
import prototype.App;
import player.*;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.List;

public class GameCoordinator 
{
    // Singleton instances
    private JFrame mainFrame;
    private PanelRenderer panelRenderer;


    // Game state variables
    private Map gameMap;
    private List<Player> players;
    private int currentTurn;

    public void setFrame(JFrame frame) { this.mainFrame = frame; }
    public void setPanelRenderer(PanelRenderer renderer) { this.panelRenderer = renderer; }

    final int HUD_WIDTH = 350;
    final int GAME_WINDOW_WIDTH = 1024 + HUD_WIDTH;
    final int GAME_WINDOW_HEIGHT = 1024;

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

    public void startGame(int mapSize, List<Player> players, String saveName) {
        // invoke the InGamePresenter and set the render strategy to InGameStrategy
        System.out.println("GameCoordinator: Starting game...");

        // Create the game map based on the selected size
        if (mapSize == 32) {
            gameMap = new Map(32, 32); // Create a 32x32 map
            if (gameMap == null) {
                System.err.println("GameCoordinator Error: Game map is null. Cannot start game.");
                return;
            }
            System.out.println("GameCoordinator: Created a 32x32 map.");
            // generate a tekton and assign tiles
        } else if (mapSize == 64) {
            gameMap = new Map(64, 64); // Create a 64x64 map
        } else {
            System.err.println("GameCoordinator Error: Invalid map size. Cannot start game.");
            return;
        }
        // dump the map hash to the console for debugging
        System.out.println("GameCoordinator: Map hash: " + gameMap.hashCode());
        Tekton tek = new Tekton(gameMap);
        // dump the tekton hash to the console for debugging
        System.out.println("GameCoordinator: Tekton hash: " + tek.hashCode());
        // for every tile in the map, assign the tile to the tekton and its reverse
        for (int i = 0; i < gameMap.getWidth(); i++) {
            for (int j = 0; j < gameMap.getHeight(); j++) {
                gameMap.getTile(i,j).setParentTekton(tek);
                tek.addTile(gameMap.getTile(i,j));
            }
        }

        this.players = players;
        this.currentTurn = 0; // Start with the first player

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
            mainFrame.setPreferredSize(new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT));
            mainFrame.setMinimumSize(new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT));
            mainFrame.setMaximumSize(new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT));
            mainFrame.setSize(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
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
        System.out.println("GameCoordinator: Exiting application...");
        if (mainFrame != null) {
            mainFrame.dispose(); // Close the main frame
            System.out.println("GameCoordinator: Main frame disposed.");
        } else {
            System.err.println("mainFrame is null. No idea how this happened.");
        }
        App.stop(); // Stop and exit the application
        App.printExitMessage();
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
        
        if (!(gameMap.getWidth() != 64 || gameMap.getWidth() != 32)) {
            System.err.println("GameCoordinator Error: Map size is not 32 or 64. Cannot get map size.");
            return -1; // or some default value
        } 
        
        if (gameMap != null) {
            ret =  gameMap.getWidth(); // Assuming width and height are the same for square maps
        } 
        
        return ret;
    }

    public int getWindowWidth() {
        return mainFrame != null ? mainFrame.getWidth() : 0;
    }

    public int getWindowHeight() {
        return mainFrame != null ? mainFrame.getHeight() : 0;
    }

    public int getHUDWidth() {
        return HUD_WIDTH;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int turn) {
        this.currentTurn = turn;
    }

    public Map getGameMap() {
        return gameMap;
    }
}