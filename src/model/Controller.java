package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Controller {
    private ArrayList<Producer> producers;
    private ArrayList<Consumer> consumers;
    private ArrayList<Playlist> playlists;

    private ArrayList<Audio> catalogue;
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
        this.catalogue = new ArrayList<>();
    }

    // REGISTRATION METHODS

    public void registerContentCreator(String name, String pictureUrl) {
        if(searchProducer(name) != null) throw new IllegalArgumentException("Content creator is already in list");
        ContentCreator newContentCreator = new ContentCreator(name, pictureUrl);
        producers.add(newContentCreator);
    }
    public void registerArtist(String name, String pictureUrl) {
        if(searchProducer(name) != null) throw new IllegalArgumentException("Content creator is already in list");
        Artist newArtist = new Artist(name, pictureUrl);
        producers.add(newArtist);
    }

    public void registerStandardConsumer(String nickname, String documentId) {
        if(searchConsumer(nickname) != null) throw new IllegalArgumentException("Content creator is already in list");
        StandardConsumer newStandardConsumer = new StandardConsumer(nickname, documentId);
        consumers.add(newStandardConsumer);
    }
    public void registerPremiumConsumer(String nickname, String documentId) {
        if(searchConsumer(nickname) != null) throw new IllegalArgumentException("Content creator is already in list");
        PremiumConsumer newPremiumConsumer = new PremiumConsumer(nickname, documentId);
        consumers.add(newPremiumConsumer);
    }

    public void registerSong(String songName, String pictureUrl, String duration, int genre, String album, double saleValue, String artistName) {

        Song newSong = new Song(songName, pictureUrl, parseDuration(duration), genre, album, saleValue);
        Producer foundProducer = searchProducer(artistName);

        if(foundProducer == null) throw new IllegalArgumentException("Producer not found");

        foundProducer.addAudio(newSong);
        catalogue.add(newSong);
    }

    public boolean registerPodcast(String podcastName, String pictureUrl, String duration, int category, String description, String contentCreatorName) {

        Podcast newPodcast = new Podcast(podcastName, pictureUrl, parseDuration(duration), category, description);
        Producer foundProducer = searchProducer(contentCreatorName);

        if( foundProducer == null || !(foundProducer instanceof ContentCreator) ) return false;

        foundProducer.addAudio(newPodcast);
        catalogue.add(newPodcast);

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

    public String showSongGenres() {
        String genresList = "";
        for(int i=0; i<SongGenre.values().length; i++) {
            genresList += (i+1) + "." + SongGenre.values()[i] + ( (i<SongGenre.values().length-1) ? (", ") : (""));
        }
        return genresList;
    }
    public String showPodcastCategories() {
        String categoriesList = "";
        for(int i=0; i<PodcastCategory.values().length; i++) {
            categoriesList += (i+1) + "." + SongGenre.values()[i] + ( (i<SongGenre.values().length-1) ? (", ") : (""));
        }
        return categoriesList;
    }

    public String showProducers() {
        String producerList = "";
        for(Producer producer : producers) producerList += "\n - " + producer.getName() + ((producer instanceof Artist) ? " (Artist)" : " (Content creator)");
        return (producerList.equals("")) ? ("") : ("--- Producer list ---" + producerList);
    }

    public String showConsumers() {
        String consumerList = "";
        for(Consumer consumer : consumers) consumerList += "\n - " + consumer.getNickname() + ((consumer instanceof StandardConsumer) ? (" (Standard)")  : (" (Premium"));
        return (consumerList.equals("")) ? ("") : ("--- Consumer list ---" + consumerList);
    }

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

    // STATISTICS

    public String usersMostPlayedSongGenre(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = searchConsumer(consumerName);
        if(tmpConsumer == null) return "not found";

        int[] maxSong = tmpConsumer.mostPlayedSongGenre();

        statistics += String.format("For user %s: %nMost played song genre: %s (%d plays)", tmpConsumer.getNickname(), SongGenre.values()[maxSong[0]], maxSong[1]);
        return statistics;
    }
    public String usersMostPlayedPodcastCategory(String consumerName) {
        String statistics = "";
        Consumer tmpConsumer = searchConsumer(consumerName);
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
        for(int i=0; i<producers.size(); i++) {
            for(int j=1; j<producers.size()-i; j++) {
                if(producers.get(j-1).getTotalPlays() < producers.get(j).getTotalPlays()) Collections.swap(producers, j, j-1);
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
        if(producers.size() < limit*2) return String.format("There must be at least %d producers of each type", limit);
        for(int i=0, ccIndex = 1, aIndex = 1; i<limit*2; i++) {
            if(producers.get(i) instanceof Artist) {
                artistTop += String.format("%n %d. %s (%d plays)", aIndex, producers.get(i).getName(), producers.get(i).getTotalPlays());
                aIndex++;
            }
            if(producers.get(i) instanceof ContentCreator) {
                contentCreatorTop += String.format("%n %d. %s (%d plays)", ccIndex, producers.get(i).getName(), producers.get(i).getTotalPlays());
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

    public int parseDuration(String durationStr) {
        int duration = 0;
        String[] durationArr =  durationStr.split(":");
        for(int i=0, j=durationArr.length-1; i<durationArr.length; i++) {
            duration += Integer.parseInt(durationArr[i]) * 60 * j;
            j--;
        }
        return duration;
    }

}
