package model;

public class PodcastPlaylist extends Playlist {

    public PodcastPlaylist(String name) {
        super(name);
    }

    @Override public String calculateId() {
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
        return newId;
    }

    @Override
    public boolean addAudio(Audio newAudio) {
        if(searchAudio(newAudio) != null) return false;
        if(!(newAudio instanceof Podcast)) return false;
        return getAudios().add(newAudio);
    }
}
