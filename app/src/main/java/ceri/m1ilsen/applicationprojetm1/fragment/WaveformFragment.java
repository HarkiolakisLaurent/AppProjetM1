package ceri.m1ilsen.applicationprojetm1.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.soundfile.CheapSoundFile;
import ceri.m1ilsen.applicationprojetm1.soundfile.MediaPlayerFactory;
import ceri.m1ilsen.applicationprojetm1.soundfile.Segment;
import ceri.m1ilsen.applicationprojetm1.soundfile.WavFile;
import ceri.m1ilsen.applicationprojetm1.soundfile.WavFileException;
import ceri.m1ilsen.applicationprojetm1.ui.WaveformView;

/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * To implement this, declare this line on your Activity
 * public static class ImplementWaveformFragment extends WaveformFragment {}
 *
 * And put this on your onCreat
 * ImplementWaveformFragment wf = new ImplementWaveformFragment();
 * wf.setFileName(your_file_name);
 * getSupportFragmentManager().beginTransaction()
                .add(R.id.container, wf)
                .commit();
*/

/**
 * Keeps track of the waveform display, current horizontal offset, marker handles,
 * start / end text boxes, and handles all of the buttons and controls
 *
 * Modified by Anna Stępień <anna.stepien@semantive.com>
 * Modified by Baudson Guillaume <baudson.guillaume@hotmail.fr>
 */
public abstract class WaveformFragment extends Fragment implements WaveformView.WaveformListener
{
    public static final String TAG = "WaveformFragment";

