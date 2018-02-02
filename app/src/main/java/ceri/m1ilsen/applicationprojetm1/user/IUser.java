package ceri.m1ilsen.applicationprojetm1.user;

import java.io.File;

/**
 * Created by Laurent on 28/01/2018.
 */

public interface IUser {

    void consultRecordFile(File recordFile);
    void deleteRecordFile(File recordFile);
    void consultProfile();
    void changePassword();

}
