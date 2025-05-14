package graphics.strategies;

import graphics.customUIElements.CustomButton; // Import your Button class
import graphics.presenters.MainMenuPresenter;
import prototype.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * MainMenuRenderStrategy is a concrete implementation of the RenderStrategy interface.
 * It defines how to render the main menu of the game, including buttons and background.
 */
public class MainMenuStrategy extends AbstractRenderStrategy {

    MainMenuPresenter presenter; // Reference to the presenter for handling button actions

    private static final List<BufferedImage> ANIMATION_FRAMES = new ArrayList<>();
    private static final int ANIMATION_FRAME_DELAY = 100; // Milliseconds per frame
    private long lastFrameTime = 0;
    private int currentFrameIndex = 0;

    static {
        try {
            File animationDir = new File("res/animation_seq");
            File[] files = animationDir.listFiles((dir, name) -> name.endsWith(".png"));
            if (files != null) {
                for (File file : files) {
                    ANIMATION_FRAMES.add(ImageIO.read(file));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load animation frames", e);
        }
    }

    public MainMenuStrategy(MainMenuPresenter presenter) {
        
        if (presenter == null) {
            throw new IllegalArgumentException("MainMenuPresenter cannot be null");
        }
        this.presenter = presenter;

        buttons.add(new CustomButton("Start Game", 200, 150, 200, 40));
        buttons.add(new CustomButton("Load Game", 200, 200, 200, 40));
        buttons.add(new CustomButton("Open Console", 200, 250, 200, 40));
        buttons.add(new CustomButton("Exit", 200, 300, 200, 40));
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        // Call helper methods to draw different parts
        drawBackground(g2d, dimension);
        drawTitle(g2d, dimension);
        drawButtons(g2d); // Pass the graphics context
        drawFooterCredits(g2d, dimension); // Pass the graphics context
        drawAnimation(g2d, dimension); // Add animation rendering
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

    private void drawAnimation(Graphics2D g2d, Dimension dimension) {
        if (ANIMATION_FRAMES.isEmpty()) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > ANIMATION_FRAME_DELAY) {
            currentFrameIndex = (currentFrameIndex + 1) % ANIMATION_FRAMES.size();
            lastFrameTime = currentTime;
        }

        BufferedImage currentFrame = ANIMATION_FRAMES.get(currentFrameIndex);
        int x = (dimension.width - currentFrame.getWidth()) / 2;
        int y = (dimension.height - currentFrame.getHeight()) / 2 - 145;
        g2d.drawImage(currentFrame, x, y, null);
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

    @Override
    protected void onButtonClicked(CustomButton btn) {
        switch (btn.getText()) {
            case "Start Game":
                presenter.onStartGameClicked();
                break;
            case "Open Console":
                presenter.onTesterClicked();
                break;
            case "Exit":
                presenter.onExitClicked();
                break;
            case "Load Game":
                presenter.onLoadGameClicked();
            default:
                System.err.println("MainMenuStrategy: Unknown button clicked: " + btn.getText());
        }
    }
}