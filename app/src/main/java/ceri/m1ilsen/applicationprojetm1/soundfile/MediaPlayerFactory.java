package ceri.m1ilsen.applicationprojetm1.soundfile;

import android.media.MediaPlayer;

// Helper for Lig-Aikuma integration
public class MediaPlayerFactory
{
    private static MediaPlayer _mediaPlayer = null;

    public static MediaPlayer getMediaPlayer()
    {
        if(_mediaPlayer==null)  _mediaPlayer = new MediaPlayer();
        return _mediaPlayer;
    }

    public static MediaPlayer getNewMediaPlayer()
    {
        _mediaPlayer = new MediaPlayer();
        return _mediaPlayer;
    }

    public static String _currentReadFile;

    // Memory for elicitation file
    public static String _elicit_source="", _elicit_rspk="";
}
