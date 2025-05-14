package graphics.strategies;

import graphics.presenters.NewGameSetupPresenter;
import player.Player;
import player.FungusPlayer;
import player.InsectPlayer;
import graphics.customUIElements.CustomButton;

import graphics.customUIElements.CustomPlayerList;
import graphics.customUIElements.CustomTextField;
import graphics.customUIElements.Interactable;

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
    private List<CustomButton> playerIconButtons = new ArrayList<>();
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
        mapSize64Button.setEnabled(false); // Make 64 map unavailable by default
        confirmButton = new CustomButton("Confirm Setup", 200, 350, 200, 40);
        backButton = new CustomButton("< Back", 10, 360, 100, 30);

        buttons.add(addPlayerButton);
        buttons.add(mapSize32Button);
        buttons.add(mapSize64Button);
        buttons.add(confirmButton);
        buttons.add(backButton);

        saveNameField = new CustomTextField(RIGHT_PANEL_X, PLAYER_LIST_Y, 200, 30, presenter.getCoordinator());
        saveNameField.setText("Save1");
        textFields.add(saveNameField);

        syncPlayerTextFields();
    }

    private void syncPlayerTextFields() {
        List<Player> players = presenter.getPlayers();
        // Sync text fields
        while (playerTextFields.size() < players.size()) {
            int index = playerTextFields.size();
            Player player = players.get(index);
            CustomTextField newPlayerField = new CustomTextField(0, 0, 0, 0, presenter.getCoordinator());
            newPlayerField.setText(player.getName());
            newPlayerField.setOnEnterCallback(() -> updatePlayerNameFromField(index));
            playerTextFields.add(newPlayerField);
            textFields.add(newPlayerField);
        }
        // Remove extra fields if players were removed
        while (playerTextFields.size() > players.size()) {
            playerTextFields.remove(playerTextFields.size() - 1);
        }
        // Sync icon buttons
        playerIconButtons.clear();
        for (int i = 0; i < players.size(); i++) {
            int itemY = playerListBounds.y + (i * 25); // ITEM_HEIGHT
            int iconX = playerListBounds.x + 5; // PADDING
            int iconY = itemY + (25 - 15) / 2; // ITEM_HEIGHT - ICON_SIZE
            CustomButton iconBtn = new CustomButton("", iconX, iconY, 15, 15);
            iconBtn.setImage(graphics.customUIElements.CustomPlayerList.getIconForType(players.get(i)));
            playerIconButtons.add(iconBtn);
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

        List<Player> players = presenter.getPlayers();
        // Draw icon buttons and text fields for each player
        for (int i = 0; i < players.size(); i++) {
            CustomButton iconBtn = playerIconButtons.get(i);
            // Update icon image in case type changed
            iconBtn.setImage(graphics.customUIElements.CustomPlayerList.getIconForType(players.get(i)));
            iconBtn.draw(g2d);
        }
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

        // Enable confirm button only when at least one FungusPlayer and one InsectPlayer exist AND save name is set
        List<Player> pList = presenter.getPlayers();
        boolean hasFungus = false, hasInsect = false;
        for (Player p : pList) {
            if (p instanceof FungusPlayer) hasFungus = true;
            if (p instanceof InsectPlayer) hasInsect = true;
        }
        boolean hasName = saveNameField.getText() != null && !saveNameField.getText().trim().isEmpty();
        confirmButton.setEnabled(hasFungus && hasInsect && hasName);

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

    @Override
    public void updateHover(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
        for (CustomTextField textField : textFields) {
            textField.setHovered(textField.contains(mouseX, mouseY));
        }
        for (CustomButton iconBtn : playerIconButtons) {
            iconBtn.setHovered(iconBtn.contains(mouseX, mouseY));
        }
    }

    @Override
    public void handlePress(int mouseX, int mouseY, int button) {
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
                break; // Only press one button
            }
        }
        for (CustomTextField textField : textFields) {
            if (textField.contains(mouseX, mouseY)) {
                textField.setFocused(true); // Set focused state for text field
            } else {
                textField.setFocused(false); // Reset other fields
            }
        }
        for (CustomButton iconBtn : playerIconButtons) {
            if (iconBtn.contains(mouseX, mouseY)) {
                iconBtn.setPressed(true);
            } else {
                iconBtn.setPressed(false);
            }
        }
    }

    @Override
    public Interactable handleRelease(int mouseX, int mouseY) {
        Interactable clickedInteractable = null;
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                clickedInteractable = btn;
                onButtonClicked(btn);
            }
            btn.setPressed(false);
        }
        for (int i = 0; i < playerIconButtons.size(); i++) {
            CustomButton iconBtn = playerIconButtons.get(i);
            if (iconBtn.contains(mouseX, mouseY)) {
                presenter.togglePlayerType(i);
                syncPlayerTextFields();
                if (presenter.getCoordinator() != null) {
                    presenter.getCoordinator().initiateRepaint();
                }
            }
            iconBtn.setPressed(false);
        }
        // For text fields, the highlight state is not handled here
        return clickedInteractable;
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