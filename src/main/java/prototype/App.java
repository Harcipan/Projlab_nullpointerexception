package prototype;

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

    public void setMap(Map map) {
        this.map = map;
    }

    public void run() {
        System.out.println("Fungorium prototype");
        System.out.println(" - By (NullPointerException)");

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
        System.out.println("Goodbye");
    }
}
