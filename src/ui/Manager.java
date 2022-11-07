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
        // manager.test();
        manager.showMenu();
    }

    // MENUS

    public void showMenu() {
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
        String info[]; int type;
        String name, pictureUrl; // attributes

        // getting information
        System.out.print("""
                \n<<< Producer Registration >>>
                
                Enter the following information in the given format (separated by " , "):
                
                 | producerType | Allowed: Artist(1), ContentCreator(2).
                 | name         | identifier for the user, it is unique in the platform.
                 | pictureUrl   | A picture that represents the new user.
                
                Example: "1 , The Score , theScore.jpg"
                
                Info:\s""");
        info = sc.nextLine().split(" , ");

        try { // parsing
            type = Integer.parseInt(info[0]); name = info[1]; pictureUrl = info[2];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        try { // registering
            switch(type) {
                case 1 -> controller.registerArtist(name, pictureUrl);
                case 2 -> controller.registerContentCreator(name, pictureUrl);
                default -> throw new IllegalArgumentException("Invalid producer type");
            }
            System.out.println("\nProducer registered successfully");
            System.out.println(controller.showUsers(1));
        } catch(Exception e) {
            System.out.println("\nError adding producer: " + e.getMessage());
        }

    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new consumer, either standard or premium, and add it to the platform.
     * </pre>
     */
    public void registerConsumer() {
        String info[]; int type;
        String nickName, documentId; // attributes

        // getting information
        System.out.print("""
                \n<<< Consumer Registration >>>
                
                Enter the following information in the given format (separated by " , "):
                
                 | consumerType | Allowed: Standard(1), Premium(2).
                 | nickname     | identifier for the user, it is unique in the platform.
                 | document     | The document of the new user.
                
                Example: "1 , Yuluka , 1587495621"
                
                Info:\s""");
        info = sc.nextLine().split(" , ");

        try { // parsing
            type = Integer.parseInt(info[0]); nickName = info[1]; documentId = info[2];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        try { // registering
            switch(type) {
                case 1 -> controller.registerStandardConsumer(nickName, documentId);
                case 2 -> controller.registerPremiumConsumer(nickName, documentId);
                default -> throw new IllegalArgumentException("Invalid consumer type");
            }
            System.out.println("\nConsumer registered successfully");
            System.out.println(controller.showUsers(2));
        } catch(Exception e) {
            System.out.println("\nError adding consumer: " + e.getMessage());
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new song and add it to an existing artist’s song list.
     * </pre>
     */
    public void registerAudio() {
        if(controller.showUsers(1).equals("")) {
            System.out.println("\nFirst add a producer");
            return;
        }
        String info[]; int type;
        String name, pictureUrl, duration, producerId; // general attributes

        // getting information
        System.out.printf("""
                \n<<< Audio Registration >>>
                
                Enter the following information in the given format (separated by " , "):
                
                 | audioType                | Allowed: Song(1), Podcast(2).
                 | name                     | identifier for the audio, it is unique in the platform.
                 | pictureUrl               | Picture that represents the song or podcast
                 | duration                 | Duration written in hh:mm:ss (mm:ss for songs).
                 | genre or category        | The song genre or podcast category number.
                    - Podcast Categories:   | %s
                    - Song Genres:          | %s
                 | album or description     | The name of the song album, or the podcast description.
                 | producerId               | The name of the producer.
                 | sale value (for song)    | The song sale value.
                 %s
                 
                Example: "1 , bones , bones.jpg , 03:15 , 1 , Mercury , Imagine Dragons , 5.24"
                
                Info:\s""", controller.showPodcastCategories(), controller.showSongGenres(), controller.showUsers(1));
        info = sc.nextLine().split(" , ");

        try { // parsing general
            type = Integer.parseInt(info[0]);
            name = info[1];
            pictureUrl = info[2];
            duration = info[3];
            producerId = info[6];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        try { // registering
            switch (type) {
                case 1 -> {
                    String album = info[5];
                    int genre = Integer.parseInt(info[4]);
                    double saleValue = Double.parseDouble(info[7]); // song attributes
                    controller.registerSong(name, pictureUrl, duration, genre, album, saleValue, producerId);
                }
                case 2 -> {
                    String description = info[5];
                    int category = Integer.parseInt(info[4]); // podcast attributes
                    controller.registerPodcast(name, pictureUrl, duration, category, description, producerId);
                }
                default -> throw new IllegalArgumentException("Invalid audio type");
            }
            System.out.println("\nAudio registered successfully");
            System.out.println(controller.showCatalogue());
        } catch(Exception e) {
            System.out.println("\nError adding audio: " + e.getMessage());
        }
    }

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new podcast and add it to an existing artist’s podcast list.
     * </pre>
     */
    public void registerPlaylist() {
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a consumer");
            return;
        }
        String info[]; int type;
        String name, consumerName; // general attributes

        // getting information
        System.out.printf("""
                \n<<< Playlist Registration >>>
                
                Enter the following information in the given format (separated by " , "):
                
                 | playlistType            | Allowed: AllAudios(1), OnlyPodcast(2), OnlySongs(3).
                 | playlistName            | Name of the playlist, it is not unique.
                 | consumerNickname        | Picture that represents the song or podcast
                 %s
                 
                Example: "3 , Workout and programming , Chris78"
                
                Info:\s""", controller.showUsers(2));
        info = sc.nextLine().split(" , ");

        try { // parsing general
            type = Integer.parseInt(info[0]);
            name = info[1];
            consumerName = info[2];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        try { // registering
            controller.registerPlaylist(consumerName, type, name);
            System.out.println("\nPlaylist registered successfully, you can check the matrix by sharing it.");
            System.out.println(controller.showConsumerPlaylists(consumerName));
        } catch(Exception e) {
            System.out.println("\nError adding playlist: " + e.getMessage());
        }
    }

    // INTERACTION

    /**
     * <pre>
     * <strong>Description:</strong> It allows the user to create a new playlist and add it to an existing consumer’s song list, also to the general platform.
     * </pre>
     */
    public void editPlaylist() {
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a playlist");
            return;
        }

        String info[]; int operation;
        String newName, id, consumerName; // general attributes

        System.out.print("""
                \nEnter the nickname of the consumer that owns the playlist
                Name:\s""");
        consumerName = sc.nextLine();

        if(controller.showConsumerPlaylists(consumerName).equals("")) {
            System.out.printf("%nThe consumer %s does not have playlist", consumerName);
        }

        // getting information
        System.out.printf("""
                \n<<< Playlist Editing >>>
                
                Enter the following information in the given format (separated by " , "):
                
                 | playlistId              | Name of the playlist, it is unique.
                 | operation               | Allowed: AddSong(1) , RemoveSong(2) , RenamePlaylist(3)
                 | audio name or new name  | Audio name for adding and removing. Playlist name for renaming.
                 %s
                 
                Examples: "5874596857485126 , 1 , dreamers"
                          "1547856598659854 , 3 , Reading songs"
                
                Info:\s""", controller.showConsumerPlaylists(consumerName));
        info = sc.nextLine().split(" , ");

        try { // parsing general
            id = info[0];
            operation = Integer.parseInt(info[1]);
            newName = info[2];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        try { // registering
            switch(operation) {
                case 1 -> controller.addOrRemoveAudioToPlaylist(1, consumerName, newName, id);
                case 2 -> controller.addOrRemoveAudioToPlaylist(2, consumerName, newName, id);
                case 3 -> controller.renamePlaylist(consumerName, id, newName);
                default -> throw new IllegalArgumentException("\nInvalid option");
            }
            System.out.println("\nPlaylist edited successfully");
            System.out.println(controller.showConsumerPlaylists(consumerName));
        } catch(Exception e) {
            System.out.println("\nError editing playlist: " + e.getMessage());
        }
    }
    public void sharePlaylist() {

    }
    public void playAudio() {

    }
    public void buySong() {

    }

    // STATISTICS

    public void showStatistics() {

    }

}