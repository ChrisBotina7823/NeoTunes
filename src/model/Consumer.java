package model;

import java.util.ArrayList;

public abstract class Consumer extends User {
    private String documentId;
    private String nickname;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlists;
    private ArrayList<Record> playbacks;

    public Consumer(String nickname, String documentId) {
        super();
        this.documentId = documentId;
        this.nickname = nickname;
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.playbacks = new ArrayList<>();
    }

    // GETTERS AND SETTERS

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    // PLAYLISTS AND SONGS

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new playlist to the consumer's playlists collection, ensuring that it is not already in it.
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized
     * @param newPlaylist <strong>Playlist</strong> the playlist that is going to be added to the list
     * </pre>
     */
    public abstract boolean addPlaylist(Playlist newPlaylist);

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the found playlist based on its id
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized
     * @param id <strong>String</strong> target playlist unique identifier
     * @return foundPlaylist <strong>Audio</strong> the playlist that matches with the id
     * </pre>
     */
    public Playlist searchPlaylist(String id) {
        for(Playlist playlist : playlists) {
            if(playlist.getId().equals(id)) return playlist;
        }
        return null;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of playlists of the artist. An empty text if there are not any playlist.
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized
     * @return playlistList <strong>String</strong> a readable list of playlists
     * </pre>
     */
    public String showPlaylists() {
        String playlistList = "";
        for(Playlist playlist : playlists) {
            playlistList += "\n - " + playlist.getName() + " (id='" + playlist.getId() + "')";
            if(playlist instanceof SongPlaylist) {
                playlistList += " [songs]";
            } else if(playlist instanceof PodcastPlaylist) {
                playlistList += " [podcasts]";
            } else {
                playlistList += " [all]";
            }
            playlistList += "\n\tAudios: " + playlist.showAudios() ;
        }
        return (playlists.isEmpty()) ? ("") : ("\n--- " + this.nickname + " Playlists ---" + playlistList);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new song to the consumer's songs collection, ensuring that it is not already in it.
     * <strong>pre:</strong> songs <strong>ArrayList</strong> must be initialized
     * @param newSong <strong>Audio</strong> the song that is going to be added to the list
     * @return status <strong>boolean</strong> it is false if the song is already in the consumer's songs collection
     * </pre>
     */
    public abstract boolean addSong(Song newSong);

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the found song based on its name
     * <strong>pre:</strong> songs <strong>ArrayList</strong> must be initialized
     * @param songName <strong>String</strong> target song name
     * @return foundSong <strong>Audio</strong> the song that matches with the name
     * </pre>
     */
    public Song searchSong(String songName) {
        for(Song song : songs) {
            if(song.getName().equals(songName)) return song;
        }
        return null;
    }


    // INTERACTION

    public void playAudio(Audio tmpAudio) {
        playbacks.add(new Record(this.nickname, tmpAudio));
        tmpAudio.increaseNumberOfPlays();
    }

    public int[] mostPlayedSongGenre() {
        int[] playsPerSongType = countPlaysPerAudioType()[0];
        int maxGenre = 0;
        for(int i=0; i<playsPerSongType.length; i++) {
            if(playsPerSongType[i] > playsPerSongType[maxGenre]) maxGenre = i;
        }
        return new int[]{maxGenre, playsPerSongType[maxGenre]};
    }
    public int[] mostPlayedPodcastCategory() {
        int[] playsPerPodcastCategory = countPlaysPerAudioType()[1];
        int maxCategory = 0;
        for(int i=0; i<playsPerPodcastCategory.length; i++) {
            if(playsPerPodcastCategory[i] > playsPerPodcastCategory[maxCategory]) maxCategory = i;
        }
        return new int[]{maxCategory, playsPerPodcastCategory[maxCategory]};
    }

    public int[][] countPlaysPerAudioType() {
        int[] playsPerSongGenre = new int[SongGenre.values().length];
        int[] playsPerPodcastCategory = new int[PodcastCategory.values().length];
        for(Record playback : playbacks) {
            if(playback.getAudio() instanceof Song) {
                for(int i=0; i<SongGenre.values().length; i++) {
                    if(((Song) playback.getAudio()).getGenre() == SongGenre.values()[i]) {
                        playsPerSongGenre[i]++;
                    }
                }
            } else if(playback.getAudio() instanceof Podcast) {
                for(int i=0; i<PodcastCategory.values().length; i++) {
                    if(((Podcast) playback.getAudio()).getCategory() == PodcastCategory.values()[i]) {
                        playsPerPodcastCategory[i]++;
                    }
                }
            }

        }
        return new int[][]{playsPerSongGenre, playsPerPodcastCategory};
    }

    public String showPlaybacks() {
        String playbackList = "";
        for(Record playback : playbacks) {
            playbackList += "\n - " + playback;
        }
        return (playbacks.isEmpty()) ? ("") : ("\n--- " + this.nickname + " Playbacks ---" + playbackList);
    }


    @Override
    public String toString() {
        return "Consumer{" +
                "nickname='" + nickname + '\'' +
                ", joiningDate='" + getJoiningDate() + '\'' +
                ", documentId='" + getDocumentId() + '\'' +
                '}';
    }
}
