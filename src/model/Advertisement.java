package model;

public class Advertisement extends Audio {

    public Advertisement(String producerName, String name, String pictureUrl, int duration) {
        super(name, pictureUrl, duration, producerName);
    }
}
