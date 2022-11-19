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
    public void setPlayedSongs(int playedSongs) {
        this.playedSongs = playedSongs;
    }


    // PLAYLISTS AND SONGS

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new playlist to the consumer's playlists collection, ensuring that it is not already in it and the user does not have more than 20 playlists.
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized
     * @param newPlaylist <strong>Playlist</strong> the playlist that is going to be added to the list
     * @return status <strong>boolean</strong> it returns false if the collection is full or the playlist is already in the collection
     * </pre>
     */
    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(super.getPlaylists().size() >= 20 || searchPlaylist(newPlaylist.getId()) != null) return false;
        return super.getPlaylists().add(newPlaylist);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new song to the consumer's songs collection, ensuring that it is not already in it and the consumer does not have more than 100 songs.
     * <strong>pre:</strong> songs <strong>ArrayList</strong> must be initialized
     * @param newSong <strong>Audio</strong> the song that is going to be added to the list
     * @return status <strong>boolean</strong> it is false if the song is already in the consumer's songs collection or the collection is full
     * </pre>
     */
    @Override
    public boolean addSong(Song newSong) {
        if(super.getPlaylists().size() > 100) return false;
        if(searchSong(newSong.getName()) != null) return false;
        newSong.increaseNumberOfSales();
        return getSongs().add(newSong);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It decides when to show an advertise before a song, each 3 songs it will return true, it also increases the count
     * <strong>Pre: </strong> playedSongs <strong>int</strong> must be initialized
     * <strong>Pos: </strong> playedSongs <strong>int</strong> will be increased by one
     * @return status <strong>boolean</strong> true when showing advertisement.
     * </pre>
     */
    @Override
    public boolean showAdvertise() {
        if(playedSongs % 2 == 0) return true;
        setPlayedSongs(getPlayedSongs()+1);
        return false;
    }
}
