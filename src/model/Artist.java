package model;

public class Artist extends Producer {

    public Artist(String name, String pictureUrl) {
        super(name, pictureUrl);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new song to the artist's song collection
     * <strong>Pre: </strong> audios <strong>ArrayList</strong> Must be initialized
     * <strong>Pos: </strong> audios <strong>ArrayList</strong> Will be modified with a new audio in it
     * @param newAudio <strong>Audio</strong> the audio that is going to be added to the list
     * @return status <strong>boolean</strong> will be true if the audio is added successfully
     * </pre>
     */
    @Override
    public boolean addAudio(Audio newAudio) {
        if(!(newAudio instanceof Song)) return false;
        return getAudios().add( newAudio );
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + getName() + '\'' +
                ", joiningDate='" + super.getJoiningDate() + '\'' +
                ", pictureUrl='" + getPictureUrl() + '\'' +
                ", NumberOfPlays=" + calculateTotalPlays() +
                ", TimePlayed=" + calculateTotalTimePlayed() +
                '}';
    }
}
