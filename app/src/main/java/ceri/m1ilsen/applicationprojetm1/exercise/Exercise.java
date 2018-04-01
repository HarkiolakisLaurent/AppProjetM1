package ceri.m1ilsen.applicationprojetm1.exercise;

import java.io.File;

import ceri.m1ilsen.applicationprojetm1.task.Task;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Exercise {
    private String name;
    private Task task;
    private File words;
    private double maxDuration;
    private int readWordsCount;
    private File recordFile;
    private String comment;
    private Configuration currentConfiguration;

    public Exercise(String name, Task task, File words, double maxDuration, int readWordsCount, File recordFile, String comment, Configuration currentConfiguration) {
        this.name = name;
        this.task = task;
        this.words = words;
        this.maxDuration = maxDuration;
        this.readWordsCount = readWordsCount;
        this.recordFile = recordFile;
        this.comment = comment;
        this.currentConfiguration = currentConfiguration;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public File getWords() {
        return words;
    }

    public void setWords(File words) {
        this.words = words;
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

    public File getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(File recordFile) {
        this.recordFile = recordFile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }
}
