package model;

public class PodcastPlaylist extends Playlist {

    public PodcastPlaylist(String name) {
        super(name);
    }

    @Override public void calculateId() {
        int[][] matrixId = super.getMatrixId();
        // only podcasts - t
        String newId = "";
        for(int i=0; i<matrixId.length; i++) {
            if(i == 2) {
                for(int j=0; j<matrixId.length; j++) newId += matrixId[j][i];
            } else if(i == 3) {
                for(int j=matrixId.length-1; j>=0; j--) newId += matrixId[j][i];
            } else {
                newId += matrixId[0][i];
            }
        }
        setId(newId);
    }

    @Override
    public boolean addAudio(Audio newAudio) {
        if( !(newAudio instanceof Podcast) || searchAudio(newAudio) != null) return false;
        return super.getAudios().add(newAudio);
    }
}
