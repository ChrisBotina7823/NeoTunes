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
}
