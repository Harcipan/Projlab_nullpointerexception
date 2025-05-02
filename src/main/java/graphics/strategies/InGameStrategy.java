package graphics.strategies;

import graphics.customUIElements.CustomButton;
import graphics.presenters.InGamePresenter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InGameStrategy extends AbstractRenderStrategy {

    private InGamePresenter presenter;
    private BufferedImage backgroundImage;

    private static final int HUD_HEIGHT = 50;
    private static final int TILE_SIZE = 32;

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

        // Example buttons for HUD
        buttons.add(new CustomButton("Pause", 10, 10, 100, 30));
        buttons.add(new CustomButton("Save", 120, 10, 100, 30));
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        drawGameMap(g2d, dimension);
        drawHUD(g2d, dimension);
    }

    private void drawGameMap(Graphics2D g2d, Dimension dimension) {
        // Draw background image if available
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, HUD_HEIGHT, dimension.width, dimension.height - HUD_HEIGHT, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(0, HUD_HEIGHT, dimension.width, dimension.height - HUD_HEIGHT);
        }
        int mapSize = presenter.getMapSize();
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE + HUD_HEIGHT, TILE_SIZE, TILE_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x * TILE_SIZE, y * TILE_SIZE + HUD_HEIGHT, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawHUD(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, dimension.width, HUD_HEIGHT);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Score: " + presenter.getScore(), 10, 30);

        for (CustomButton button : buttons) {
            button.draw(g2d);
        }
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        switch (btn.getText()) {
            case "Pause":
                presenter.onPauseClicked();
                break;
            case "Save":
                presenter.onSaveClicked();
                break;
            default:
                System.err.println("InGameStrategy: Unknown button clicked: " + btn.getText());
        }
    }
}
