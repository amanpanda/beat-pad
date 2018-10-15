import javafx.scene.media.AudioClip;


/** Interface for Model class*/
public interface ModelInterface {

    /** Add a particular sound to play matrix */
    void addAudioClipToMatrix(int beat, AudioClip m);

    /** Remove a particular sound to play matrix */
    boolean removeAudioClipFromMatrix(int beat, AudioClip m);

    /** Play a sample clip m */
    void playSample(AudioClip m);

    /** Clear all sounds from the matrix */
    void clearMatrix();

    /** Play a sound at a particular beat */
    void playBeat(int beat);

    /** As the volume slider changes, change the volume the model is playing audio clips at */
    void updateVolume(double newVolume);

}
