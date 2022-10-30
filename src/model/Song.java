package model;

import java.util.Arrays;

public class Song extends Audio {
    private SongGenre genre;
    private String album;
    private double saleValue;
    private int numberOfSales;

    public Song(String name, String pictureUrl, int[] duration, int genre, String album, double saleValue) {
        super(name, pictureUrl, duration);

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

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + super.getName() + '\'' +
                ", pictureUrl='" + super.getPictureUrl() + '\'' +
                ", duration=" + Arrays.toString(super.getDuration()) +
                ", genre=" + genre.toString().toLowerCase() +
                ", album='" + album + '\'' +
                ", numberOfPlays='" + super.getNumberOfPlays() + '\'' +
                ", saleValue=" + saleValue +
                ", totalSales=" + numberOfSales +
                '}';
    }
}
