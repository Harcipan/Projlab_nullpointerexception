package prototype.commands;

import prototype.*;

public class Save extends Command {
    private GameData gameData;
    public Save() {
        super("save", "Write the game state to the specified file", "save <filename>");
        this.gameData = null;
    }

    @Override
    public boolean execute(String[] args) {
        gameData = app.getGameData();
        if (args.length != 2) {
            System.out.println("Usage: save <file_path>");
            return false;
        }
        
        String path = args[1];
        if (gameData == null) {
            System.out.println("Data manager is not initialized.");
            return true; //exit the program
        }
        gameData.syncStored(app);
        boolean success = gameData.saveGame(path);
        if (success) {
            System.out.println("Save successfull.");
        } else {
            System.out.println("Failed to save.");
        }

        return false; //keep app running
    }
}
