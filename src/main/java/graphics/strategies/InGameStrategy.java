package graphics.strategies;

import graphics.accentManager.TintedEntityDrawer;
import graphics.customUIElements.CustomButton;
import graphics.presenters.InGamePresenter;
import entities.*;
import map.*;
import player.FungusPlayer;
import player.InsectPlayer;
import player.Player;
import util.Vec2;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;

public class InGameStrategy extends AbstractRenderStrategy {
    private InGamePresenter presenter;
    private BufferedImage backgroundImage;
    private CustomButton nextTurnButton;
    private CustomButton saveGameButton;

    private static final int TILE_SIZE = 32;
    private static final int PLAYER_ICON_SIZE = 40;
    private static final int PLAYER_ICON_GAP = 18;
    private static final int PLAYER_ICON_START_Y = 90;

    // player icons
    private static BufferedImage FUNGUS_ICON;
    private static BufferedImage INSECT_ICON;

    // Mycelium spritesheet
    private static BufferedImage MYC_DISCONNECTED_ICON;
    private static BufferedImage MYC_UP_ICON;
    private static BufferedImage MYC_UPDOWN_ICON;
    private static BufferedImage MYC_UPRIGHT_ICON;
    private static BufferedImage MYC_LEFTUPDOWN_ICON;
    private static BufferedImage MYC_UPDOWNLEFTRIGHT_ICON;

    public record TektonCenterAngle(Tekton tekton, Dimension centerOfMass, float randomAngle) {}

    static {
        try {
            // Player Icons
            FUNGUS_ICON = ImageIO.read(Paths.get("res/player_icons/mushroom_icon.png").toFile());
            INSECT_ICON = ImageIO.read(Paths.get("res/player_icons/insect_icon.png").toFile());

            // Mycelium Spritesheet
            MYC_UPDOWNLEFTRIGHT_ICON = ImageIO.read(Paths.get("res/elements/myc_updownleftright.png").toFile());
            MYC_UP_ICON = ImageIO.read(Paths.get("res/elements/myc_up.png").toFile());
            MYC_UPDOWN_ICON = ImageIO.read(Paths.get("res/elements/myc_updown.png").toFile());
            MYC_UPRIGHT_ICON = ImageIO.read(Paths.get("res/elements/myc_upright.png").toFile());
            MYC_LEFTUPDOWN_ICON = ImageIO.read(Paths.get("res/elements/myc_leftupdown.png").toFile());
            MYC_DISCONNECTED_ICON = ImageIO.read(Paths.get("res/elements/myc_disconnected.png").toFile());

        } catch (IOException e) {
            System.err.println("Could not load player icons");
        }
    }

    public InGameStrategy(InGamePresenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("InGamePresenter cannot be null");
        }
        this.presenter = presenter;

        try {
            backgroundImage = ImageIO.read(new File("res/backgrounds/32bg.png"));
        } catch (IOException e) {
            System.err.println("Could not load background image: res/32bg.png");
            backgroundImage = null;
        }

        int btnWidth = 180;
        int btnHeight = 40;
        int btnX = 30;
        int btnY = 600; // Will be adjusted dynamically in drawLeftPanel
        nextTurnButton = new CustomButton("Next Turn", btnX, btnY, btnWidth, btnHeight);
        buttons.add(nextTurnButton);

