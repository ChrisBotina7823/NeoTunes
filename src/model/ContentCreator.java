package model;

public class ContentCreator extends Producer {

    public ContentCreator(String name, String pictureUrl) {
        super(name, pictureUrl);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new podcast to the content creator's podcast collection
     * <strong>Pre: </strong> audios <strong>ArrayList</strong> Must be initialized
     * <strong>Pos: </strong> audios <strong>ArrayList</strong> Will be modified with a new audio in it
     * @param newAudio <strong>Audio</strong> the audio that is going to be added to the list
     * @return status <strong>boolean</strong> will be true if the audio is added successfully
     * </pre>
     */
    @Override
    public boolean addAudio(Audio newAudio) {
        if(!(newAudio instanceof Podcast)) return false;
        return getAudios().add( newAudio );
    }

    @Override
    public String toString() {
        return "ContentCreator{" +
                "name='" + getName() + '\'' +
                ", joiningDate='" + super.getJoiningDate() + '\'' +
                ", pictureUrl='" + getPictureUrl() + '\'' +
                ", NumberOfPlays=" + calculateTotalPlays() +
                ", TimePlayed=" + calculateTotalTimePlayed() +
                '}';
    }
}
