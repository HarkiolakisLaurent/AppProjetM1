package ceri.m1ilsen.applicationprojetm1.user;

import java.util.List;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Clinician {
    private String mail;
    private String password;
    private String pseudo;

    private String favouriteWord;
    private List<Patient> patients;

    public Clinician(String mail, String password, String pseudo, String favouriteWord, List<Patient> patients) {
        this.mail = mail;
        this.password = password;
        this.pseudo = pseudo;
        this.favouriteWord = favouriteWord;
        this.patients = patients;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFavouriteWord() { return favouriteWord; }

    public void setFavouriteWord(String favouriteWord) { this.favouriteWord = favouriteWord; }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}
