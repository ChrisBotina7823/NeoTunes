package model;

import javax.naming.InvalidNameException;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Producer> producers;
    private ArrayList<Consumer> consumers;
    private ArrayList<Playlist> playlists;

    public Controller() {
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    // REGISTRATION METHODS

    public boolean registerProducer(int producerType, String name, String pictureUrl) {
        switch(producerType) {
            case 1:
                Artist newArtist = new Artist(name, pictureUrl);
                producers.add(newArtist);
                break;
            case 2:
                ContentCreator newContentCreator = new ContentCreator(name, pictureUrl);
                producers.add(newContentCreator);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean registerConsumer(int consumerType, String nickname, String documentId) {
        switch (consumerType) {
            case 1:
                StandardConsumer newStandardConsumer = new StandardConsumer(nickname, documentId);
                consumers.add(newStandardConsumer);
                break;
            case 2:
                PremiumConsumer newPremiumConsumer = new PremiumConsumer(nickname, documentId);
                consumers.add(newPremiumConsumer);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean registerSong(String songName, String pictureUrl, int[] duration, int genre, String album, double saleValue, String artistName) {

        Song newSong = new Song(songName, pictureUrl, duration, genre, album, saleValue);
        Producer foundProducer = searchProducer(artistName);

        if( foundProducer == null || !(foundProducer instanceof Artist) ) throw new IllegalArgumentException("Error: Producer not found");

        foundProducer.addAudio(newSong);

        return true;
    }

    public boolean registerPodcast(String podcastName, String pictureUrl, int[] duration, int category, String description, String contentCreatorName) {

        Podcast newPodcast = new Podcast(podcastName, pictureUrl, duration, category, description);
        Producer foundProducer = searchProducer(contentCreatorName);

        if( foundProducer == null || !(foundProducer instanceof ContentCreator) ) throw new IllegalArgumentException("Error: Producer not found");

        foundProducer.addAudio(newPodcast);

        return true;
    }

    public boolean registerPlaylist(String consumerName, int type, String name) {
        Playlist newPlaylist = null;
        switch (type) {
            case 1:
                newPlaylist = new Playlist(name);
                break;
            case 2:
                newPlaylist = new PodcastPlaylist(name);
                break;
            case 3:
                newPlaylist = new SongPlaylist(name);
                break;
        }

        Consumer tmpConsumer = searchConsumer(consumerName);
        if(tmpConsumer == null) throw new IllegalArgumentException("Error: Consumer not found");

        if(tmpConsumer.addPlaylist(newPlaylist)) {
            playlists.add(newPlaylist);
        } else {
            throw new IllegalArgumentException("Error: Playlist was not added to the consumer list");
        }
        return true;
    }

    // PLAYLIST METHODS

    public boolean renamePlaylist(String playlistId, String newName) {
        Playlist tmpPlaylist = searchPlaylist(playlistId);
        if(tmpPlaylist == null) throw new IllegalArgumentException("Error: Playlist not found");
        tmpPlaylist.setName(newName);
        return true;
    }

    public boolean addOrRemoveAudioToPlaylist(int operation, String consumerName, String audioName, String playlistId) {
        Consumer tmpConsumer = searchConsumer(consumerName);
        if(tmpConsumer == null) return false;
        Playlist tmpPlaylist = tmpConsumer.searchPlaylist(playlistId);
        if(tmpPlaylist == null) return false;
        Audio tmpAudio = searchAudio(audioName);
        if(tmpAudio == null) return false;
        return (operation == 1) ? tmpPlaylist.addAudio(tmpAudio) : (tmpPlaylist.removeAudio(tmpAudio));
    }

    public String sharePlaylist(String consumerName, String playlistId) {
        Consumer tmpConsumer = searchConsumer(consumerName);
        if(tmpConsumer == null) return "User not found";
        Playlist tmpPlaylist = searchPlaylist(playlistId);
        if(tmpPlaylist == null) return "User does not have the playlist";
        return "\n" + tmpPlaylist.toString();
    }

    // SHOWING METHODS

    public String showCatalogue() {
        String catalogue = "";

        for(Producer producer : producers) {
            if(producer.showAudios().equals("")) continue;
            if(producer instanceof Artist) catalogue += producer.showAudios();
            if(producer instanceof ContentCreator) catalogue += producer.showAudios();
        }
        return ( catalogue.equals("") ) ? ("") : ("\n--- Current Catalogue --- " ) + catalogue;
    }

    public String showUserInformation(int userType, String name) {
        switch (userType) {
            case 1:
                Producer foundProducer = searchProducer(name);
                return (foundProducer != null) ? foundProducer.toString() : "Producer not found";
            case 2:
                Consumer foundConsumer = searchConsumer(name);
                return (foundConsumer != null) ? foundConsumer.toString() : "Consumer not found";
            default:
                return "";
        }
    }

    public String showConsumerPlaylists(String consumerName) {
        Consumer matchedConsumer = searchConsumer(consumerName);
        if(matchedConsumer == null) return "";

        return matchedConsumer.showPlaylists();
    }

    // SEARCHING METHODS

    public Producer searchProducer(String name) {
        for(Producer producer : producers) {
            if(producer.getName().equals(name)) return producer;
        }
        return null;
    }

    public Consumer searchConsumer(String name) {
        for(Consumer consumer : consumers) {
            if(consumer.getNickname().equals(name)) return consumer;
        }
        return null;
    }

    public Playlist searchPlaylist(String id) {
        for(Playlist playlist : playlists) {
            if(playlist.getId().equals(id)) return playlist;
        }
        return null;
    }

    public Audio searchAudio(String audioName) {
        for(Producer producer : producers) {
            Audio tmpAudio = producer.searchAudio(audioName);
            if(tmpAudio != null) return tmpAudio;
        }
        return null;
    }
}
