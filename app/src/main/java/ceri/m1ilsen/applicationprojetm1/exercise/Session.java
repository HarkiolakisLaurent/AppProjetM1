package ceri.m1ilsen.applicationprojetm1.exercise;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Session {
    private String name;
    private double results;
    private String comment;

    public Session(String name, double results, String comment) {
        this.name = name;
        this.results = results;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
