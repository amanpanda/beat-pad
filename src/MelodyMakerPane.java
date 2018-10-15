import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

import static javafx.application.Application.launch;

/**
 * Class that holds all of the buttons for the melody maker in a single pane.
 **/
public class MelodyMakerPane {

    //Instance variables
    private static final int NUM_NOTES = 8;
    private static final int NUM_TIME = 16;
    private Pane root;
    private Controller controller;
    private AudioButton button[][]= new AudioButton[NUM_NOTES][NUM_TIME];


    /**
     * Initialize an instance of the MelodyMakerPane
     */
    public MelodyMakerPane (Controller c){
        this.controller = c;
        root = buildMelodyMaker(); }

    /**
     * Get the root node for the melody maker pane.
     * @return the root node for the melodymaker pane
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Adds the pre-stored media files to an AudioClip array which can then be used to play the music.
     * @return the array of AudioClip with all the sounds
     */

    public AudioClip[] addMedia(){
        AudioClip[] music = new AudioClip[NUM_NOTES];
        int i;
        for(i=0; i< NUM_NOTES;i++) {
             music[i] = new AudioClip(Paths.get("tones/" + i + ".wav").toUri().toString());
        }
        return music;
    }

    /**
     * Deselct all the buttons when the user chooses to clear all
     * @return nothing
     */
    public void deselectAllMelodyMakerButtons(){
        for(AudioButton[] buttonRow : button){
            for(AudioButton singleButton : buttonRow){
                singleButton.setStatus(false);
            }
        }
    }
    /**
     * Helper Method that generates the grid pane and creates all the buttons.
     */
    private GridPane buildMelodyMaker() {
        GridPane grid = new GridPane();
        String[] notes = {"C", "B", "A", "G", "F", "E", "D", "C"};
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 0, 10)); //margins around the whole grid
        int beatsToPlayOn[][] = new int [16][1];
        //Initialize all the sounds
        AudioClip[] music = addMedia();
        for (int i=0;i<NUM_NOTES;i++) {
            for (int j = 0; j < NUM_TIME; j++) {
                //Adding the scale labels on 0th column
                if(j == 0)
                {
                    Label label= new Label(notes[i]);
                    label.getStyleClass().add("melody-label");
                    grid.setConstraints(label,j, i);
                    grid.getChildren().add(label);

                }
                //Creating a new button and setting the width, text, and color
                final ToggleButton curButton= new ToggleButton();
                curButton.setPrefHeight(30);
                curButton.setPrefWidth(30);
                if(j%4 == 0) {
                    curButton.getStyleClass().add("grey-button");
                    Label label = new Label(Integer.toString(j/4 + 1));
                    label.getStyleClass().add("melody-label");
                    label.setPadding(new Insets(0, 0, 0, 10));
                    grid.setConstraints(label, j + 1, 9);
                    grid.getChildren().add(label);
                }
                //Adding it to the exact place in grid
                grid.setRowIndex(curButton, i);
                grid.setColumnIndex(curButton, j+1);
                grid.getChildren().add(curButton);
                //Initializing the array with the beat the sound should be played on
                beatsToPlayOn[j][0] = j;
                //Adding the button to the matrix of melody maker buttons
                button[i][j] = new AudioButton(curButton, music[i],false, beatsToPlayOn[j]);
                //creating a 'final' button to implement clicker
                final AudioButton currentAudioButton = button[i][j];
                curButton.setOnAction(event -> {
                    //If inactive, make active and add the sound to the model
                    if(currentAudioButton.isActive()==false)
                    {
                        currentAudioButton.setStatus(true);
                        controller.addSoundToModel(currentAudioButton.getBeatsToPlayOn(), currentAudioButton.getSound());
                    }
                    //If active, make inactive and remove the sound to the model
                    else {
                        currentAudioButton.setStatus(false);
                        controller.removeSoundFromModel(currentAudioButton.getBeatsToPlayOn(), currentAudioButton.getSound());
                    }
                });
            }
        }
        return grid;
    }
}
