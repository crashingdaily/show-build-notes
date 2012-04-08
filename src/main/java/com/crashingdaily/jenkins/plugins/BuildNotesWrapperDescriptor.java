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

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.util.ListBoxModel;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;


@Extension
public class BuildNotesWrapperDescriptor extends BuildWrapperDescriptor {

    private String notesFilename;
    private String notesTitle;
    private boolean isPlainText;
    
    public BuildNotesWrapperDescriptor() {
        super(BuildNotesWrapper.class);
        load();
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

    public void setNotesFilename(final String notesFilename) {
        this.notesFilename = notesFilename;
    }

    public void setNotesTitle(final String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public void setIsPlainText(final boolean isPlainText) {
        this.isPlainText = isPlainText;
    }

    public boolean configure(final StaplerRequest request, final JSONObject formData) {
        request.bindJSON(this, formData);
        save();
        return true;
    }

    @Override
    public String getDisplayName() {
        return "Display file contents as notes on the build summary page";
    }

    @Override
    public boolean isApplicable(AbstractProject<?, ?> abstractProject) {
        return true;
    }

}
