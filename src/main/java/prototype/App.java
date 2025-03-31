package prototype;

import java.util.Random;
import java.util.Scanner;

import map.Map;
import player.*;

public class App {
    boolean running = true;
    CommandList commands;
    Map map;

    InsectPlayer insectPlayer = new InsectPlayer();
    FungusPlayer fungusPlayer = new FungusPlayer();

    public InsectPlayer getInsectPlayer() {
        return insectPlayer;
    }

    public FungusPlayer getFungusPlayer() {
        return fungusPlayer;
    }

    public Map getMap() {
        return map;
    }

    public CommandList getCommands() {
		return commands;
	}

	public void setMap(Map map) {
        this.map = map;
    }

    public void run() {
        System.out.println("Fungorium prototype");
        System.out.println(" - By (NullPointerException)");
        System.out.println("To get a list of all available commands, type in \"list\"");

        Scanner scanner = new Scanner(System.in);

        commands = new CommandList(this, scanner);

        while (running) {
            System.out.print("Pr> ");
            String[] cmdArgs = scanner.nextLine().trim().split("\\s+");
            String command = cmdArgs[0];
            boolean shouldStop = commands.get(command).execute(cmdArgs);
            if (shouldStop) {
                running = false;
                break;
            }
        }

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
