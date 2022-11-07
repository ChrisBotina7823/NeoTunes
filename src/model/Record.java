package model;

import java.time.LocalDateTime;

public class Record {
    private String consumerName;
    private Audio audio;
    private LocalDateTime date;

    public Record(String consumerName, Audio audio) {
        this.consumerName = consumerName;
        this.audio = audio;
        this.date = LocalDateTime.now();
    }

    // GETTERS AND SETTERS

    public String getConsumerName() {
        return consumerName;
    }
    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
    public Audio getAudio() {
        return audio;
    }
    public void setAudio(Audio audio) {
        this.audio = audio;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Record{" +
                "consumerName='" + consumerName + '\'' +
                ", audio=" + audio.getName() +
                ", date=" + date +
                '}';
    }
}
