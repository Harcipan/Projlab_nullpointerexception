package prototype;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommandParser {
    private final CommandList commandList;
    private String currentCommand = null;
    private String[] currentCommandArgs = null;
    private static final String BASE_PATH = "src/main/commands_in/";

    public CommandParser(CommandList commandList) {
        this.commandList = commandList;
    }

    /*
     * Executes the command set as currentCommand with the arguments set as currentCommandArgs.
     */
    public void executeCommand() {
        if (currentCommand == null || currentCommandArgs == null) {
            return;
        }
        Command command = commandList.get(currentCommand);
        System.out.println("Executing command: " + currentCommand + " with args: " + String.join(", ", currentCommandArgs));
        if (command != null) {
            command.execute(currentCommandArgs);
        } else {
            System.out.println("Unknown command: " + currentCommand);
        }
    }
    /**
     * * Loads and runs test from a text file. The file should contain commands in the same format as the console input.
     * 
     * @param number The number of the test to run.
     * @return true if the test was executed successfully, false otherwise.
     */
    public boolean runInputTextFile(String filePath) {
        System.out.println("Loading input from: " + filePath);
        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    currentCommand = parts[0].toLowerCase();
                    currentCommandArgs = new String[parts.length];
                    System.arraycopy(parts, 0, currentCommandArgs, 0, parts.length);
                    executeCommand();
                    // Reset currentCommand and currentCommandArgs for the next line
                    currentCommand = null;
                    currentCommandArgs = null;
            }
        }
        System.out.print("test executed");
        return true;
        
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return false;
        }
    }

    /*
     * find and run the test file with the given number
     * the file should be in the format "<number>_description.txt" in the basePath directory
     */
    public boolean runInputTextFile(int number) {
        java.io.File folder = new java.io.File(BASE_PATH);
        if (! folder.isDirectory()){
            System.out.println("Base path is not a directory: " + BASE_PATH);
            return false;
        }
        java.io.File[] files = folder.listFiles((dir, name) -> name.startsWith(number + "_"));
        if (files == null || files.length == 0) {
            System.out.println("No test file found for number: " + number);
            return false;
        }
        // Assuming we take the first matching file
        java.io.File testFile = files[0];
        return runInputTextFile(testFile.getPath());
    }
}