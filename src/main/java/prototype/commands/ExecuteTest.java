package prototype.commands;

import prototype.App;
import prototype.Command;
import prototype.CommandParser;

public class ExecuteTest extends Command {
    private CommandParser commandParser;
    
    public ExecuteTest() {
        super("execute_test", "Executes a test file with commands");
        this.commandParser = null; // Initialize with null, will be set in CommandList
    }

    /*
     * execute a command based on its calling number
     */
    @Override
    public boolean execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: execute_test <file_path>");
            return false;
        }

        if(args[1].equals("all")){
            executeAllTests();
        }
        else {
            executeTest(Integer.parseInt(args[1])); // Execute the test file with the given number
        }
        return false; //exit the program
         // Keep the program running after executing the test
    }

    public void executeAllTests() {
        System.out.println("Executing all tests...");
        for (int i = 1; i <= 30; i++) { // Assuming you have 10 test files
            executeTest(i); // Convert int to char
        }
    }

    public boolean executeTest(int number) {
        commandParser = app.getCommandParser(); // Get the command parser from the app
        if (commandParser == null) {
            System.out.println("Command parser is not initialized.");
            return false; //exit the program
        }
        boolean success = commandParser.runInputTextFile(number);
        if (success) {
            System.out.println("Test executed successfully.");
        } else {
            System.out.println("Failed to execute test.");
        }

        System.out.println("\n-------------------------\n");

        compareToExpectedOutput(number); // Compare the output to the expected output

        System.out.println("\n-------------------------\n");
        System.out.println("\n\n\n");
        
        app.reset();

        return true;
    }

    /*
     * Compare the output of the test to the expected output
     * Goes through the output file and compares it to the expected output line by line
     * If the output is not as expected, print the line number and the expected and actual output
     * Known issue: if the output is shorter than the expected output, the missing lines will not be shown as missing
     */
    public void compareToExpectedOutput(int number) {
        System.out.println("Comparing output of test " + number + " to expected output...");
        //get currect directory
        String currentDirectory = System.getProperty("user.dir");

        String outputFilePath = currentDirectory+"\\out.txt";
        String expectedOutputFilePath = getExpectedOutputPath(number);//"src\\main\\commands_out\\"+"expected_output.txt";

        System.out.println("Output file: " + outputFilePath);
        System.out.println("Expected output file: " + expectedOutputFilePath);
        try (java.io.BufferedReader outputReader = new java.io.BufferedReader(new java.io.FileReader(outputFilePath));
             java.io.BufferedReader expectedOutputReader = new java.io.BufferedReader(new java.io.FileReader(expectedOutputFilePath))) {
            String outputLine;
            String expectedOutputLine;
            int lineNumber = 1;
            boolean isEqual = true;
            while ((outputLine = outputReader.readLine()) != null && (expectedOutputLine = expectedOutputReader.readLine()) != null) {
                if (!outputLine.equals(expectedOutputLine)) {
                    System.out.println("Mismatch at line " + lineNumber + ": Expected " + expectedOutputLine + " but got " + outputLine);
                    isEqual = false;
                }
                lineNumber++;
            }
            if (isEqual) {
                System.out.println("All lines match!");
            } else {
                System.out.println("There were mismatches in the output.");
            }
        } catch (java.io.IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    /*
     * Get the expected output file path based on the test number
     * The expected output file should be in the format "<number>_expected_output.txt" in the basePath directory
     */
    public String getExpectedOutputPath(int number) {
        java.io.File folder = new java.io.File("src/main/commands_out/");
        if (! folder.isDirectory()){
            System.out.println("Base path is not a directory: " + "src/main/commands_out/");
            return null;
        }
        java.io.File[] files = folder.listFiles((dir, name) -> name.startsWith(number + "_"));
        if (files == null || files.length == 0) {
            System.out.println("No test file found for number: " + number);
            return null;
        }
        // Assuming we take the first matching file
        java.io.File testFile = files[0];
        return testFile.getPath();
    }
}