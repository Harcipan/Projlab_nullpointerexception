package player;

import map.*;
import entities.*;

public abstract class Player {
    int score;
    int actionPoints;

    protected Player() {
        this.score = 0;
        this.actionPoints = 0;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void pickStartingTile(Tile tile) {
        // Will implement later
    }

    public void calculatePoints() {
        // Will implement later
    }
}
