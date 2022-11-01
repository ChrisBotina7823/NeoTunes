package model;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private ArrayList<Producer> producers;
    private ArrayList<Consumer> consumers;
    private ArrayList<Playlist> playlists;

    private ArrayList<Record> purchases;

    private final Advertisement[] ADS = {
            new Advertisement("Nike - Just Do It", "picture", 10),
            new Advertisement("Coca-Cola - Open Happiness", "picture", 10),
            new Advertisement("M&Ms - Melts in your Mouth, not in your hands", "picture", 10),
    };

    public Controller() {
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.purchases = new ArrayList<>();
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

    public boolean registerSong(String songName, String pictureUrl, int duration, int genre, String album, double saleValue, String artistName) {

        Song newSong = new Song(songName, pictureUrl, duration, genre, album, saleValue);
        Producer foundProducer = searchProducer(artistName);

        if( foundProducer == null || !(foundProducer instanceof Artist) ) return false;

        foundProducer.addAudio(newSong);

        return true;
    }

    public boolean registerPodcast(String podcastName, String pictureUrl, int duration, int category, String description, String contentCreatorName) {

        Podcast newPodcast = new Podcast(podcastName, pictureUrl, duration, category, description);
        Producer foundProducer = searchProducer(contentCreatorName);

        if( foundProducer == null || !(foundProducer instanceof ContentCreator) ) return false;

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
        if(tmpConsumer == null) return false;

        if(tmpConsumer.addPlaylist(newPlaylist)) {
            playlists.add(newPlaylist);
        } else {
            return false;
        }
        return true;
    }

    // PLAYLIST METHODS

    public boolean renamePlaylist(String playlistId, String newName) {
        Playlist tmpPlaylist = searchPlaylist(playlistId);
        if(tmpPlaylist == null) return false;
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
        if(matchedConsumer == null) return "Consumer not found";

        return matchedConsumer.showPlaylists();
    }

    public String showPurchases() {
        String purchasesList = "";
        for(Record purchase : purchases) {
            purchasesList += "\n - " + purchase;
        }
        return (purchasesList.equals("")) ? ("") : ("\n--- Purchase history --- " ) + purchasesList;
    }

    public String showConsumerPlaybacks(String consumerName) {
        Consumer tmpConsumer = searchConsumer(consumerName);
        if(tmpConsumer == null) return "Consumer not found";
        return tmpConsumer.showPlaybacks();
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

    // PLAYING

    public String[] playAudio(String consumerName, String audioName) {
        String[] audioList = new String[2];
        Random rnd = new Random();
        Advertisement advertisement = ADS[rnd.nextInt(ADS.length-1)];

        Consumer tmpConsumer = searchConsumer(consumerName);
        Audio tmpAudio = searchAudio(audioName);
        if(tmpConsumer == null || tmpAudio == null) return audioList;

        tmpConsumer.playAudio(tmpAudio);
        if(tmpConsumer instanceof Advertisable) {
            if(tmpAudio instanceof Podcast || ( (tmpAudio instanceof Song) && ((Advertisable) tmpConsumer).showAdvertise()) ) {
                audioList[0] = String.format("Playing advertisement: %s\nDuration %s", advertisement.getName(), advertisement.getDuration());
            }
        }
        audioList[1] = String.format("Playing advertisement: %s\nDuration %s", tmpAudio.getName(), tmpAudio.getDuration());;
        return audioList;
    }

    // BUYING
    public boolean buySong(String consumerName, String songName) {
        Consumer tmpConsumer = searchConsumer(consumerName);
        Audio tmpAudio = searchAudio(songName);
        if(tmpConsumer == null || tmpAudio == null || !(tmpAudio instanceof Song)) return false;

        if(tmpConsumer.addSong((Song)tmpAudio)) {
            purchases.add(new Record(consumerName, tmpAudio));
        }
        return true;
    }
}
