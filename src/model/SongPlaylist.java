package model;

public class SongPlaylist extends Playlist {

    public SongPlaylist(String name) {
        super(name);
    }

    @Override
    public String calculateId() {
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
        return newId;
    }

    @Override
    public boolean addAudio(Audio newAudio) {
        if(searchAudio(newAudio) != null ) return false;
        if(!(newAudio instanceof Song) ) return false;
        return getAudios().add(newAudio);
    }
}
