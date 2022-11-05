package model;

public class StandardConsumer extends Consumer implements Advertisable {

    private int playedSongs;

    public StandardConsumer(String nickname, String documentId) {
        super(nickname, documentId);
        this.playedSongs = 0;
    }

    public int getPlayedSongs() {
        return playedSongs;
    }

    public void increasePlayedSongs() {
        this.playedSongs++;
    }
    public void resetPlayedSongs() {
        this.playedSongs = 0;
    }
    
    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(super.getPlaylists().size() > 20 || searchPlaylist(newPlaylist.getId()) != null) return false;
        return super.getPlaylists().add(newPlaylist);
    }

    @Override
    public boolean addSong(Song newSong) {
        if(super.getPlaylists().size() > 100 || searchSong(newSong.getName()) != null) return false;
        getSongs().add(newSong);
        newSong.increaseNumberOfSales();
        return true;
    }

    @Override
    public boolean showAdvertise() {
        if(playedSongs == 2) {
            resetPlayedSongs();
            return true;
        } else {
            increasePlayedSongs();
        }
        return false;
    }
}
