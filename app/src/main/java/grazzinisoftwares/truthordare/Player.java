package grazzinisoftwares.truthordare;

enum Gender {MALE, FEMALE, ANY}

public class Player {
    public int id;
    public String iconName;
    public Gender gender;

    public Player(int id, String iconName, Gender gender) {
        this.id = id;
        this.iconName = iconName;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
