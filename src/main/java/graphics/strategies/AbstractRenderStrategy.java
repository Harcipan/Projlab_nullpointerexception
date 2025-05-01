package graphics.strategies;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import graphics.customUIElements.CustomButton;
import graphics.customUIElements.CustomTextField;
import graphics.customUIElements.Interactable;

/**
 * Abstract base class for render strategies providing common input handling.
 */
public abstract class AbstractRenderStrategy implements IRenderStrategy {
    protected List<CustomButton> buttons = new ArrayList<>(); // List to hold buttons
    protected List<CustomTextField> textFields = new ArrayList<>(); // List to hold text fields (if needed)

    @Override
    public abstract void render(Graphics2D g2d, Dimension dimension);

    @Override
    public void updateHover(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
        for (CustomTextField textField : textFields) {
            textField.setHovered(textField.contains(mouseX, mouseY));
        }
    }

    @Override
    public void handlePress(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
                break; // Only press one button
            }
        }
        for (CustomTextField textField : textFields) {
            if (textField.contains(mouseX, mouseY)) {
                textField.setFocused(true); // Set focused state for text field
            } else {
                textField.setFocused(false); // Reset other fields
            }
        }
    }

    @Override
    public Interactable handleRelease(int mouseX, int mouseY) {
        Interactable clickedInteractable = null;
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                clickedInteractable = btn;
                onButtonClicked(btn);
            }
            btn.setPressed(false);
        }
        // For text fields, the highlight state is not handled here

        return clickedInteractable;
    }

    @Override
    public List<CustomTextField> getTextFields() {
        return textFields;
    }

    /**
     * Handles action dispatch for a clicked button.
     * Subclasses should implement this to perform button-specific logic.
     */
    protected abstract void onButtonClicked(CustomButton btn);
}