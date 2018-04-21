package ceri.m1ilsen.applicationprojetm1.exercise;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Exercise {
    private String name;
    private String task;
    private String creationDate;
    private double maxDuration;
    private int readWordsCount;
    private double score;
    private int session;
    private Configuration currentConfiguration;

    public Exercise(String name, String task, String creationDate, double maxDuration, Configuration currentConfiguration, int readWordsCount, int session) {
        this.name = name;
        this.task = task;
        this.creationDate = creationDate;
        this.maxDuration = maxDuration;
        this.currentConfiguration = currentConfiguration;
        this.readWordsCount = readWordsCount;
        this.session = session;
        this.score = 0;
    }

    public Exercise(String name, String task, String creationDate, int readWordsCount, int session) {
        this.name = name;
        this.task = task;
        this.creationDate = creationDate;
        this.readWordsCount = readWordsCount;
        this.session = session;
        this.score = 0;
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

    public String getCreationDate() { return creationDate; }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getReadWordsCount() {
        return readWordsCount;
    }

    public void setReadWordsCount(int readWordsCount) {
        this.readWordsCount = readWordsCount;
    }

    public double getScore() { return score; }

    public void setScore(double score) { this.score = score; }

    public int getSession() { return session; }

    public void setSession(int session) { this.session = session; }

    public Configuration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(Configuration currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }
}
