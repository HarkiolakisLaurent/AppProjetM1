package ceri.m1ilsen.applicationprojetm1.exercise;

import java.io.File;

import ceri.m1ilsen.applicationprojetm1.task.Task;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Exercise {
    private String name;
    private String task;
    private double maxDuration;
    private int readWordsCount;
    private Configuration currentConfiguration;

    public Exercise(String name, String task, double maxDuration, Configuration currentConfiguration, int readWordsCount) {
        this.name = name;
        this.task = task;
        this.maxDuration = maxDuration;
        this.currentConfiguration = currentConfiguration;
        this.readWordsCount = readWordsCount;
    }

    public Exercise(String name, String task, int readWordsCount) {
        this.name = name;
        this.task = task;
        this.readWordsCount = readWordsCount;
    }

    public void doExercise() {

    }

    private double executeAlign(File speechRecordFile) {
        return 0.0;
    }

    private void updateRecordFile(File lastSpeechFile) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getReadWordsCount() {
        return readWordsCount;
    }

    public void setReadWordsCount(int readWordsCount) {
        this.readWordsCount = readWordsCount;
    }

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }
}
