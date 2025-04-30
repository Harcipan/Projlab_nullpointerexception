package graphics;

// Import strategy interface and concrete strategy classes
import graphics.strategies.MainMenuRenderStrategy;
import graphics.strategies.RenderStrategy;

// Import necessary Swing and graphics classes
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;

// Import custom UI elements
import graphics.customUIElements.*;

// Import necessary AWT classes
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/* 
 * PanelRenderer is a custom JPanel that uses a RenderStrategy to draw its content.
 * It handles mouse events to interact with buttons or other UI elements.
 */

public class PanelRenderer extends JPanel implements MouseListener, MouseMotionListener {

    private RenderStrategy currentRenderStrategy;

    // Constructor that accepts a RenderStrategy
    public PanelRenderer(RenderStrategy initialStrategy) {
        setRenderStrategy(initialStrategy);
        setPreferredSize(new Dimension(600, 400));
        // Add mouse listeners to the panel itself
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // Defaulting to main menu strategy for the constructor
    public PanelRenderer() {
        // Set a default strategy, e.g., main menu, or null if required externally
        this(new MainMenuRenderStrategy()); // Example: Default to main menu
    }

    public void setRenderStrategy(RenderStrategy strategy) {
        if (strategy == null) {
            System.err.println("Warning: RenderStrategy cannot be null. Setting a default.");
            this.currentRenderStrategy = new MainMenuRenderStrategy(); // Fallback example
        } else {
            this.currentRenderStrategy = strategy;
        }
        // Request a repaint whenever the strategy changes
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the background

        // Use Graphics2D for better rendering control (like anti-aliasing)
        Graphics2D g2d = (Graphics2D) g.create(); // Create a copy to avoid modifying the original

        try {
            // Check if a strategy is set
            if (currentRenderStrategy != null) {
                // Delegate the actual drawing to the current strategy
                // Pass the graphics context and the current panel dimensions
                currentRenderStrategy.render(g2d, getSize()); // Pass actual panel size
            } else {
                // Optional: Draw a fallback message if no strategy is set
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.drawString("No Render Strategy is currently set!", 20, 30);
            }
        } finally {
            // Dispose of the graphics copy to release system resources
            g2d.dispose();
        }
    }

    // Override preferred size to give the panel a default size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400); // Set a preferred width and height
    }

    // --- MouseListener Methods --- 

    @Override
    public void mouseClicked(MouseEvent e) {
        // Often less useful than pressed/released for buttons
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentRenderStrategy != null) {
            currentRenderStrategy.handlePress(e.getX(), e.getY());
            repaint(); // Repaint after press to show pressed state
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentRenderStrategy != null) {
            // Handle the release and potentially get the clicked button
            Button clickedButton = currentRenderStrategy.handleRelease(e.getX(), e.getY());
            repaint(); // Repaint after release to show normal/hover state

            // TODO: Add logic here to handle the action for the clickedButton
            if (clickedButton != null) {
                System.out.println("Action triggered for: " + clickedButton.getText());
                // Example: switch (clickedButton.getText()) { case "Start Game": ... }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Called when mouse enters the panel bounds
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Called when mouse exits the panel bounds - reset hover state?
        if (currentRenderStrategy != null) {
             // Optionally reset all hover states when mouse leaves the panel
             currentRenderStrategy.updateHover(-1, -1); // Pass coordinates outside bounds
             repaint();
        }
    }

    // --- MouseMotionListener Methods ---

    @Override
    public void mouseDragged(MouseEvent e) {
        // Can be treated similar to mouseMoved for hover, or ignored
        if (currentRenderStrategy != null) {
            currentRenderStrategy.updateHover(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentRenderStrategy != null) {
            currentRenderStrategy.updateHover(e.getX(), e.getY());
            repaint(); // Repaint to show hover effect
        }
    }
}