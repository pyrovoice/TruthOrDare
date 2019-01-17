package grazzinisoftwares.truthordare;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    ArrayList<Player> players = new ArrayList<>();
    float level;
    boolean isQuickGame;

    public Game(ArrayList<Player> players, float level, boolean isQuickGame) {
        this.players = players;
        this.level = level;
        this.isQuickGame = isQuickGame;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    // Return players at random given a list of genders
    public ArrayList<Player> getRandomPlayers(ArrayList<Gender> targets, Player player) {

        ArrayList<Player> selectedPlayers = new ArrayList<>();

        //If the list select all players, no need to randomize
        if (targets.size() != players.size())
            Collections.shuffle(players);
        for (Gender g : targets) {
            //Get the next player in the list for the given gender
            if(player != null && !selectedPlayers.contains(player) && (player.gender == g || g == Gender.Any)){
                selectedPlayers.add(player);
                continue;
            }
            for (Player p : players) {
                if (!selectedPlayers.contains(p) && (g == Gender.Any || g == p.gender)) {
                    selectedPlayers.add(p);
                    break;
                }
            }
        }
        return selectedPlayers;
    }

    public boolean isDisplayableCompatible(Challenge d) {
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
