package player;

import map.*;

public abstract class Player {
    int score;
    int actionPoints;
    public static final int ACTIONS_PER_TICK = 5; // Number of actions a player gets per tick

    protected Player() {
        this.score = 0;
        this.actionPoints = 0;
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

}
