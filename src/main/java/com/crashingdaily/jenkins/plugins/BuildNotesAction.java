package com.crashingdaily.jenkins.plugins;

/**
 * Records notes from a build as an action, so the content
 * is persistent in the builds's build.xml on the master.
 * @author crashingdaily@gmail.com
 */

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


import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.InvisibleAction;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class BuildNotesAction extends InvisibleAction {

    private AbstractBuild<?,?> build;
    private String content;
    private String notesTitle;
    private boolean isPlainText;
    
    public BuildNotesAction(AbstractBuild<?,?> build, String notesFilename, 
                            String notesTitle, boolean isPlainText) {
        this.build = build;
        this.notesTitle = notesTitle;
        this.isPlainText = isPlainText;
        content = readFile(notesFilename);
    }
    
    public String getNotesTitle() {
        return notesTitle;
    }
    
    public String getContent(){
        return content;
    }

    public boolean getisPlainText(){
        return isPlainText;
    }

    private String readFile(String notesFilename) {
        try {
            FilePath fp = getFilePath(notesFilename);
            if (fp == null || ! fp.exists()) {
                System.err.println(notesFilename + " is not a valid file for show-build-notes plugin");
                return null;
            }
            return IOUtils.toString(fp.read());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }

    private FilePath getFilePath(String notesFilename) throws IOException, InterruptedException {
        FilePath notesFile = build.getWorkspace().child(notesFilename);
        if (!notesFile.exists()) {
            System.err.println(notesFile.toString() + " not found");
            return null;
        }
        return notesFile;
    }
}
