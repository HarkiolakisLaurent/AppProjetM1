package ceri.m1ilsen.applicationprojetm1.exercise;

import java.io.File;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Configuration {
    private File acousticModel;
    private File phonetisedDictionary;

    public Configuration() {
        this.acousticModel = null;
        this.phonetisedDictionary = null;
    }

    public Configuration(File acousticModel, File phonetisedDictionary) {
        this.acousticModel = acousticModel;
        this.phonetisedDictionary = phonetisedDictionary;
    }

    public File getAcousticModel() {
        return acousticModel;
    }

    public void setAcousticModel(File acousticModel) {
        this.acousticModel = acousticModel;
    }

    public File getPhonetisedDictionary() {
        return phonetisedDictionary;
    }

    public void setPhonetisedDictionary(File phonetisedDictionary) {
        this.phonetisedDictionary = phonetisedDictionary;
    }
}
