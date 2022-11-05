package ui;

import java.util.Scanner;
import model.Controller;

public class Manager {
    private final int ARTIST_ID = 1, CONTENT_CREATOR_ID = 2, STANDARD_ID = 1, PREMIUM_ID = 2, PRODUCER_ID = 1, CONSUMER_ID = 2;
    private Scanner sc;
    private Controller controller;

    public Manager() {
        sc = new Scanner(System.in);
        controller = new Controller();
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.showMenu();
    }

    // MENUS

    public void showMenu() {
        int operation;
        do {
            System.out.print("""
                    \nSelect the operation:
                    0. Exit
                    1. Registration
                    2. Interaction
                    3. Statistics
                    Option:\s""");
            operation = sc.nextInt();
            switch (operation) {
                case 0 -> System.out.println("\nBye :)");
                case 1 -> showRegistrationMenu();
                case 2 -> showInteractionMenu();
                case 3 -> showStatisticsMenu();
                default -> System.out.println("\nEnter a valid option");
            }
        } while(operation != 0);
    }

    public void showRegistrationMenu() {
        System.out.print("""
                \nSelect the operation
                0. Exit
                1. Register Producer
                2. Register Consumer
                3. Register audio
                4. Register playlist
                Option:\s""");
        int operation = sc.nextInt();
        switch (operation) {
            case 1 -> registerProducer();
            case 2 -> registerConsumer();
            case 3 -> registerAudio();
            case 4 -> registerPlaylist();
            default -> System.out.println("\nEnter a valid option");
        }
    }
    public void showInteractionMenu() {
        System.out.print("""
                \nSelect the operation
                0. Exit
                1. Edit playlist
                2. Share playlist
                3. Play song
                4. Buy song
                Option:\s""");
        int operation = sc.nextInt();
        switch (operation)  {
            case 1 -> editPlayist();
            case 2 -> sharePlaylist();
            case 3 -> playSong();
            case 4 -> buySong();
            default -> System.out.println("\nEnter a valid option");
        }
    }
    public void showStatisticsMenu() {
        System.out.print("""
                \nSelect the operation
                0. Exit
                1. Edit playlist
                2. Share playlist
                3. Play song
                4. Buy song
                Option:\s""");
        int operation = sc.nextInt();
        switch (operation)  {
            case 1 -> showTotalPlaysPerType();
            case 2 -> showMostPlayedSongGenre();
            case 3 -> showMostPlayedPodcastCategory();
            case 4 -> showProducersTopInfo();
            case 5 -> showSongsTopInfo();
            case 6 -> showTotalSalesPerGenre();
            case 7 -> showBestSellerInfo();
            default -> System.out.println("\nEnter a valid option");
        }
    }

    // REGISTRATION

    public void registerProducer() {
        String name, pictureUrl;
        System.out.print("""
                \nEnter the producer type
                1. Artist
                2. Content creator
                Option:\s""");
        int type = sc.nextInt();
        System.out.print("""
                \nEnter the following information for the new Producer:
                Format: [name] , [pictureUrl]
                Example: "The Score , theScore.jpg"
                Information:\s""");
        sc.nextLine();
        String[] info = sc.nextLine().split(" , ");

        try {
            // parsing
            name = info[0]; pictureUrl = info[1];
        } catch (Exception e) {
            System.out.println("\nEnter the information in a valid format");
            return;
        }

        switch (type) {
            case 1 -> controller.registerArtist(name, pictureUrl);
            case 2 -> controller.registerContentCreator(name, pictureUrl);
        }

        System.out.println("\nProducer registered successfully");
        System.out.println(controller.showProducers());
    }
    public void registerConsumer() {
        String name, documentId;
        System.out.print("""
                \nEnter the producer type
                1. Standard consumer
                2. Premium Consumer
                Option:\s""");
        int type = sc.nextInt();
        System.out.print("""
                \nEnter the following information for the new Producer:
                Format: [name] , [documentId]
                Example: "Yuluka , 1251960801"
                Information:\s""");
        sc.nextLine();
        String[] info = sc.nextLine().split(" , ");

        try {
            // parsing
            name = info[0]; documentId = info[1];
        } catch (Exception e) {
            System.out.println("\nEnter the information in a valid format");
            return;
        }

        switch (type) {
            case 1 -> controller.registerStandardConsumer(name, documentId);
            case 2 -> controller.registerPremiumConsumer(name, documentId);
        }

        System.out.println("\nConsumer registered successfully");
        System.out.println(controller.showConsumers());
    }
    public void registerAudio() {
        try {
            String[] info;
            String name, pictureUrl, duration;

            System.out.print("""
                    \nEnter the producer type
                    1. Song
                    2. Podcast
                    Option:\s""");
            int type = sc.nextInt();

            System.out.print("""
                    \nEnter the following information for the new Audio:
                    Format: [name] , [pictureUrl] , [duration]
                    Example: "Bones , bones.jpg , 3:15"
                    Information:\s""");
            sc.nextLine();
            info = sc.nextLine().split(" , ");

            // parsing
            name = info[0];
            pictureUrl = info[1];
            duration = info[2];
            switch (type) {
                case 1:
                    String album, artistName;
                    int genre;
                    double saleValue;
                    System.out.print(String.format("""
                            \nEnter the following information for the new Song:
                            Format: [album] , [genre: %s] , [saleValue]
                            Example: "Mercury , 1 , 25.3"
                            Information:\s""", controller.showSongGenres()));
                    info = sc.nextLine().split(" , ");

                    // parsing
                    album = info[0];
                    genre = Integer.parseInt(info[1]);
                    saleValue = Double.parseDouble(info[2]);

                    System.out.println("""
                            \nEnter the producer name
                            Info:\s""");
                    artistName = sc.nextLine();

                    controller.registerSong(name, pictureUrl, duration, genre, album, saleValue, artistName);
                    break;
                case 2:
                    String description, contentCreatorName;
                    int category;
                    System.out.print(String.format("""
                            \nEnter the following information for the new Podcast:
                            Format: [description] , [category: %s]
                            Example: "Mercury , 1 , 25.3"
                            Information:\s""", controller.showPodcastCategories()));
                    info = sc.nextLine().split(" , ");

                    // parsing
                    description = info[0];
                    category = Integer.parseInt(info[1]);

                    System.out.println(String.format("""
                            \nEnter the producer name
                            Info:\s""", controller.showProducers()));
                    contentCreatorName = sc.nextLine();

                    controller.registerPodcast(name, pictureUrl, duration, category, description, contentCreatorName);

                    break;
                default:
                    System.out.println("Enter a valid option");
                    return;
            }
        } catch(Exception e) {
            System.out.println("Error, check the input format and be sure that the producer exists");
        }
    }
    public void registerPlaylist() {

    }

    // INTERACTION

    public void editPlayist() {

    }
    public void sharePlaylist() {

    }
    public void playSong() {

    }
    public void buySong() {

    }

    // STATISTICS

    public void showTotalPlaysPerType() {

    }
    public void showMostPlayedSongGenre() {

    }
    public void showMostPlayedPodcastCategory() {

    }
    public void showProducersTopInfo() {

    }
    public void showSongsTopInfo() {

    }
    public void showTotalSalesPerGenre() {

    }
    public void showBestSellerInfo() {

    }

}