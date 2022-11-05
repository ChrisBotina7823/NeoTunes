package model;

public abstract class Producer extends User {
    private String name;
    private String pictureUrl;

    public Producer(String name, String pictureUrl) {
        super();
        this.name = name;
        this.pictureUrl = pictureUrl;
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

    public abstract String showAudios();

    public abstract void addAudio(Audio newAudio);

    public abstract Audio searchAudio(String name);

    public abstract int getTotalPlays();

    public abstract int getTotalTimePlayed();

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                ", joiningDate='" + super.getJoiningDate() + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", NumberOfPlays=" + getTotalPlays() +
                ", TimePlayed=" + getTotalTimePlayed() +
                '}';
    }
}
