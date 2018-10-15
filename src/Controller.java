import javafx.scene.media.AudioClip;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

/***
 * A controller in the model-view-controller design pattern. Takes in information from the view and updates both the
 * model and the view.
 * Also contains the main loop that controls the timing of the music.
 */
public class Controller {

    //Instance variables
    private ModelInterface model;
    private static final int TIME_BETWEEN_BEATS = 250;
    public static final int BEATS_IN_MEASURE = 16;
    private MainPane view;

    /**
     * Instantiates a Controller with a reference to a model and a MainPane.
     * @param model The ModelInterface representing the music to play.
     * @param mp The view that the user interacts with.
     */
    public Controller(ModelInterface model, MainPane mp) {
        this.model = model;
        this.view = mp;
    }

    /**
     * Adds a sound to the currently playing music.
     * @param beatToPlayOn The beats that the sound will trigger on, from 0 to Controller.BEATS_IN_MEASURE inclusive.
     * @param sound An AudioClip that holds the sound to play.
     */
    public void addSoundToModel(int[] beatToPlayOn, AudioClip sound) {
        for(int beat : beatToPlayOn){
            model.addAudioClipToMatrix(beat, sound);
        }
    }

    /**
     * Removes a sound from the currently playing music.
     * @param beatWasPlayedOn The beats that the sound was triggered on, from 0 to Controller.BEATS_IN_MEASURE inclusive.
     * @param sound The AudioClip representing the sound to remove. Must be .equal() to the AudioClip that was added in
     *              order to successfully remove.
     */
    public void removeSoundFromModel(int[] beatWasPlayedOn, AudioClip sound) {
        for(int beat : beatWasPlayedOn){
            model.removeAudioClipFromMatrix(beat, sound);
        }
    }

    /**
     * Tell the view to reset the melody and loop buttons.
     */
    public void clearLoopAndMelodyButtons(){
        view.getBeatPadPane().deselectAllLoopButtons();
        view.getMelodyMakerPane().deselectAllMelodyMakerButtons();
    }

    /**
     * Turns on a particular loop. Updates both the graphical interface and adds the sound to the model.
     * @param i The i index of the loop in the beat pad display.
     * @param j The j index of the loop in the beat pad display.
     */
    public void turnOnLoop(int i, int j){
        view.getBeatPadPane().turnButtonOn(i,j);
        AudioButton btn = view.getBeatPadPane().getLoopButtons()[i][j];
        addSoundToModel(btn.getBeatsToPlayOn(), btn.getSound());
    }

    /**
     * Play a sample right now.
     * @param sound The sound to play.
     */
    public void triggerSamplePlayback(AudioClip sound) { model.playSample(sound); }

    /**
     * Clear all the sounds from the model-- stop playing music.
     */
    public void removeAllSoundsFromModel(){
        model.clearMatrix();
    }

    /**
     * Changes the volume of the model.
     * @param newVolume The new volume to play at.
     */
    public void updateVolume(double newVolume) {
        model.updateVolume(newVolume);
    }

    /**
     * The loop that controls when music is played. Updates the counter in the side bar, and triggers the model to play
     * the music at the given beat.
     * @throws InterruptedException
     */
    public void mainLoop() throws InterruptedException {
        int beat = 0;

        while(view.isOpen()){
            try {
                model.playBeat(beat);
                if(beat%4 == 0) {
                    final int currBeat = beat;
                    Platform.runLater(() -> view.getSideBarPane().updateCounter(currBeat));
                }
                beat++;
                beat = beat % BEATS_IN_MEASURE;
                TimeUnit.MILLISECONDS.sleep(TIME_BETWEEN_BEATS);
            } catch (NullPointerException e){
                break;
            }
        }
    }

}
