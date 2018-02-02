package ceri.m1ilsen.applicationprojetm1.user;

import java.io.File;
import java.util.Date;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.comment.Comment;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.language.Language;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Patient implements IUser {
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
    private Date birthday;
    private boolean gender;
    private Language spokenLanguage;
    private Clinician clinicianInCharge;
    private Comment comment;
    private List<Session> sessions;

    public Patient(String mail, String password, String lastName, String firstName, Date birthday, boolean gender, Language spokenLanguage, Clinician clinicianInCharge, Comment comment, List<Session> sessions) {
        this.mail = mail;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.spokenLanguage = spokenLanguage;
        this.clinicianInCharge = clinicianInCharge;
        this.comment = comment;
        this.sessions = sessions;
    }

    @Override
    public void consultRecordFile(File recordFile) {

    }

    @Override
    public void deleteRecordFile(File recordFile) {

    }

    @Override
    public void consultProfile() {

    }

    @Override
    public void changePassword() {

    }

    public void consultExercise() {

    }

    public void updateResults(Exercise completedExercise) {

    }

    public void displayLastResults() {

    }

    public void displayResultsAsDashBoard() {

    }

    public Session startSession() {
        return null;
    }

    public void closeSession() {

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Language getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(Language spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public Clinician getClinicianInCharge() {
        return clinicianInCharge;
    }

    public void setClinicianInCharge(Clinician clinicianInCharge) {
        this.clinicianInCharge = clinicianInCharge;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
