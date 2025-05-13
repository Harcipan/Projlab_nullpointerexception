package graphics.presenters;

import app.GameCoordinator;
import graphics.accentManager.PaletteGenerator;
import player.FungusPlayer;
import player.InsectPlayer;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class NewGameSetupPresenter {

    private GameCoordinator coordinator;
    private List<Player> players;
    private String saveName = "MyGame"; // Default save name
    private int mapSize = 32; // Default map size

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
        Player player = (nextNum % 2 == 0) ? new InsectPlayer() : new FungusPlayer();
        players.add(player);
    }

    public void setSaveName(String name) {
        // Called when focus lost or Enter pressed on save name field
        System.out.println("NewGameSetupPresenter: Save name updated to: " + name);
        this.saveName = name;
   }

   public void updatePlayerName(int playerIndex, String newName) {
       if (playerIndex >= 0 && playerIndex < players.size()) {
           Player oldInfo = players.get(playerIndex);
           if (!oldInfo.getName().equals(newName)) {
               oldInfo.setName(newName);
               System.out.println("NewGameSetupPresenter: Updated player " + playerIndex + " name to: " + newName);
           }
       } else {
           System.err.println("NewGameSetupPresenter: Invalid player index for name update: " + playerIndex);
       }
   }

    /**
     * Toggles the player type (FUNGUS <-> INSECT) for the player at the given index.
     */
    public void togglePlayerType(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.size()) {
            Player oldPlayer = players.get(playerIndex);
            String name = oldPlayer.getName();
            Player newPlayer;
            if (oldPlayer instanceof FungusPlayer) {
                newPlayer = new InsectPlayer();
            } else {
                newPlayer = new FungusPlayer();
            }
            newPlayer.setName(name);
            players.set(playerIndex, newPlayer);
            System.out.println("NewGameSetupPresenter: Toggled player " + playerIndex + " type.");
        } else {
            System.err.println("NewGameSetupPresenter: Invalid player index for type toggle: " + playerIndex);
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
        
        // Setting player accent colors based on the number of players
        PaletteGenerator.setPlayerAccentColors(players);

        System.out.println("NewGameSetupPresenter: Requesting game start (actual settings TBD).");
        coordinator.startGame(mapSize, players, saveName); // Pass the map size, players, and save name to the coordinator
    }

    public void onBackToMainMenuClicked() {
        System.out.println("NewGameSetupPresenter: Back to Main Menu clicked.");
        coordinator.showMainMenu();
    }

    // --- Methods to provide data TO the View ---
    public List<Player> getPlayers() {
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