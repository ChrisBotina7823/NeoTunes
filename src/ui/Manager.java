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

    // MENUS

    /**
     * <pre>
     * <strong>Description: </strong> Shows the menu options to the user, it ensures that the answer is a valid number.
     * </pre>
     */
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
                    System.out.println("");
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
        String[] info; int type;
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
        String[] info; int type;
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
        if(controller.showUsers(1).equals("")) {
            System.out.println("\nFirst add a producer");
            return;
        }
        String[] info; int type;
        String name, pictureUrl, producerId; int duration ; // general attributes

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
            duration = parseDuration(info[3]);
            producerId = info[6];
        } catch(Exception e) {
            System.out.println("\nError: Invalid format");
            return;
        }

        switch (type) {
            case 1 -> {
                String album; int genre; double saleValue;
                try { // parsing song attributes
                    album = info[5];
                    genre = Integer.parseInt(info[4]);
                    saleValue = Double.parseDouble(info[7]);
                } catch(Exception e) {
                    System.out.println("\nError: Invalid format");
                    return;
                }
                if(!controller.registerSong(name, pictureUrl, duration, genre, album, saleValue, producerId)) {
                    System.out.println("\nError registering song");
                    return;
                }

            }
            case 2 -> {
                String description; int category;
                try { // parsing podcast attributes
                    description = info[5];
                    category = Integer.parseInt(info[4]);
                } catch (Exception e) {
                    System.out.println("\nError: Invalid format");
                    return;
                }
                if(!controller.registerPodcast(name, pictureUrl, duration, category, description, producerId)) {
                    System.out.println("\nError registering podcast");
                    return;
                }
            }
            default -> throw new IllegalArgumentException("Invalid audio type");
        }
        System.out.println("\nAudio registered successfully");
        System.out.println(controller.showCatalogue());
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
        String[] info; int type;
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

        if(controller.registerPlaylist(consumerName, type, name)) {
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
        if(controller.showUsers(2).equals("")) {
            System.out.println("\nFirst add a playlist");
            return;
        }

        String[] info; int operation;
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

        switch(operation) {
            case 1 -> {
                if(!controller.addOrRemovePlaylistAudio(1, consumerName, newName, id)) {
                    System.out.println("\nError adding song to playlist");
                    return;
                }
            }
            case 2 -> {
                if(controller.addOrRemovePlaylistAudio(2, consumerName, newName, id)){
                    System.out.println("\nError removing song from playlist");
                    return;
                }
            }
            case 3 -> {
                if(controller.renamePlaylist(consumerName, id, newName)) {
                    System.out.printf("%nError renaming playlist to %s", newName);
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
    public void sharePlaylist() {

    }
    public void playAudio() {

    }
    public void buySong() {

    }

    // STATISTICS

    public void showStatistics() {

    }

    // UTILITIES

    /**
     * <pre>
     * <strong>Description: </strong> It parses a text that represents an audio duration to seconds
     * @param durationStr <strong>String</strong> Duration in format hh:mm:ss or mm:ss
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