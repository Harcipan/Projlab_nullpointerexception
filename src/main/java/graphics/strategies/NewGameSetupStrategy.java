package graphics.strategies;

import graphics.presenters.NewGameSetupPresenter;
import graphics.customUIElements.CustomButton;
import app.PlayerInfo;

import graphics.customUIElements.CustomPlayerList;
import graphics.customUIElements.CustomTextField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class NewGameSetupStrategy extends AbstractRenderStrategy {
    private NewGameSetupPresenter presenter;

    private CustomButton addPlayerButton;
    private CustomButton mapSize32Button;
    private CustomButton mapSize64Button;
    private CustomButton confirmButton;
    private CustomButton backButton;

    private CustomTextField saveNameField;

    private List<CustomTextField> playerTextFields = new ArrayList<>();
    private int focusedPlayerIndex = -1;

    private static final int PLAYER_LIST_X = 30;
    private static final int PLAYER_LIST_Y = 80;
    private static final int PLAYER_LIST_WIDTH = 200;
    private static final int PLAYER_LIST_HEIGHT = 200;

    private static final int RIGHT_PANEL_X = 300;

    final Rectangle playerListBounds = new Rectangle(PLAYER_LIST_X, PLAYER_LIST_Y, PLAYER_LIST_WIDTH, PLAYER_LIST_HEIGHT);

    public NewGameSetupStrategy(NewGameSetupPresenter presenter) {
        this.presenter = presenter;

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

        saveNameField = new CustomTextField(RIGHT_PANEL_X, PLAYER_LIST_Y, 200, 30, presenter.getCoordinator());
        textFields.add(saveNameField);

        syncPlayerTextFields();
    }

    private void syncPlayerTextFields() {
        List<PlayerInfo> players = presenter.getPlayers();
        while (playerTextFields.size() < players.size()) {
            int index = playerTextFields.size();
            PlayerInfo player = players.get(index);
            CustomTextField newPlayerField = new CustomTextField(0, 0, 0, 0, presenter.getCoordinator());
            newPlayerField.setText(player.name());
            newPlayerField.setOnEnterCallback(() -> updatePlayerNameFromField(index));
            playerTextFields.add(newPlayerField);
            textFields.add(newPlayerField);
        }
    }

    private void updatePlayerNameFromField(int index) {
        if (index >= 0 && index < playerTextFields.size()) {
            CustomTextField textField = playerTextFields.get(index);
            String newName = textField.getText();
            presenter.updatePlayerName(index, newName);
        }
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.GRAY);
        if (dimension != null) {
            g2d.fillRect(0, 0, dimension.width, dimension.height);
        } else {
            g2d.fillRect(0, 0, 600, 400);
            dimension = new Dimension(600, 400);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String title = "New Game Setup";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (dimension.width - titleWidth) / 2;
        g2d.drawString(title, titleX, 40);

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Players", PLAYER_LIST_X, PLAYER_LIST_Y - 10);

        List<PlayerInfo> players = presenter.getPlayers();
        // Pass playerTextFields instead of the general textFields list
        CustomPlayerList.draw(g2d, players, playerTextFields, playerListBounds);

        addPlayerButton.draw(g2d);

        g2d.drawString("Name of save:", RIGHT_PANEL_X, PLAYER_LIST_Y - 10);
        g2d.setColor(Color.LIGHT_GRAY);

        for (CustomTextField textField : textFields) {
            textField.draw(g2d);
        }

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Map size:", RIGHT_PANEL_X, 160);

        int selectedMapSize = presenter.getMapSize();
        mapSize32Button.setPressed(selectedMapSize == 128);
        mapSize64Button.setPressed(selectedMapSize == 256);
        mapSize32Button.draw(g2d);
        mapSize64Button.draw(g2d);
        mapSize32Button.setPressed(false);
        mapSize64Button.setPressed(false);

        confirmButton.draw(g2d);
        backButton.draw(g2d);
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        unfocusAllTextFields();
        focusedPlayerIndex = -1;

        if (btn == addPlayerButton) {
            System.out.println("NewGameSetupStrategy: Add Player Button Clicked");
            presenter.addPlayerRequested();
            syncPlayerTextFields();
            if (presenter.getCoordinator() != null) {
                presenter.getCoordinator().initiateRepaint();
            }
        } else if (btn == mapSize32Button || btn == mapSize64Button) {
            System.out.println("NewGameSetupStrategy: Clicked Map Size Button: " + btn.getText());
            presenter.setMapSize(Integer.parseInt(btn.getText()));
            if (presenter.getCoordinator() != null) {
                presenter.getCoordinator().initiateRepaint();
            }
        } else if (btn == confirmButton) {
            presenter.onConfirmSetupClicked();
        } else if (btn == backButton) {
            presenter.onBackToMainMenuClicked();
        }
    }

    public void handleKeyPressEvent(KeyEvent e) {
        boolean consumed = false;
        if (focusedPlayerIndex != -1) {
            consumed = playerTextFields.get(focusedPlayerIndex).handleKeyPress(e);
        } else if (saveNameField.isFocused()) {
            consumed = saveNameField.handleKeyPress(e);
        }

        if (consumed && presenter.getCoordinator() != null) {
            presenter.getCoordinator().initiateRepaint();
        }
    }

    private void unfocusAllTextFields() {
        for (CustomTextField textField : textFields) {
            textField.setFocused(false);
        }
    }
}