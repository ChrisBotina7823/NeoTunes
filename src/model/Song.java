package model;

import java.time.LocalDate;

public class Song extends Audio {
    private SongGenre genre;
    private String album;
    private double saleValue;
    private int numberOfSales;

    private LocalDate purchaseDate;

    public Song(String producerId, String name, String pictureUrl,int duration, int genre, String album, double saleValue) {
        super(name, pictureUrl, duration, producerId);

        if(genre < 0 || genre >= SongGenre.values().length) {
            String msg = "Select a genre within the range (1 to " + SongGenre.values().length + ")";
            throw new IllegalArgumentException(msg);
        } else {
            this.genre = SongGenre.values()[genre];
        }

        this.album = album;

        if(saleValue <= 0) {
            throw new IllegalArgumentException("Sale value must be greater than 0");
        } else {
            this.saleValue = saleValue;
        }

        this.numberOfSales = 0;
    }

    // GETTERS AND SETTERS

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public SongGenre getGenre() {
        return genre;
    }
    public void setGenre(SongGenre genre) {
        this.genre = genre;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public double getSaleValue() {
        return saleValue;
    }
    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }
    public int getNumberOfSales() {
        return numberOfSales;
    }
    public void increaseNumberOfSales() {
        this.numberOfSales++;
    }
    public double getTotalIncome() {
        return numberOfSales * saleValue;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + super.getName() + '\'' +
                ", pictureUrl='" + super.getPictureUrl() + '\'' +
                ", duration=" + super.getDuration() +
                ", genre=" + genre.toString().toLowerCase() +
                ", album='" + album + '\'' +
                ", numberOfPlays='" + super.getNumberOfPlays() + '\'' +
                ", saleValue=" + saleValue +
                ", totalSales=" + numberOfSales +
                '}';
    }
}
