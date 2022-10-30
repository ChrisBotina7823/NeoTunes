package model;

import java.util.ArrayList;

public abstract class Consumer extends User {
    private String nickname;
    private String documentId;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlists;


    public Consumer(String nickname, String documentId) {
        super();
        this.nickname = nickname;
        this.documentId = documentId;
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
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

    // FUNCTIONAL

    public String showPlaylists() {
        String playlistList = "";
        for(Playlist playlist : playlists) {
            playlistList += "\n - " + playlist.getName() + " (id='" + playlist.getId() + "')" + "\n\tSongs: " + playlist.showAudios() ;
        }
        return (playlists.isEmpty()) ? ("") : ("\n--- " + this.nickname + " Playlists ---" + playlistList);
    }

    public Playlist searchPlaylist(String id) {
        for(Playlist playlist : playlists) {
            if(playlist != null && playlist.getId().equals(id)) return playlist;
        }
        return null;
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
