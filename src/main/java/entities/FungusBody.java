package entities;

import static use_cases.UseCase.replace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import map.Map;
import map.Tile;
import player.FungusPlayer;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * FungusBody is a subclass of Fungus that represents the body of a fungus.
 * It is responsible for growing mycelium and managing the spore charge.
 */
public class FungusBody extends Fungus implements Serializable {
    public static final int BODY_COST = 5;                  // Spore cost to grow a fungus body on a new tekton
    private static final int MAX_SPORE_CHARGE = 100;        // Maximum spore charge
    private static final int CHARGE_PER_TICK = 1;           // Spore charge increase per tick
    public static final int SPORECLOUD_COST_MULTIPLIER = 5; // Cost of spore cloud (per unit of size)
    private static final int SPORECLOUD_RADIUS_MULTIPLIER = 5; // Radius of spore cloud (per unit of size)
    private static final int  SPOERCLOUD_PERCENTAGE = 10;   // Percentage of Tiles that will get spores when covered by a spore cloud
    private FungusPlayer player = null;                     // The player that owns this fungus body
    private int sporeCharge = 0;

    /**
     * Old constructor for FungusBody used in use-case simulation
     * @deprecated
     */
    @Deprecated
    public FungusBody(int health, int initialSporeCharge, Tile currentTile){
        super(1, health, currentTile);
        this.sporeCharge = initialSporeCharge;
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this), ArrowDirection.LEFT);
        this.sporeCharge = initialSporeCharge;
    }
    /**
     * Constructor for FungusBody used in the game
     * @param id The id of the fungus body
     * @param health The health of the fungus body
     * @param currentTile The tile where the fungus body is located
     * @param player The player that owns this fungus body
     */
    public FungusBody(int id, int health,  Tile currentTile, FungusPlayer player) {
        super(id, health, currentTile);
        this.player = player;
        this.player.addFungusBody(this);
        this.currentTile.addEntity(this);
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public FungusBody(int id, int health, int initialSporeCharge,  Tile currentTile, FungusPlayer player) {
        super(id, health, currentTile);
        this.player = player;
        this.sporeCharge = initialSporeCharge;
        this.player.addFungusBody(this);
        this.currentTile.addEntity(this);
        replace(this);
        UseCase.printWrapper("Initializing FungusBody as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
        UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    @Override
    public void update(){
        sporeCharge+= CHARGE_PER_TICK;
        if (sporeCharge > MAX_SPORE_CHARGE) {
            sporeCharge = MAX_SPORE_CHARGE;
        }
    }

    public void decrementSporeCharge() {
        this.sporeCharge--;
    }

    public void decrementSporeCharge(int amount) {
        this.sporeCharge -= amount;
    }

    public int getSporeCharge() {
        return this.sporeCharge;
    }

    @Override
    public void damage() {
        this.health--;
        if (this.health <= 0) {
            kill();
        }
    }

    public void kill(){
        this.die();
        player.removeFungusBody(this);
    }

    /**
     * Creates a spore cloud of the given size: places random spores in the surrounding area.
     * The spore cloud will cost size * SPORECLOUD_COST spore charge.
     * It is assumed that the calling player has verfied that the body has enough spore charge.
     * @param size The size of the spore cloud to create.
     */
    public void sporeCloud(int size) {
        List<Tile> tiles = new ArrayList<>();
        int radius = size * SPORECLOUD_RADIUS_MULTIPLIER;
        int percentage = size * SPOERCLOUD_PERCENTAGE;
        int sporeChargeCost = size * SPORECLOUD_COST_MULTIPLIER;
        
        // Get the coordinates of the tiles in the spore cloud
        int x = this.getCurrentTile().getX();
        int y = this.getCurrentTile().getY();
        List<int[]> coordinates = new ArrayList<>();
        int radiusSquared = radius * radius;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radiusSquared) {
                    coordinates.add(new int[]{x + i, y + j});
                }
            }
        }
        // Randomly select the tiles to place spores on
        Map currentMap = this.getCurrentTile().getParentTekton().getMap();
        for (int i = 0; i < percentage; i++) {
            int randomIndex = (int) (Math.random() * coordinates.size());
            int[] coordinate = coordinates.get(randomIndex);
            Tile tile = currentMap.getTile(coordinate[0], coordinate[1]);
            if (tile != null) {
                tiles.add(tile);
                coordinates.remove(randomIndex);
            }
        }

        // Place spores on the selected tiles
        for (Tile tile : tiles) {
            // generate random number to decide spore  (Cut, Freeze, Slow, Speed, Split)
            int randomSporeType = (int) (Math.random() * 5);
            // generate random number to decide spore nutrient value (1-10)
            int randomNutrientValue = (int) (Math.random() * 10) + 1;
            // generate random number to decide spore lifetime (1-10)
            int randomLifetime = (int) (Math.random() * 10) + 1;
            // generate random number to decide spore effect time (1-10)
            int randomEffectTime = (int) (Math.random() * 10) + 1;
            // generate random number to decide spore effect value (1-10)
            int randomEffectValue = (int) (Math.random() * 10) + 1;
            // create spore, constructor places it on the tile
            // 0 = CutSpore, 1 = FreezeSpore, 2 = SlowSpore, 3 = SpeedUpSpore, 4 = SplitSpore
            switch (randomSporeType) {
                case 0:
                    player.addSpore(new CutSpore(GameEntity.getNextId(), tile, randomNutrientValue, randomLifetime, randomEffectTime));
                    break;
                case 1:
                    player.addSpore(new FreezeSpore(GameEntity.getNextId(), tile, randomNutrientValue, randomLifetime, randomEffectTime));
                    break;
                case 2:
                    player.addSpore(new SlowSpore(GameEntity.getNextId(), tile, randomNutrientValue, randomLifetime, randomEffectTime, randomEffectValue));
                    break;
                case 3:
                    player.addSpore(new SpeedUpSpore(GameEntity.getNextId(), tile, randomNutrientValue, randomLifetime, randomEffectTime, randomEffectValue));
                    break;
                case 4:
                    player.addSpore(new SplitSpore(GameEntity.getNextId(), tile, randomNutrientValue, randomLifetime));
                    break;
                default:
                    break;
            }
            tile.getParentTekton().addPlayerSpore(player);

            
        }
        // Remove the spore charge cost and inflict damage
        decrementSporeCharge(sporeChargeCost);
        damage();
    }
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"fungusbody_").append(id).append("\": {\n");
        int tileValue = currentTile.getParentTekton().getTileId(currentTile);
        sb.append("\t\"currentTile\": t").append(tileValue).append(",\n");
        sb.append("\t\"health\": ").append(health).append(",\n");
        sb.append("\t\"sporecharge\": ").append(sporeCharge).append("\n");
        sb.append("}");

        return sb.toString();
    }
    
    
    
    





}
