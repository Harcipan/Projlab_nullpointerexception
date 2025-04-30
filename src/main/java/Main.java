import graphics.GUI;
import prototype.App;

import javax.swing.*;
/*
*   Tester program for Use-Cases derived from Sequence Diagrams.
*   To create a test, add a new class that extends UseCase and implement the execute method.
*   To add the new use-case to the tester program, add it to the UseCaseList constructor.
* */

public class Main {
    public static void main(String[] args) {
        App app = new App();
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
        app.run(); // Comment this out to prevent blocking the main thread
    }
}
