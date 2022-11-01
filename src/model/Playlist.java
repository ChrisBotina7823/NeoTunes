package model;

import java.util.ArrayList;
import java.util.Random;

public class Playlist {
    private String name;
    private String id;
    private int[][] matrixId;
    private ArrayList<Audio> audios;

    public Playlist(String name) {
        this.name = name;
        this.audios = new ArrayList<Audio>();
        this.matrixId = new int[6][6];
        calculateMatrixId();
        calculateId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int[][] getMatrixId() {
        return matrixId;
    }

    public void setMatrixId(int[][] matrixId) {
        this.matrixId = matrixId;
    }

    public ArrayList<Audio> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<Audio> audios) {
        this.audios = audios;
    }

    public String showMatrixId() {
        String matrixStr = "";
        for(int i=0; i<matrixId.length; i++) {
            for(int j=0; j<matrixId.length; j++) {
                matrixStr += matrixId[i][j] + " ";
            }
            matrixStr += "\n";
        }
        return matrixStr;
    }

    public String showAudios() {
        String audioList = "";
        for(Audio audio : audios) {
            audioList += "\n\t - " + audio.getName();
        }
        return (audioList.equals("")) ? ("There are not songs yet") : (audioList);
    }

    public void calculateMatrixId() {
        Random rnd = new Random();
        for(int i=0; i<matrixId.length; i++) {
            for(int j=0; j<matrixId[i].length; j++) {
                matrixId[i][j] = rnd.nextInt(9);
            }
        }
    }

    public void calculateId() {
        // songs and podcasts
        String newId = "";
        for(int i=matrixId.length-1; i>=0; i--) {
            for(int j=matrixId[i].length-1; j>=0; j--) {
                if( ( (i+j)%2 != 0 ) && ( (i+j) > 1 ) ) newId += matrixId[i][j];
            }
        }
        setId(newId);
    }

    public boolean addAudio(Audio newAudio) {
        if(searchAudio(newAudio) != null) return false;
        return audios.add(newAudio);
    }
    public boolean removeAudio(Audio tmpAudio) {
        return audios.remove(tmpAudio);
    }

    public Audio searchAudio(Audio newAudio) {
        for(Audio audio : audios) {
            if(audio.equals(newAudio)) return audio;
        }
        return null;
    }

    public String printMatrix(int[][] matrix) {
        String matrixStr = "";
        for(int i=0; i<matrix.length; i++) {
            matrixStr += "\n\t\t";
            for(int j=0; j<matrix[i].length; j++) {
                matrixStr += matrix[i][j] + " ";
            }
        }
        return matrixStr;
    }

    @Override
    public String toString() {
        return "----- Playlist information -----" +
                "\nname = '" + name + '\'' +
                "\nid = '" + id + '\'' +
                printMatrix(matrixId) +
                "";
    }
}
