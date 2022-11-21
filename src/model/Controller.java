package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Controller {
    private ArrayList<User> users;
    private ArrayList<Playlist> playlists;
    private ArrayList<Audio> catalogue;
    private ArrayList<Record> purchases;

    private final Advertisement[] ADS = {
            new Advertisement("Nike", "Just Do It", "picture", 10),
            new Advertisement("Coca-Cola", "Open Happiness", "picture", 10),
            new Advertisement("M&Ms", "Melts in your Mouth, not in your hands", "picture", 10),
    };

    public Controller() {
        this.users = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.purchases = new ArrayList<>();
        this.catalogue = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<Audio> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(ArrayList<Audio> catalogue) {
        this.catalogue = catalogue;
    }

    public ArrayList<Record> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Record> purchases) {
        this.purchases = purchases;
    }

    public Advertisement[] getADS() {
        return ADS;
    }

    // REGISTRATION METHODS

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new content creator to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param name <strong>String</strong> new content creator name
     * @param pictureUrl <strong>String</strong> picture that represents the content creator
     * @return status <strong>boolean</strong> It will be false if the content creator is already in the collection
     * </pre>
     */
    public boolean registerContentCreator(String name, String pictureUrl) {
        if(searchUser(name) != null) return false;
        return users.add(new ContentCreator(name, pictureUrl));
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new artist to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param name <strong>String</strong> new artist name
     * @param pictureUrl <strong>String</strong> picture that represents the artist
     * @return status <strong>boolean</strong> It will be false if the artist is already in the collection
     * </pre>
     */
    public boolean registerArtist(String name, String pictureUrl) {
        if(searchUser(name) != null) return false;
        return users.add(new Artist(name, pictureUrl));
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new standard consumer to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param nickname <strong>String</strong> new artist name
     * @param documentId <strong>String</strong> new user's document
     * @return status <strong>boolean</strong> It will be false if the standard consumer is already in the collection
     * </pre>
     */
    public boolean registerStandardConsumer(String nickname, String documentId) {
        if(searchUser(nickname) != null) return false;
        return users.add(new StandardConsumer(nickname, documentId));
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new premium consumer to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param nickname <strong>String</strong> new artist name
     * @param documentId <strong>String</strong> new user's document
     * @return status <strong>boolean</strong> It will be false if the premium consumer is already in the collection
     * </pre>
     */
    public boolean registerPremiumConsumer(String nickname, String documentId) {
        if(searchUser(nickname) != null) return false;
        return users.add(new PremiumConsumer(nickname, documentId));
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new song to the artist's songs collection and in the general catalogue, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one producer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new song in it
     * <strong>pos: </strong> catalogue <strong>ArrayList</strong> modified with a new song in it
     * @param songName <strong>String</strong> name of the new song
     * @param pictureUrl <strong>String</strong> url that represents the new song
     * @param duration <strong>String</strong> duration written in "MM:SS"
     * @param genre <strong>int</strong> genre number 1.rock, 2.pop, 3.trap, 4.house
     * @param album <strong>String</strong> name of the album that contains the song
     * @param saleValue <strong>double</strong> sale value of the song
     * @param artistName <strong>String</strong> name of the producer to which the song is going to be added.
     * @return status <strong>boolean</strong> It will be false if the producer is not found or the audio is already in the list
     * </pre>
     */
    public boolean registerSong(String songName, String pictureUrl, int duration, int genre, String album, double saleValue, String artistName) {
        User foundProducer = searchUser(artistName);
        if(!(foundProducer instanceof Artist)) return false;

        Song newSong = new Song(artistName, songName, pictureUrl, duration, genre, album, saleValue);

        if(searchAudio(songName) != null) return false;

        if(((Artist)foundProducer).addAudio(newSong)) {
            return catalogue.add(newSong);
        } else {
            return false;
        }
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new podcast to the content creator's podcasts collection and in the general catalogue, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one producer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> foundProducer <strong>Producer</strong> modified with a new podcast in its list
     * <strong>pos: </strong> catalogue <strong>ArrayList</strong> modified with a new podcast in it
     * @param podcastName <strong>String</strong> name of the new podcast
     * @param pictureUrl <strong>String</strong> url that represents the new podcast
     * @param duration <strong>String</strong> duration written in "HH:MM:SS"
     * @param category <strong>int</strong> category number 1.politics, 2.entertainment, 3.videoGames, 4.fashion
     * @param description <strong>String</strong> description of the podcast
     * @param contentCreatorName <strong>String</strong> name of the producer to which the podcast is going to be added.
     * @return status <strong>boolean</strong> It will be false if the producer is not found or the audio is already in the list
     * </pre>
     */
    public boolean registerPodcast(String podcastName, String pictureUrl, int duration, int category, String description, String contentCreatorName) {
        User foundProducer = searchUser(contentCreatorName);
        if(!(foundProducer instanceof ContentCreator) ) return false;

        Podcast newPodcast = new Podcast(contentCreatorName, podcastName, pictureUrl, duration, category, description);

        if(searchAudio(podcastName) != null) return false;

        if(((ContentCreator)foundProducer).addAudio(newPodcast)) {
            return catalogue.add(newPodcast);
        } else {
            return false;
        }
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new playlist to the consumer's playlists collection and in the general list, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized and have at least one audio
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> foundConsumer <strong>Producer</strong> modified with a new playlist in its list
     * <strong>pos: </strong> playlists <strong>ArrayList</strong> modified with a new playlist in it
     * @param consumerName <strong>String</strong> name of the consumer to which the playlist is going to be added.
     * @param name <strong>String</strong> name of the new playlist
     * @return status <strong>boolean</strong> It will be false if the consumer is not found or the consumer playlists list is full
     * </pre>
     */
    public boolean registerPlaylist(String consumerName, String name) {
        Playlist newPlaylist = new Playlist(name);

        User tmpConsumer = searchUser(consumerName);

        if(!(tmpConsumer instanceof Consumer) ) return false;

        if(((Consumer)tmpConsumer).addPlaylist(newPlaylist)) {
            return playlists.add(newPlaylist);
        } else {
            return false;
        }
    }

    // PLAYLIST METHODS

    /**
     * <pre>
     * <strong>Description:</strong> It allows to change the name of an existing playlist
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized and have at least one audio
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized with at least one playlist
     * <strong>pos: </strong> tmpPlaylist <strong>Playlist</strong> will be renamed
     * @param consumerName <strong>String</strong> name of the consumer that owns the playlist.
     * @param playlistId <strong>String</strong> playlist unique identifier
     * @param newName <strong>new name for the playlist</strong>
     * @return status <strong>boolean</strong> It will be false if the playlist or consumer are not found
     * </pre>
     */
    public boolean renamePlaylist(String consumerName, String playlistId, String newName) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return false;
        Playlist tmpPlaylist = tmpConsumer.searchPlaylist(playlistId);
        if(tmpPlaylist == null) return false;

        tmpPlaylist.setName(newName);
        return true;
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to add or remove a song from a selected playlist
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized and have at least one audio
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized with at least one playlist
     * <strong>pos: </strong> tmpPlaylist <strong>Playlist</strong> will be modified with a song added or removed from its list
     * @param consumerName <strong>String</strong> name of the consumer that owns the playlist.
     * @param playlistId <strong>String</strong> playlist unique identifier
     * @param operation <strong>int</strong> Operation to be performed: 1.adding, 2.removing
     * @param audioName <strong>String</strong> name of the audio that is going to be added.
     * @return status <strong>boolean</strong> It will be false if the producer is not found or the audio is already in the list
     * </pre>
     */
    public boolean addOrRemovePlaylistAudio(int operation, String consumerName, String audioName, String playlistId) {
        User tmpConsumer = searchUser(consumerName);
        if(!(tmpConsumer instanceof Consumer)) return false;
        Playlist tmpPlaylist = ((Consumer)tmpConsumer).searchPlaylist(playlistId);
        Audio tmpAudio = searchAudio(audioName);
        if(tmpAudio == null || tmpPlaylist == null) return false;
        if(operation == 1) {
            return tmpPlaylist.addAudio(tmpAudio);
        } else {
            return tmpPlaylist.removeAudio(tmpAudio);
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> It shows the id and matrix that generated the
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized and have at least one audio
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized with at least one playlist
     * <strong>pos: </strong> tmpPlaylist <strong>Playlist</strong> will be modified with a song added or removed from its list
     * @param consumerName <strong>String</strong> name of the consumer that owns the playlist.
     * @param playlistId <strong>String</strong> playlist unique identifier
     * @return tmpPlaylist <strong>String</strong> The written version of the playlist, that includes the id and matrix
     * </pre>
     */
    public String sharePlaylist(String consumerName, String playlistId) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "\nUser not found";
        Playlist tmpPlaylist = searchPlaylist(playlistId);
        if(tmpPlaylist == null) return "\nUser does not have the playlist";
        tmpPlaylist.updateId();
        return "\n" + tmpPlaylist;
    }

    // SHOWING METHODS

    /**
     * <pre>
     * <strong>Description:</strong> Prints the allowed genres of the platform
     * @return songGenres <strong>String</strong> a readable list of genres
     * </pre>
     */
    public String showSongGenres() {
        String genresList = "";
        for(int i=0; i<SongGenre.values().length; i++) {
            genresList += (i+1) + "." + SongGenre.values()[i].toString().toLowerCase() + ( (i<SongGenre.values().length-1) ? (", ") : (""));
        }
        return genresList;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the allowed podcast categories of the platform
     * @return podcastCategories <strong>String</strong> a readable list of podcast categories
     * </pre>
     */
    public String showPodcastCategories() {
        String categoriesList = "";
        for(int i=0; i<PodcastCategory.values().length; i++) {
            categoriesList += (i+1) + "." + PodcastCategory.values()[i].toString().toLowerCase() + ( (i<PodcastCategory.values().length-1) ? (", ") : (""));
        }
        return categoriesList;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of users of the platform. An empty text if there are none.
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized.
     * @param type <strong>int</strong> User type: 0.All, 1.OnlyProducers, 2.OnlyConsumers
     * @return userList <strong>String</strong> a readable list of users
     * </pre>
     */
    public String showUsers(int type) {
        String userList = "";
        for(User user : users) {
            switch (type) {
                case 0 -> userList += "\n - " + user;
                case 1 -> {
                    if(user instanceof Producer) {
                        userList += "\n - " + user;
                    }
                }
                case 2 -> {
                    if(user instanceof Consumer) {
                        userList += "\n - " + user;
                    }
                }
            }
        }
        return (userList.equals("")) ? ("") : ((type == 1) ? ("\n--- Producer list ---" + userList) : ("\n--- Consumer list ---" + userList));
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of audios of the platform, that is, the catalogue. An empty text if there are not any audio.
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized.
     * @param type <strong>int</strong> The type of audios to be displayed (0.all, 1.songs, 2.podcasts)
     * @return catalogueList <strong>String</strong> a readable list of audios
     * </pre>
     */
    public String showCatalogue(int type) {
        String catalogueList = "";

        for(Audio audio : catalogue) {
            switch (type) {
                case 0 -> catalogueList += "\n - " + audio;
                case 1 -> {
                    if(audio instanceof Song) {
                        catalogueList += "\n - " + audio;
                    }
                }
                case 2 -> {
                    if(audio instanceof Podcast) {
                        catalogueList += "\n - " + audio;
                    }
                }
            }
        }
        return ( catalogueList.equals("") ) ? ("") : ("\n--- Current Catalogue --- " ) + catalogueList;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of playlist that a selected consumer has.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized and have at least one playlist
     * @param consumerName <strong>String</strong> The name of the consumer to be searched
     * @return consumerPlaylists <strong>String</strong> a readable list of playlists of the consumer, it will be an empty String if the consumer is not found
     * </pre>
     */
    public String showConsumerPlaylists(String consumerName) {
        User matchedConsumer = searchUser(consumerName);
        if(!(matchedConsumer instanceof Consumer)) return "\nConsumer not found";

        return ((Consumer)matchedConsumer).showPlaylists();
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of purchases, either in the whole platform or for a specified user.
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized.
     * <strong>Pre: </strong> purchases <strong>ArrayList</strong> Must be initialized.
     * @param consumerName <strong>int</strong> the name of the consumer if specified, else it will be an empty String.
     * @return purchasesList <strong>String</strong> a readable list of purchases
     * </pre>
     */
    public String showPurchases(String consumerName) {
        String purchasesList = "";
        for(Record purchase : purchases) {
            if(consumerName.equals("") || purchase.getConsumerName().equals(consumerName)) {
                purchasesList += "\n - " + purchase;
            }
        }
        if(purchasesList.equals("")) return purchasesList;

        return ("\n--- Purchase history --- " ) + purchasesList;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of purchases for a specified user.
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized and have at least one consumer
     * @param consumerName <strong>int</strong> the name of the consumer to search.
     * @return playbackList <strong>String</strong> a readable list of consumer's playbacks
     * </pre>
     */
    public String showConsumerPlaybacks(String consumerName) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "Consumer not found";
        return tmpConsumer.showPlaybacks();
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of audios that a selected playlist has
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized and have at least one consumer
     * @param id <strong>String</strong> the playlist unique identifier
     * @return audioList <strong>String</strong> a readable list of the playlist's audios
     * </pre>
     */
    public String showPlaylistAudios(String id) {
        Playlist tmpPlaylist = searchPlaylist(id);
        return tmpPlaylist.showAudios();
    }

    // SEARCHING METHODS

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the found user based on its name
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * @param userName <strong>String</strong> name if it is producer, or nickname if it is consumer
     * @return foundUser <strong>User</strong> the user that match the name or nickname
     * </pre>
     */
    public User searchUser(String userName) {
        for(User user : users) {
            if(user instanceof Consumer && ((Consumer)user).getNickname().equalsIgnoreCase(userName)) {
                return user;
            } else if(user instanceof Producer && ((Producer) user).getName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the found playlist based on its id
     * <strong>pre:</strong> playlists <strong>ArrayList</strong> must be initialized
     * @param id <strong>String</strong> playlist unique identifier
     * @return foundPlaylist <strong>Playlist</strong> the playlist for which its id matches with the given
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
     * <strong>Description:</strong> It searches and returns the found audio based on its name
     * <strong>pre:</strong> catalogue <strong>ArrayList</strong> must be initialized
     * @param audioName <strong>String</strong> target audio name
     * @return foundAudio <strong>Audio</strong> the audio that matches with the name
     * </pre>
     */
    public Audio searchAudio(String audioName) {
        for(Audio audio : catalogue) {
            if(audio.getName().equalsIgnoreCase(audioName)) return audio;
        }
        return null;
    }

    // AUDIO METHODS

    /**
     * <pre>
     * <strong>Description: </strong> it selects an audio from the list and returns the playback of the audio and an AD if necessary
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> ADS <strong>Advertisement[]</strong> Must be initialized
     * <strong>Pos: </strong> tmpAudio <strong>Audio</strong> will be modified with one more play in it
     * @param consumerName <strong>String</strong> the name of the consumer to search
     * @param audioName <strong>String</strong> the name of the audio to search
     * @return queue <strong>String[]</strong> the list of the audios to be played
     * </pre>
     */
    public String[] playAudio(String consumerName, String audioName) {
        String[] queue = new String[2];
        Random rnd = new Random();
        Advertisement advertisement = ADS[rnd.nextInt(ADS.length-1)];

        User tmpConsumer = searchUser(consumerName);

        Audio tmpAudio = searchAudio(audioName);
        if(!(tmpConsumer instanceof Consumer) || tmpAudio == null) return new String[]{"", ""};

        queue[1] = tmpAudio.play(); queue[0] = "";
        if(tmpConsumer instanceof Advertisable) {
            if(tmpAudio instanceof Podcast || ( (tmpAudio instanceof Song) && ((Advertisable)tmpConsumer).showAdvertise()) ) {
                queue[0] = advertisement.play();
            }
        }
        ((Consumer)tmpConsumer).playAudio(tmpAudio);
        return queue;
    }

    /**
     * <pre>
     * <strong>Description:</strong> It searches a song and add it to the consumer's song collection
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> catalogue <strong>ArrayList</strong> must be initialized and have at least one audi
     * <strong>pos: </strong> songs <strong>Playlist</strong> will be modified with a song added
     * @param consumerName <strong>String</strong> name of the consumer to search.
     * @param songName <strong>String</strong> name of the song that is going to be added.
     * @return status <strong>boolean</strong> It will be false if the audio or consumer are not found, or the consumer's song list is full
     * </pre>
     */
    public boolean buySong(String consumerName, String songName) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        Audio tmpAudio = searchAudio(songName);
        if(tmpConsumer == null) return false;
        if(!(tmpAudio instanceof Song)) return false;

        if(tmpConsumer.addSong((Song)tmpAudio)) {
            return purchases.add(new Record(tmpConsumer.getNickname(), tmpAudio));
        } else {
            return false;
        }
    }

    // STATISTICS

    /**
     * <pre>
     * <strong>Description:</strong> It calculates the most played song genre for a specified consumer
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @param consumerName <strong>String</strong> the name of the consumer to search
     * @return stats <strong>String</strong> the most played song genre with the number of plays
     * </pre>
     */
    public String mostPlayedSongGenre(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "\n User not found";

        int[] maxSong = tmpConsumer.mostPlayedSongGenre();

        statistics += String.format("\nFor user %s: %nMost played song genre: %s (%d plays)", tmpConsumer.getNickname(), SongGenre.values()[maxSong[0]], maxSong[1]);
        return statistics;
    }

    /**
     * <pre>
     * <strong>Description:</strong> It calculates the most played podcast category for a specified consumer
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @param consumerName <strong>String</strong> the name of the consumer to search
     * @return stats <strong>String</strong> the most played podcast category with the number of plays
     * </pre>
     */
    public String mostPlayedPodcastCategory(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "\nUser not found";

        int[] maxPodcast = tmpConsumer.mostPlayedPodcastCategory();

        statistics += String.format("\nFor user %s: %nMost played podcast category: %s (%d plays)", tmpConsumer.getNickname(), PodcastCategory.values()[maxPodcast[0]], maxPodcast[1]);
        return statistics;
    }

    /**
     * <pre>
     * <strong>Description:</strong> It calculates the most played song genre for the whole platform
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return stats <strong>String</strong> the most played podcast category with the number of plays
     * </pre>
     */
    public String mostPlayedSongGenre() {
        int[] playsPerSongGenre = calculateTotalPlaysPerType()[0];
        int maxGenre = 0;
        for(int i=0; i<playsPerSongGenre.length; i++) {
            if(playsPerSongGenre[i] > playsPerSongGenre[maxGenre]) {
                maxGenre = i;
            }
        }
        if(playsPerSongGenre[maxGenre] > 0) {
            return String.format("\nMost played Song genre: %s %nNumber of plays : %d", SongGenre.values()[maxGenre], playsPerSongGenre[maxGenre]);
        } else {
            return "\nThere must be at least one play to calculate most played genre";
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> It calculates the most played podcast category for the whole platform
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return stats <strong>String</strong> the most played podcast category with the number of plays
     * </pre>
     */
    public String mostPlayedPodcastCategory() {
        int[] playsPerPodcastCategory = calculateTotalPlaysPerType()[1];
        int maxCategory = 0;
        for(int i=0; i<playsPerPodcastCategory.length; i++) {
            if(playsPerPodcastCategory[i] > playsPerPodcastCategory[maxCategory]) {
                maxCategory = i;
            }
        }
        if(playsPerPodcastCategory[maxCategory] > 0) {
            return String.format("\nMost played Podcast category: %s %nNumber of plays : %d", PodcastCategory.values()[maxCategory], playsPerPodcastCategory[maxCategory]);
        } else {
            return "\nThere must be at least one play to calculate most played category";
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> Counts the number of each audio type, podcast and song
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return totalPlaysPerType <strong>int[][]</strong> Bi-dimensional array, 0 represents songs and 1 podcasts
     * </pre>
     */
    public int[][] calculateTotalPlaysPerType() {
        int[] playsPerSongGenre = new int[SongGenre.values().length];
        int[] playsPerPodcastCategory = new int[PodcastCategory.values().length];


        return new int[][]{playsPerSongGenre, playsPerPodcastCategory};
    }

    /**
     * <pre>
     * <strong>Description:</strong> Shows the total of plays for each audio type, podcast and song
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return stats <strong>String</strong> Total of plays of each audio type
     * </pre>
     */
    public String showTotalPlaysPerType() {
        // initialization
        String statistics = "";
        int[] playsPerSongGenre = calculateTotalPlaysPerType()[0];
        int[] playsPerPodcastCategory = calculateTotalPlaysPerType()[1];
        // counting
        for(Audio audio : catalogue) {
            if(audio instanceof Song) {
                for(int i=0; i<SongGenre.values().length; i++) {
                    if(((Song)audio).getGenre().equals(SongGenre.values()[i])) playsPerSongGenre[i] += audio.getNumberOfPlays();
                }
            } else if(audio instanceof Podcast) {
                for(int i=0; i<PodcastCategory.values().length; i++) {
                    if (((Podcast) audio).getCategory().equals(PodcastCategory.values()[i])) playsPerPodcastCategory[i] += audio.getNumberOfPlays();
                }
            }
        }
        // format
        statistics += "\nTotal plays per Song Genre";
        for(int i=0; i<playsPerSongGenre.length; i++) {
            statistics += String.format("%n - %s: %d", SongGenre.values()[i], playsPerSongGenre[i]);
        }
        statistics += "\nTotal plays per Podcast Category";
        for(int i=0; i<playsPerPodcastCategory.length; i++) {
            statistics += String.format("%n - %s: %d", PodcastCategory.values()[i], playsPerPodcastCategory[i]);
        }
        return statistics;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Generates a copy of the users collection, including only producers and sorting it
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * @return producers <strong>ArrayList</strong> Sorted producers array
     * </pre>
     */
    public ArrayList<Producer> sortProducers() {
        ArrayList<Producer> producers = new ArrayList<>();
        for(User user : users) {
            if(user instanceof Producer) producers.add((Producer)user);
        }

        for(int i = 0; i< producers.size(); i++) {
            for(int j = 1; j< producers.size()-i; j++) {
                if((producers.get(j-1)).calculateTotalPlays() < (producers.get(j)).calculateTotalPlays()) {
                    Collections.swap(producers, j, j-1);
                }
            }
        }

        return producers;
    }

    /**
     * <pre>
     * <strong>Description:</strong> Sorts the current catalogue based on the number of plays
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * <strong>Pos: </strong> catalogue <strong>ArrayList</strong> It will be sorted
     * </pre>
     */
    public void sortAudios() {
        for(int i=0; i<catalogue.size(); i++) {
            for(int j=1; j<catalogue.size()-i; j++) {
                if(catalogue.get(j-1).getNumberOfPlays() < catalogue.get(j).getNumberOfPlays()) Collections.swap(catalogue, j, j-1);
            }
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> Shows the producers sorted array from sortProducers method and including a limit
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * @param limit <strong>int</strong> the number of producers to be shown
     * @return stats <strong>String</strong> It will be the top if there are enough producers, otherwise, it will return an error message
     * </pre>
     */
    public String showProducersTop(int limit) {
        String artistTop = "", contentCreatorTop = "";
        int aCount = 0, ccCount = 0;
        ArrayList<Producer> producerTop = sortProducers();
        for(int i=0; (ccCount<limit || aCount<limit) && i<producerTop.size(); i++) {
            if(producerTop.get(i) instanceof Artist && aCount < limit) {
                artistTop += String.format("%n %d. %s (%d plays)", ++aCount, (producerTop.get(i)).getName(), (producerTop.get(i)).calculateTotalPlays());
            }
            if(producerTop.get(i) instanceof ContentCreator && ccCount < limit) {
                contentCreatorTop += String.format("%n %d. %s (%d plays)", ++ccCount, (producerTop.get(i)).getName(), (producerTop.get(i)).calculateTotalPlays());
            }
        }
        if(aCount < limit || ccCount < limit) return String.format("\nThere must be at least %d producers of each type", limit);
        return String.format("\n----- Artists Top %d -----%s%n----- Content Creator Top %d -----%s", limit, artistTop, limit, contentCreatorTop);
    }

    /**
     * <pre>
     * <strong>Description:</strong> Shows the audios sorted array and including a limit
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * <strong>Pre: </strong> users <strong>ArrayList</strong> Must be initialized
     * @param limit <strong>int</strong> the number of producers to be shown
     * @return stats <strong>String</strong> It will be the top if there are enough producers, otherwise, it will return an error message
     * </pre>
     */
    public String showAudiosTop(int limit) {
        sortAudios();
        int sCount = 0, pCount = 0;
        String songTop = "", podcastTop = "";
        for(int i=0; (sCount < limit || pCount < limit) && i<catalogue.size(); i++) {
            if(catalogue.get(i) instanceof Song && sCount < limit) {
                Audio song = catalogue.get(i);
                songTop += String.format("%n %d. %s [%s] (%d plays)", ++sCount, song.getName(), ((Song)song).getGenre(), song.getNumberOfPlays());
            }
            if(catalogue.get(i) instanceof Podcast && pCount < limit) {
                Audio podcast = catalogue.get(i);
                podcastTop += String.format("%n %d. %s [%s] (%d plays)", ++pCount, podcast.getName(), ((Podcast)podcast).getCategory(), podcast.getNumberOfPlays());
            }

        }
        if(sCount < limit || pCount < limit) return String.format("\nThere must be at least %d audios of each type", limit);
        return String.format("\n----- Song Top %d -----%s%n----- Podcast Top %d -----%s", limit, songTop, limit, podcastTop);
    }

    /**
     * <pre>
     * <strong>Description:</strong> Shows the total of sales per song genre
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return stats <strong>String</strong> Total of sales for each song genre
     * </pre>
     */
    public String showTotalSalesPerGenre() {
        String totalSales = "";
        int[] salesPerGenre = new int[SongGenre.values().length];
        double[] incomePerGenre = new double[SongGenre.values().length];
        for(Audio song : catalogue) {
            if(song instanceof Song) {
                for(int i=0; i<SongGenre.values().length; i++) {
                    if(((Song) song).getGenre().equals(SongGenre.values()[i])) {
                        salesPerGenre[i] += ((Song) song).getNumberOfSales();
                        incomePerGenre[i] += ((Song) song).calculateTotalIncome();
                    }
                }
            }
        }
        for(int i=0; i<salesPerGenre.length; i++) {
            totalSales += String.format("%n - %s%n   - Sales: %d%n   - Income: $%.2f", SongGenre.values()[i], salesPerGenre[i], incomePerGenre[i]);
        }
        return String.format("\n----- Sales per Genre -----%n%s", totalSales);
    }

    /**
     * <pre>
     * <strong>Description:</strong> Shows the information of the best seller song
     * <strong>Pre: </strong> catalogue <strong>ArrayList</strong> Must be initialized
     * @return stats <strong>String</strong> Best seller information
     * </pre>
     */
    public String bestSellerInformation() {
        Audio bestSeller = null;
        int maxSales = 0;
        for(Audio song : catalogue) {
            if( song instanceof  Song && ((Song)song).getNumberOfSales() > maxSales) {
                bestSeller = song;
                maxSales = ((Song) song).getNumberOfSales();
            }
        }
        if(bestSeller == null) return "\nThere is not best seller yet";
        return String.format("\n----- Best seller information -----%n - Name: %s%n - Sales number: %d%n - Income: $%.2f", bestSeller.getName(), ((Song)bestSeller).getNumberOfSales(), ((Song)bestSeller).calculateTotalIncome());
    }
}