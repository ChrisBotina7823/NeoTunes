package model;


import java.util.ArrayList;

public class ContentCreator extends Producer {
    private ArrayList<Podcast> podcasts;

    public ContentCreator(String name, String pictureUrl) {
        super(name, pictureUrl);
        this.podcasts = new ArrayList<>();
    }



    @Override
    public String showAudios() {
        String podcastList = "";

        for(Podcast podcast : podcasts) {
            podcastList += "\n - " + podcast.getName() + " by " + this.getName();
        }

        return podcastList;
    }

    @Override
    public void addAudio(Audio newAudio) {
        podcasts.add( (Podcast) newAudio );
    }


    @Override
    public int getTotalPlays() {
        int totalPlays = 0;
        for(Podcast podcast : podcasts) {
            totalPlays += podcast.getNumberOfPlays();
        }
        return totalPlays;
    }

    @Override
    public int getTotalTimePlayed() {
        int totalTimePlayed = 0;
        for(Podcast podcast : podcasts) {
            totalTimePlayed += podcast.getDuration();
        }
        return totalTimePlayed;
    }

    @Override
    public Audio searchAudio(String name) {
        for(Podcast podcast : podcasts) {
            if(podcast.getName().equals(name)) return podcast;
        }
        return null;
    }
}
