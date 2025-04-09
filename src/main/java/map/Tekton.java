package map;

import java.util.ArrayList;
import java.util.List;

import entities.FungusBody;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;

import static use_cases.UseCase.printWrapper;

public class Tekton {
    int breakChance;
    List<Tile> tiles;
    FungusBody fungusBody;
    Map map;

    public Tekton( Map map) {
        UseCase.replace(this);
        UseCase.printWrapper("Initializing Tekton as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        this.breakChance = 0;
        this.map = map;
        map.addTekton(this);
        tiles = new ArrayList<>();
        fungusBody = null;
        UseCase.printWrapper("Tekton: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Map getMap() {
        return map;
    }

    public boolean hasFungusBody() {
        return fungusBody != null;
    }

    // TODO this is a crude simplification of the actual process
    public ArrayList<Tekton> breakTekton() {

        ArrayList<Tekton> tl = new ArrayList<>();
        printWrapper("Breaking tekton...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);


        int faultLine = faultLine();
        List<Tile> tilesAlongFault = new ArrayList<>();
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
            Tekton t1 = new Tekton(map);
            Tekton t2 = new Tekton(map);
        } else {
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
        }

        // based on some algorithm we break it into two pieces
        UseCase.logger.put(null, "t1");
        tl.add(new Tekton(map));
        UseCase.logger.put(null, "t2");
        tl.add(new Tekton(map));
        for (Tekton tekton : tl) {
            // migrating elements into new tektons
            printWrapper("New tekton " + System.identityHashCode(tekton)
                    + " created and migrated elements based on some algorithm",
                    UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        }

        return tl;
    }

    public void increaseChance(int amount) {
        breakChance += amount;
    }

    /*
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

    /*
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

    /*
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

    /*
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

    /*
     * Finds the width of the tekton
     * @return the width of the tekton
     */
    private int findWidth() {
        return getRightmostTile().getX() - getLeftmostTile().getX() + 1;
    }

    /*
     * Finds the height of the tekton
     * @return the height of the tekton
     */
    private int findHeight() {
        return getBottommostTile().getY() - getTopmostTile().getY() + 1;
    }

    /*
     * Finds the fault line along which the tekton will break
     * Approximate to the center of the tekton, some randomness added
     * if positive, it will break along the y axis
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
        faultLine += (int) (Math.random() * 2) - 1;
        return faultLine;
    }

}

