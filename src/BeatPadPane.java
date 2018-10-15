import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

/**
 * Class that holds all of the buttons for the beat pad in a single pane.
 **/
public class BeatPadPane {
    private static final double WINDOW_WIDTH = 650;
    private static final double WINDOW_HEIGHT = 300;

    private static final int SAMPLE_BUTTON_ROWS = 3;
    private static final int SAMPLE_BUTTON_COLS = 2;

    private static final int LOOP_BUTTON_ROWS = 3;
    private static final int LOOP_BUTTON_COLS = 3;

    private static final int BUTTON_HGAP = 20;
    private static final int BUTTON_VGAP = 20;

    private static final int BUTTON_DIMENSION = 100;

    private static final int GAP_BETWEEN_SETS =50;

    private static final int[][] beatLoops = {{0,8},
                                              {2,6,10,14},
                                              {4,12},
                                              {4,12},
                                              {2,7,9,11},
                                              {2,10},
                                              {0,8,12},
                                              {7,10},
                                              {4}};

    private Pane root;
    private AudioButton[][] sampleButtons;
    private AudioButton[][] loopButtons;
    private Controller controller;

    /**
     * Initialize an instance of the BeatPadPane
     */
    public BeatPadPane(Controller c){
        this.controller = c;
        root = buildRoot();
    }

    /**
     * Get the root node for the beat pad pane.
     * @return the root node for the beat pad pane
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Get an array of the buttons used to play the samples.
     * @return Button[][] - the sample buttons
     */
    public AudioButton[][] getSampleButtons() {
        return sampleButtons;
    }

    /**
     * Get an array of the buttons used to play the loops.
     * @return ToggleButton[][] - the toggle buttons
     */
    public AudioButton[][] getLoopButtons() {
        return loopButtons;
    }

    public void deselectAllLoopButtons(){
        for(AudioButton[] loopRow : loopButtons){
            for(AudioButton loopButton : loopRow){
                loopButton.setStatus(false);
            }
        }
    }

    /**
     * Turns a button on by setting its status to true
     * @param i The i coordinate of the button on the screen
     * @param j The j coordinate of the button on the screen
     */
    public void turnButtonOn(int i, int j){
        loopButtons[i][j].setStatus(true);
    }

    /*
     * Helper method that generates the root pane.
     */
    private Pane buildRoot(){
        Node sampleButtons = addSampleButtons();
        Node loopButtons = addLoopButtons();
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20, 0, 0, 40));
        root.setAlignment(Pos.CENTER);
        root.setHgap(GAP_BETWEEN_SETS);
        root.getChildren().add(sampleButtons);
        root.getChildren().add(loopButtons);
        root.setMinWidth(WINDOW_WIDTH);
        root.setMinHeight(WINDOW_HEIGHT);

        return root;
    }
    private AudioClip[] addSampleMedia(){
        AudioClip[] music = new AudioClip[SAMPLE_BUTTON_COLS*SAMPLE_BUTTON_ROWS];
        for(int i=0; i<music.length; i++){
            music[i] = new AudioClip(Paths.get("samples/"+i+".wav").toUri().toString());
        }
        return music;
    }
    /*
     * Helper method that generates the sample buttons.
     */
    private Node addSampleButtons(){
        GridPane grid = new GridPane();
        grid.setHgap(BUTTON_HGAP);
        grid.setVgap(BUTTON_VGAP);
        AudioClip[] music = addSampleMedia();
        int sampleLoop[]= new int[0];
        this.sampleButtons = new AudioButton[SAMPLE_BUTTON_ROWS][SAMPLE_BUTTON_COLS];
        for (int i = 0; i < sampleButtons.length; i++){
            for (int j = 0; j < sampleButtons[i].length; j++){
                Button curButton = new Button();
                curButton.getStyleClass().add("button");
                curButton.setPrefHeight(BUTTON_DIMENSION);
                curButton.setPrefWidth(BUTTON_DIMENSION);
                curButton.setMinHeight(BUTTON_DIMENSION);
                curButton.setMaxHeight(BUTTON_DIMENSION);
                sampleButtons[i][j] = new AudioButton(curButton, music[i*SAMPLE_BUTTON_COLS+j], false, sampleLoop);
                grid.add(curButton, j, i);
                final AudioButton currentAudioButton = sampleButtons[i][j];
                curButton.setOnAction(event -> {
                    Button b = (Button)event.getSource();
                    if(!currentAudioButton.isActive())
                    {
                        currentAudioButton.setStatus(true);
                        controller.triggerSamplePlayback(currentAudioButton.getSound());
                    }
                    else {
                        currentAudioButton.setStatus(false);
                        controller.triggerSamplePlayback(currentAudioButton.getSound());
                    }
                });
            }
        }
        return grid;
    }

    /*
     * Helper method that loads all of the loops.
     */
    private AudioClip[] addLoopMedia(){
        AudioClip[] music = new AudioClip[LOOP_BUTTON_COLS*LOOP_BUTTON_ROWS];
        int i;
        for(i=0; i < 9;i++) {
            music[i] = new AudioClip(Paths.get("loops/" + i + ".wav").toUri().toString());
        }
        return music;
    }

    /*
     * Helper method that generates the loop toggleButtons.
     */
    private Node addLoopButtons(){
        GridPane grid = new GridPane();
        grid.setHgap(BUTTON_HGAP);
        grid.setVgap(BUTTON_VGAP);
        this.loopButtons = new AudioButton[LOOP_BUTTON_ROWS][LOOP_BUTTON_COLS];

        AudioClip[] music = addLoopMedia();
        for (int i = 0; i < loopButtons.length; i++) {
            for (int j = 0; j < loopButtons.length; j++) {
                final ToggleButton curButton = new ToggleButton();
                curButton.setPrefHeight(BUTTON_DIMENSION);
                curButton.setPrefWidth(BUTTON_DIMENSION);
                curButton.setMinHeight(BUTTON_DIMENSION);
                curButton.setMaxHeight(BUTTON_DIMENSION);
                loopButtons[i][j] = new AudioButton(curButton, music[LOOP_BUTTON_COLS * i + j], false, beatLoops[LOOP_BUTTON_COLS * i + j]);
                grid.add(curButton, j, i);
                final AudioButton currentAudioButton = loopButtons[i][j];
                curButton.setOnAction(event -> {
                    if(!currentAudioButton.isActive())
                    {
                        currentAudioButton.setStatus(true);
                        controller.addSoundToModel(currentAudioButton.getBeatsToPlayOn(), currentAudioButton.getSound());
                    }
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