        // save game button at the bottom
        saveGameButton = new CustomButton("Save Game and Exit", btnX, 700, btnWidth, btnHeight);
        buttons.add(saveGameButton);
    }

    @Override
    public void render(Graphics2D g2d, Dimension dimension) {
        drawLeftPanel(g2d, dimension);
        drawMap(g2d, dimension);
        // Draw placement hover icon if in placement phase
        if (presenter.isPlacementPhase() && presenter.getPlacementHover() != null) {
            Player player = presenter.getPlayers().get(presenter.getPlacingPlayerIndex());
            BufferedImage icon;
            if (player instanceof FungusPlayer) {
                icon = FUNGUS_ICON;
            } else if (player instanceof InsectPlayer) {
                icon = INSECT_ICON;
            } else {
                icon = null;
            }
            Point p = presenter.getPlacementHover();
            Composite oldComp = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(icon, p.x - PLAYER_ICON_SIZE / 2, p.y - PLAYER_ICON_SIZE / 2, PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, null);
            g2d.setComposite(oldComp);
        }
    }

    private void drawLeftPanel(Graphics2D g2d, Dimension dimension) {
        g2d.setColor(new Color(40, 40, 40)); // Dark gray panel
        g2d.fillRect(0, 0, presenter.getHUDWidth(), dimension.height);
        g2d.setColor(Color.WHITE);

        // display current turn and active player
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Turn: " + (int)(Math.floor((double)presenter.getCurrentTurn() / presenter.getPlayers().size())+1), 30, 40);
        java.util.List<Player> _playersInfo = presenter.getPlayers();
        if (!_playersInfo.isEmpty()) {
            Player _current = _playersInfo.get(presenter.getCurrentTurn() % _playersInfo.size());
            g2d.drawString("Current: " + _current.getName(), 30, 60);
        }

        // Draw player icons in rows, wrapping if needed
        java.util.List<Player> players = presenter.getPlayers();
        int currentTurnTruncated = presenter.getCurrentTurn() % (players.isEmpty() ? 1 : players.size());
        int hudWidth = presenter.getHUDWidth();
        int iconX = 30;
        int iconY = PLAYER_ICON_START_Y;
        int iconSpacingX = PLAYER_ICON_SIZE + 24;
        int iconSpacingY = PLAYER_ICON_SIZE + 24;
        int iconsPerRow = Math.max(1, (hudWidth - 2 * iconX) / iconSpacingX);
        int row = 0;
        int col = 0;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            BufferedImage icon;
            if (player instanceof FungusPlayer) {
                icon = FUNGUS_ICON;
            } else if (player instanceof InsectPlayer) {
                icon = INSECT_ICON;
            } else {
                icon = null;
            }
            int drawX = iconX + col * iconSpacingX;
            int drawY = iconY + row * iconSpacingY;
            // Highlight current player
            if (i == currentTurnTruncated) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(drawX - 4, drawY - 4, PLAYER_ICON_SIZE + 8, PLAYER_ICON_SIZE + 8);
            }
            //g2d.drawImage(icon, drawX, drawY, PLAYER_ICON_SIZE, PLAYER_ICON_SIZE, null);
            TintedEntityDrawer.drawPlayer(g2d, player, drawX, drawY, PLAYER_ICON_SIZE);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 13));
            g2d.drawString(player.getName(), drawX, drawY + PLAYER_ICON_SIZE + 16);
            col++;
            if (col >= iconsPerRow) {
                col = 0;
                row++;
            }
        }
        // Calculate Y position for Next Turn button so it snaps under the player list
        int btnMargin = 30;
        int btnWidth = nextTurnButton.getBounds().width;
        int btnHeight = nextTurnButton.getBounds().height;
        int btnX = btnMargin;
        int btnY;
        if (players.isEmpty()) {
            btnY = dimension.height - btnHeight - btnMargin;
        } else {
            int totalRows = row + (col > 0 ? 1 : 0);
            btnY = iconY + totalRows * iconSpacingY + btnMargin;
        }
        // Prevent button from overflowing panel
        if (btnY + btnHeight + btnMargin > dimension.height) {
            btnY = dimension.height - btnHeight - btnMargin;
        }
        nextTurnButton.setBounds(btnX, btnY, btnWidth, btnHeight);
        // Disable next turn button during placement phase
        nextTurnButton.setEnabled(!presenter.isPlacementPhase());
        nextTurnButton.draw(g2d);

        // Draw save game button
        saveGameButton.setBounds(btnX, dimension.height - btnHeight - btnMargin, btnWidth, btnHeight);
        
        // Disable save game button during placement phase
        saveGameButton.setEnabled(!presenter.isPlacementPhase());
        saveGameButton.draw(g2d);

    }

    private void drawMap(Graphics2D g2d, Dimension dimension) {
        // Draw background image if available
        if (backgroundImage != null) {
            int mapPixelWidth = presenter.getMapSize() * TILE_SIZE;
            int mapPixelHeight = presenter.getMapSize() * TILE_SIZE;
            g2d.drawImage(backgroundImage,
                          presenter.getHUDWidth(), 0,
                          mapPixelWidth, mapPixelHeight,
                          null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(presenter.getHUDWidth(), 0, presenter.getMapSize() * TILE_SIZE, presenter.getMapSize() * TILE_SIZE);
        }

        // Draw Tekton borders
        int mapSize = presenter.getMapSize();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Tile t = presenter.getTile(i, j);
                Vec2 tilePos = new Vec2(i * TILE_SIZE, j * TILE_SIZE);
                List<Tile> neighbors = t.getNeighbors();

                // discard diagonal neighbors
                neighbors.removeIf(neighbor -> Math.abs(t.getX() - neighbor.getX()) == 1 && Math.abs(t.getY() - neighbor.getY()) == 1);

                for (Tile neighbor : neighbors) {
                    if (t.getParentTekton() != neighbor.getParentTekton()) {
                        Vec2 neighborPos = new Vec2(neighbor.getX() * TILE_SIZE, neighbor.getY() * TILE_SIZE);
                        Vec2 halfway = tilePos.add(neighborPos).scale(0.5f);
                        Vec2 direction = neighborPos.subtract(tilePos).normalize();
                        Vec2 perpendicular = new Vec2(-direction.getY(), direction.getX());
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(3));
                        Vec2 start = halfway.add(perpendicular.scale(TILE_SIZE / 2));
                        Vec2 end = halfway.subtract(perpendicular.scale(TILE_SIZE / 2));
                        g2d.drawLine((int) start.getX() + presenter.getHUDWidth(), (int) start.getY(), (int) end.getX() + presenter.getHUDWidth(), (int) end.getY());
                    }
                }
            }
        }
        
        // Draw FungusBodies
        for (FungusPlayer fp : presenter.getFungusPlayers()) {
            for (FungusBody fb : fp.getFungusBodies()) {
                Tile t = fb.getCurrentTile();
                int x = t.getX() * TILE_SIZE + presenter.getHUDWidth();
                int y = t.getY() * TILE_SIZE;
                TintedEntityDrawer.drawPlayer(g2d, (Player)fp, x, y, TILE_SIZE);
            }
        }

        // Draw Insects
        for (InsectPlayer ip : presenter.getInsectPlayers()) {
            for (Insect i : ip.getControlledInsects()) {
                Tile t = i.getCurrentTile();
                int x = t.getX() * TILE_SIZE + presenter.getHUDWidth();
                int y = t.getY() * TILE_SIZE;
                TintedEntityDrawer.drawPlayer(g2d, (Player)ip, x, y, TILE_SIZE);
            }
        }
        // Draw Mycelium
        for (FungusPlayer fp : presenter.getFungusPlayers()) {
            for (Mycelium m : fp.getMycelia()) {
                Tile t = m.getCurrentTile();
                int x = t.getX() * TILE_SIZE + presenter.getHUDWidth();
                int y = t.getY() * TILE_SIZE;

                // Check connections in four cardinal directions
                boolean up = false, down = false, left = false, right = false;
                int tx = t.getX();
                int ty = t.getY();
                Map map = t.getParentTekton().getMap(); // Get map reference

                // Up
                Tile upTile = map.getTile(tx, ty - 1);
                if (upTile != null) {
                    for (entities.GameEntity e : upTile.getEntities()) {
                        if (e instanceof Mycelium myc && myc.getPlayer() == fp) {
                            up = true;
                            break;
                        }
                    }
                }
                // Down
                Tile downTile = map.getTile(tx, ty + 1);
                if (downTile != null) {
                    for (entities.GameEntity e : downTile.getEntities()) {
                        if (e instanceof Mycelium myc && myc.getPlayer() == fp) {
                            down = true;
                            break;
                        }
                    }
                }
                // Left
                Tile leftTile = map.getTile(tx - 1, ty);
                if (leftTile != null) {
                    for (entities.GameEntity e : leftTile.getEntities()) {
                        if (e instanceof Mycelium myc && myc.getPlayer() == fp) {
                            left = true;
                            break;
                        }
                    }
                }
                // Right
                Tile rightTile = map.getTile(tx + 1, ty);
                if (rightTile != null) {
                    for (entities.GameEntity e : rightTile.getEntities()) {
                        if (e instanceof Mycelium myc && myc.getPlayer() == fp) {
                            right = true;
                            break;
                        }
                    }
                }

                BufferedImage mycSprite = MYC_DISCONNECTED_ICON; // Default
                boolean needsRotation = false;
                double rotationAngle = 0;

                
                if (up && down && left && right) { 
                    mycSprite = MYC_UPDOWNLEFTRIGHT_ICON;
                }
                

                else if (up && down && left) { 
                    mycSprite = MYC_LEFTUPDOWN_ICON;
                } else if (up && down && right) {
                    mycSprite = MYC_LEFTUPDOWN_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI;
                } else if (down && left && right) { 
                    mycSprite = MYC_LEFTUPDOWN_ICON;
                    needsRotation = true;
                    rotationAngle = ( 3 * Math.PI / 2);
                } else if (up && left && right) { 
                    mycSprite = MYC_LEFTUPDOWN_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI / 2; 
                }
               
                
                else if (up && down) { // Shape: │
                    mycSprite = MYC_UPDOWN_ICON;
                } else if (left && right) { // Shape: ─
                    mycSprite = MYC_UPDOWN_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI / 2; // 90 degrees clockwise
                }
                
                
                else if (up && right) { 
                    mycSprite = MYC_UPRIGHT_ICON;
                } else if (up && left) {
                    mycSprite = MYC_UPRIGHT_ICON;
                    needsRotation = true;
                    rotationAngle = (3 * Math.PI) / 2; 
                } else if (down && right) { 
                    mycSprite = MYC_UPRIGHT_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI / 2; 
                } else if (down && left) { 
                    mycSprite = MYC_UPRIGHT_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI;
                }
                

                else if (up) {
                    mycSprite = MYC_UP_ICON;
                } else if (down) { 
                    mycSprite = MYC_UP_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI; 
                } else if (left) { 
                    mycSprite = MYC_UP_ICON;
                    needsRotation = true;
                    rotationAngle = (3 * Math.PI) / 2; 
                } else if (right) {
                    mycSprite = MYC_UP_ICON;
                    needsRotation = true;
                    rotationAngle = Math.PI / 2; 
                }

                // Draw the mycelium sprite with tint
                if (needsRotation) {
                    Graphics2D g2dRot = (Graphics2D) g2d.create();
                    g2dRot.rotate(rotationAngle, x + TILE_SIZE / 2.0, y + TILE_SIZE / 2.0);
                    TintedEntityDrawer.drawMycelium(g2dRot, x, y, TILE_SIZE, fp, mycSprite);
                    g2dRot.dispose();
                } else {
                    TintedEntityDrawer.drawMycelium(g2d, x, y, TILE_SIZE, fp, mycSprite);
                }
            }
        }
        
    }

    @Override
    protected void onButtonClicked(CustomButton btn) {
        if (btn == nextTurnButton) {
            // Advance turn
            presenter.getCoordinator().setCurrentTurn(presenter.getCoordinator().getCurrentTurn() + 1);

            // Check if round ended. Trigger tekton break if so
            if (presenter.getCoordinator().getCurrentTurn() % presenter.getPlayers().size() == 0 && presenter.getCoordinator().getCurrentTurn() > 0) {
                System.out.println("Round ended. Breaking tektons.");
                int currentTurn = presenter.getCoordinator().getCurrentTurn()  % presenter.getPlayers().size();
                List<Tekton> tektons = presenter.getCoordinator().getGameMap().getTektons();
                
                // Determine the count of breaks (log2 of the count of tektons. 1 for 1 tekton, 1 for 2 tektons, 2 for 4 tektons, etc.)
                int breakCount = (int) Math.ceil(Math.log(tektons.size() + 1) / Math.log(2));
                System.out.println("Break count: " + breakCount + " (log2 of " + tektons.size() + ")");

                // Find the center of mass of all tektons and generate random angles
                List<TektonCenterAngle> records = new ArrayList<>();
                for (Tekton t : tektons) {
                    Dimension center = t.getCenterOfMass();
                    float randomAngle = (float) (Math.random() * 2 * Math.PI);
                    records.add(new TektonCenterAngle(t, center, randomAngle));
                    System.out.println("Tekton " + tektons.indexOf(t) + " center: " + new Vec2((float)center.getWidth(), (float)center.getHeight()) + ", random angle: " + randomAngle*180/Math.PI);
                }

                for (int i = 0; i < breakCount; i++) {
                    // Select a random tekton and break it
                    int randomIndex = (int) (Math.random() * records.size());
                    TektonCenterAngle selectedTekton = records.get(randomIndex);
                    Tekton t = selectedTekton.tekton();
                    Dimension center = selectedTekton.centerOfMass();
                    float randomAngle = selectedTekton.randomAngle();

                    // Break the tekton
                    t.breakTekton(center, randomAngle);
                }
            }
            

            for (FungusPlayer fPlayer : presenter.getFungusPlayers()) {
                fPlayer.floodFillCheck();
            }
            presenter.getCoordinator().getGameMap().tick();
            presenter.getCoordinator().initiateRepaint();
        } else if (btn == saveGameButton) {
            System.out.println("[InGameStrategy] Save game and exit button clicked.");
            presenter.saveGameAndExit();
        }
    }

    @Override
    public void updateHover(int mouseX, int mouseY) {
        super.updateHover(mouseX, mouseY);
        if (presenter.isPlacementPhase()) {
            presenter.setPlacementHover(new Point(mouseX, mouseY));
        }
    }

    @Override
    public void handlePress(int mouseX, int mouseY, int button) {
        int gridX = (mouseX - presenter.getHUDWidth()) / TILE_SIZE;
        int gridY = mouseY / TILE_SIZE;
        if (presenter.isPlacementPhase()) {
            int mapSize = presenter.getMapSize();
            if (gridX >= 0 && gridY >= 0 && gridX < mapSize && gridY < mapSize) {
                Player player = presenter.getPlayers().get(presenter.getPlacingPlayerIndex());
                String entityType;
                if (player instanceof FungusPlayer) {
                    entityType = "FungusEntity";
                } else if (player instanceof InsectPlayer) {
                    entityType = "InsectEntity";
                } else {
                    entityType = "UnknownEntity";
                }

                System.out.println("Placing " + entityType + " for player '" + player.getName() + "' at tile (" + gridX + ", " + gridY + ")");
                Tile t = presenter.getTile(gridX, gridY);
                
                if (player instanceof FungusPlayer) {
                    FungusBody fb = new FungusBody(1,1, t, (FungusPlayer)player);
                    t.addEntity(fb);
                    System.out.println("FungusBody placed at tile (" + gridX + ", " + gridY + ")");
                } else if (player instanceof InsectPlayer) {
                    Insect ib = new Insect(1, t, (InsectPlayer) player);
                    t.addEntity(ib);
                    System.out.println("Insect placed at tile (" + gridX + ", " + gridY + ")");
                } else {
                    System.err.println("Unknown player type: " + player.getClass().getName());
                }

                // Increment turn after placement
                presenter.getCoordinator().setCurrentTurn(presenter.getCoordinator().getCurrentTurn() + 1);
                int next = presenter.getPlacingPlayerIndex() + 1;
                if (next >= presenter.getPlayers().size()) {
                    presenter.setPlacementPhase(false);
                    presenter.setPlacingPlayerIndex(0);
                    presenter.setPlacementHover(null);
                } else {
                    presenter.setPlacingPlayerIndex(next);
                }
            }
        }  
        
        else {
            // Not in placement phase, handle game actions
            Player currentPlayer = presenter.getCurrentPlayer();
            // if the current player is an insect, move the insect to the tile
            if (currentPlayer instanceof InsectPlayer insectPlayer) {
                // Current player is an InsectPlayer
                System.out.println("Clicked while InsectPlayer '" + insectPlayer.getName() + "' is active. Tile: (" + gridX + ", " + gridY + ")");
                // left click to move insect
                if (button == 1) {
                    System.out.println("Left click detected. Moving insect.");
                    // WIP: Move the first controlled insect to the clicked tile
                    if (!insectPlayer.getControlledInsects().isEmpty()) {
                         Tile targetTile = presenter.getTile(gridX, gridY);
                         if (targetTile != null) {
                             insectPlayer.moveTo(targetTile);
                             System.out.println("Insect moved to tile (" + gridX + ", " + gridY + ")");
                         } else {
                             System.err.println("Target tile is null or invalid: (" + gridX + ", " + gridY + ")");
                         }
                    }
                }
                // right click to cut
                else if (button == 3) {
                    System.out.println("Right click detected. Cutting.");
                    // WIP: Cut the first controlled insect to the clicked tile or eat the spore on the tile
                    if (!insectPlayer.getControlledInsects().isEmpty()) {
                        Tile targetTile = presenter.getTile(gridX, gridY);
                        List<Insect> insectpossible=insectPlayer.getControlledInsects();
                        Insect controlledInsect=null;
                        for (Insect insect : insectpossible) {
                            if(insect.getCurrentTile().isNeighbor(targetTile))
                            controlledInsect=insect;
                        }
                        
                        if (targetTile != null) {
                            insectPlayer.cut(targetTile);

                            for (FungusPlayer fPlayer : presenter.getFungusPlayers()) {
                                List<Spore> eaten = fPlayer.getSpores();
                                for (Spore spore : fPlayer.getSpores()) {
                                    if(spore.getCurrentTile()!=targetTile){
                                        eaten.remove(spore);
                                    }
                                }
                                for (Spore spore2 : eaten) {
                                    if(controlledInsect!=null)insectPlayer.eat(spore2,controlledInsect);
                                }
                            }

                            System.out.println("Cut at tile (" + gridX + ", " + gridY + ")");
                        } else {
                            System.err.println("Target tile is null or invalid: (" + gridX + ", " + gridY + ")");
                        }
                    }
                }

            }
            else if (currentPlayer instanceof FungusPlayer fungusPlayer) {
                // Current player is a FungusPlayer
                System.out.println("Clicked while FungusPlayer '" + fungusPlayer.getName() + "' is active. Tile: (" + gridX + ", " + gridY + ")");
                
                // WIP: grow mycelium in the tile
                if(button==1) {//grow mycelium on left click
                    Tile targetTile = presenter.getTile(gridX, gridY);
                    if (targetTile != null) {
                        Mycelium myc = ((FungusPlayer) currentPlayer).growMycelium(targetTile);
                        if (myc != null){
                            System.out.println("Mycelium grown at tile (" + myc.getCurrentTile().hashCode() + ")");
                        }
                        else {
                            System.out.println("Mycelium space is full or some other problem: (" + gridX + ", " + gridY + ")");
                        }
                    } else {
                        System.err.println("Target tile is null or invalid: (" + gridX + ", " + gridY + ")");
                    }
                }
                else if(button==3) {//spread spore on right click
                    Tile targetTile = presenter.getTile(gridX, gridY);
                    if (targetTile != null) {
                        List<FungusBody> playerFungusBodies=((FungusPlayer)currentPlayer).getFungusBodies();
                        for (FungusBody fb : playerFungusBodies) {
                            if(fb.getCurrentTile()==targetTile) {
                                fb.sporeCloud(3);
                            }
                        }
                    }
                }
            }
        }
    }
}
