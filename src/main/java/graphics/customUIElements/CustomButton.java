package graphics.customUIElements;

import java.awt.*; // Import necessary AWT classes (Graphics2D, Color, Font, FontMetrics, Rectangle)
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/*
 * This button is a custom UI element that can be used in various parts of the application.
 * It provides a simple way to create buttons with text, colors, and hover effects.
 */

public class CustomButton extends Interactable {
    private String text;

    // Colors (can be customized)
    private Color normalColor = Color.LIGHT_GRAY;
    private Color hoverColor = Color.GRAY;
    private Color pressedColor = Color.DARK_GRAY;
    private Color textColor = Color.BLACK;
    private Color disabledTextColor = Color.GRAY;
    private Font textFont = new Font("Arial", Font.BOLD, 14);

    private BufferedImage image = null;
    private boolean enabled = true;

    public CustomButton(String text, int x, int y, int width, int height) {
        super(x, y, width, height); // Call the constructor of Interactable
        this.text = text;
    }

    /**
     * Sets the image for the button using a file path.
     * @param imagePath The path to the image file.
     */
    public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("CustomButton: Could not load image: " + imagePath);
            image = null;
        }
    }

    /**
     * Sets the image for the button using a BufferedImage.
     * @param img The BufferedImage to set.
     */
    public void setImage(BufferedImage img) {
        this.image = img;
    }

    /**
     * Sets whether the button is enabled or disabled.
     * @param enabled true to enable the button, false to disable it.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks whether the button is enabled.
     * @return true if the button is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Draws the button onto the provided Graphics context.
     * @param g2d The Graphics2D context to draw on.
     */
    public void draw(Graphics2D g2d) {
        // Save original settings
        beforeDraw(g2d);

        // Determine background color based on state
        Color bgColor;
        if (!enabled) {
            bgColor = normalColor.darker();
        } else if (isPressed) {
            bgColor = pressedColor;
        } else if (isHovered) {
            bgColor = hoverColor;
        } else {
            bgColor = normalColor;
        }

        // Draw background rectangle
        g2d.setColor(bgColor);
        g2d.fillRect(x, y, width, height);

        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

        // Draw image if present
        if (image != null) {
            int imgW = image.getWidth();
            int imgH = image.getHeight();
            int drawW = Math.min(imgW, width - 8);
            int drawH = Math.min(imgH, height - 8);
            int imgX = x + (width - drawW) / 2;
            int imgY = y + (height - drawH) / 2;
            g2d.drawImage(image, imgX, imgY, drawW, drawH, null);
        }

        // Draw text centered (over image if present)
        g2d.setColor(enabled ? textColor : disabledTextColor);
        g2d.setFont(textFont);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent() - fm.getDescent(); // More accurate height

        int textX = x + (width - textWidth) / 2;
        int textY = y + (height - textHeight) / 2 + fm.getAscent(); // Center vertically

        g2d.drawString(text, textX, textY);

        // Restore original settings
        afterDraw(g2d);
    }

    /**
     * Checks if the given coordinates are within the button's bounds.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     * @return true if the point is inside the button, false otherwise.
     */
    public boolean contains(int mouseX, int mouseY) {
        return enabled && bounds.contains(mouseX, mouseY);
    }

    // --- Getters and Setters ---
    public String getText() {
        return text;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
    }

    // Optional: Add setters for colors/font if needed
}