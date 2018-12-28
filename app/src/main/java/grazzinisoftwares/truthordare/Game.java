package grazzinisoftwares.truthordare;

import java.util.ArrayList;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    int level;
    ArrayList<Displayable> displayables = new ArrayList<>();

    public Game(ArrayList<Player> players, int level, ArrayList<Displayable> displayables) {
        this.players = players;
        this.level = level;
        this.displayables = displayables;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }

    public void setDisplayables(ArrayList<Displayable> displayables) {
        this.displayables = displayables;
    }
}
