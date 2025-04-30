package graphics;

import javax.swing.*;

public class GUI {

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Fungorium - made by NullPointerException");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add the custom drawing panel
        
        PanelRenderer renderer = new PanelRenderer();
        frame.add(renderer);

        // Pack the frame based on the preferred size of its contents (the panel)
        frame.pack();

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}