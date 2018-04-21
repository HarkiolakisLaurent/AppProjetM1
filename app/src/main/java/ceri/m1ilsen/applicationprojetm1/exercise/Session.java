package ceri.m1ilsen.applicationprojetm1.exercise;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Session {
    private String title;
    private double results;
    private String comment;

    public Session(String title, double results, String comment) {
        this.title = title;
        this.results = results;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getResults() {
        return results;
    }

    public void setResults(double results) {
        this.results = results;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
