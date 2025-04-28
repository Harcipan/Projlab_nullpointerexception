package prototype;

import map.Map;
import map.Tile;
import player.FungusPlayer;
import player.InsectPlayer;
import prototype.commands.ExecuteTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/*
 * Class for storing and saving game data
 * This class is responsible for managing the game state, including player data, map data, and other relevant information.
 */
public class GameData {
    private Map map; //tha game map
    private List<FungusPlayer> fungusPlayers; //list of fungus players
    private List<InsectPlayer> insectPlayers; //list of insect players
    private int currentTurn; //the current turn number
    private int maxTurns; //the maximum number of turns in the game

    /**
     * save the game in the specified file
     * @param filePath file to save to
     * @return success or fail to save
     */
    public boolean saveGame(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            //List tektons
            //writer.write("Tektons:\n");

            //We use this variable so that we can correctly put commas after the curly brackets that specify game objects.
            boolean skipFirst = true;

            for (var tekton : map.getTektons()) {
                if(skipFirst){
                    skipFirst = false;
                }else{
                    writer.write(",\n\n");                    
                }
                writer.write(tekton.serialize());
            }

            for (var tekton : map.getTektons()) {
                for (var tile : tekton.getTiles()) {
                    if(skipFirst){
                        skipFirst = false;
                    }else{
                        writer.write(",\n\n");                    
                    }
                    writer.write(tile.serialize());
                }
            }

            // skip save if there are no insect players
            if (!(insectPlayers == null || insectPlayers.isEmpty())) {
                for (var insectPlayer : insectPlayers) {
                    for (var insect : insectPlayer.getControlledInsects()) {
                        if(skipFirst){
                            skipFirst = false;
                        }else{
                            writer.write(",\n\n");                    
                        }
                        writer.write(insect.serialize());
                    }
                }

                for (var insectPlayer : insectPlayers) {
                    for (var insect : insectPlayer.getControlledInsects()) {
                        for (var spore : insect.getUnderInfluence()) {
                            if(skipFirst){
                                skipFirst = false;
                            }else{
                                writer.write(",\n\n");                    
                            }
                            writer.write(spore.serialize());
                        }
                    }
                }
            }

            //skip save if there are no fungus players
            if (!(fungusPlayers == null || fungusPlayers.isEmpty())) {
                //list fungus bodies, mycelia and spores
                //writer.write("Fungus:\n");
                for (var fungusPlayer : fungusPlayers) {
                    for (var body : fungusPlayer.getFungusBodies()) {
                        if(skipFirst){
                            skipFirst = false;
                        }else{
                            writer.write(",\n\n");                    
                        }
                        writer.write(body.serialize());
                    }
                    for (var myc : fungusPlayer.getMycelia()) {
                        if(skipFirst){
                            skipFirst = false;
                        }else{
                            writer.write(",\n\n");                    
                        }
                        writer.write(myc.serialize());
                    }
                    for (var spore : fungusPlayer.getSpores()) {
                        if(skipFirst){
                            skipFirst = false;
                        }else{
                            writer.write(",\n\n");                    
                        }
                        writer.write(spore.serialize());
                    }
                }
            }

            return true;

                
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
        return false;
    }

    public boolean loadGame(String filePath) {
        // Load the game data from a file
        // This method should read the serialized game data from the specified file path and deserialize it
        //System.out.println("saving is not implemented yet :(");
        ExecuteTest e=new ExecuteTest();
        String[] test={"execute_test ",filePath};
        e.execute(test);
        return false;
        
    }

    /**
     * Update the stored game state to match the running app.
     * Should be called before saving the game
     * @param app The app to sync with
     */
    public void syncStored(App app) {
        
        this.map = app.getMap();
        this.fungusPlayers = app.getFungusPlayers();
        this.insectPlayers = app.getInsectPlayers();
        this.currentTurn = app.getCurrentTurn();
        this.maxTurns = app.getMaxTurns();
    }

    /** Update the app to match with the stored game state.
    * Should be called after loading the game
    * @param app The app to update
    */
    public void syncApp(App app) { 
        app.setMap(this.map);
        App.setFungusPlayers(this.fungusPlayers);
        App.setInsectPlayers(this.insectPlayers);
        app.setCurrentTurn(this.currentTurn);
        app.setMaxTurns(this.maxTurns);
    }
}
