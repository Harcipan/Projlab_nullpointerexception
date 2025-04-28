package prototype.commands;

import prototype.*;

public class Load extends Command {
    private GameData gameData;

    public Load() {
        super("load", "Load the game state from the specified file", "load <filename>");
        this.gameData = null;

    }

    @Override
    public boolean execute(String[] args) {
        gameData = app.getGameData();
        if (args.length != 2) {
            System.out.println("Usage: load <file_path>");
            return false;
        }
        
        String path = args[1];
        if (gameData == null) {
            System.out.println("Data manager is not initialized.");
            return true; //exit the program
        }
        boolean success = gameData.loadGame(path);
        if (success) {
            gameData.syncApp(app);
            System.out.println("Load successfull.");
        } else {
            System.out.println("Failed to load.");
        }

        return false; //keep app running
    }
}
