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
        manager.test();
        // manager.showMenu();
    }

    public void test() {

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
                    Option:\s""");
            operation = sc.nextInt();
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

    public void registerProducer() {
    }
    public void registerConsumer() {

    }
    public void registerAudio() {

    }
    public void registerPlaylist() {

    }

    // INTERACTION

    public void editPlaylist() {

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