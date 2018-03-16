package ceri.m1ilsen.applicationprojetm1.exercise;

import java.util.Date;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.comment.Comment;
import ceri.m1ilsen.applicationprojetm1.task.Task;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Session {
    private Date creationDate;
    private List<Exercise> results;
    private Comment comment;
    private int patient_id;

    public Session(Date creationDate, List<Exercise> results, Comment comment, int patient_id) {
        this.creationDate = creationDate;
        this.results = results;
        this.comment = comment;
        this.patient_id = patient_id;
    }

    public Exercise startExercise(Task selectedTask) {
        return null;
    }

    public Exercise resumeExercise(Exercise selectedExercise) {
        return null;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Exercise> getResults() {
        return results;
    }

    public void setResults(List<Exercise> results) {
        this.results = results;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getPatient() {  return patient_id; }

    public void setPatient(int patient) { this.patient_id = patient; }
}
