package model;

import java.util.ArrayList;

public abstract class Producer extends User {
    private ArrayList<Audio> audios;
    private String name;
    private String pictureUrl;

    public Producer(String name, String documentId, String pictureUrl) {
        super(documentId);
        this.audios = new ArrayList<>();
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

    public ArrayList<Audio> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<Audio> audios) {
        this.audios = audios;
    }

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
