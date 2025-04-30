package graphics.strategies;

import graphics.customUIElements.Button; // Import your Button class

import java.awt.*;
import java.util.List;

/*
 * MainMenuRenderStrategy is a concrete implementation of the RenderStrategy interface.
 * It defines how to render the main menu of the game, including buttons and background.
 */
public class MainMenuRenderStrategy implements RenderStrategy {

    private List<Button> buttons = new java.util.ArrayList<>(); // List to hold buttons

    public MainMenuRenderStrategy(/* MainMenuViewModel viewModel */) {
        // this.viewModel = viewModel;
        // Create buttons (adjust coordinates as needed)
        buttons.add(new Button("Start Game", 200, 150, 200, 40));
        buttons.add(new Button("Options", 200, 200, 200, 40));
        buttons.add(new Button("Exit", 200, 250, 200, 40));
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        // Call helper methods to draw different parts
        drawBackground(g2d, dimension);
        drawTitle(g2d, dimension);
        drawButtons(g2d); // Pass the graphics context
        drawFooterCredits(g2d, dimension); // Pass the graphics context
    }

    // New private helper method for drawing the background
    private void drawBackground(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.DARK_GRAY);
        // Use dimension if available, otherwise get from component if needed (though dimension is passed)
        if (dimension != null) {
            g2d.fillRect(0, 0, dimension.width, dimension.height); // Fill background
        } else {
             // Fallback or error handling if dimension is unexpectedly null
             System.err.println("MainMenuRenderStrategy: Dimension is null in drawBackground");
        }
    }

    // New private helper method for drawing the title
    private void drawTitle(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        // Basic centering example (adjust as needed)
        String title = "Fungorium";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = 50; // Example fixed X, or calculate based on dimension.width
         if (dimension != null) {
             titleX = (dimension.width - titleWidth) / 2;
         }
        g2d.drawString(title, titleX, 100); // Adjust Y as needed
    }

    private void drawFooterCredits(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String credits = "Made by NullPointerException";
        FontMetrics fm = g2d.getFontMetrics();
        int creditsWidth = fm.stringWidth(credits);
        int creditsX = (dimension.width - creditsWidth) / 2;
        g2d.drawString(credits, creditsX, dimension.height - 30); // Adjust Y as needed
    }

    // New private helper method for drawing buttons
    private void drawButtons(Graphics2D g2d) {
        // Draw all the buttons
        for (Button btn : buttons) {
            btn.draw(g2d); // The Button's draw method also uses the passed g2d
        }
    }

    // --- Methods needed for interaction (called by Mouse Listeners) ---
    public void updateHover(int mouseX, int mouseY) {
        for (Button btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
    }

    public void handlePress(int mouseX, int mouseY) {
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
                // Optional: Store which button is being pressed
                break; // Assuming only one button can be pressed at a time
            }
        }
    }

    public Button handleRelease(int mouseX, int mouseY) {
        Button clickedButton = null;
        for (Button btn : buttons) {
            if (btn.isPressed() && btn.contains(mouseX, mouseY)) {
                 System.out.println("Clicked: " + btn.getText()); // Debug
                 clickedButton = btn; // This button was clicked
                 // Trigger action based on clickedButton.getText() or the button object itself
                 // e.g., if (viewModel != null) viewModel.triggerAction(btn.getText());
            }
            btn.setPressed(false); // Reset pressed state on release regardless
        }
        return clickedButton; // Return the button that was clicked, if any
    }

    // Getter for buttons if listeners need direct access
    public List<Button> getButtons() {
        return buttons;
    }
}