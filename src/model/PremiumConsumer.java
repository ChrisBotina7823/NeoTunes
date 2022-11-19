package model;

public class PremiumConsumer extends Consumer {

    public PremiumConsumer(String nickname, String documentId) {
        super(nickname, documentId);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new playlist to the consumer's playlists collection, ensuring that it is not already in it, without any other restriction.
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized
     * @param newPlaylist <strong>Playlist</strong> the playlist that is going to be added to the list
     * </pre>
     */
    @Override
    public boolean addPlaylist(Playlist newPlaylist) {
        if(searchPlaylist(newPlaylist.getId()) != null) return false;
        return getPlaylists().add(newPlaylist);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new song to the consumer's songs collection, ensuring that it is not already in it without any other restriction.
     * <strong>pre:</strong> songs <strong>ArrayList</strong> must be initialized
     * @param newSong <strong>Audio</strong> the song that is going to be added to the list
     * @return status <strong>boolean</strong> it is false if the song is already in the consumer's songs collection
     * </pre>
     */
    @Override
    public boolean addSong(Song newSong) {
        if(searchSong(newSong.getName()) != null) return false;
        newSong.increaseNumberOfSales();
        return getSongs().add(newSong);
    }

}
