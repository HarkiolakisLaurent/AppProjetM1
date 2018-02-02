package ceri.m1ilsen.applicationprojetm1.align;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Align {
    private File pronouncedText;

    public Align(File prononcedText) {
        this.pronouncedText = pronouncedText;
    }

    public void doAlign() {

    }

    public List<Vector> signalParameterization() {
        return null;
    }

    public List<Vector> acousticModeling() {
        return null;
    }

    public boolean checkInPhonetisedDictionary() {
        return true;
    }

    public double viterbi(List<Vector> phonemes) {
        return 0.0;
    }

    public File getPronouncedText() {
        return pronouncedText;
    }

    public void setPronouncedText(File pronouncedText) {
        this.pronouncedText = pronouncedText;
    }
}
