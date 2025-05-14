import graphics.PanelRenderer;
import app.GameCoordinator;

import javax.swing.*;

/*
*   Tester program for Use-Cases derived from Sequence Diagrams.
*   To create a test, add a new class that extends UseCase and implement the execute method.
*   To add the new use-case to the tester program, add it to the UseCaseList constructor.
* */

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create the main application coordinator
            GameCoordinator coordinator = new GameCoordinator();

            // 2. Create the main UI panel (uses default constructor which sets MainMenuStrategy initially)
            //    We will let the coordinator set the correct initial strategy.
            PanelRenderer panelRenderer = new PanelRenderer(); // Create it, but strategy will be set by coordinator

            // 3. Set up the main frame
            JFrame frame = new JFrame("Fungorium");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panelRenderer); // Add the panel that strategies will draw on
            frame.pack(); // Size the frame based on panel's preferred size
            frame.setLocationRelativeTo(null); // Center on screen

            // 4. Give coordinator references to the UI components it needs to manage
            coordinator.setFrame(frame);
            coordinator.setPanelRenderer(panelRenderer);

            // 5. Start the application flow via the coordinator
            coordinator.startApplication(); // This will show the main menu initially

            // 6. Make the frame visible
            frame.setVisible(true);
        });
    }
}
