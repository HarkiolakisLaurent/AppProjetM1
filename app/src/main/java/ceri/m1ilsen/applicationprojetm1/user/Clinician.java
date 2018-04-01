package ceri.m1ilsen.applicationprojetm1.user;

import java.util.List;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Clinician {
    private String mail;
    private String password;
    private String pseudo;
    private List<Patient> patients;

    public Clinician(String mail, String password, String pseudo, List<Patient> patients) {
        this.mail = mail;
        this.password = password;
        this.pseudo = pseudo;
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

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
