package model;

public class PremiumConsumer extends Consumer {

    public PremiumConsumer(String nickname, String documentId) {
        super(nickname, documentId);
    }

    @Override
    public void addPlaylist(Playlist newPlaylist) {
        if(searchPlaylist(newPlaylist.getId()) != null) throw new IllegalArgumentException("Playlist already in consumer list");
        super.getPlaylists().add(newPlaylist);
    }

    @Override
    public boolean addSong(Song newSong) {
        if(searchSong(newSong.getName()) != null) return false;
        getSongs().add(newSong);
        newSong.increaseNumberOfSales();
        return true;
    }

}
