package graphics.presenters;

import app.GameCoordinator;
import app.PlayerInfo; // Import PlayerInfo
import app.PlayerType; // Import PlayerType

import java.util.ArrayList;
import java.util.List;

public class NewGameSetupPresenter {

    private GameCoordinator coordinator;
    private List<PlayerInfo> players;
    private String saveName = "MyGame"; // Default save name
    private int mapSize = 128; // Default map size

    public NewGameSetupPresenter(GameCoordinator coordinator) {
        this.coordinator = coordinator;
        this.players = new ArrayList<>();
        System.out.println("NewGameSetupPresenter created with default players.");
    }

    // --- Methods called by the View (NewGameSetupStrategy) ---

    public void onSettingChanged(String settingName, Object value) {
        System.out.println("NewGameSetupPresenter: Setting '" + settingName + "' changed to " + value);
        // TODO: Update currentSettings based on the change
        // e.g., if (settingName.equals("playerType")) currentSettings.setPlayerType((PlayerType)value);
    }

    public void addPlayerRequested() {
        System.out.println("NewGameSetupPresenter: Add Player requested");
        int nextNum = players.size() + 1;
        PlayerType type = (nextNum % 2 == 0) ? PlayerType.INSECT : PlayerType.FUNGUS;
        players.add(new PlayerInfo("Player " + nextNum, type));
    }

    public void setSaveName(String name) {
        // Called when focus lost or Enter pressed on save name field
        System.out.println("NewGameSetupPresenter: Save name updated to: " + name);
        this.saveName = name;
   }

   public void updatePlayerName(int playerIndex, String newName) {
       if (playerIndex >= 0 && playerIndex < players.size()) {
           PlayerInfo oldInfo = players.get(playerIndex);
           if (!oldInfo.name().equals(newName)) {
               players.set(playerIndex, new PlayerInfo(newName, oldInfo.type()));
               System.out.println("NewGameSetupPresenter: Updated player " + playerIndex + " name to: " + newName);
           }
       } else {
           System.err.println("NewGameSetupPresenter: Invalid player index for name update: " + playerIndex);
       }
   }

    public void setMapSize(int size) {
        if (size == 32 || size == 64) {
            System.out.println("NewGameSetupPresenter: Map size set to: " + size);
            this.mapSize = size;
        } else {
            System.err.println("NewGameSetupPresenter: Invalid map size requested: " + size);
        }
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
    public List<PlayerInfo> getPlayers() {
        return players; // Return defensive copy if modification is risky: return new ArrayList<>(players);
    }

    public String getSaveName() {
        return saveName;
    }

    public int getMapSize() {
        return mapSize;
    }

    // --- Getter for Coordinator ---
    public GameCoordinator getCoordinator() {
        return coordinator;
    }
}