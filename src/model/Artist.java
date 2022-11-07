package model;

public class Artist extends Producer {

    public Artist(String name, String pictureUrl) {
        super(name, pictureUrl);
    }


    @Override
    public void addAudio(Audio newAudio) {
        getAudios().add( newAudio );
    }

    @Override
    public Audio searchAudio(String name) {
        for(Audio song : getAudios()) {
            if(song.getName().equals(name)) return song;
        }
        return null;
    }

    @Override
    public String showAudios() {
        String songList = "";

        for(Audio song : getAudios()) {
            songList += "\n - " + song.getName() + " by " + this.getName();
        }
        return songList;
    }

    @Override
    public int getTotalPlays() {
        int totalPlays = 0;
        for(Audio song: getAudios()) {
            totalPlays += song.getNumberOfPlays();
        }
        return totalPlays;
    }

    @Override
    public int getTotalTimePlayed() {
        int totalTimePlayed = 0;
        for(Audio song : getAudios()) {
            totalTimePlayed += song.getDuration();
        }
        return totalTimePlayed;
    }

}
