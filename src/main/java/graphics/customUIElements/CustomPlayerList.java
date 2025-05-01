package graphics.customUIElements;

import app.PlayerInfo;
import app.PlayerType;

import java.awt.*;
import java.util.List;

/**
 * A utility class to draw a list of players within specified bounds.
 */
public class CustomPlayerList {

    private static final int ITEM_HEIGHT = 25; // Height of each player item row
    private static final int PADDING = 5;      // Padding within the list and items
    private static final int ICON_SIZE = 15;   // Size of the player type icon

        /**
     * Draws the player list.
     *
     * @param g2d     The Graphics2D context to draw on.
     * @param players The list of PlayerInfo objects to display.
     * @param bounds  The Rectangle defining the area where the list should be drawn.
     * @param editingIndex The index of the player currently being edited (-1 if none).
     */
    public static void draw(Graphics2D g2d, List<PlayerInfo> players, Rectangle bounds) {
        if (players == null || bounds == null) {
            return;
        }

        // Save original settings
        Color originalColor = g2d.getColor();
        Font originalFont = g2d.getFont();
        Shape originalClip = g2d.getClip();

        // Draw list background/border
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Set clip to prevent drawing outside the bounds
        g2d.clip(bounds);

        // Draw player items
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int textAscent = fm.getAscent();

        for (int i = 0; i < players.size(); i++) {
            PlayerInfo player = players.get(i);
            int itemY = bounds.y + (i * ITEM_HEIGHT) + PADDING;

            // Stop if exceeding bounds vertically
            if (itemY + ITEM_HEIGHT > bounds.y + bounds.height) {
                break;
            }

            // Draw icon placeholder
            g2d.setColor(player.type() == PlayerType.FUNGUS ? Color.MAGENTA : Color.CYAN);
            g2d.fillRect(bounds.x + PADDING, itemY + (ITEM_HEIGHT - ICON_SIZE) / 2, ICON_SIZE, ICON_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(bounds.x + PADDING, itemY + (ITEM_HEIGHT - ICON_SIZE) / 2, ICON_SIZE, ICON_SIZE);

            // Draw name (vertically centered in the item row)
            int textY = itemY + (ITEM_HEIGHT - fm.getHeight()) / 2 + textAscent;
            g2d.drawString(player.name(), bounds.x + PADDING + ICON_SIZE + PADDING, textY);
        }

        // Restore original settings
        g2d.setColor(originalColor);
        g2d.setFont(originalFont);
        g2d.setClip(originalClip);
    }
}