package graphics.strategies;

import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.Dimension;
import graphics.customUIElements.CustomButton;
import graphics.customUIElements.CustomTextField;
import graphics.customUIElements.Interactable;

import java.util.List;

/*
 * RenderStrategy is an interface that defines the contract for rendering strategies (think of it as scenes or screens).
 * Implementations of this interface will provide specific rendering logic for different views or screens.
 */

public interface IRenderStrategy {

    /**
     * Renders the content based on the current strategy.
     * This method should be called within the paintComponent method of a JPanel.
     *
     * @param g2d The Graphics2D object used for rendering.
     * @param dimension The dimension of the panel or component.
     * */
    void render(Graphics2D g2d, Dimension dimension);

    /**
     * Updates the hover state of buttons based on mouse position.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     */
    public void updateHover(int mouseX, int mouseY);

    /**
     * Handles mouse press events.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     */
    void handlePress(int mouseX, int mouseY);

    /**
     * Handles mouse release events.
     *
     * @param mouseX The X coordinate of the mouse.
     * @param mouseY The Y coordinate of the mouse.
     * @return The button that was clicked, if any.
     */
    public Interactable handleRelease(int mouseX, int mouseY);

    public List<CustomTextField> getTextFields(); // Added to get the list of text fields
}