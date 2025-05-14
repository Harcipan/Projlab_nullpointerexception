package graphics.strategies;

import graphics.presenters.LoadGamePresenter;
import graphics.customUIElements.CustomButton;
import graphics.customUIElements.CustomPlayerList;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoadGameStrategy extends AbstractRenderStrategy {
    private LoadGamePresenter presenter;
    private CustomButton loadButton;
    private CustomButton backButton;
    private List<String> saveNames;
    private int selectedSaveIndex = -1;

    public LoadGameStrategy(LoadGamePresenter presenter) {
        this.presenter = presenter;
        loadButton = new CustomButton("Load Selected Save", 200, 350, 200, 40);
        backButton = new CustomButton("< Back", 10, 360, 100, 30);
        buttons.add(loadButton);
        buttons.add(backButton);
        // Placeholder save names TODO
        saveNames = new ArrayList<>();
        saveNames.add("PlaceholderSave1");
        saveNames.add("PlaceholderSave2");
        saveNames.add("PlaceholderSave3");
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(Color.GRAY);
        if (dimension != null) {
            g2d.fillRect(0, 0, dimension.width, dimension.height);
        } else {
            g2d.fillRect(0, 0, 600, 400);
            dimension = new Dimension(600, 400);
        }
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String title = "Load Game";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (dimension.width - titleWidth) / 2;
        g2d.drawString(title, titleX, 40);

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Saved Games", 30, 80);

        // Draw placeholder save list using CustomPlayerList as a placeholder
        Rectangle listBounds = new Rectangle(30, 100, 200, 120);
        CustomPlayerList.drawSaves(g2d, saveNames, selectedSaveIndex, listBounds);

        loadButton.setEnabled(selectedSaveIndex != -1);
        loadButton.draw(g2d);
        backButton.draw(g2d);
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        if (btn == loadButton && selectedSaveIndex != -1) {
            presenter.onLoadSaveClicked(saveNames.get(selectedSaveIndex));
        } else if (btn == backButton) {
            presenter.onBackToMainMenuClicked();
        }
    }

    @Override
    public void handlePress(int mouseX, int mouseY, int button) {
        Rectangle listBounds = new Rectangle(30, 100, 200, 120);
        for (int i = 0; i < saveNames.size(); i++) {
            Rectangle itemRect = new Rectangle(listBounds.x, listBounds.y + i * 30, listBounds.width, 30);
            if (itemRect.contains(mouseX, mouseY)) {
                selectedSaveIndex = i;
                if (presenter.getCoordinator() != null) {
                    presenter.getCoordinator().initiateRepaint();
                }
                break;
            }
        }
        for (CustomButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                btn.setPressed(true);
            } else {
                btn.setPressed(false);
            }
        }
    }

    @Override
    public void updateHover(int mouseX, int mouseY) {
        for (CustomButton btn : buttons) {
            btn.setHovered(btn.contains(mouseX, mouseY));
        }
    }
}
