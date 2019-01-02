package grazzinisoftwares.truthordare;

import java.util.ArrayList;

enum DisplayableType {QUESTION, DARE}

public class Displayable {
    String text;
    int level;
    DisplayableType type;
    ArrayList<Gender> targets;

    public Displayable(String text, int level, DisplayableType type, ArrayList<Gender> targets) {
        this.text = text;
        this.level = level;
        this.type = type;
        this.targets = targets;
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

    public DisplayableType getType() {
        return type;
    }

    public void setType(DisplayableType type) {
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
}
