package model;

import java.util.Arrays;

public abstract class Audio implements Playable {
    private String name;
    private String pictureUrl;
    private int numberOfPlays;
    private int[] duration;

    public Audio(String name, String pictureUrl, int[] duration) {
        this.name = name;
        this.numberOfPlays = 0;
        this.pictureUrl = pictureUrl;
        this.duration = duration;
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
        return numberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

    public int[] getDuration() {
        return duration;
    }

    public void setDuration(int[] duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", numberOfPlays='" + numberOfPlays + '\'' +
                ", duration=" + Arrays.toString(duration) +
                '}';
    }
}
