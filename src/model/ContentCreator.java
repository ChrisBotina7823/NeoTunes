package model;

public class ContentCreator extends Producer {

    public ContentCreator(String name, String documentId, String pictureUrl) {
        super(name, documentId, pictureUrl);
    }



    @Override
    public String showAudios() {
        String podcastList = "";

        for(Audio podcast : getAudios()) {
            podcastList += "\n - " + podcast.getName() + " by " + this.getName();
        }

        return podcastList;
    }

    @Override
    public void addAudio(Audio newAudio) {
        getAudios().add( (Audio) newAudio );
    }


    @Override
    public int getTotalPlays() {
        int totalPlays = 0;
        for(Audio podcast : getAudios()) {
            totalPlays += podcast.getNumberOfPlays();
        }
        return totalPlays;
    }

    @Override
    public int getTotalTimePlayed() {
        int totalTimePlayed = 0;
        for(Audio podcast : getAudios()) {
            totalTimePlayed += podcast.getDuration();
        }
        return totalTimePlayed;
    }

    @Override
    public Audio searchAudio(String name) {
        for(Audio podcast : getAudios()) {
            if(podcast.getName().equals(name)) return podcast;
        }
        return null;
    }
}
