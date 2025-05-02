package prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entities.GameEntity;
import map.Map;
import player.*;

public class App {
    static boolean running = true;
    CommandList commands;
    CommandParser commandParser;
    GameData gameData;  //data store for writing to file
    Map map;
    int currentTurn = 0;
    int maxTurns = 1000; //max turns for the game, if it reaches this, the game ends

    static List<InsectPlayer> insectPlayers;
    static List<FungusPlayer> fungusPlayers;

    public App() { //app inicialization so loggers work
        insectPlayers = new ArrayList<InsectPlayer>();

        fungusPlayers = new ArrayList<FungusPlayer>();
    }

    public void reset(){
        currentTurn = 0;
        maxTurns = 1000;

        insectPlayers.clear();
        fungusPlayers.clear();
        map = null;
        gameData = new GameData();

        gameData.syncStored(this);

        
        GameEntity.reset();
    }

    public static List<InsectPlayer> getInsectPlayers() {
        return insectPlayers;
    }
    public static void addInsectPlayer(InsectPlayer insectPlayer) {
        insectPlayers.add(insectPlayer);
    }
    public static void setInsectPlayers(List<InsectPlayer> insectPlayers) {
        App.insectPlayers = insectPlayers;
    }

    public static List<FungusPlayer> getFungusPlayers() {
        return fungusPlayers;
    }
    public static void addFungusPlayer(FungusPlayer fungusPlayer) {
        fungusPlayers.add(fungusPlayer);
    }
    public static void setFungusPlayers(List<FungusPlayer> fungusPlayers) {
        App.fungusPlayers = fungusPlayers;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public Map getMap() {
        return map;
    }

    public CommandList getCommands() {
		return commands;
	}

    public CommandParser getCommandParser(){
        return commandParser;
    }

    public GameData getGameData(){
        return gameData;
    }

	public void setMap(Map map) {
        this.map = map;
    }

    public static void start(){
        running = true;
    }

    public static void stop(){
        running = false;
    }

    public void run() {
        System.out.println("Fungorium prototype");
        System.out.println(" - By (NullPointerException)");
        System.out.println("To get a list of all available commands, type in \"list\"");

        Scanner scanner = new Scanner(System.in);

        commands = new CommandList(this, scanner);
        commandParser = new CommandParser(commands);
        gameData = new GameData();

        gameData.syncStored(this);

        while (running) {
            System.out.print("Pr> ");
            String[] cmdArgs = scanner.nextLine().trim().split("\\s+");
            String command = cmdArgs[0];
            if(commands.get(command)!=null) { //protects against execption when comment doesnt exist
                boolean shouldStop = commands.get(command).execute(cmdArgs);
                if (shouldStop) {
                    running = false;
                    break;
                }
            }
        }

        printExitMessage();
    }

    public static void printExitMessage() {
        //A nagyrészét Claude AI adta nekem... additions are welcome :)
        String[] haha = {
            "Goodbye!",
            "バイバイ!",
            "さよなら",
            "Good riddance.",
            "Holnap után kiskedden!",
            "Sporing off now, see you later!",
            "Time to hibernate until the next mycelial network connection...",
            "Your session has been decomposed by friendly fungi.",
            "The tectonic plates are shifting... logging you out!",
            "Insects have eaten your session. Come back after they digest!",
            "Your connection has split in two, just like a spore-affected insect!",
            "Mycelium network disconnected. Have a fungi-tastic day!",
            "Command terminated: Too many spores in the system.",
            "Program execution has slowed down after consuming questionable spores...",
            "This session has been composted. Goodbye!",
            "Error 404: Fungi went underground. Please try again later.",
            "Your connection has drifted to another tectonic plate. Farewell!",
            "System hibernating like a bear in the Bükk Mountains. Logging off...",
            "Your tectonic plates shifted all the way to the Hungarian Great Plain. Bye-bye!",
            "Tectonic activity detected near Tokaj wine region - fungi taking shelter!"
        };

        Random rand = new Random();
        System.out.println(haha[rand.nextInt(0, haha.length)]);
    }
}
