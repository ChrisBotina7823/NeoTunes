package model;

import java.util.ArrayList;

public class PremiumConsumer extends Consumer {

    public PremiumConsumer(String nickname, String documentId) {
        super(nickname, documentId);
    }

    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(searchPlaylist(newPlaylist.getId()) != null) return false;
        return super.getPlaylists().add(newPlaylist);
    }

    @Override
    public boolean addSong(Song newSong) {
        if(searchSong(newSong.getName()) != null) return false;
        getSongs().add(newSong);
        newSong.increaseNumberOfSales();
        return true;
    }

}
