package model;

public class SongPlaylist extends Playlist {

    public SongPlaylist(String name) {
        super(name);
    }

    @Override
    public void calculateId() {
        int[][] matrixId = getMatrixId();
        // only songs - n
        String newId = "";
        for(int i=0; i<matrixId.length; i++) {
            if(i == 0 || i == matrixId.length-1) {
                for(int j=matrixId.length-1; j>=0; j--) {
                    newId += matrixId[j][i];
                }
            } else {
                newId += matrixId[i][i];
            }
        }
        setId(newId);
    }

    @Override
    public void addAudio(Audio newAudio) {
        if(searchAudio(newAudio) != null ) throw new IllegalArgumentException("Audio is already in playlist");
        if(!(newAudio instanceof Song) ) throw new IllegalArgumentException("This playlist only supports songs");
        getAudios().add(newAudio);
    }
}
