package model;

public class Advertisement extends Audio {

    public Advertisement(String producerName, String name, String pictureUrl, int duration) {
        super(name, pictureUrl, duration, producerName);
    }

    /**
     * <pre>
     * <strong>Description: </strong> Prints all advertisement information, including parsed duration (mm:ss)
     * @return playbackInformation <strong>String</strong> The screen that will be shown to the user when he plays an audio
     * </pre>
     */
    @Override
    public String play() {
        int minutes = getDuration()/60;
        int seconds = getDuration()%60;
        return String.format("""
                ===== Playing advertisement =====
                %s • %s [AD]
                %02d:%02d left
                Switch to premium and enjoy benefits:
                - Music with no ads
                - Unlimited purchases
                ====== Press enter to skip ad ======
                """, getName(), getProducerName(), minutes, seconds);
    }
}
