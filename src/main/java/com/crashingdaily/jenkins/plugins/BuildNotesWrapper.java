package com.crashingdaily.jenkins.plugins;

/*
 *  The MIT License
 *
 *  Copyright (C) 2012 crashingdaily@gmail.com
 *  
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the
 *  Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall
 *  be included in all copies or substantial portions of the
 *  Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 *  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.io.IOException;
import hudson.Launcher;
import hudson.model.Hudson;
import hudson.tasks.BuildWrapper;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import java.util.Collections;
import org.kohsuke.stapler.DataBoundConstructor;
import java.util.List;
import java.util.ArrayList;

public class BuildNotesWrapper extends BuildWrapper {

    private String notesFilename;
    private String notesTitle;
    private boolean isPlainText;

    @DataBoundConstructor
    public BuildNotesWrapper(String notesFilename, String notesTitle, boolean isPlainText) {
        this.notesFilename = notesFilename;
        this.notesTitle = notesTitle;
        this.isPlainText = isPlainText;
    }

    public String getNotesFilename() {
        return notesFilename;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public boolean getIsPlainText() {
        return isPlainText;
    }
    
    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) 
      throws IOException, InterruptedException {
       return new BuildWrapper.Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener)
              throws IOException, InterruptedException {
                build.addAction(new BuildNotesAction(build, notesFilename, notesTitle, isPlainText));
                return true;
            }
        };
    }

    @Override
    public BuildNotesWrapperDescriptor getDescriptor() {
          
          BuildNotesWrapperDescriptor b = Hudson.getInstance().getDescriptorByType(BuildNotesWrapperDescriptor.class);
       return b;
    }

}