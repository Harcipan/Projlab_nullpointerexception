package graphics.customUIElements;

import java.awt.*; // Import necessary AWT classes (Graphics2D, Color, Font, FontMetrics, Rectangle)

/*
 * This button is a custom UI element that can be used in various parts of the application.
 * It provides a simple way to create buttons with text, colors, and hover effects.
 */

public class Button {
    private String text;
    private int x, y, width, height;
    private boolean isHovered; // Added for visual feedback on mouse over
    private boolean isPressed;
    private Rectangle bounds; // Store bounds for easier checking

    // Colors (can be customized)
    private Color normalColor = Color.LIGHT_GRAY;
    private Color hoverColor = Color.GRAY;
    private Color pressedColor = Color.DARK_GRAY;
    private Color textColor = Color.BLACK;
    private Font textFont = new Font("Arial", Font.BOLD, 14);


    public Button(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isPressed = false;
        this.isHovered = false;
        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Draws the button onto the provided Graphics context.
     * @param g2d The Graphics2D context to draw on.
     */
    public void draw(Graphics2D g2d) {
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

        // Draw border (optional)
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

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public String getText() {
        return text;
    }

    // Optional: Add setters for colors/font if needed
}