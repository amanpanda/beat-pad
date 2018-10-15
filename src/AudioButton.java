import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.AudioClip;

/**
 * A button that has a corresponding audio file.
 * Created by laddhaa on 3/7/17.
 */
public class AudioButton {

    //Instance variables
    private ButtonBase btn;
    private AudioClip sound;
    private int[] beatsToPlayOn;
    private boolean status;

    /**
     * Instantiate an AudioButton.
     * @param input The javafx Button object that corresponds to a particular sound
     * @param m The audio that the button corresponds to
     * @param s Whether the button is on or off (probably should be instantiated to false, unless you're sure about
     *          what you're doing)
     * @param beatsToPlayOn The beats that the sound should trigger on, from 1 to Controller.BEATS_IN_MEASURE inclusive.
     */
    AudioButton(ButtonBase input, AudioClip m, boolean s, int[] beatsToPlayOn) {
        btn = input;
        sound = m;
        status = s;
        this.beatsToPlayOn = beatsToPlayOn;
    }

    /**
     * Get the button.
     * @return The ButtonBase that this object represents.
     */
    public ButtonBase getBtn() {
        return btn;
    }

    /**
     * Get the sound.
     * @return The AudioClip that this object represents.
     */
    public AudioClip getSound() {
        return sound;
    }

    /**
     * Change the sound of this button.
     * @param sound The sound to set this button to.
     */
    public void setSound(AudioClip sound) {
        this.sound = sound;
    }

    /**
     * Is the button turned on?
     * @return True if active, false otherwise.
     */
    public boolean isActive() {
        return status;
    }

    /**
     * Changes the status of the button, and graphically updates the button to reflect the change.
     * @param status True to turn the button on, false to turn off.
     */
    public void setStatus(boolean status) {
        if(btn instanceof ToggleButton){
            ((ToggleButton) btn).setSelected(status);
        }
        this.status = status;
    }

    /**
     * Gets the beats this sound will be triggered on.
     * @return An array of the beats the sound should be played on, from 0 to Controller.BEATS_IN_MEASURE inclusive.
     */
    public int[] getBeatsToPlayOn() {
        return beatsToPlayOn;
    }
}


