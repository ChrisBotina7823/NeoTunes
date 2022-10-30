package model;

public abstract class Producer extends User {
    private String name;
    private String pictureUrl;
    private int NumberOfPlays;
    private int TimePlayed;

    public Producer(String name, String pictureUrl) {
        super();
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.NumberOfPlays = 0;
        this.TimePlayed = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getNumberOfPlays() {
        return NumberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays) {
        NumberOfPlays = numberOfPlays;
    }

    public int getTimePlayed() {
        return TimePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        TimePlayed = timePlayed;
    }

    public abstract String showAudios();

    public abstract void addAudio(Audio newAudio);

    public abstract Audio searchAudio(String name);

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                ", joiningDate='" + super.getJoiningDate() + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", NumberOfPlays=" + NumberOfPlays +
                ", TimePlayed=" + TimePlayed +
                '}';
    }
}
