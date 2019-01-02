package grazzinisoftwares.truthordare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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

    // Return players at random given a list of genders
    public ArrayList<Player> getRandomPlayers(ArrayList<Gender> targets) {

        ArrayList<Player> selectedPlayers = new ArrayList<>();
        //If the list select all players, no need to randomize
        if (targets.size() != players.size())
            Collections.shuffle(players);
        for (Gender g : targets) {
            //Get the next player in the list for the given gender
            for (Player p : players) {
                if (!selectedPlayers.contains(p) && (g == Gender.Any || g == p.gender)) {
                    selectedPlayers.add(p);
                    break;
                }
            }
        }
        return selectedPlayers;
    }

    public ArrayList<Displayable> getRandomDisplayables() {
        ArrayList<Displayable> levelDisplayable = new ArrayList<>();
        for (Displayable d : displayables) {
            if (d.level == this.level && isDisplayableCompatible(d)) {
                levelDisplayable.add(d);
            }
        }
        if (levelDisplayable.size() <= 20) {
            return levelDisplayable;
        }

        Random r = new Random();
        ArrayList<Displayable> selectedDisplayables = new ArrayList<>();
        while (selectedDisplayables.size() < 20) {
            Displayable d = levelDisplayable.get(r.nextInt(levelDisplayable.size() - 1));
            if (!selectedDisplayables.contains(d))
                selectedDisplayables.add(d);
        }
        return selectedDisplayables;
    }

    private boolean isDisplayableCompatible(Displayable d) {
        return d.getNumberMale() <= this.getNumberMale() && d.getNumberFemale() <= this.getNumberFemale() && d.targets.size() <= this.players.size();
    }

    public int getNumberMale() {
        int counter = 0;
        for (Player p : players) {
            if (p.gender == Gender.Boy)
                counter++;
        }
        return counter;
    }

    public int getNumberFemale() {
        int counter = 0;
        for (Player p : players) {
            if (p.gender == Gender.Girl)
                counter++;
        }
        return counter;
    }
}
