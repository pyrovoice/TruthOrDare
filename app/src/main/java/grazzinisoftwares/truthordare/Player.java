package grazzinisoftwares.truthordare;

enum Gender {Boy, Girl, Any}

public class Player {
    public String iconName;
    public Gender gender;

    public Player( String iconName, Gender gender) {
        this.iconName = iconName;
        this.gender = gender;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
