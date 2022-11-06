package model;

import java.util.Arrays;

public class Podcast extends Audio {
    private PodcastCategory category;
    private String description;

    public Podcast(String producerId, String name, String pictureUrl, int duration, int category, String description) {
        super(name, pictureUrl, duration, producerId);
        if(category < 0 || category >= PodcastCategory.values().length) {
            String msg = "Select a category within the range (1 to " + PodcastCategory.values().length + ")";
            throw new IllegalArgumentException(msg);
        } else {
            this.category = PodcastCategory.values()[category];
        }
        this.description = description;
    }

    public PodcastCategory getCategory() {
        return category;
    }

    public void setCategory(PodcastCategory category) { this.category = category; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "name='" + super.getName() + '\'' +
                ", pictureUrl='" + super.getPictureUrl() + '\'' +
                ", numberOfPlays='" + super.getNumberOfPlays() + '\'' +
                ", duration=" + super.getDuration() +
                ", category=" + category +
                '}';
    }
}
