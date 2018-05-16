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

package ceri.m1ilsen.applicationprojetm1.soundfile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CheapSoundFile is the parent class of several subclasses that each
 * do a "cheap" scan of various sound file formats, parsing as little
 * as possible in order to understand the high-level frame structure
 * and get a rough estimate of the volume level of each frame.  Each
 * subclass is able to:
 *  - open a sound file
 *  - return the sample rate and number of frames
 *  - return an approximation of the volume level of each frame
 *
 * A frame should represent no less than 1 ms and no more than 100 ms of
 * audio.  This is compatible with the native frame sizes of most audio
 * file formats already, but if not, this class should expose virtual
 * frames in that size range.
 *
 * Modified by Anna Stępień <anna.stepien@semantive.com>
 * Modified by Baudson Guillaume <baudson.guillaume@hotmail.fr>
 */
public class CheapSoundFile
{
    public interface ProgressListener {
        /**
         * Will be called by the CheapSoundFile subclass periodically
         * with values between 0.0 and 1.0.  Return true to continue
         * loading the file, and false to cancel.
         */
        boolean reportProgress(double fractionComplete);
    }

    public interface Factory {
        CheapSoundFile create();
        String[] getSupportedExtensions();
    }

    private static Factory[] sSubclassFactories = new Factory[] {
        CheapAAC.getFactory(),
        CheapAMR.getFactory(),
        CheapMP3.getFactory(),
        CheapWAV.getFactory(),
    };

    private static ArrayList<String> sSupportedExtensions = new ArrayList<>();
    private static HashMap<String, Factory> sExtensionMap = new HashMap<>();

    static {
        for(Factory f : sSubclassFactories)
            for(String extension : f.getSupportedExtensions()) {
                sSupportedExtensions.add(extension);
                sExtensionMap.put(extension, f);
            }
    }

	/**
	 * Static method to create the appropriate CheapSoundFile subclass
	 * given a filename.
	 *
	 */
    public static CheapSoundFile create(String fileName, ProgressListener progressListener)
        throws java.io.IOException {
        File f = new File(fileName);
        if(!f.exists())
            throw new java.io.FileNotFoundException(fileName);
        String[] components = f.getName().toLowerCase().split("\\.");
        if(components.length < 2)
            return null;
        Factory factory = sExtensionMap.get(components[components.length - 1]);
        if(factory == null)
            return null;
        CheapSoundFile soundFile = factory.create();
        soundFile.setProgressListener(progressListener);
        soundFile.ReadFile(f);
        return soundFile;
    }

    protected ProgressListener mProgressListener = null;
    protected File mInputFile = null;

    protected CheapSoundFile() {
    }

    public void ReadFile(File inputFile) throws java.io.IOException
    {
        mInputFile = inputFile;
    }

    public void setProgressListener(ProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    public int getNumFrames() {
        return 0;
    }

    public int getSamplesPerFrame() {
        return 0;
    }

    public int[] getFrameGains() {
        return null;
    }

    public int getSampleRate() {
        return 0;
    }

    /**
     * If and only if this particular file format supports seeking
     * directly into the middle of the file without reading the rest of
     * the header, this returns the byte offset of the given frame,
     * otherwise returns -1.
     */
    public int getSeekableFrameOffset(int frame) {
        return -1;
    }
}
