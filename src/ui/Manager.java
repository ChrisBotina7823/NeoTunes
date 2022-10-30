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

    public void showMenu() {
        // Registering producers
        controller.registerProducer(ARTIST_ID,"premiumProducer1", "thisIsAPicture1");
        controller.registerProducer(ARTIST_ID,"standardProducer1", "thisIsAPicture1");
        controller.registerProducer(CONTENT_CREATOR_ID,"premiumProducer2", "thisIsAPicture2");
        controller.registerProducer(CONTENT_CREATOR_ID, "standardProducer2", "thisIsAPicture2" );

        // Registering consumers
        controller.registerConsumer(STANDARD_ID, "standardConsumer1", "1234567890");
        controller.registerConsumer(PREMIUM_ID, "premiumConsumer1", "987654321");
        controller.registerConsumer(PREMIUM_ID, "premiumConsumer2", "987654321");

        // Displaying information
        System.out.println(controller.showUserInformation(PRODUCER_ID, "premiumProducer1"));
        System.out.println(controller.showUserInformation(CONSUMER_ID, "premiumProducer1"));
        System.out.println(controller.showUserInformation(CONSUMER_ID, "premiumConsumer1"));
        System.out.println(controller.showUserInformation(PRODUCER_ID, "premiumConsumer1"));

        // Registering songs and podcasts
        int[] podcastDuration = {1, 45, 30};
        int[] songDuration = {0, 4, 10};
        controller.registerPodcast("podcastName1", "picture1", podcastDuration , 1, "this is a description", "premiumProducer2");
        controller.registerPodcast("podcastName3", "picture1", podcastDuration , 1, "this is a description", "premiumProducer2");
        controller.registerPodcast("podcastName2", "picture1", podcastDuration , 1, "this is a description", "premiumProducer1");
        try {
            controller.registerSong("songName1", "picture1", songDuration, 3, "album1", 4.5, "standardProducer1");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // Showing catalogue
        System.out.println(controller.showCatalogue());

        // Registering playlist
        for(int i=0; i<1; i++) {
            controller.registerPlaylist("standardConsumer1", 2, "podcasts1");
            controller.registerPlaylist("standardConsumer1", 1, "default1");
            controller.registerPlaylist("standardConsumer1", 3, "songs1");
        }
        for(int i=0; i<1; i++) {
            controller.registerPlaylist("premiumConsumer2", 2, "podcasts1");
            controller.registerPlaylist("premiumConsumer2", 1, "default1");
            controller.registerPlaylist("premiumConsumer3", 3, "songs1");
        }

        // Show playlists
        System.out.println(controller.showConsumerPlaylists("standardConsumer1"));
        System.out.println(controller.showConsumerPlaylists("premiumConsumer2"));

        String flag = controller.searchConsumer("standardConsumer1").getPlaylists().get(0).getId();
        controller.addOrRemoveAudioToPlaylist(1,"standardConsumer1","songName1",flag);
        System.out.println(controller.showConsumerPlaylists("standardConsumer1"));
        controller.renamePlaylist(flag, "newPlaylistName");
        controller.addOrRemoveAudioToPlaylist(2,"standardConsumer1","songName1",flag);
        System.out.println(controller.showConsumerPlaylists("standardConsumer1"));

        System.out.println(controller.sharePlaylist("standardConsumer1", flag));


    }
}