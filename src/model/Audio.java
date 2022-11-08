package model;

public abstract class Audio {
    private String name;
    private String pictureUrl;
    private int numberOfPlays;
    private int duration;

    private String producerName;

    public Audio(String name, String pictureUrl, int duration, String producerName) {
        this.name = name;
        this.numberOfPlays = 0;
        this.pictureUrl = pictureUrl;
        if(duration > 0) {
            this.duration = duration;
        } else {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.producerName = producerName;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerId) {
        this.producerName = producerId;
    }

    public void increaseNumberOfPlays() {
        this.numberOfPlays++;
    }
    @Override
    public String toString() {
        return "Audio{" +
                "name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", numberOfPlays='" + numberOfPlays + '\'' +
                ", duration=" + duration +
                '}';
    }
}
