package ceri.m1ilsen.applicationprojetm1.audio;

import java.io.File;

/**
 * Created by Laurent on 28/01/2018.
 */

public interface IAudioManager {
    File writeAudioFile(String audioFileName);
    void deleteAudioFile(String audioFileName);
}
