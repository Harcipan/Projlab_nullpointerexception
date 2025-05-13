package graphics.accentManager;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import player.FungusPlayer;
import player.Player;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Color;

public class TintedEntityDrawer {

    private static final int TILE_SIZE = 32;
    private static final int PLAYER_ICON_SIZE = 40;
    private static final int PLAYER_ICON_GAP = 18;
    private static final int PLAYER_ICON_START_Y = 90;

    private static BufferedImage FUNGUS_ICON;
    private static BufferedImage INSECT_ICON;
    private static BufferedImage MYCELIUM_ICON;

    private static BufferedImage FUNGUS_GREYSCALE_ICON;
    private static BufferedImage INSECT_GREYSCALE_ICON;
    private static BufferedImage MYCELIUM_GREYSCALE_ICON;

    private static BufferedImage FUNGUS_HUE_MASK;
    private static BufferedImage INSECT_HUE_MASK;
    private static BufferedImage MYCELIUM_HUE_MASK;

    static {
        try {
            FUNGUS_ICON = ImageIO.read(Paths.get("res/player_icons/mushroom_icon.png").toFile());
            INSECT_ICON = ImageIO.read(Paths.get("res/player_icons/insect_icon.png").toFile());
            MYCELIUM_ICON = ImageIO.read(Paths.get("res/elements/myc_updownleftright.png").toFile());

            FUNGUS_GREYSCALE_ICON = ImageIO.read(Paths.get("res/player_icons/mushroom_icon_greyscale.png").toFile());
            INSECT_GREYSCALE_ICON = ImageIO.read(Paths.get("res/player_icons/insect_icon_greyscale.png").toFile());

            FUNGUS_HUE_MASK = ImageIO.read(Paths.get("res/player_icons/mushroom_icon_hue_mask.png").toFile());
            INSECT_HUE_MASK = ImageIO.read(Paths.get("res/player_icons/insect_icon_hue_mask.png").toFile());

        } catch (IOException e) {
            System.err.println("Could not load player icons");
        }
    }

    /*
     * * Draws the player icon at the specified coordinates.
     * * @param g2d The Graphics2D object used for rendering.
     * * @param player The player object to draw.
     * * @param x The x coordinate to draw the icon at.
     * * @param y The y coordinate to draw the icon at.
     * * @param playerIconSize The size of the player icon.
     * 
     * * The greyscale and hue mask images are used to create a colorized version of the
     * * player icon. The colorization is done by applying the player's accent color to the
     * * greyscale image using the hue mask.
     */
    public static void drawPlayer(Graphics2D g2d, Player player, int x, int y, int playerIconSize) {
        // Determine player type and select images
        BufferedImage greyscaleIcon = null;
        BufferedImage hueMask = null;
        BufferedImage originalIcon = null;
        BufferedImage colorizedIcon = null;

        if (player instanceof player.FungusPlayer) {
            greyscaleIcon = FUNGUS_GREYSCALE_ICON;
            hueMask = FUNGUS_HUE_MASK;
            originalIcon = FUNGUS_ICON;
        } else if (player instanceof player.InsectPlayer) {
            greyscaleIcon = INSECT_GREYSCALE_ICON;
            hueMask = INSECT_HUE_MASK;
            originalIcon = INSECT_ICON;
        } else {
            // fallback: draw nothing or a default icon
            return;
        }

        colorizedIcon = tintPlayerIcon(player.getAccentColor(), greyscaleIcon, hueMask, originalIcon);

        g2d.drawImage(colorizedIcon, x, y, playerIconSize, playerIconSize, null);
    }

    public static void drawMycelium(Graphics2D g2d, int x, int y, int size, FungusPlayer fp, BufferedImage myceliumIcon) {
        // Draw the mycelium icon with a tint
        BufferedImage originalIcon = myceliumIcon;

        Color color = fp.getAccentColor();
        BufferedImage colorizedIcon = tintMyceliumIcon(color, originalIcon);

        g2d.drawImage(colorizedIcon, x, y, size, size, null);
    }

    static float mix(float a, float b, float t) {
        return a * (1 - t) + b * t;
    }

    private static BufferedImage tintPlayerIcon(Color color, BufferedImage greyscaleSprite, BufferedImage hueMask, BufferedImage originalSprite) {
        BufferedImage tintedSprite = new BufferedImage(greyscaleSprite.getWidth(), greyscaleSprite.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(greyscaleSprite, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int origARGB = originalSprite.getRGB(i, j);
                int greyARGB = greyscaleSprite.getRGB(i, j);
                int maskARGB = hueMask.getRGB(i, j);

                int a_out = (origARGB >> 24) & 0xFF;

                // Extract greyscale value
                float grey = (greyARGB >> 16) & 0xFF;
                grey = grey / 255f;

                // Extract mask alpha (use red channel, normalized. It doesn't matter which channel we use, they are all the same)
                float maskAlpha = 1f - ((maskARGB >> 16) & 0xFF) / 255f;

                // Tint color normalized
                float tintR = color.getRed() / 255f;
                float tintG = color.getGreen() / 255f;
                float tintB = color.getBlue() / 255f;

                // Original color channels
                int origR = (origARGB >> 16) & 0xFF;
                int origG = (origARGB >> 8) & 0xFF;
                int origB = origARGB & 0xFF;

                // Mix tinted greyscale and original using maskAlpha
                int r_out = (int)mix(grey * tintR * 255f, origR, maskAlpha);
                int g_out = (int)mix(grey * tintG * 255f, origG, maskAlpha);
                int b_out = (int)mix(grey * tintB * 255f, origB, maskAlpha);

                // Clamp to [0,255]
                r_out = Math.max(0, Math.min(255, r_out));
                g_out = Math.max(0, Math.min(255, g_out));
                b_out = Math.max(0, Math.min(255, b_out));

                tintedSprite.setRGB(i, j, (a_out << 24) | (r_out << 16) | (g_out << 8) | (b_out));
            }
        }
        return tintedSprite;
    }

    private static BufferedImage tintMyceliumIcon(Color color, BufferedImage originalSprite) {
        BufferedImage tintedSprite = new BufferedImage(originalSprite.getWidth(), originalSprite.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(originalSprite, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int origARGB = originalSprite.getRGB(i, j);

                int a_out = (origARGB >> 24) & 0xFF;

                // Tint color normalized
                float tintR = color.getRed() / 255f;
                float tintG = color.getGreen() / 255f;
                float tintB = color.getBlue() / 255f;

                // Original color channels
                int origR = (origARGB >> 16) & 0xFF;
                int origG = (origARGB >> 8) & 0xFF;
                int origB = origARGB & 0xFF;

                // Mix tinted greyscale and original using maskAlpha
                int r_out = (int)mix(tintR * 100f, origR, .4f);
                int g_out = (int)mix(tintG * 100f, origG, .4f);
                int b_out = (int)mix(tintB * 100f, origB, .4f);

                // Clamp to [0,255]
                r_out = Math.max(0, Math.min(255, r_out));
                g_out = Math.max(0, Math.min(255, g_out));
                b_out = Math.max(0, Math.min(255, b_out));

                tintedSprite.setRGB(i, j, (a_out << 24) | (r_out << 16) | (g_out << 8) | (b_out));
            }
        }

        return tintedSprite;
    }
}
