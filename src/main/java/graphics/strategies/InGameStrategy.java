package graphics.strategies;

import graphics.customUIElements.CustomButton;
import graphics.presenters.InGamePresenter;
import app.PlayerInfo;
import app.PlayerType;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class InGameStrategy extends AbstractRenderStrategy {

    private InGamePresenter presenter;
    private BufferedImage backgroundImage;
    private CustomButton nextTurnButton;

    private static final int TILE_SIZE = 32;
    private static final int PLAYER_ICON_SIZE = 40;
    private static final int PLAYER_ICON_GAP = 18;
    private static final int PLAYER_ICON_START_Y = 90; // moved down from 70 to 90
    private static BufferedImage FUNGUS_ICON;
    private static BufferedImage INSECT_ICON;

    static {
        try {
            FUNGUS_ICON = ImageIO.read(Paths.get("res/player_icons/mushroom_icon.png").toFile());
            INSECT_ICON = ImageIO.read(Paths.get("res/player_icons/insect_icon.png").toFile());
        } catch (IOException e) {
            System.err.println("Could not load player icons");
        }
    }

    public InGameStrategy(InGamePresenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("InGamePresenter cannot be null");
        }
        this.presenter = presenter;

        try {
            backgroundImage = ImageIO.read(new File("res/backgrounds/32bg.png"));
        } catch (IOException e) {
            System.err.println("Could not load background image: res/32bg.png");
            backgroundImage = null;
        }

        int btnWidth = 180;
        int btnHeight = 40;
        int btnX = 30;
        int btnY = 600; // Will be adjusted dynamically in drawLeftPanel
        nextTurnButton = new CustomButton("Next Turn", btnX, btnY, btnWidth, btnHeight);
        buttons.add(nextTurnButton);
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        drawLeftPanel(g2d, dimension);
        drawGameMap(g2d, dimension);
        // Draw placement hover icon if in placement phase
        if (presenter.isPlacementPhase() && presenter.getPlacementHover() != null) {
            PlayerInfo player = presenter.getPlayers().get(presenter.getPlacingPlayerIndex());
            BufferedImage icon = player.type() == PlayerType.FUNGUS ? FUNGUS_ICON : INSECT_ICON;
            Point p = presenter.getPlacementHover();
            Composite oldComp = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(icon, p.x - PLAYER_ICON_SIZE / 2, p.y - PLAYER_ICON_SIZE / 2, PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, null);
            g2d.setComposite(oldComp);
        }
    }

    private void drawLeftPanel(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(new Color(40, 40, 40)); // Dark gray panel
        g2d.fillRect(0, 0, presenter.getHUDWidth(), dimension.height);
        g2d.setColor(Color.WHITE);

        // display current turn and active player
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Turn: " + (int)(Math.floor((double)presenter.getCurrentTurn() / presenter.getPlayers().size())+1), 30, 40);
        java.util.List<PlayerInfo> _playersInfo = presenter.getPlayers();
        if (!_playersInfo.isEmpty()) {
            PlayerInfo _current = _playersInfo.get(presenter.getCurrentTurn() % _playersInfo.size());
            g2d.drawString("Current: " + _current.name(), 30, 60);
        }

        // Draw player icons in rows, wrapping if needed
        java.util.List<PlayerInfo> players = presenter.getPlayers();
        int currentTurnTruncated = presenter.getCurrentTurn() % (players.isEmpty() ? 1 : players.size());
        int hudWidth = presenter.getHUDWidth();
        int iconX = 30;
        int iconY = PLAYER_ICON_START_Y;
        int iconSpacingX = PLAYER_ICON_SIZE + 24;
        int iconSpacingY = PLAYER_ICON_SIZE + 24;
        int iconsPerRow = Math.max(1, (hudWidth - 2 * iconX) / iconSpacingX);
        int row = 0;
        int col = 0;
        for (int i = 0; i < players.size(); i++) {
            PlayerInfo player = players.get(i);
            BufferedImage icon = player.type() == PlayerType.FUNGUS ? FUNGUS_ICON : INSECT_ICON;
            int drawX = iconX + col * iconSpacingX;
            int drawY = iconY + row * iconSpacingY;
            // Highlight current player
            if (i == currentTurnTruncated) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(drawX - 4, drawY - 4, PLAYER_ICON_SIZE + 8, PLAYER_ICON_SIZE + 8);
            }
            g2d.drawImage(icon, drawX, drawY, PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, null);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 13));
            g2d.drawString(player.name(), drawX, drawY + PLAYER_ICON_SIZE + 16);
            col++;
            if (col >= iconsPerRow) {
                col = 0;
                row++;
            }
        }
        // Calculate Y position for Next Turn button so it snaps under the player list
        int btnMargin = 30;
        int btnWidth = nextTurnButton.getBounds().width;
        int btnHeight = nextTurnButton.getBounds().height;
        int btnX = btnMargin;
        int btnY;
        if (players.isEmpty()) {
            btnY = dimension.height - btnHeight - btnMargin;
        } else {
            int totalRows = row + (col > 0 ? 1 : 0);
            btnY = iconY + totalRows * iconSpacingY + btnMargin;
        }
        // Prevent button from overflowing panel
        if (btnY + btnHeight + btnMargin > dimension.height) {
            btnY = dimension.height - btnHeight - btnMargin;
        }
        nextTurnButton.setBounds(btnX, btnY, btnWidth, btnHeight);
        // Disable next turn button during placement phase
        nextTurnButton.setEnabled(!presenter.isPlacementPhase());
        nextTurnButton.draw(g2d);
    }

    private void drawGameMap(Graphics2D g2d, Dimension dimension) {
        // Draw background image if available
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, presenter.getHUDWidth(), 0, dimension.width - presenter.getHUDWidth(), dimension.height, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(presenter.getHUDWidth(), 0, dimension.width - presenter.getHUDWidth(), dimension.height);
        }
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        if (btn == nextTurnButton) {
            // Advance turn
            presenter.getCoordinator().setCurrentTurn(presenter.getCoordinator().getCurrentTurn() + 1);
            presenter.getCoordinator().initiateRepaint();
        }
    }

    @Override
    public void updateHover(int mouseX, int mouseY) {
        super.updateHover(mouseX, mouseY);
        if (presenter.isPlacementPhase()) {
            presenter.setPlacementHover(new Point(mouseX, mouseY));
        }
    }

    @Override
    public void handlePress(int mouseX, int mouseY) {
        if (presenter.isPlacementPhase()) {
            int gridX = (mouseX - presenter.getHUDWidth()) / TILE_SIZE;
            int gridY = mouseY / TILE_SIZE;
            int mapSize = presenter.getMapSize();
            if (gridX >= 0 && gridY >= 0 && gridX < mapSize && gridY < mapSize) {
                PlayerInfo player = presenter.getPlayers().get(presenter.getPlacingPlayerIndex());
                String entityType = player.type() == PlayerType.FUNGUS ? "FungusEntity" : "InsectEntity";
                System.out.println("Placing " + entityType + " for player '" + player.name() + "' at tile (" + gridX + ", " + gridY + ")");
                // TODO: Actually create and add the entity to the game model here
                // Increment turn after placement
                presenter.getCoordinator().setCurrentTurn(presenter.getCoordinator().getCurrentTurn() + 1);
                int next = presenter.getPlacingPlayerIndex() + 1;
                if (next >= presenter.getPlayers().size()) {
                    presenter.setPlacementPhase(false);
                    presenter.setPlacingPlayerIndex(0);
                    presenter.setPlacementHover(null);
                } else {
                    presenter.setPlacingPlayerIndex(next);
                }
            }
        } else {
            super.handlePress(mouseX, mouseY);
        }
    }
}
