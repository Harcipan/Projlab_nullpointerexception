package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.FungusBody;
import entities.GameEntity;
import player.FungusPlayer;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;

import static use_cases.UseCase.printWrapper;

public class Tekton {
    int breakChance;
    List<Tile> tiles;
    FungusBody fungusBody;
    Map map;
    HashMap<FungusPlayer, Integer> playerSpores; // spores per player
    int sporeCount;
    boolean growFungusFlag = true;

    public Tekton(Map map)
    {
        UseCase.replace(this);
        UseCase.printWrapper("Initializing Tekton as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.breakChance = 0;
        this.map = map;
        map.addTekton(this);
        tiles = new ArrayList<>();
        fungusBody = null;
        playerSpores = new HashMap<>();
        UseCase.printWrapper("Tekton: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public Tekton(Map map, int breakChance, int sporeCount) {
        UseCase.replace(this);
        UseCase.printWrapper("Initializing Tekton as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.breakChance = breakChance;
        this.map = map;
        map.addTekton(this);
        tiles = new ArrayList<>();
        fungusBody = null;
        playerSpores = new HashMap<>();
        UseCase.printWrapper("Tekton: "+UseCase.logger.get(this), ArrowDirection.LEFT);
        this.sporeCount = sporeCount;
    }

    public int addTile(Tile tile) {
        tiles.add(tile);
        tile.setParentTekton(this);
        return tiles.size() - 1;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int getTileId(Tile tile) {
        return tiles.indexOf(tile);
    }

    public Map getMap() {
        return map;
    }

    public int getPlayerSpores(FungusPlayer player) {
        if (playerSpores.containsKey(player)) {
            return playerSpores.get(player);
        } else {
            return 0;
        }
    }

    public void addPlayerSpore(FungusPlayer player) {
        if (playerSpores.containsKey(player)) {
            playerSpores.put(player, playerSpores.get(player) + 1);
        } else {
            playerSpores.put(player, 1);
        }
    }

    public boolean hasFungusBody() {
        return fungusBody != null;
    }


    /**
     * Breaks the tekton into two pieces along a fault line around the middle
     * @return the two pieces of the tekton as an ArrayList
     */
    public List<Tekton> breakTekton() {

        printWrapper("Breaking tekton...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);

        int faultLine = faultLine();
        List<Tile> tilesAlongFault = new ArrayList<>();
        Tekton t1 = new Tekton(map, breakChance, sporeCount);
        Tekton t2 = new Tekton(map, breakChance, sporeCount);

        if(faultLine > 0) {
            List<Tile> left = new ArrayList<>();
            List<Tile> right = new ArrayList<>();
            for (Tile tile : tiles) {
                if (tile.getX() < faultLine) {
                    left.add(tile);
                } else {
                    right.add(tile);
                }
                if (tile.getX() == faultLine || tile.getX() == faultLine - 1) {
                    tilesAlongFault.add(tile);
                }
            }
            // add the tiles to the new tektons (changes parent too)
            for (Tile tile : left) {
                t1.addTile(tile);
            }
            for (Tile tile : right) {
                t2.addTile(tile);
            }
        } else {
            faultLine *= -1;
            List<Tile> top = new ArrayList<>();
            List<Tile> bottom = new ArrayList<>();
            for (Tile tile : tiles) {
                if (tile.getY() < faultLine) {
                    top.add(tile);
                } else {
                    bottom.add(tile);
                }
                if (tile.getY() == faultLine || tile.getY() == faultLine - 1) {
                    tilesAlongFault.add(tile);
                }
            }
            // add the tiles to the new tektons (changes parent too)
            for (Tile tile : top) {
                t1.addTile(tile);
            }
            for (Tile tile : bottom) {
                t2.addTile(tile);
            }
        }
        // trigger a cut event on all entities along the fault line
        for (Tile tile : tilesAlongFault) {
            for (int i = tile.getEntities().size() - 1; i >= 0; --i){
                GameEntity entity = tile.getEntities().get(i);
                entity.getCut();
            }
        }
        ArrayList<Tekton> tl = new ArrayList<>();
        tl.add(t1);
        tl.add(t2);
        // remove the tekton from the map
        map.removeTekton(this);

        return tl;
    }

    public void increaseChance(int amount) {
        breakChance += amount;
    }

    /**
     * Returns the leftmost tile of the tekton
     * @return the leftmost tile of the tekton
     */
    private Tile getLeftmostTile() {
        Tile leftmostTile = tiles.get(0);
        for (Tile tile : tiles) {
            if (tile.getX() < leftmostTile.getX()) {
                leftmostTile = tile;
            }
        }
        return leftmostTile;
    }

    /**
     * Returns the rightmost tile of the tekton
     * @return the rightmost tile of the tekton
     */
    private Tile getRightmostTile() {
        Tile rightmostTile = tiles.get(0);
        for (Tile tile : tiles) {
            if (tile.getX() > rightmostTile.getX()) {
                rightmostTile = tile;
            }
        }
        return rightmostTile;
    }

    /**
     * Returns the topmost tile of the tekton
     * @return the topmost tile of the tekton
     */
    private Tile getTopmostTile() {
        Tile topmostTile = tiles.get(0);
        for (Tile tile : tiles) {
            if (tile.getY() < topmostTile.getY()) {
                topmostTile = tile;
            }
        }
        return topmostTile;
    }

    /**
     * Returns the bottommost tile of the tekton
     * @return the bottommost tile of the tekton
     */
    private Tile getBottommostTile() {
        Tile bottommostTile = tiles.get(0);
        for (Tile tile : tiles) {
            if (tile.getY() > bottommostTile.getY()) {
                bottommostTile = tile;
            }
        }
        return bottommostTile;
    }

    /**
     * Finds the width of the tekton
     * @return the width of the tekton
     */
    private int findWidth() {
        return getRightmostTile().getX() - getLeftmostTile().getX() + 1;
    }

    /**
     * Finds the height of the tekton
     * @return the height of the tekton
     */
    private int findHeight() {
        return getBottommostTile().getY() - getTopmostTile().getY() + 1;
    }

    /**
     * Finds the fault line along which the tekton will break
     * Approximate to the center of the tekton, some randomness added.
     * If positive, it will break along the y axis
     * if negative, it will break along the x axis
     * @return the fault line of the tekton
     */
    private int faultLine() {
        int width = findWidth();
        int height = findHeight();
        int faultLine = 0;
        if (width > height) {
            faultLine = width / 2;
            faultLine += getLeftmostTile().getX();
        } else {
            faultLine = height / 2;
            faultLine += getTopmostTile().getY();
            faultLine *= -1;
        }
        // add some randomness to the fault line
        //TODO: add this back when we are ready to have randomness in our game
        //Prototypes need deterministic behavior
        //faultLine += (int) (Math.random() * 2) - 1;
        return faultLine;
    }

    /**
     * serializes necessary info from object
     * 
     * Looks like:
     * 
     * "T1": {
     *       "Tipus": "normal", ???????
     *       "Tiles": [t1,t2],
     *       "breakChance": 3,
     *       "sporeCount": 4
     *   }
     * @return the formatted string
     */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"T").append(map.getTektons().indexOf(this)).append("\"").append(": {\n");
        sb.append("\t\"Tiles\": [");
        int lineValue = map.getWidth();
        //add first tile without ","
        if(tiles.size()!=0) {
            for(int i = 0; i< tiles.size(); i++){
                Tile tile = tiles.get(i);
                //int value = tile.getX() + tile.getY() * lineValue;
                if(i != 0){
                    sb.append(",");
                }
                sb.append("t").append(i); //temporary fix
            }
        }

        sb.append("],\n");
        sb.append("\t\"breakChance\": ").append(breakChance).append(",\n");
        sb.append("\t\"maxSporeCount\": ").append(sporeCount).append("\n");
        sb.append("}");
        return sb.toString();
    }

    //Programmed into a corner... :(
    public boolean canGrowFungus(){
        return growFungusFlag;
    }

    public void setCanGrowFungus(boolean growFungusFlag){
        this.growFungusFlag = growFungusFlag;
    }
}

