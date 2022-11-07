package model;

import java.util.ArrayList;

public abstract class Producer extends User {
    private ArrayList<Audio> audios;
    private String name;
    private String pictureUrl;

    public Producer(String name, String pictureUrl) {
        super();
        this.audios = new ArrayList<>();
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    // GETTERS AND SETTERS
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
    public void setAudios(ArrayList<Audio> audios) {
        this.audios = audios;
    }
    public ArrayList<Audio> getAudios() {
        return audios;
    }

    // AUDIO REGISTRATION

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of audios of the artist. An empty text if there are not any audio.
     * <strong>pre: </strong> audios <strong>ArrayList</strong> must be initialized
     * @return audioList <strong>String</strong> a readable list of audios
     * </pre>
     */
    public abstract String showAudios();

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new audio to the artist's audios collection, ensuring that it is not already in it.
     * <strong>pre:</strong> audios <strong>ArrayList</strong> must be initialized
     * @param newAudio <strong>Audio</strong> the audio that is going to be added to the list
     * </pre>
     */
    public abstract void addAudio(Audio newAudio);

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the found audio based on its name
     * <strong>pre:</strong> audios <strong>ArrayList</strong> must be initialized
     * @param name <strong>String</strong> target audio name
     * @return foundAudio <strong>Audio</strong> the audio that matches with the name, song for artists, podcast for content creators
     * </pre>
     */
    public abstract Audio searchAudio(String name);

    // STATISTICS

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
