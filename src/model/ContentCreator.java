package model;

public class ContentCreator extends Producer {

    public ContentCreator(String name, String pictureUrl) {
        super(name, pictureUrl);
    }

    @Override
    public void addAudio(Audio newAudio) {
        getAudios().add( (Audio) newAudio );
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
    public Audio searchAudio(String name) {
        for(Audio podcast : getAudios()) {
            if(podcast.getName().equals(name)) return podcast;
        }
        return null;
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
}
