package model;

public class Podcast extends Audio {
    private PodcastCategory category;
    private String description;

    public Podcast(String producerId, String name, String pictureUrl, int duration, int category, String description) {
        super(name, pictureUrl, duration, producerId);
        this.category = PodcastCategory.values()[category];
        this.description = description;
    }

    // GETTERS AND SETTERS

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

    // PLAYING

    /**
     * <pre>
     * <strong>Description: </strong> Prints all podcast information, including parsed duration (hh:mm:ss)
     * @return playbackInformation <strong>String</strong> The screen that will be shown to the user when he plays an audio
     * </pre>
     */
    @Override
    public String play() {
        int hours = getDuration()/3600;
        int minutes = (getDuration()-hours*3600)/60 ;
        int seconds = (getDuration()-hours*3600-minutes*60)%60;
        return String.format("""
                ======== Playing podcast ========
                %s • %s [%s]
                %02d:%02d:%02d left
                About this podcast: %s
                ====== Press enter to exit ======
                """, getName(), getProducerName(), getCategory(), hours, minutes, seconds, getDescription());
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
