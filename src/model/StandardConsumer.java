package model;

import java.util.ArrayList;

public class StandardConsumer extends Consumer {

    public StandardConsumer(String nickname, String documentId) {
        super(nickname, documentId);
    }



    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(super.getPlaylists().size() > 20 || searchPlaylist(newPlaylist.getId()) != null) return false;
        return super.getPlaylists().add(newPlaylist);
    }

}
