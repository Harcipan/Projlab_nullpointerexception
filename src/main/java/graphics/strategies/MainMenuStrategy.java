package graphics.strategies;

import graphics.customUIElements.CustomButton; // Import your Button class
import graphics.customUIElements.CustomTextField;
import graphics.customUIElements.Interactable; // Import your Interactable interface
import graphics.presenters.MainMenuPresenter;

import java.awt.*;
import java.util.List;

/*
 * MainMenuRenderStrategy is a concrete implementation of the RenderStrategy interface.
 * It defines how to render the main menu of the game, including buttons and background.
 */
public class MainMenuStrategy implements IRenderStrategy {

    private MainMenuPresenter presenter;

    private List<CustomButton> buttons = new java.util.ArrayList<>(); // List to hold buttons
    private List<CustomTextField> textFields = new java.util.ArrayList<>(); // List to hold text fields (if needed)

    public MainMenuStrategy(MainMenuPresenter presenter) {
        
        if (presenter == null) {
            throw new IllegalArgumentException("MainMenuPresenter cannot be null");
        }
        this.presenter = presenter;

        buttons.add(new CustomButton("Start Game", 200, 150, 200, 40));
        buttons.add(new CustomButton("Options", 200, 200, 200, 40));
        buttons.add(new CustomButton("Exit", 200, 250, 200, 40));
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
        String title = "Fungorium";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = 50;
         if (dimension != null) {
             titleX = (dimension.width - titleWidth) / 2;
         }
        g2d.drawString(title, titleX, 100);
    }

    private void drawFooterCredits(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String credits = "Made by NullPointerException";
        FontMetrics fm = g2d.getFontMetrics();
        int creditsWidth = fm.stringWidth(credits);
        int creditsX = (dimension.width - creditsWidth) / 2;
        g2d.drawString(credits, creditsX, dimension.height - 30);
    }

    // New private helper method for drawing buttons
    private void drawButtons(Graphics2D g2d) {
        // Draw all the buttons
        for (CustomButton btn : buttons) {
            btn.draw(g2d); // The Button's draw method also uses the passed g2d
        }
    }

    // --- Methods needed for interaction (called by Mouse Listeners) ---
    public void updateHover(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
    }

    public void handlePress(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
                // Optional: Store which button is being pressed
                break; // Assuming only one button can be pressed at a time
            }
        }
    }

    public Interactable handleRelease(int mouseX, int mouseY) {

        Interactable clickedInteractable = null; // Initialize to null
        CustomButton clickedButton = null; // Initialize to null
        CustomTextField clickedTextField = null; // Initialize to null

        for (CustomButton btn : buttons) {
            if (btn.isPressed() && btn.contains(mouseX, mouseY)) {
                 System.out.println("MainMenuRenderStrategy: Click detected on " + btn.getText()); // Debug in View
                 clickedButton = btn;

                 // Delegation happens here to the presenter
                 switch (clickedButton.getText()) {
                     case "Start Game":
                         presenter.onStartGameClicked(); // Tell presenter about the action
                         break;
                     case "Options":
                         presenter.onOptionsClicked();
                         break;
                     case "Exit":
                         presenter.onExitClicked();
                         break;
                     default:
                         System.out.println("MainMenuRenderStrategy: Unknown button clicked: " + clickedButton.getText());
                         break;
                 }
                 // No game logic here!
            }
            btn.setPressed(false); // Reset pressed state on release regardless
        }
        
        // For text fields the highlight state in not handled here

        if (clickedButton != null) {
            clickedInteractable = clickedButton; // Set the clicked button as the interactable
        }

        return clickedInteractable; // Return the clicked interactable (button or text field)
    }

    // Getter for buttons if listeners need direct access
    public List<CustomButton> getButtons() {
        return buttons;
    }

    @Override
    public List<CustomTextField> getTextFields() {
        return textFields; // Return the list of text fields
    }
}