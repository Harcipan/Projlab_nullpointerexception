package graphics.customUIElements;

import java.awt.*; // Import necessary AWT classes (Graphics2D, Color, Font, FontMetrics, Rectangle)

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
    private Font textFont = new Font("Arial", Font.BOLD, 14);


    public CustomButton(String text, int x, int y, int width, int height) {
        super(x, y, width, height); // Call the constructor of Interactable
        this.text = text;
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
        if (isPressed) {
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

        // Draw text centered
        g2d.setColor(textColor);
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
        return bounds.contains(mouseX, mouseY);
    }


    // --- Getters and Setters ---
    public String getText() {
        return text;
    }

    // Optional: Add setters for colors/font if needed
}