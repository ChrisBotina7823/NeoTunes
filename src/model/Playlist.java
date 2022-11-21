package model;

import java.util.ArrayList;
import java.util.Random;

public class Playlist {
    private String name;
    private String id;
    private int[][] matrixId;
    private ArrayList<Audio> audios;
    private PlaylistType type;

    public Playlist(String name) {
        this.name = name;
        this.audios = new ArrayList<>();
        updateId();
    }

    // GETTERS AND SETTERS

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
    public PlaylistType getType() {
        return type;
    }
    public void setType(PlaylistType type) {
        this.type = type;
    }

    // MATRIX AND ID

    /**
     * <pre>
         * <strong>Description: </strong> Updates the id by generating a new matrix and, therefore, a new code.
         * <strong>Pos: </strong> matrixId <strong>int[]</strong> Each position will be filled with a random value
         * <strong>Pos: </strong> id <strong>String</strong> Will be changed or initialized to the new generated id.
     * </pre>
     */
    public void updateId() {
        this.matrixId = generateRandomMatrix(6);
        this.id = calculateId();
    }

    /**
     * <pre>
         * <strong>Description: </strong> Fill the 6x6 matrix with random numbers between 0-9
         * <strong>Pre: </strong> matrixId <strong>int[]</strong> Must be initialized with random values
         * <strong>Pos: </strong> matrixId <strong>int[]</strong> Each position will be filled with a random value
         * @param size <strong>int</strong> The size of the new matrix
         * @return matrix <strong>int[][]</strong> The new random matrix
     * </pre>
     */
    public int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random rnd = new Random();
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                matrix[i][j] = rnd.nextInt(9);
            }
        }
        return matrix;
    }

    /**
     * <pre>
     * <strong>Description: </strong> It calculates the unique id of the playlist based on a pattern in the matrix.
     * For Only songs it is an N, Only Podcasts is a T, and AllAudios is the odd sums of rows and columns greater than 1
     * <strong>Pre: </strong> matrixId <strong>int[]</strong> Must be initialized and filled with random values
     * <strong>Pos: </strong> id <strong>String</strong> Will be updated with the new calculated value
     * @return newId <strong>String</strong> The new id for the playlist
     * </pre>
     */
    public String calculateId() {
        String newId = "";
        boolean onlySongs = true, onlyPodcasts = true;
        if(audios.size() == 0) {
            onlySongs = false;
            onlyPodcasts = false;
        }
        for(Audio audio: audios) {
            if(audio instanceof Song) onlyPodcasts = false;
            if(audio instanceof Podcast) onlySongs = false;
        }

        if(onlySongs) { // N shape
            setType(PlaylistType.SONGS);
            for(int i=0; i<matrixId.length; i++) {
                if(i == 0 || i == matrixId.length-1) {
                    for(int j=matrixId.length-1; j>=0; j--) {
                        newId += matrixId[j][i];
                    }
                } else {
                    newId += matrixId[i][i];
                }
            }
        } else if(onlyPodcasts) { // T shape
            setType(PlaylistType.PODCAST);
            for(int i=0; i<matrixId.length; i++) {
                if(i == 2) {
                    for (int[] ints : matrixId) newId += ints[i];
                } else if(i == 3) {
                    for(int j=matrixId.length-1; j>=0; j--) newId += matrixId[j][i];
                } else {
                    newId += matrixId[0][i];
                }
            }
        } else { // sum of odd numbers (greater than 1)
            setType(PlaylistType.ALL);
            for(int i=matrixId.length-1; i>=0; i--) {
                for(int j=matrixId[i].length-1; j>=0; j--) {
                    if( ( (i+j)%2 != 0 ) && ( (i+j) > 1 ) ) newId += matrixId[i][j];
                }
            }
        }
        return newId;
    }

    /**
     * <pre>
     * <strong>Description: </strong> It prints the matrix that generated the playlist identifier
     * <strong>pre: </strong> matrixId <strong>int[]</strong> Must be initialized
     * @param matrix <strong>int[][]</strong> the matrix to print
     * @return matrixStr <strong>String</strong> a readable version of the matrix
     * </pre>
     */
    public String showMatrixId(int[][] matrix) {
        String matrixStr = "";
        for (int[] ints : matrix) {
            matrixStr += "\n\t\t";
            for (int j = 0; j < ints.length; j++) {
                matrixStr += ints[j] + " ";
            }
        }
        return matrixStr;
    }

    // AUDIOS

    /**
     * <pre>
     * <strong>Description: </strong> It adds a new audio to the playlist's audios collection, ensuring that it is not already in it.
     * <strong>pre:</strong> audios <strong>ArrayList</strong> must be initialized
     * @param newAudio <strong>Audio</strong> the audio that is going to be added to the list
     * @return status <strong>boolean</strong> it will be false if the audio is already in the list
     * </pre>
     */
    public boolean addAudio(Audio newAudio) {
        if(searchAudio(newAudio) != null) return false;
        return audios.add(newAudio);
    }

    /**
     * <pre>
     * <strong>Description: </strong> It removes an existing audio from the playlist's audios collection, ensuring that it is inside it.
     * <strong>pre:</strong> audios <strong>ArrayList</strong> must be initialized
     * @param tmpAudio <strong>Audio</strong> the audio that is going to be removed from the list
     * @return status <strong>boolean</strong> it is false if the audio is not in the playlist
     * </pre>
     */
    public boolean removeAudio(Audio tmpAudio) {
        return audios.remove(tmpAudio);
    }

    /**
     * <pre>
     * <strong>Description:</strong> It searches and returns the audio that is the same as the given one
     * <strong>pre:</strong> audios <strong>ArrayList</strong> must be initialized
     * @param newAudio <strong>Audio</strong> The audio to be compared with each in the list
     * @return audio <strong>Audio</strong> the audio that matches with the given object
     * </pre>
     */
    public Audio searchAudio(Audio newAudio) {
        for(Audio audio : audios) {
            if(audio.equals(newAudio)) return audio;
        }
        return null;
    }

    /**
     * <pre>
     * <strong>Description: </strong> It prints the audios that are in the playlist
     * <strong>pre: </strong> audios <strong>ArrayList</strong> Must be initialized
     * @return audioList <strong>String</strong> A readable list of audios
     * </pre>
     */
    public String showAudios() {
        String audioList = "";
        for(Audio audio : audios) {
            audioList += "\n\t - " + audio;
        }
        return (audioList.equals("")) ? ("There are not audios yet") : (audioList);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "\nname='" + name + '\'' +
                "\naudios=" + audios.size() +
                "\ntype=" + type +
                "\nnewId='" + id + '\'' + showMatrixId(matrixId) +
                "\n}";
    }
}
