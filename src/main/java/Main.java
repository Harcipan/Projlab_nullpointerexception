import use_cases.UseCase;
import use_cases.UseCaseList;

import java.util.Scanner;

import prototype.App;

/*
*   Tester program for Use-Cases derived from Sequence Diagrams.
*   To create a test, add a new class that extends UseCase and implement the execute method.
*   To add the new use-case to the tester program, add it to the UseCaseList constructor.
* */

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
