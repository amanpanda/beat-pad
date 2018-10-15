import javafx.scene.media.AudioClip;
import javafx.scene.media.AudioClip;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.lang.String.valueOf;

/**
 * The implementation of the ModelInterface.
 * Created by nachtm on 3/8/17.
 */
public class Model implements ModelInterface{

    //Instance variables
    private Map<Integer, Collection<AudioClip>> beatMatrix;
    private double volume;

    /**
     * Instantiate a model.
     */
    public Model(){
        beatMatrix = new HashMap<>();
        volume = 0.8;
        for(int i=0; i < Controller.BEATS_IN_MEASURE; i++){
            beatMatrix.put(i, new ConcurrentSkipListSet<>(Comparator.comparingInt(Object::hashCode)));
        }
    }

    @Override
    public void addAudioClipToMatrix(int beat, AudioClip m) {
        beatMatrix.get(beat).add(m);
    }

    @Override
    public boolean removeAudioClipFromMatrix(int beat, AudioClip m) {
        return beatMatrix.get(beat).remove(m);
    }

    @Override
    public void playSample(AudioClip sound) {
        if(sound.isPlaying()) {
            sound.stop();
        }
        sound.play();
    }

    @Override
    public void clearMatrix() {
        for(Collection l : beatMatrix.values()){
            l.clear();
        }
    }

    @Override
    public void playBeat(int beat) {
        Collection<AudioClip> beatToPlay = beatMatrix.get(beat);
        for(AudioClip sound : beatToPlay){
            sound.stop();
            sound.setVolume(volume);
            sound.play();
        }
    }

    @Override
    public void updateVolume(double newVolume) {
        volume = newVolume/100;
    }
}
