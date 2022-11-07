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
        this.users = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.purchases = new ArrayList<>();
        this.catalogue = new ArrayList<>();
    }

    // REGISTRATION METHODS


    /**
     * <pre>
     * <strong>Description: </strong> It adds a new content creator to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param name new content creator name
     * @param pictureUrl picture that represents the artist
     * </pre>
     */
    public void registerContentCreator(String name, String pictureUrl) {
        if(searchUser(name) != null) throw new IllegalArgumentException("Content creator is already in list");
        ContentCreator newContentCreator = new ContentCreator(name, pictureUrl);
        users.add(newContentCreator);
    }


    /**
     * <pre>
     * <strong>Description: </strong> It adds a new artist to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param name new artist name
     * @param pictureUrl picture that represents the artist
     * </pre>
     */
    public void registerArtist(String name, String pictureUrl) {
        if(searchUser(name) != null) throw new IllegalArgumentException("Artist is already in list");
        Artist newArtist = new Artist(name, pictureUrl);
        users.add(newArtist);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new standard consumer to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param nickname new artist name
     * @param documentId new user's document
     * </pre>
     */
    public void registerStandardConsumer(String nickname, String documentId) {
        if(searchUser(nickname) != null) throw new IllegalArgumentException("Consumer is already in list");
        StandardConsumer newStandardConsumer = new StandardConsumer(nickname, documentId);
        users.add(newStandardConsumer);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new premium consumer to the users' collection, ensuring that it is not already in it.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized
     * <strong>pos: </strong> users <strong>ArrayList</strong> modified with a new user in it
     * @param nickname new artist name
     * @param documentId new user's document
     * </pre>
     */
    public void registerPremiumConsumer(String nickname, String documentId) {
        if(searchUser(nickname) != null) throw new IllegalArgumentException("Consumer is already in list");
        PremiumConsumer newPremiumConsumer = new PremiumConsumer(nickname, documentId);
        users.add(newPremiumConsumer);
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
     * </pre>
     */
    public void registerSong(String songName, String pictureUrl, String duration, int genre, String album, double saleValue, String artistName) {
        User foundProducer = searchUser(artistName);
        if(!(foundProducer instanceof Artist)) throw new IllegalArgumentException("Artist not found");

        Song newSong = new Song(((Artist)foundProducer).getName(), songName, pictureUrl, parseDuration(duration), genre, album, saleValue);
        if(searchAudio(songName) != null) throw new IllegalArgumentException("Song is already in catalogue");

        ((Artist)foundProducer).addAudio(newSong);
        catalogue.add(newSong);
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
     * </pre>
     */
    public void registerPodcast(String podcastName, String pictureUrl, String duration, int category, String description, String contentCreatorName) {
        User foundProducer = searchUser(contentCreatorName);
        if(!(foundProducer instanceof ContentCreator) ) throw new IllegalArgumentException("Producer not found");

        Podcast newPodcast = new Podcast(((ContentCreator)foundProducer).getName(), podcastName, pictureUrl, parseDuration(duration), category, description);
        if(searchAudio(podcastName) != null) throw new IllegalArgumentException("Podcast is already in catalogue");

        ((ContentCreator)foundProducer).addAudio(newPodcast);
        catalogue.add(newPodcast);
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
     * @param type <strong>int</strong> type of playlist 1.AllSongs 2.OnlyPodcasts 3.OnlySongs
     * @param name <strong>String</strong> name of the new playlist
     * </pre>
     */
    public void registerPlaylist(String consumerName, int type, String name) {
        Playlist newPlaylist = switch (type) {
            case 1 -> new Playlist(name);
            case 2 -> new PodcastPlaylist(name);
            case 3 -> new SongPlaylist(name);
            default -> throw new IllegalArgumentException("Invalid playlist type");
        };

        User tmpConsumer = searchUser(consumerName);
        if(!(tmpConsumer instanceof Consumer) ) throw new IllegalArgumentException("Consumer not found");

        ((Consumer)tmpConsumer).addPlaylist(newPlaylist);
        playlists.add(newPlaylist);
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
     * </pre>
     */
    public void renamePlaylist(String consumerName, String playlistId, String newName) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) throw new IllegalArgumentException("Consumer not found");
        Playlist tmpPlaylist = tmpConsumer.searchPlaylist(playlistId);
        if(tmpPlaylist == null) throw new IllegalArgumentException(String.format("Consumer %s does not have this playlist", tmpConsumer.getNickname()));

        tmpPlaylist.setName(newName);
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
     * </pre>
     */
    public void addOrRemoveAudioToPlaylist(int operation, String consumerName, String audioName, String playlistId) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) throw new IllegalArgumentException("Consumer not found");
        Playlist tmpPlaylist = tmpConsumer.searchPlaylist(playlistId);
        if(tmpPlaylist == null) throw new IllegalArgumentException("Playlist not found");
        Audio tmpAudio = searchAudio(audioName);
        if(tmpAudio == null) throw new IllegalArgumentException("Audio not found");
        if(operation == 1) {
            tmpPlaylist.addAudio(tmpAudio);
        } else {
            tmpPlaylist.removeAudio(tmpAudio);
        }
    }

    public String sharePlaylist(String consumerName, String playlistId) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "User not found";
        Playlist tmpPlaylist = searchPlaylist(playlistId);
        if(tmpPlaylist == null) return "User does not have the playlist";
        return "\n" + tmpPlaylist.toString();
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
     * @param type <strong>int</strong> User type: 0.All, 1.OnlyProducers, 2.OnlyConsumers
     * @return userList <strong>String</strong> a readable list of users
     * </pre>
     */
    public String showUsers(int type) {
        String userList = "";
        for(User user : users) {
            switch (type) {
                case 0:
                    userList += "\n - " + user;
                    break;
                case 1:
                    if(user instanceof Producer) userList += "\n - " + user;
                    break;
                case 2:
                    if(user instanceof Consumer) userList += "\n - " + user;
            }
        }
        return (userList.equals("")) ? ("") : ((type == 1) ? ("\n--- Producer list ---" + userList) : ("\n--- Consumer list ---" + userList));
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of audios of the platform, that is, the catalogue. An empty text if there are not any audio.
     * @return catalogueList <strong>String</strong> a readable list of audios
     * </pre>
     */
    public String showCatalogue() {
        String catalogueList = "";

        for(Audio audio : catalogue) {
            catalogueList += "\n - " + audio;
        }
        return ( catalogueList.equals("") ) ? ("") : ("\n--- Current Catalogue --- " ) + catalogueList;
    }

    public String showUserInformation(int userType, String name) {
        switch (userType) {
            case 1:
                Producer foundProducer = (Producer)searchUser(name);
                return (foundProducer != null) ? foundProducer.toString() : "Producer not found";
            case 2:
                Consumer foundConsumer = (Consumer)searchUser(name);
                return (foundConsumer != null) ? foundConsumer.toString() : "Consumer not found";
            default:
                return "";
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> Prints the list of playlist that a selected consumer has.
     * <strong>pre:</strong> users <strong>ArrayList</strong> must be initialized and have at least one consumer
     * <strong>pre: </strong> playlists <strong>ArrayList</strong> must be initialized and have at least one playlist
     * @return consumerPlaylists <strong>String</strong> a readable list of playlists of the consumer
     * </pre>
     */
    public String showConsumerPlaylists(String userName) {
        User matchedConsumer = searchUser(userName);
        if(!(matchedConsumer instanceof Consumer)) throw new IllegalArgumentException("Consumer not found");

        return ((Consumer)matchedConsumer).showPlaylists();
    }

    public String showPurchases() {
        String purchasesList = "";
        for(Record purchase : purchases) {
            purchasesList += "\n - " + purchase;
        }
        return (purchasesList.equals("")) ? ("") : ("\n--- Purchase history --- " ) + purchasesList;
    }

    public String showConsumerPlaybacks(String consumerName) {
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "Consumer not found";
        return tmpConsumer.showPlaybacks();
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
            if( (user instanceof Consumer && ((Consumer)user).getNickname().equals(userName) ) || ( user instanceof Producer && ((Producer) user).getName().equals(userName) ) ) return user;
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
            if(audio.getName().equals(audioName)) return audio;
        }
        return null;
    }

    // PLAYING

    public String[] playAudio(String consumerName, String audioName) {
        String[] audioList = new String[2];
        Random rnd = new Random();
        Advertisement advertisement = ADS[rnd.nextInt(ADS.length-1)];

        Consumer tmpConsumer = (Consumer)searchUser(consumerName);

        Audio tmpAudio = searchAudio(audioName);
        if(tmpConsumer == null || tmpAudio == null) throw new IllegalArgumentException("Consumer or audio not found");

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
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        Audio tmpAudio = searchAudio(songName);
        if(tmpConsumer == null || tmpAudio == null || !(tmpAudio instanceof Song)) return false;

        if(tmpConsumer.addSong((Song)tmpAudio)) {
            purchases.add(new Record(tmpConsumer.getNickname(), tmpAudio));
        }
        return true;
    }

    // STATISTICS

    public String usersMostPlayedSongGenre(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "not found";

        int[] maxSong = tmpConsumer.mostPlayedSongGenre();

        statistics += String.format("For user %s: %nMost played song genre: %s (%d plays)", tmpConsumer.getNickname(), SongGenre.values()[maxSong[0]], maxSong[1]);
        return statistics;
    }
    public String usersMostPlayedPodcastCategory(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = (Consumer)searchUser(consumerName);
        if(tmpConsumer == null) return "not found";

        int[] maxPodcast = tmpConsumer.mostPlayedPodcastCategory();

        statistics += String.format("For user %s: %nMost played podcast category: %s (%d plays)", tmpConsumer.getNickname(), PodcastCategory.values()[maxPodcast[0]], maxPodcast[1]);
        return statistics;
    }

    public String mostPlayedSongGenre() {
        int[] playsPerSongGenre = calculateTotalPlaysPerType()[0];
        int maxGenre = 0;
        for(int i=0; i<playsPerSongGenre.length; i++) {
            if(playsPerSongGenre[i] > playsPerSongGenre[maxGenre]) {
                maxGenre = i;
            }
        }
        return String.format("Most played Song genre: %s %nNumber of plays : %d", SongGenre.values()[maxGenre], playsPerSongGenre[maxGenre]);
    }
    public String mostPlayedPodcastCategory() {
        int[] playsPerPodcastCategory = calculateTotalPlaysPerType()[1];
        int maxCategory = 0;
        for(int i=0; i<playsPerPodcastCategory.length; i++) {
            if(playsPerPodcastCategory[i] > playsPerPodcastCategory[maxCategory]) {
                maxCategory = i;
            }
        }
        return String.format("Most played Podcast category: %s %nNumber of plays : %d", PodcastCategory.values()[maxCategory], playsPerPodcastCategory[maxCategory]);
    }


    public int[][] calculateTotalPlaysPerType() {
        int[] playsPerSongGenre = new int[SongGenre.values().length];
        int[] playsPerPodcastCategory = new int[PodcastCategory.values().length];

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
        return new int[][]{playsPerSongGenre, playsPerPodcastCategory};
    }
    public String totalPlaysPerType() {
        // initialization
        String statistics = "";
        int[] playsPerSongGenre = calculateTotalPlaysPerType()[0];
        int[] playsPerPodcastCategory = calculateTotalPlaysPerType()[1];
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

    public void sortProducers() {
        for(int i = 0; i< users.size(); i++) {
            for(int j = 1; j< users.size()-i; j++) {
                if((users.get(j) instanceof Producer && users.get(j-1) instanceof Producer) || ((Producer)users.get(j-1)).getTotalPlays() < ((Producer)users.get(j)).getTotalPlays()) Collections.swap(users, j, j-1);
            }
        }
    }
    public void sortAudios() {
        for(int i=0; i<catalogue.size(); i++) {
            for(int j=1; j<catalogue.size()-i; j++) {
                if(catalogue.get(j-1).getNumberOfPlays() < catalogue.get(j).getNumberOfPlays()) Collections.swap(catalogue, j, j-1);
            }
        }
    }

    public String showProducersTop(int limit) {
        String artistTop = "", contentCreatorTop = "";
        sortProducers();
        if(users.size() < limit*2) return String.format("There must be at least %d producers of each type", limit);
        for(int i=0, ccIndex = 1, aIndex = 1; i<limit*2; i++) {
            if(users.get(i) instanceof Artist) {
                artistTop += String.format("%n %d. %s (%d plays)", aIndex, ((Producer)users.get(i)).getName(), ((Producer)users.get(i)).getTotalPlays());
                aIndex++;
            }
            if(users.get(i) instanceof ContentCreator) {
                contentCreatorTop += String.format("%n %d. %s (%d plays)", ccIndex, ((Producer)users.get(i)).getName(), ((Producer)users.get(i)).getTotalPlays());
                ccIndex++;
            }
        }
        return String.format("----- Artists Top %d -----%s%n----- Content Creator Top %d -----%s", limit, artistTop, limit, contentCreatorTop);
    }
    
    public String showAudiosTop(int limit) {
        String songTop = "", podcastTop = "";
        sortAudios();
        if(catalogue.size() < limit*2) return String.format("There must be at least %d audios of each type", limit);
        for(int i=0, sIndex = 1, pIndex = 1; i<limit*2; i++) {
            if(catalogue.get(i) instanceof Song) {
                Audio song = catalogue.get(i);
                songTop += String.format("%n %d. %s [%s] (%d plays)", pIndex, song.getName(), ((Song)song).getGenre(), song.getNumberOfPlays());
                pIndex++;
            }
            if(catalogue.get(i) instanceof Podcast) {
                Audio podcast = catalogue.get(i);
                podcastTop += String.format("%n %d. %s [%s] (%d plays)", sIndex, podcast.getName(), ((Podcast)podcast).getCategory(), podcast.getNumberOfPlays());
                sIndex++;
            }

        }
        return String.format("----- Song Top %d -----%s%n----- Podcast Top %d -----%s", limit, songTop, limit, podcastTop);
    }

    public String showTotalSalesPerGenre() {
        String totalSales = "";
        int[] salesPerGenre = new int[SongGenre.values().length];
        double[] incomePerGenre = new double[SongGenre.values().length];
        for(Audio song : catalogue) {
            if(song instanceof Song) {
                for(int i=0; i<SongGenre.values().length; i++) {
                    if(((Song) song).getGenre().equals(SongGenre.values()[i])) {
                        salesPerGenre[i] += ((Song) song).getNumberOfSales();
                        incomePerGenre[i] += ((Song) song).getTotalIncome();
                    }
                }
            }
        }
        for(int i=0; i<salesPerGenre.length; i++) {
            totalSales += String.format("%n - %s%n   - Sales: %d%n   - Income: $%.2f", SongGenre.values()[i], salesPerGenre[i], incomePerGenre[i]);
        }
        return String.format("----- Sales per Genre -----%n%s", totalSales);
    }

    public String bestSellerInformation() {
        if(catalogue.isEmpty()) return "";
        Audio bestSeller = null;
        int maxSales = 0;
        for(Audio song : catalogue) {
            if( song instanceof  Song && ((Song)song).getNumberOfSales() > maxSales) {
                bestSeller = song;
                maxSales = ((Song) song).getNumberOfSales();
            }
        }
        return String.format("----- Best seller information -----%n - Name: %s%n - Sales number: %d%n - Income: $%.2f", bestSeller.getName(), ((Song)bestSeller).getNumberOfSales(), ((Song)bestSeller).getTotalIncome());
    }


    /**
     * <pre>
     * <strong>Description: </strong> It parses a text that represents an audio duration to seconds
     * @param durationStr <strong>String</strong> Duration in format hh:mm:ss or mm:ss
     * @return duration <strong>int</strong> duration in seconds
     * </pre>
     */
    public int parseDuration(String durationStr) {
        int duration = 0;
        String[] durationArr =  durationStr.split(":");
        for(int i=0, j=durationArr.length-1; i<durationArr.length; i++) {
            int durationInt;
            try {
                durationInt = Integer.parseInt(durationArr[i]);
            } catch(Exception e) {
                throw new IllegalArgumentException("Invalid format for duration");
            }
            if(durationInt >= 60 || durationInt < 0) throw new IllegalArgumentException("Time out of bounds");
            duration += Integer.parseInt(durationArr[i]) * Math.pow(60,j);
            j--;
        }
        return duration;
    }

}
