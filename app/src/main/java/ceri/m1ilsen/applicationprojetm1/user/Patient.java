package ceri.m1ilsen.applicationprojetm1.user;

import java.util.Date;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.language.Language;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Patient {
    private String mail;
    private String password;
    private String pseudo;
    private String lastName;
    private String firstName;
    private Date birthday;
    private boolean gender;
    private Language spokenLanguage;
    private int clinicianInCharge;
    private String comment;
    private String favouriteWord;
    private List<Session> sessions;

    public Patient(String mail, String password, String pseudo, String lastName, String firstName, Date birthday, boolean gender, Language spokenLanguage, int clinicianInCharge, String comment, String favouriteWord, List<Session> sessions) {
        this.mail = mail;
        this.password = password;
        this.pseudo=pseudo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.spokenLanguage = spokenLanguage;
        this.clinicianInCharge = clinicianInCharge;
        this.comment = comment;
        this.favouriteWord = favouriteWord;
        this.sessions = sessions;
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

    public int getClinicianInCharge() {
        return clinicianInCharge;
    }

    public void setClinicianInCharge(int clinicianInCharge) { this.clinicianInCharge = clinicianInCharge; }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getComment() {
        return comment;
    }

    public String getFavouriteWord() { return favouriteWord; }

    public void setFavouriteWord(String favouriteWord) { this.favouriteWord = favouriteWord; }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
