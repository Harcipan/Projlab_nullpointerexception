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
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        drawLeftPanel(g2d, dimension);
        drawGameMap(g2d, dimension);
    }

    private void drawLeftPanel(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(new Color(40, 40, 40)); // Dark gray panel
        g2d.fillRect(0, 0, presenter.getHUDWidth(), dimension.height);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Info Panel", 30, 40);
        // You can add more info or UI elements here later
    }

    private void drawGameMap(Graphics2D g2d, Dimension dimension) {
        // Draw background image if available
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, presenter.getHUDWidth(), 0, dimension.width - presenter.getHUDWidth(), dimension.height, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(presenter.getHUDWidth(), 0, dimension.width - presenter.getHUDWidth(), dimension.height);
        }
        int mapSize = presenter.getMapSize();
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                int drawX = presenter.getHUDWidth() + x * TILE_SIZE;
                int drawY = y * TILE_SIZE;
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        // No buttons to handle in the in-game strategy
    }
}
