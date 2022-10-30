package model;

import java.util.ArrayList;

public class Artist extends Producer {
    private ArrayList<Song> songs;

    public Artist(String name, String pictureUrl) {
        super(name, pictureUrl);
        this.songs = new ArrayList<Song>();
    }

    @Override
    public void addAudio(Audio newAudio) {
        songs.add( (Song) newAudio );
    }

    @Override
    public Audio searchAudio(String name) {
        for(Song song : songs) {
            if(song.getName().equals(name)) return song;
        }
        return null;
    }

    @Override
    public String showAudios() {
        String songList = "";

        for(Song song : songs) {
            songList += "\n - " + song.getName() + " by " + this.getName();
        }

        return songList;
    }

}
