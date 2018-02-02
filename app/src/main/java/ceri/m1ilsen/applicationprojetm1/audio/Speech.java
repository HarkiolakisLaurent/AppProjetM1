package ceri.m1ilsen.applicationprojetm1.audio;

import java.io.File;
import java.util.Date;

import ceri.m1ilsen.applicationprojetm1.align.Align;

/**
 * Created by Laurent on 28/01/2018.
 */

public class Speech {
    private File recordFile;
    private Date recordDate;
    private double duration;
    private Align currentAlign;

    public Speech(File recordFile, Date recordDate, double duration, Align currentAlign) {
        this.recordFile = recordFile;
        this.recordDate = recordDate;
        this.duration = duration;
        this.currentAlign = currentAlign;
    }

    public File getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(File recordFile) {
        this.recordFile = recordFile;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Align getCurrentAlign() {
        return currentAlign;
    }

    public void setCurrentAlign(Align currentAlign) {
        this.currentAlign = currentAlign;
    }
}
