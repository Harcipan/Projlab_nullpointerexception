package graphics.strategies;

import graphics.presenters.NewGameSetupPresenter;
import graphics.customUIElements.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewGameSetupStrategy implements RenderStrategy {

    private NewGameSetupPresenter presenter;
    private List<Button> buttons;
    // Add fields for other UI elements (e.g., JComboBox, JTextField wrappers)

    public NewGameSetupStrategy(NewGameSetupPresenter presenter) {
        this.presenter = presenter;
        this.buttons = new ArrayList<>();

        // TODO: Add UI elements for game settings (player type, map size, etc.)
        // Example buttons:
        buttons.add(new Button("Confirm Setup", 200, 300, 200, 40));
        buttons.add(new Button("Back", 10, 10, 100, 30));
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        // 1. Draw Background
        g2d.setColor(Color.GRAY); // Different background
        if (dimension != null) {
            g2d.fillRect(0, 0, dimension.width, dimension.height);
        }

        // 2. Draw Title
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String title = "New Game Setup";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (dimension != null) ? (dimension.width - titleWidth) / 2 : 50;
        g2d.drawString(title, titleX, 50);

        // 3. Draw Setup Options (Placeholders)
        // TODO: Draw labels, dropdowns, text fields etc. based on presenter data
        g2d.drawString("Player Type: [Dropdown Placeholder]", 100, 120);
        g2d.drawString("Map Size: [Input Placeholder]", 100, 160);

        // 4. Draw Buttons
        for (Button btn : buttons) {
            btn.draw(g2d);
        }
    }

    // --- Input Handling ---

    @Override
    public void updateHover(int mouseX, int mouseY) {
        for (Button btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
        // TODO: Handle hover for other interactive elements
    }

    @Override
    public void handlePress(int mouseX, int mouseY) {
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
                break;
            }
        }
        // TODO: Handle press for other interactive elements
    }

    @Override
    public Button handleRelease(int mouseX, int mouseY) {
        Button clickedButton = null;
        for (Button btn : buttons) {
            if (btn.isPressed() && btn.contains(mouseX, mouseY)) {
                clickedButton = btn;
                System.out.println("NewGameSetupStrategy: Clicked Button: " + btn.getText());

                // Delegate to presenter
                switch (btn.getText()) {
                    case "Confirm Setup":
                        presenter.onConfirmSetupClicked();
                        break;
                    case "Back":
                        presenter.onBackToMainMenuClicked();
                        break;
                }
            }
            btn.setPressed(false);
        }
        return clickedButton;
    }

    public List<Button> getButtons() {
        return buttons;
    }
}