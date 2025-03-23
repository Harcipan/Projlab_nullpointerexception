import use_cases.UseCase;
import use_cases.MyceliumDies;
import use_cases.UseCaseList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   Tester program for Use-Cases derived from Sequence Diagrams.
*   To create a test, add a new class that extends UseCase and implement the execute method.
*   To add the new use-case to the tester program, add it to the UseCaseList constructor.
* */

public class Main {
    public static void main(String[] args) {
        System.out.println("Tester program for Use-Cases derived from Sequence Diagrams.\nUse the command 'list' to see the available use-cases. Use the command 'execute' followed by the name of the use-case or ID number to execute it.");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        UseCaseList useCases = new UseCaseList();

        // Tester program loop
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Command handling
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                running = false;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Sorting use-cases...");
                useCases.sortUseCases();
                System.out.println("Available use-cases:");
                for (UseCase useCase : useCases.getUseCases()) {
                    System.out.println(useCase.getID() + " - " + useCase.getName());
                }
            } else if (input.toLowerCase().startsWith("execute ")) {
                String identifier = input.substring(8).trim();
                try {
                    int id = Integer.parseInt(identifier);
                    Boolean isFound = false;
                    for (UseCase useCase : useCases.getUseCases()) {
                        if (useCase.getID() == id) {
                            useCase.execute();
                            isFound = true;
                            break;
                        }
                    }
                    if (!isFound) {
                        System.out.println("Use-case with ID " + id + " not found.");
                    }
                } catch (NumberFormatException e) {
                    // Not a number, search by name
                    for (UseCase useCase : useCases.getUseCases()) {
                        if (useCase.getName().equalsIgnoreCase(identifier)) {
                            useCase.execute();
                            break;
                        }
                    }
                    // keyword 'all'
                    if (identifier.equalsIgnoreCase("all")) {
                        System.out.println("Sorting use-cases...");
                        useCases.sortUseCases();
                        for (UseCase useCase : useCases.getUseCases()) {
                            System.out.println("\n====== Use Case " + useCase.getID() + " - " + useCase.getName() + " ======");
                            useCase.execute();
                        }
                    }
                }
            } else {
                System.out.println("Unknown command. Try 'list', 'execute [id/name/all]', or 'exit'");
            }
        }

        scanner.close();
        System.out.println("Program terminated.");

    }
}
