package graphics.accentManager;

import java.util.List;
import player.Player;
import java.awt.Color;


public class PaletteGenerator {
    public static void setPlayerAccentColors(List<Player> players) {
        int numPlayers = players.size();
        float hueIncrement = 1.0f / numPlayers; // Increment for each player's hue
        for (int i = 0; i < numPlayers; i++) {
            Player player = players.get(i);
            player.setAccentColor(Color.getHSBColor(i * hueIncrement, 1f, 1f)); // Set color with saturation and brightness
            System.out.println("Player " + player.getName() + " accent color is set to: " + player.getAccentColor());
        }
    }
}
