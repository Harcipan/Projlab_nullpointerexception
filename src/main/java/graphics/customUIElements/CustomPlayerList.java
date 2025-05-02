package graphics.customUIElements;

import app.PlayerInfo;
import app.PlayerType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * A utility class to draw a list of players within specified bounds,
 * using CustomTextFields for editable names.
 */
public class CustomPlayerList {

    private static final int ITEM_HEIGHT = 25; // Height of each player item row
    private static final int PADDING = 5;      // Padding within the list and items
    private static final int ICON_SIZE = 15;   // Size of the player type icon
    private static final int ICON_TEXT_GAP = 5; // Gap between icon and text field

    private static final BufferedImage FUNGUS_ICON;
    private static final BufferedImage INSECT_ICON;

    static {
        try {
            FUNGUS_ICON = ImageIO.read(Paths.get("res/player_icons/mushroom_icon.png").toFile());
            INSECT_ICON = ImageIO.read(Paths.get("res/player_icons/insect_icon.png").toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load player icons", e);
        }
    }

    /**
     * Exposes the correct icon for a given PlayerType.
     *
     * @param type The PlayerType for which the icon is needed.
     * @return The BufferedImage representing the icon.
     */
    public static BufferedImage getIconForType(PlayerType type) {
        return type == PlayerType.FUNGUS ? FUNGUS_ICON : INSECT_ICON;
    }

    /**
     * Draws the player list using CustomTextFields for names.
     *
     * @param g2d             The Graphics2D context to draw on.
     * @param players         The list of PlayerInfo objects (used for icons).
     * @param playerTextFields The list of CustomTextField objects corresponding to the players.
     * @param bounds          The Rectangle defining the area where the list should be drawn.
     * @param focusedPlayerIndex The index of the player/text field currently focused for editing (-1 if none).
     */
    public static void draw(Graphics2D g2d, List<PlayerInfo> players, List<CustomTextField> playerTextFields, Rectangle bounds) {
        if (players == null || playerTextFields == null || bounds == null || players.size() != playerTextFields.size()) {
            System.err.println("CustomPlayerList.draw: Invalid input - lists null, mismatched, or bounds null.");
            return; // Or throw an exception
        }

        // Save original settings
        Color originalColor = g2d.getColor();
        Font originalFont = g2d.getFont();
        Shape originalClip = g2d.getClip();
        Stroke originalStroke = g2d.getStroke(); // Save stroke

        // Draw list background/border
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1)); // Ensure border stroke is 1
        g2d.drawRect(bounds.x, bounds.y, bounds.width -1 , bounds.height -1); // Draw inside bounds slightly

        // Set clip to prevent drawing outside the bounds
        // Clip slightly inside to avoid drawing over the border
        g2d.clipRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);

        for (int i = 0; i < players.size(); i++) {
            PlayerInfo player = players.get(i);
            CustomTextField textField = playerTextFields.get(i);
            int itemY = bounds.y + (i * ITEM_HEIGHT); // Start Y of the item row

            // Stop if exceeding bounds vertically
            if (itemY + ITEM_HEIGHT > bounds.y + bounds.height) {
                break;
            }

            // Calculate positions within the item row
            int iconX = bounds.x + PADDING;
            int iconY = itemY + (ITEM_HEIGHT - ICON_SIZE) / 2;
            int textFieldX = iconX + ICON_SIZE + ICON_TEXT_GAP;
            int textFieldY = itemY + PADDING / 2; // Align text field within the row padding
            int textFieldWidth = bounds.width - (textFieldX - bounds.x) - PADDING;
            int textFieldHeight = ITEM_HEIGHT - PADDING; // Use padding top/bottom

            // Draw icon using the loaded images
            BufferedImage icon = getIconForType(player.type());
            g2d.drawImage(icon, iconX, iconY, ICON_SIZE, ICON_SIZE, null);

            // Configure and draw the text field
            textField.setBounds(textFieldX, textFieldY, textFieldWidth, textFieldHeight);
            // Focus state is managed externally, but we could highlight here if needed
            textField.draw(g2d); // Delegate drawing to the CustomTextField

        }

        // Restore original settings
        g2d.setColor(originalColor);
        g2d.setFont(originalFont);
        g2d.setClip(originalClip); // Restore clip *before* restoring stroke if needed elsewhere
        g2d.setStroke(originalStroke); // Restore stroke
    }

    /**
     * Calculates the index of the player item at a given Y coordinate within the list bounds.
     *
     * @param mouseY The Y coordinate of the mouse click.
     * @param bounds The bounds of the player list component.
     * @param playerCount The total number of players in the list.
     * @return The index of the clicked player (0-based), or -1 if the click is outside item rows or bounds.
     */
    public static int getPlayerIndexAt(int mouseY, Rectangle bounds, int playerCount) {
        if (bounds == null || !bounds.contains(bounds.x, mouseY)) {
            return -1; // Click outside bounds horizontally (or vertically if bounds check includes Y)
        }

        int relativeY = mouseY - bounds.y;
        if (relativeY < 0 || relativeY >= bounds.height) {
             return -1; // Click outside vertical bounds
        }

        int index = relativeY / ITEM_HEIGHT;

        if (index >= 0 && index < playerCount) {
            return index;
        } else {
            return -1; // Click below the last item or in padding
        }
    }
}