    protected long mLoadingLastUpdateTime;
    protected boolean mLoadingKeepGoing;
    protected ProgressDialog mProgressDialog;
    protected CheapSoundFile mSoundFile;
    protected File mFile;
    protected String mFilename;
    protected WaveformView mWaveformView;
    protected boolean mKeyDown;
    protected int mWidth;
    protected int mMaxPos;
    protected int mStartPos;
    protected int mEndPos;
    protected int mOffset;
    protected int mOffsetGoal;
    protected int mFlingVelocity;
    protected int mPlayStartMsec;
    protected int mPlayStartOffset;
    protected int mPlayEndMsec;
    protected Handler mHandler;
    protected boolean mIsPlaying;
    protected MediaPlayer mPlayer;
    protected boolean mTouchDragging;
    protected float mTouchStart;
    protected int mTouchInitialOffset;
    protected long mWaveformTouchStartMsec;
    protected float mDensity;
    private String _fileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_waveform, container, false);
        loadGui(view);
        if (mSoundFile == null)
            loadFromFile();
        else
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    finishOpeningSoundFile();
                }
            });

        return view;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        mPlayer = null;
        mIsPlaying = false;
        mFilename = _fileName;
        mSoundFile = null;
        mKeyDown = false;
        mHandler = new Handler();
        onPlay(0);
    }

    @Override
    public void onDestroy() {
        //If first player is not work to destroy it.
        /*if(mPlayer != null && mPlayer.isPlaying())
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }*/

        mSoundFile = null;
        mWaveformView = null;
        super.onDestroy();
    }

    public void setFileName(String path)
    {
        _fileName = path;

    }

    //
    // WaveformListener CONTROLER
    //

    /**
     * Every time we get a message that our waveform drew, see if we need to
     * animate and trigger another redraw.
     */
    public void waveformDraw() {
        mWidth = mWaveformView.getMeasuredWidth();
        if((mOffsetGoal != mOffset && !mKeyDown) || mIsPlaying || mFlingVelocity != 0)
            updateDisplay();
    }

    public void waveformTouchStart(float x)
    {
        mTouchDragging = true;
        mTouchStart = x;
        mTouchInitialOffset = mOffset;
        mFlingVelocity = 0;
        mWaveformTouchStartMsec = System.currentTimeMillis();
    }

    public void waveformTouchMove(float x)
    {
        mOffset = trap((int) (mTouchInitialOffset + (mTouchStart - x)));
        updateDisplay();
    }

    public void waveformTouchEnd()
    {
        mTouchDragging = false;
        mOffsetGoal = mOffset;
    }

    public void waveformFling(float vx)
    {
        mTouchDragging = false;
        mOffsetGoal = mOffset;
        mFlingVelocity = (int) (-vx);
        updateDisplay();
    }

    public void waveformZoomIn()
    {
        mWaveformView.zoomIn();
        mStartPos = mWaveformView.getStart();
        mEndPos = mWaveformView.getEnd();
        mMaxPos = mWaveformView.maxPos();
        mOffset = mWaveformView.getOffset();
        mOffsetGoal = mOffset;
        updateDisplay();
    }

    public void waveformZoomOut()
    {
        mWaveformView.zoomOut();
        mStartPos = mWaveformView.getStart();
        mEndPos = mWaveformView.getEnd();
        mMaxPos = mWaveformView.maxPos();
        mOffset = mWaveformView.getOffset();
        mOffsetGoal = mOffset;
        updateDisplay();
    }

    //
    // Internal methods
    //

    protected void loadGui(View view)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;

        mWaveformView = (WaveformView) view.findViewById(R.id.waveform);
        mWaveformView.setListener(this);
        mWaveformView.setSegments(getSegments());

        mMaxPos = 0;

        if (mSoundFile != null && !mWaveformView.hasSoundFile()) {
            mWaveformView.setSoundFile(mSoundFile);
            mWaveformView.recomputeHeights(mDensity);
            mMaxPos = mWaveformView.maxPos();
        }

        updateDisplay();
    }

    protected void loadFromFile()
    {
        mFile = new File(mFilename);
        mLoadingLastUpdateTime = System.currentTimeMillis();
        mLoadingKeepGoing = true;
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle(R.string.progress_dialog_loading);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {mLoadingKeepGoing = false;}});
        mProgressDialog.show();

        // Create the MediaPlayer in a background thread
        new Thread() {
            public void run() {
                try {
                    MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
                    if(player.getDuration()==-1) {  //If is not init
                        player.setDataSource(mFile.getAbsolutePath());
                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        player.prepare();
                    }
                    mPlayer = player;
                } catch (final java.io.IOException ignored) {}
            }
        }.start();

        // Load the sound file in a background thread
        new Thread() {
            public void run() {
                try {
                    mSoundFile = CheapSoundFile.create(mFile.getAbsolutePath(), new ImplementedProgressListener());
                } catch (final Exception e) {
                    mProgressDialog.dismiss();
                    return;
                }
                if (mLoadingKeepGoing)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            finishOpeningSoundFile();
                        }
                    });
            }
        }.start();
    }

    protected void finishOpeningSoundFile()
    {
        mWaveformView.setSoundFile(mSoundFile);
        mWaveformView.recomputeHeights(mDensity);
        mMaxPos = mWaveformView.maxPos();
        mTouchDragging = false;
        mOffset = 0;
        mOffsetGoal = 0;
        mFlingVelocity = 0;
        resetPositions();
        mProgressDialog.dismiss();
        updateDisplay();
    }

    protected synchronized void updateDisplay()
    {
        if (mIsPlaying)
        {
            int now = mPlayer.getCurrentPosition() + mPlayStartOffset, frames = mWaveformView.millisecsToPixels(now);
            mWaveformView.setPlayback(frames);
            setOffsetGoalNoUpdate(frames - mWidth / 2);
            if(now >= mPlayEndMsec)
                handlePause();
        }

        if(!mTouchDragging)
        {
            if (mFlingVelocity != 0)
            {
                mOffset = mFlingVelocity / 30;
                if(mFlingVelocity > 80)         mFlingVelocity -= 80;
                else if (mFlingVelocity < -80)  mFlingVelocity += 80;
                else                            mFlingVelocity = 0;

                if (mOffset + mWidth / 2 > mMaxPos) {
                    mOffset = mMaxPos - mWidth / 2;
                    mFlingVelocity = 0;
                }
                if (mOffset < 0) {
                    mOffset = 0;
                    mFlingVelocity = 0;
                }
                mOffsetGoal = mOffset;
            }
            else
            {
                int offsetDelta = mOffsetGoal - mOffset;
                if(offsetDelta > 10)        mOffset += (offsetDelta / 10);
                else if(offsetDelta > 0)    mOffset += 1;
                else if(offsetDelta < -10)  mOffset += (offsetDelta / 10);
                else if(offsetDelta < 0)    mOffset += -1;
                else                        mOffset += 0;
            }
        }

        mWaveformView.setParameters(mStartPos, mEndPos, mOffset);
        mWaveformView.invalidate();
    }

    protected void resetPositions()
    {
        mStartPos = 0;
        mEndPos = mMaxPos;
    }

    protected int trap(int pos)
    {
        if (pos < 0)        return 0;
        if (pos > mMaxPos)  return mMaxPos;
                            return pos;
    }

    protected void setOffsetGoalNoUpdate(int offset) {
        if (mTouchDragging) return;
        mOffsetGoal = offset;
        if (mOffsetGoal + mWidth / 2 > mMaxPos)
            mOffsetGoal = mMaxPos - mWidth / 2;
        if (mOffsetGoal < 0)
            mOffsetGoal = 0;
    }

    protected synchronized void handlePause()
    {
        if(mPlayer != null && mPlayer.isPlaying())
            mPlayer.pause();
        mWaveformView.setPlayback(-1);
        mIsPlaying = false;
    }

    protected synchronized void onPlay(int startPosition)
    {
        if(mIsPlaying) {
            handlePause();
            return;
        }
        if(mPlayer == null)
            return;

        mPlayStartMsec = mWaveformView.pixelsToMillisecs(startPosition);
        if (startPosition < mStartPos)
            mPlayEndMsec = mWaveformView.pixelsToMillisecs(mStartPos);
        else if (startPosition > mEndPos)
            mPlayEndMsec = mWaveformView.pixelsToMillisecs(mMaxPos);
        else
            mPlayEndMsec = mWaveformView.pixelsToMillisecs(mEndPos);

        mPlayStartOffset = 0;

        int startByte = mSoundFile.getSeekableFrameOffset(mWaveformView.secondsToFrames(mPlayStartMsec*0.001)),
                endByte = mSoundFile.getSeekableFrameOffset(mWaveformView.secondsToFrames(mPlayEndMsec*0.001));
        if (startByte >= 0 && endByte >= 0)
        {
            try {
                mPlayer.reset();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(new FileInputStream(mFile.getAbsolutePath()).getFD(),startByte, endByte - startByte);
                mPlayer.prepare();
                mPlayStartOffset = mPlayStartMsec;
            } catch (Exception e) {
                mPlayer.reset();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mPlayer.setDataSource(mFile.getAbsolutePath());
                    mPlayer.prepare();
                } catch (Exception e2) {}
                mPlayStartOffset = 0;
            }
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override public void onCompletion(MediaPlayer mp) {
                handlePause();
            }});
        mIsPlaying = true;

        if(mPlayStartOffset == 0)
            mPlayer.seekTo(mPlayStartMsec);
        mPlayer.start();
        updateDisplay();

    }

    protected List<Segment> getSegments() {
        return null;
    }

    // Substitute for lambda on java before 1.8
    private class ImplementedProgressListener implements CheapSoundFile.ProgressListener
    {
        public boolean reportProgress(double fractionComplete)
        {
            long now = System.currentTimeMillis();
            if(now - mLoadingLastUpdateTime > 100)
            {
                mProgressDialog.setProgress((int) (mProgressDialog.getMax() * fractionComplete));
                mLoadingLastUpdateTime = now;
            }
            return mLoadingKeepGoing;
        }
    }

    protected boolean first_play = true;
    public void setMediaPlay(boolean mediaPlay)
    {
        // I don't know why it's work but it's work. You can probably optimise this. <3
        this.mIsPlaying = mediaPlay;
        if(mediaPlay)
        {
            if(first_play)
            {
                first_play = false;
                mPlayStartMsec = mWaveformView.pixelsToMillisecs(mOffset);
                if(mOffset < mStartPos)
                    mPlayEndMsec = mWaveformView.pixelsToMillisecs(mStartPos);
                else if(mOffset > mEndPos)
                    mPlayEndMsec = mWaveformView.pixelsToMillisecs(mMaxPos);
                else
                    mPlayEndMsec = mWaveformView.pixelsToMillisecs(mEndPos);
                mPlayStartOffset = 0;
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override public void onCompletion(MediaPlayer mp) {handlePause();}});
            mPlayer.seekTo(mPlayStartMsec);
            }
            updateDisplay();
        }
    }
}