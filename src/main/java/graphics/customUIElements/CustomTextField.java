// Create this new file: c:\Users\forianzsiga\Documents\Uni\Projlab\Projlab_nullpointerexception\src\main\java\graphics\customUIElements\CustomTextField.java
package graphics.customUIElements;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class CustomTextField extends Interactable {

    private Rectangle bounds;
    private String text = "";
    private boolean focused = false;
    private int cursorPosition = 0; // Position where the next char will be inserted
    private boolean showCursor = false;
    private Runnable onEnterCallback = null; // Optional callback when Enter is pressed
    private Timer blinkTimer; // Timer for blinking cursor
    private Component repaintTarget = null;
    private Runnable repaintCallback = null; // Optional callback for repainting

    // --- Cursor Blink Listener Support ---
    public interface CursorBlinkListener {
        void onCursorBlink(boolean showCursor);
    }
    private CursorBlinkListener cursorBlinkListener;
    public void setCursorBlinkListener(CursorBlinkListener listener) {
        this.cursorBlinkListener = listener;
    }

    public CustomTextField(int x, int y, int width, int height, Runnable repaintCallback) { // Add repaintTarget
        super(x, y, width, height);
        this.bounds = new Rectangle(x, y, width, height);
        this.repaintTarget = repaintTarget; // Store the component that needs repainting
        this.repaintCallback = repaintCallback;
        
        // Initialize the timer
        blinkTimer = new Timer(500, e -> { // Use lambda for ActionListener
            if (focused) {
                showCursor = !showCursor;
                if (this.repaintCallback != null) {
                    this.repaintCallback.run(); // Execute the callback
                }
                if (cursorBlinkListener != null) {
                    cursorBlinkListener.onCursorBlink(showCursor);
                }
            }
        });
        blinkTimer.setRepeats(true);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = (text == null) ? "" : text;
        // Clamp cursor position (simple end-positioning)
        this.cursorPosition = this.text.length();
        // this.focused = false; // Keep focus if needed
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
        if (focused) {
            showCursor = true; // Ensure cursor is visible when focus gained
            blinkTimer.start(); // Start blinking
            System.out.println("CustomTextField focused: " + bounds);
        } else {
            showCursor = false; // Hide cursor when focus lost
            blinkTimer.stop(); // Stop blinking
            System.out.println("CustomTextField lost focus: " + bounds);
            // Repaint one last time using the callback to ensure cursor is hidden
            if (this.repaintCallback != null) {
                 this.repaintCallback.run(); // Use the callback
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // Method to update bounds if needed (e.g., for player list editing)
    public void setBounds(int x, int y, int width, int height) {
        this.bounds.setBounds(x, y, width, height);
    }

    public void setBounds(Rectangle bounds) {
        this.bounds.setBounds(bounds);
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public void setOnEnterCallback(Runnable callback) {
        this.onEnterCallback = callback;
    }

    /**
     * Basic key handling. Needs to be called by the container.
     * @param e The KeyEvent
     * @return true if the key was consumed, false otherwise
     */
    public boolean handleKeyPress(KeyEvent e) {
        if (!focused) {
            return false;
        }

        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();

        // Always reset blink on key press
        showCursor = true;
        blinkTimer.restart(); // Restart the blink timer

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (cursorPosition > 0) {
                text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
                cursorPosition--;
                return true;
            }
        } else if (keyCode == KeyEvent.VK_DELETE) {
             if (cursorPosition < text.length()) {
                text = text.substring(0, cursorPosition) + text.substring(cursorPosition + 1);
                // cursorPosition remains the same
                return true;
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (cursorPosition > 0) {
                cursorPosition--;
                return true;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
             if (cursorPosition < text.length()) {
                cursorPosition++;
                return true;
            }
        } else if (keyCode == KeyEvent.VK_HOME) {
             cursorPosition = 0;
             return true;
        } else if (keyCode == KeyEvent.VK_END) {
             cursorPosition = text.length();
             return true;
        } else if (keyCode == KeyEvent.VK_ENTER) {
             System.out.println("CustomTextField: Enter pressed. Final text: " + text);
             if (onEnterCallback != null) {
                 onEnterCallback.run(); // Execute the callback
             } else {
                 setFocused(false); // Default behavior: lose focus on Enter
             }
            return true; // Consumed Enter
        } else if (Character.isDefined(keyChar) && !Character.isISOControl(keyChar)) {
            // Insert character at cursor position
            text = text.substring(0, cursorPosition) + keyChar + text.substring(cursorPosition);
            cursorPosition++;
            return true;
        }

        return false; // Key not handled
    }

    public void draw(Graphics2D g2d) {
        // Save original settings
        beforeDraw(g2d);

        // Draw background
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw border (thicker if focused)
        g2d.setColor(focused ? Color.BLUE : Color.BLACK);
        g2d.setStroke(new BasicStroke(focused ? 2 : 1));
        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Set font and color for text
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.setColor(Color.BLACK);

        // Clip rendering to the text field bounds before drawing text
        // Use a slightly smaller clip area for padding
        int padding = 3;
        g2d.clipRect(bounds.x + padding, bounds.y + padding, bounds.width - 2 * padding, bounds.height - 2 * padding);

        // Draw text (vertically centered)
        FontMetrics fm = g2d.getFontMetrics();
        int textY = bounds.y + (bounds.height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, bounds.x + padding + 2, textY); // Add small horizontal padding

        // Draw cursor (simple blinking vertical line)
        if (focused && showCursor) {
            // Calculate cursor X position based on text before cursor
            String textBeforeCursor = text.substring(0, cursorPosition);
            int cursorX = bounds.x + 3 + 2 + fm.stringWidth(textBeforeCursor); // Use same padding as text
            int cursorY1 = bounds.y + (bounds.height - fm.getHeight()) / 2;
            int cursorY2 = cursorY1 + fm.getHeight();
            g2d.setColor(Color.BLACK); // Ensure cursor color is set
            g2d.drawLine(cursorX, cursorY1, cursorX, cursorY2);
        }

        // Restore original settings
        afterDraw(g2d);
    }
}