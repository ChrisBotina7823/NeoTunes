package model;

import java.util.ArrayList;

public abstract class Consumer extends User {
    private String nickname;
    private String documentId;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlists;
    private ArrayList<Record> playbacks;

    public Consumer(String nickname, String documentId) {
        this.nickname = nickname;
        this.documentId = documentId;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    // ABSTRACT METHODS

    public abstract boolean addPlaylist(Playlist newPlaylist);
    public abstract boolean addSong(Song newSong);

    // FUNCTIONAL

    public String showPlaylists() {
        String playlistList = "";
        for(Playlist playlist : playlists) {
            playlistList += "\n - " + playlist.getName() + " (id='" + playlist.getId() + "')" + "\n\tSongs: " + playlist.showAudios() ;
        }
        return (playlists.isEmpty()) ? ("") : ("\n--- " + this.nickname + " Playlists ---" + playlistList);
    }

    public String showPlaybacks() {
        String playbackList = "";
        for(Record playback : playbacks) {
            playbackList += "\n - " + playback;
        }
        return (playbacks.isEmpty()) ? ("") : ("\n--- " + this.nickname + " Playbacks ---" + playbackList);
    }

    public Playlist searchPlaylist(String id) {
        for(Playlist playlist : playlists) {
            if(playlist.getId().equals(id)) return playlist;
        }
        return null;
    }

    public Song searchSong(String songName) {
        for(Song song : songs) {
            if(song.getName().equals(songName)) return song;
        }
        return null;
    }

    public void playAudio(Audio tmpAudio) {
        playbacks.add(new Record(this.nickname, tmpAudio));
        tmpAudio.increaseNumberOfPlays();
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "nickname='" + nickname + '\'' +
                ", joiningDate='" + super.getJoiningDate() + '\'' +
                ", documentId='" + documentId + '\'' +
                '}';
    }
}
