package model;

public class PremiumConsumer extends Consumer {

    public PremiumConsumer(String nickname, String documentId) {
        super(nickname, documentId);
    }

    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(searchPlaylist(newPlaylist.getId()) != null) return false;
        return getPlaylists().add(newPlaylist);
    }

    @Override
    public boolean addSong(Song newSong) {
        if(searchSong(newSong.getName()) != null) return false;
        newSong.increaseNumberOfSales();
        return getSongs().add(newSong);
    }

}
