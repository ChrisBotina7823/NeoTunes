package model;

public class StandardConsumer extends Consumer implements Advertisable {
    private int playedSongs;
    public StandardConsumer(String nickname, String documentId) {
        super(nickname, documentId);
        this.playedSongs = 0;
    }


    // GETTERS AND SETTERS

    public int getPlayedSongs() {
        return playedSongs;
    }
    public void increasePlayedSongs() {
        this.playedSongs++;
    }
    public void resetPlayedSongs() {
        this.playedSongs = 0;
    }


    // PLAYLISTS AND SONGS

    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(super.getPlaylists().size() >= 20 || searchPlaylist(newPlaylist.getId()) != null) return false;
        return super.getPlaylists().add(newPlaylist);
    }

    @Override
    public boolean addSong(Song newSong) {
        if(super.getPlaylists().size() > 100) return false;
        if(searchSong(newSong.getName()) != null) return false;
        newSong.increaseNumberOfSales();
        return getSongs().add(newSong);
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
