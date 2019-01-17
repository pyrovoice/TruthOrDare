package grazzinisoftwares.truthordare;

import java.util.ArrayList;

enum ChallengeType {QUESTION, DARE}

public class Challenge {
    String text;
    int level;
    ChallengeType type;
    ArrayList<Gender> targets;
    int fontSize;
    boolean isUserWritten;
    private final int DEFAULT_FONT_SIZE = 24;


    public Challenge(String text, int level, ChallengeType type, ArrayList<Gender> targets, int fontSize, boolean isUserWritten) {
        this.text = text;
        this.level = level;
        this.type = type;
        this.targets = targets;
        this.fontSize = fontSize;
        this.isUserWritten = isUserWritten;
    }

    public Challenge() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ChallengeType getType() {
        return type;
    }

    public void setType(ChallengeType type) {
        this.type = type;
    }

    public ArrayList<Gender> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Gender> targets) {
        this.targets = targets;
    }

    public int getNumberMale() {
        int counter = 0;
        for(Gender g : targets){
            if(g == Gender.Boy)
                counter++;
        }
        return counter;
    }

    public int getNumberFemale() {
        int counter = 0;
        for(Gender g : targets){
            if(g == Gender.Girl)
                counter++;
        }
        return counter;
    }

    public int getFontSize() {
        return this.fontSize > 0 ? this.fontSize : DEFAULT_FONT_SIZE;
    }
}
