package prototype;

import java.util.Scanner;

public class App {
    boolean running = true;
    CommandList commands;

    public App(){

    }

    public void run(){
        System.out.println("Fungorium prototype");
        System.out.println(" - By (NullPointerException)");
        Scanner scanner = new Scanner(System.in);
        while(running){
            System.out.print("Pr> ");
            String[] cmdArgs = scanner.nextLine().trim().split("\\s+");
            String command = cmdArgs[0];
            boolean shouldStop = commands.get(command).execute(cmdArgs);
            if(shouldStop){
                running = false;
                break;
            }
        }
        System.out.println("Goodbye");
    }
}
