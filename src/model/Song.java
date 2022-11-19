package model;

public class Song extends Audio {
    private SongGenre genre;
    private String album;
    private double saleValue;
    private int numberOfSales;

    public Song(String producerId, String name, String pictureUrl,int duration, int genre, String album, double saleValue) {
        super(name, pictureUrl, duration, producerId);
        this.genre = SongGenre.values()[genre];
        this.album = album;
        this.saleValue = saleValue;
        this.numberOfSales = 0;
    }

    // GETTERS AND SETTERS
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

    // PLAYING

    /**
     * <pre>
     * <strong>Description: </strong> Prints all podcast information, including parsed duration (hh:mm:ss)
     * @return playbackInformation <strong>String</strong> The screen that will be shown to the user when he plays an audio
     * </pre>
     */
    @Override
    public String play() {
        int minutes = getDuration()/60;
        int seconds = getDuration()%60;
        return String.format("""
                \n========= Playing song =========
                %s • %s [%s]
                %02d:%02d left
                Enjoying this song? more in -> %s (album)
                ====== Press enter to exit ======
                """, getName(), getProducerName(), getGenre(), minutes, seconds, getAlbum());
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
