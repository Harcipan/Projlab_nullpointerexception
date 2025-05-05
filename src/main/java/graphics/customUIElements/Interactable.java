package graphics.customUIElements;

import java.awt.*;

public class Interactable {
    protected int x, y, width, height;
    protected boolean isHovered; // Added for visual feedback on mouse over
    protected boolean isPressed;
    protected Rectangle bounds; // Store bounds for easier checking

    // Saving state
    private Color originalColor;
    private Font originalFont;
    private Shape originalClip;
    private Stroke originalStroke;

    Interactable(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isPressed = false;
        this.isHovered = false;
        this.bounds = new Rectangle(x, y, width, height);
    }

    void beforeDraw(Graphics2D g2d) {
        // Save original settings
        originalColor = g2d.getColor();
        originalFont = g2d.getFont();
        originalClip = g2d.getClip();
        originalStroke = g2d.getStroke();
    }

    void afterDraw(Graphics2D g2d) {
        // Restore original settings
        g2d.setColor(originalColor);
        g2d.setFont(originalFont);
        g2d.setClip(originalClip);
        g2d.setStroke(originalStroke);
    }

    
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

}
