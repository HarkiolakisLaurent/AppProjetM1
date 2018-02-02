package ceri.m1ilsen.applicationprojetm1.user;

import java.io.File;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.exercise.Configuration;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.language.Language;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Clinician implements IUser {
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
    private List<Patient> patients;

    public Clinician(String mail, String password, String lastName, String firstName, List<Patient> patients) {
        this.mail = mail;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patients = patients;
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

    public void consultSettings() {

    }

    public Exercise createExercise(Patient selectedPatient, Exercise configuredExercise) {
        return null;
    }

    public Patient createPatient(String mail, String password, String lastName, String firstName, int patientAge, boolean genre, Language patientLanguage) {
        return null;
    }

    public Configuration createConfiguration(File acousticModel, File phonetisedDictionary) {
        return null;
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

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
