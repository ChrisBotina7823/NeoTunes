package ui;

import java.util.Scanner;
import model.Controller;

public class Manager {
    private Scanner sc;
    private Controller controller;

    public Manager() {
        this.sc = new Scanner(System.in);
        this.controller = new Controller();
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.showMenu();
    }

    public void test() {
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<< PRODUCER AND CONSUMER REGISTRATION >>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(int i=1; i<=10; i++) {
            try {
                controller.registerContentCreator("contentCreator"+i, "123"+i);
                controller.registerArtist("artist"+i, "12"+i);
                controller.registerPremiumConsumer("premium"+i, "1234"+i);
                controller.registerStandardConsumer("standard"+i, "12345"+i);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            /*
            try { // REPEATED USERS
                controller.registerContentCreator("contentCreator"+i, "123"+i,"pic");
                controller.registerArtist("artist"+i, "123"+i,"pic");
                controller.registerStandardConsumer("premium"+i, "1234"+i);
                controller.registerPremiumConsumer("premium"+i, "12345"+i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
             */
        }
        // System.out.println(controller.showUsers(0));



        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<< SONG AND PODCAST REGISTRATION >>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(int i=1; i<=5; i++) {
            try {
                controller.registerSong("song"+i, "pic", parseDuration("3:48"), 3, "aaa",23, "12"+i);
                controller.registerPodcast("podcast"+i, "pic", parseDuration("1:59:30"), 3, "aaa","123"+i);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                /*
                controller.registerSong("song"+i, "pic", "01:30", 1, "aaa",23, "12"+i);
                controller.registerPodcast("podcast"+i, "pic", "01:30", 1, "aaa","123"+i);
                 */
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // System.out.println(controller.showCatalogue());



        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<< PLAYLIST REGISTRATION >>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(int i=0; i<5; i++) {
            try {
                controller.registerPlaylist("12341",  "playlist"+i);
                controller.registerPlaylist("123451",  "playlist"+i);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        /*
        System.out.println(controller.showConsumerPlaylists("123451"));
        System.out.println(controller.showConsumerPlaylists("12341"));
         */


        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<< PLAYLIST EDIT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        try {
            controller.renamePlaylist("12341", controller.getPlaylists().get(0).getId(), "newName1");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            // standard consumer own playlists with even indexes, playlists for each consumer are: standard, podcast, and song playlists.
            controller.addOrRemovePlaylistAudio(1,"12341","podcast2",controller.getPlaylists().get(2).getId());
            controller.addOrRemovePlaylistAudio(2,"12341","podcast2",controller.getPlaylists().get(2).getId());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(controller.showConsumerPlaylists("12341"));
    }

    // MENUS

    /**
     * <pre>
     * <strong>Description: </strong> Shows the menu options to the user, it ensures that the answer is a valid number.
     * </pre>
     */
    public void showMenu() {
        try {
            controller.registerSong("cc","cc",parseDuration("3:15"),3,"a",4,"aa");
        } catch(Exception e) {
            System.out.println("A");
        }

        System.out.println("\n===== Welcome to NeoTunes! =====");
        int operation;
        do {
            System.out.print("""
                    \nSelect the operation:
                    0. Exit
                    1. Register producer
                    2. Register consumer
                    3. Register audio
                    4. Register playlist
                    5. Edit playlist
                    6. Share playlist
                    7. Play audio
                    8. Buy song
                    9. Show Statistics
                    """);
            operation = -1;
            while(operation < 0 || operation > 9) {
                try {
                    System.out.print("Option: ");
                    operation = Integer.parseInt(sc.nextLine());
                } catch(Exception e) {
                    System.out.println();
                }
            }
            switch (operation) {
                case 0 -> System.out.println("\nBye :)");
                case 1 -> registerProducer();
                case 2 -> registerConsumer();
                case 3 -> registerAudio();
                case 4 -> registerPlaylist();
                case 5 -> editPlaylist();
                case 6 -> sharePlaylist();
                case 7 -> playAudio();
                case 8 -> buySong();
                case 9 -> showStatistics();
                default -> System.out.println("\nEnter a valid option");
            }
        } while(operation != 0);
    }

    // REGISTRATION

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new producer, either artist or content creator, and add it to the platform.
     * </pre>
     */
    public void registerProducer() {
        System.out.println("\n===== Producer Registration =====");
        System.out.println("Enter the type (1.artist, 2.Content creator)");
        System.out.print("type: ");
        int type = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the producer name: ");
        String name = sc.nextLine();

        System.out.print("Enter the picture url: ");
        String pictureUrl = sc.nextLine();

        switch(type) {
            case 1 -> {
                if(!controller.registerArtist(name, pictureUrl)){
                    System.out.println("\nError adding artist");
                    return;
                }
            }
            case 2 -> {
                if (!controller.registerContentCreator(name, pictureUrl)) {
                    System.out.println("\nError adding content creator");
                    return;
                }
            }
            default -> {
                System.out.println("\nInvalid producer type");
                return;
            }
        }
        System.out.println("\nProducer registered successfully");
        System.out.println(controller.showUsers(1));
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new consumer, either standard or premium, and add it to the platform.
     * </pre>
     */
    public void registerConsumer() {
        System.out.println("\n===== Consumer Registration =====");
        System.out.println("Enter the type (1.standard, 2.premium)");
        System.out.print("type: ");
        int type = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the consumer nickname: ");
        String nickName = sc.nextLine();

        System.out.print("Enter the document ID: ");
        String documentId = sc.nextLine();

        switch(type) {
            case 1 -> {
                if(!controller.registerStandardConsumer(nickName, documentId)) {
                    System.out.println("\nError adding standard consumer");
                    return;
                }
            }
            case 2 -> {
                if (!controller.registerPremiumConsumer(nickName, documentId)) {
                    System.out.println("\nError adding standard consumer");
                    return;
                }
            }
            default -> {
                System.out.println("\nInvalid consumer type");
                return;
            }
        }
        System.out.println("\nConsumer registered successfully");
        System.out.println(controller.showUsers(2));
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new song and add it to an existing artist’s song list.
     * </pre>
     */
    public void registerAudio() {
        System.out.println("\n===== Audio Registration =====");
        if(controller.showUsers(1).equals("")) {
            System.out.println("\nFirst add a producer");
            return;
        }

        System.out.println("Enter the audio type (1.song, 2.podcast)");
        System.out.print("type: ");
        int type = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the audio name: ");
        String name = sc.nextLine();

        System.out.print("Enter the picture url: ");
        String pictureUrl = sc.nextLine();

        int duration;
        System.out.print("Enter the duration (hh:mm:ss) or (mm:ss): ");
        try {
            duration = parseDuration(sc.nextLine());
        } catch(Exception e) {
            System.out.println("Invalid format");
            return;
        }

        System.out.println(controller.showUsers(1));
        System.out.print("Enter the producer name: ");
        String producerId = sc.nextLine();

        switch (type) {
            case 1 -> {
                System.out.printf("Enter the genre: %s%n", controller.showSongGenres());
                System.out.print("genre: ");
                int genre = sc.nextInt();

                sc.nextLine();
                System.out.print("Enter the album: ");
                String album = sc.nextLine();

                System.out.print("Enter the sale value: ");
                double saleValue = sc.nextDouble();

                if(!controller.registerSong(name, pictureUrl, duration, genre-1, album, saleValue, producerId)) {
                    System.out.println("\nError registering song");
                    return;
                }

            }
            case 2 -> {
                System.out.printf("Enter the category: %s%n", controller.showPodcastCategories());
                System.out.print("genre: ");
                int category = sc.nextInt();

                sc.nextLine();
                System.out.print("Enter the description: ");
                String description = sc.nextLine();

                if(!controller.registerPodcast(name, pictureUrl, duration, category-1, description, producerId)) {
                    System.out.println("\nError registering podcast");
                    return;
                }
            }
            default -> throw new IllegalArgumentException("Invalid audio type");
        }
        System.out.println("\nAudio registered successfully" + controller.showCatalogue(0));
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new podcast and add it to an existing artist’s podcast list.
     * </pre>
     */
    public void registerPlaylist() {
        System.out.println("\n===== Playlist Registration =====");
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a consumer");
            return;
        }

        System.out.println(controller.showUsers(2));
        System.out.print("Enter the consumer name: ");
        String consumerName = sc.nextLine();

        System.out.print("Enter the playlist name: ");
        String name = sc.nextLine();

        if(!controller.registerPlaylist(consumerName, name)) {
            System.out.println("\nError adding playlist");
            return;
        }

        System.out.println("\nPlaylist registered successfully, you can check the matrix by sharing it.");
        System.out.println(controller.showConsumerPlaylists(consumerName));
    }

    // INTERACTION

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new playlist and add it to an existing consumer’s song list, also to the general platform.
     * </pre>
     */
    public void editPlaylist() {
        System.out.println("\n===== Playlist Editing =====");
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a playlist");
            return;
        }

        System.out.println(controller.showUsers(2));
        System.out.print("Enter the consumer name: ");
        String consumerName = sc.nextLine();
        if(controller.showConsumerPlaylists(consumerName).equals("")) {
            System.out.printf("%nThe consumer %s does not have playlists", consumerName);
        }

        System.out.println(controller.showConsumerPlaylists(consumerName));
        System.out.print("Enter the playlistId: ");
        String id = sc.nextLine();

        System.out.println("Enter the operation. Allowed: addAudio(1) , removeAudio(2) , RenamePlaylist(3)");
        System.out.print("Operation: ");
        int operation = sc.nextInt();

        String name;
        sc.nextLine();

        switch(operation) {
            case 1 -> {
                System.out.println(controller.showCatalogue(0));
                System.out.print("Enter the name of the audio to add: ");
                name = sc.nextLine();
                if(!controller.addOrRemovePlaylistAudio(1, consumerName, name, id)) {
                    System.out.println("\nError adding song to playlist");
                    return;
                }
            }
            case 2 -> {
                System.out.println(controller.showPlaylistAudios(id));
                System.out.print("Enter the name of the audio to remove: ");
                name = sc.nextLine();
                if(controller.addOrRemovePlaylistAudio(2, consumerName, name, id)){
                    System.out.println("\nError removing song from playlist");
                    return;
                }
            }
            case 3 -> {
                System.out.print("Enter the new name of the playlist: ");
                name = sc.nextLine();
                if(controller.renamePlaylist(consumerName, id, name)) {
                    System.out.printf("%nError renaming playlist to %s", name);
                }
            }
            default -> {
                System.out.println("\nInvalid operation");
                return;
            }
        }
        System.out.println("\nPlaylist edited successfully");
        System.out.println(controller.showConsumerPlaylists(consumerName));
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to get the playlist ID and the matrix that generated it, so that other users can access it.
     * </pre>
     */
    public void sharePlaylist() {
        System.out.println("\n===== Playlist Sharing =====");
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a playlist");
            return;
        }

        System.out.println(controller.showUsers(2));
        System.out.print("Enter the consumerName: ");
        String consumerName = sc.nextLine();

        if(controller.showConsumerPlaylists(consumerName).equals("")) {
            System.out.printf("%nThe consumer %s does not have playlists", consumerName);
        }

        System.out.println(controller.showConsumerPlaylists(consumerName));
        System.out.print("Enter the playlist id: ");
        String id = sc.nextLine();

        System.out.println(controller.sharePlaylist(consumerName, id));
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to play any audio that exists in the catalogue.
     * </pre>
     */
    public void playAudio() {
        System.out.println("\n===== Audio Playing =====");
        if(controller.showCatalogue(0).equals("") || controller.showUsers(2).equals("")) {
            System.out.println("\nThere must be at least a consumer and audio");
        }

        // input
        System.out.println(controller.showUsers(2));
        System.out.print("Enter the consumer name: ");
        String consumerName = sc.nextLine();

        System.out.println(controller.showCatalogue(0));
        System.out.print("Enter the audio name: ");
        String audioName = sc.nextLine();

        String[] queue = controller.playAudio(consumerName, audioName);
        if(queue[1].equals("")) {
            System.out.println("\nError playing audio");
            return;
        }

        for(String audio : queue) {
            if(!audio.equals("")) {
                System.out.println("\n" + audio);
                sc.nextLine();
            }
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to select a song from the catalogue and add it to his song list.
     * </pre>
     */
    public void buySong() {
        System.out.println("\n===== Song Purchase =====");
        if(controller.showCatalogue(1).equals("") || controller.showUsers(2).equals("")) {
            System.out.println("\nThere must be at least a consumer and audio");
        }

        // input
        System.out.println(controller.showUsers(2));
        System.out.print("Enter the consumer name: ");
        String consumerName = sc.nextLine();

        System.out.println(controller.showCatalogue(1));
        System.out.print("Enter the song name: ");
        String songName = sc.nextLine();

        if(!controller.buySong(consumerName, songName)) {
            System.out.println("\nError buying song");
            return;
        }

        System.out.println("\nSong bought successfully");
        System.out.println(controller.showPurchases(consumerName));
    }

    // STATISTICS

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new playlist and add it to an existing consumer’s song list, also to the general platform.
     * </pre>
     */
    public void showStatistics() {
        System.out.println("\n===== Statistics =====");
        int operation;
        do {
            System.out.print("""
                    \nSelect the operation:
                    0. Go back
                    1. Total of plays per type
                    2. Most played song genre per user and platform
                    3. Most played podcast category per user and platform
                    4. Producers’ top 5 information per type
                    5. Audio’s top 10 information per type
                    6. Song total sales per genre
                    7. Best seller song’s total sales
                    """);
            operation = -1;
            while(operation < 0 || operation > 7) {
                try {
                    System.out.print("Option: ");
                    operation = Integer.parseInt(sc.nextLine());
                } catch(Exception e) {
                    System.out.println();
                }
            }
            switch (operation) {
                case 0 -> System.out.print("");
                case 1 -> System.out.println("\n" + controller.showTotalPlaysPerType());
                case 2 -> {
                    System.out.println(controller.showUsers(2));
                    System.out.print("Enter the name of the consumer or press enter for the whole platform: ");
                    String consumerName = sc.nextLine();
                    if(consumerName.equals("")) {
                        System.out.println(controller.mostPlayedSongGenre());
                    } else {
                        System.out.println(controller.mostPlayedSongGenre(consumerName));
                    }
                }
                case 3 -> {
                    System.out.println(controller.showUsers(2));
                    System.out.print("Enter the name of the consumer or press enter for the whole platform: ");
                    String consumerName = sc.nextLine();
                    if(consumerName.equals("")) {
                        System.out.println(controller.mostPlayedPodcastCategory());
                    } else {
                        System.out.println(controller.mostPlayedPodcastCategory(consumerName));
                    }
                }
                case 4 -> System.out.println(controller.showProducersTop(5));
                case 5 -> System.out.println(controller.showAudiosTop(10));
                case 6 -> System.out.println(controller.showTotalSalesPerGenre());
                case 7 -> System.out.println(controller.bestSellerInformation());
                default -> System.out.println("\nEnter a valid option");
            }
        } while(operation != 0);
    }

    // UTILITIES

    /**
     * <pre>
     * <strong>Description: </strong> It parses a text that represents an audio duration to seconds
     * @param durationStr <strong>String</strong> Duration in format hh:mm:ss or mm:ss
     * @throws Exception when the String cannot be converted to an integer
     * @return duration <strong>int</strong> duration in seconds
     * </pre>
     */
    public int parseDuration(String durationStr) throws Exception {
        int duration = 0;
        String[] durationArr =  durationStr.split(":");
        for(int i=0, j=durationArr.length-1; i<durationArr.length; i++) {
            int durationInt = Integer.parseInt(durationArr[i]);
            if(durationInt >= 60 || durationInt < 0) throw new Exception();
            duration += Integer.parseInt(durationArr[i]) * Math.pow(60,j);
            j--;
        }
        return duration;
    }
}