package player;

import map.*;

public abstract class Player {
    int score;
    int actionPoints;
    private String name;
    public static final int ACTIONS_PER_TICK = 5; // Number of actions a player gets per tick
    public static int playerCounter = 0; // Counter to keep track of player instances

    protected Player() {
        this.score = 0;
        this.actionPoints = 0;
        this.name = "Player " + (++playerCounter); // Default name
    }

    public void tick() {
        actionPoints+= ACTIONS_PER_TICK;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public int getScore() {
        return score;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void pickStartingTile(Tile tile) {
        // Implement in subclasses
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
