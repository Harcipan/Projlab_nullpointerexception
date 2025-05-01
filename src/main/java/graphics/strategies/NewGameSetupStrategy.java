package graphics.strategies;

import graphics.presenters.NewGameSetupPresenter;
import graphics.customUIElements.CustomButton;
import app.PlayerInfo; // Import PlayerInfo

import graphics.customUIElements.CustomPlayerList; // Import PlayerList renderer
import graphics.customUIElements.CustomTextField;

import java.awt.*;
import java.util.List;

public class NewGameSetupStrategy extends AbstractRenderStrategy {
    private NewGameSetupPresenter presenter; // Reference to the presenter for handling button actions

    private CustomButton addPlayerButton;
    private CustomButton mapSize32Button;
    private CustomButton mapSize64Button;
    private CustomButton confirmButton;
    private CustomButton backButton;

    // Define areas/positions (adjust as needed for 600x400 panel)
    private static final int PLAYER_LIST_X = 30;
    private static final int PLAYER_LIST_Y = 80;
    private static final int PLAYER_LIST_WIDTH = 200;
    private static final int PLAYER_LIST_HEIGHT = 200;

    private static final int RIGHT_PANEL_X = 300;

    final Rectangle playerListBounds = new Rectangle(PLAYER_LIST_X, PLAYER_LIST_Y, PLAYER_LIST_WIDTH, PLAYER_LIST_HEIGHT);

    public NewGameSetupStrategy(NewGameSetupPresenter presenter, Runnable repaintCallback) {
        this.presenter = presenter;

        // Create buttons based on sketch
        addPlayerButton = new CustomButton("Add player...", PLAYER_LIST_X, PLAYER_LIST_Y + PLAYER_LIST_HEIGHT + 10, PLAYER_LIST_WIDTH, 30);
        mapSize32Button = new CustomButton("32", RIGHT_PANEL_X + 50, 180, 60, 30);
        mapSize64Button = new CustomButton("64", RIGHT_PANEL_X + 120, 180, 60, 30);
        confirmButton = new CustomButton("Confirm Setup", 200, 350, 200, 40);
        backButton = new CustomButton("< Back", 10, 360, 100, 30);

        buttons.add(addPlayerButton);
        buttons.add(mapSize32Button);
        buttons.add(mapSize64Button);
        buttons.add(confirmButton);
        buttons.add(backButton);

        // Create text fields
        CustomTextField saveNameField = new CustomTextField(RIGHT_PANEL_X, PLAYER_LIST_Y, 200, 30, repaintCallback);

        textFields.add(saveNameField); // Add to list for handling
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        // 1. Draw Background
        g2d.setColor(Color.GRAY);
        if (dimension != null) {
            g2d.fillRect(0, 0, dimension.width, dimension.height);
        } else {
             g2d.fillRect(0, 0, 600, 400); // Fallback size
             dimension = new Dimension(600, 400);
        }

        // 2. Draw Title
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String title = "New Game Setup";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (dimension.width - titleWidth) / 2;
        g2d.drawString(title, titleX, 40);

        // --- Left Panel: Players ---
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE); // Set color before drawing string
        g2d.drawString("Players", PLAYER_LIST_X, PLAYER_LIST_Y - 10);

        // Use the PlayerListRenderer to draw the list
        List<PlayerInfo> players = presenter.getPlayers();
        CustomPlayerList.draw(g2d, players, playerListBounds);

        // Draw Add Player button (position relative to list bounds)
        addPlayerButton.draw(g2d); // Ensure button position is correct

        // Save Name
        g2d.drawString("Name of save:", RIGHT_PANEL_X, PLAYER_LIST_Y - 10);
        g2d.setColor(Color.LIGHT_GRAY);
        
        // Draw all text fields (if any)
        for (CustomTextField textField : textFields) {
            textField.draw(g2d); // Draw each text field
        }

        // Map Size
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Map size:", RIGHT_PANEL_X, 160);
        // Highlight selected map size button (simple visual cue)
        int selectedMapSize = presenter.getMapSize();
        mapSize32Button.setPressed(selectedMapSize == 128); // Use pressed state for visual cue
        mapSize64Button.setPressed(selectedMapSize == 256);
        mapSize32Button.draw(g2d);
        mapSize64Button.draw(g2d);
        // Reset visual state immediately after drawing if using 'pressed' only for highlight
        mapSize32Button.setPressed(false);
        mapSize64Button.setPressed(false);

        // --- Bottom Panel: Navigation ---
        confirmButton.draw(g2d);
        backButton.draw(g2d);
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        if (btn == mapSize32Button || btn == mapSize64Button) {
            System.out.println("NewGameSetupStrategy: Clicked Map Size Button: " + btn.getText());
            presenter.setMapSize(Integer.parseInt(btn.getText()));
        } else if (btn == confirmButton) {
            System.out.println("NewGameSetupStrategy: Clicked Button: " + btn.getText());
            presenter.onConfirmSetupClicked();
        } else if (btn == backButton) {
            System.out.println("NewGameSetupStrategy: Clicked Button: " + btn.getText());
            presenter.onBackToMainMenuClicked();
        } else if (btn == addPlayerButton) {
            System.out.println("NewGameSetupStrategy: Clicked Button: " + btn.getText());
            presenter.addPlayerRequested();
        }
    }
}