/* Emacs Eclipse Plugin. Open active buffer in eclipse in emacs,
 * preseves Cursor position (only line position in case of .mxml and
 * .as files). */

/* Author: Anirudh Sasikumar http://anirudhs.chaosnet.org/ */

/* Emacs Eclipse Plugin. Copyright (C) 2009 Anirudh Sasikumar */

/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version. */

/* This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. */

/* You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA */

package com.anirudh.emacseclipse.handlers;

import java.io.IOException;

import org.eclipse.jface.preference.IPreferenceStore;

import com.anirudh.emacseclipse.Activator;

public class EmacsHandler 
{
    /* Portions of this code are from "The Emacs Plug-In for Eclipse"
     * by Alan Donovan licensed under the LGPL */
    
    /**
     *  Transmit the current set of elisp commands to Emacs, and reset.
     */
    public void emacsFlush() 
    {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        command.append(elispQuote(store.getString(Activator.POSTLISP_PREFERENCE), false));
        
        String elisp = command.toString();

        String[] cmdarray = {store.getString(Activator.GNUCLIENT_PREFERENCE), store.getString(Activator.SEXPR_PREFERENCE),   elisp};
        try {
            Runtime.getRuntime().exec(cmdarray); // fire and forget
        } catch(IOException e) {} // ignore errors
        
        command.setLength(0); // clear
    }
    
    private final StringBuffer command = new StringBuffer(128);
    
    public void emacsGotoChar(int start) 
    {
        start++; // Emacs used 1-based numbering
        command.append("(goto-char "+start+")"+
                       "(recenter)");
    }
    
    public void emacsGotoLine(int line) 
    {
        line++;
        command.append("(goto-line "+line+")"+
                       "(recenter)");
    }
    
    public void emacsFindFile(String path) 
    {
        command.append("(find-file "+elispQuote(path, true)+")");
    }
    
    public void emacsShowMessage(String message) 
    {
        command.append("(message \\\"%s\\\" "+elispQuote(message, true)+")");
    }
    
    // Java String -> Elisp string literal: escape \" and \     \
    private String elispQuote(String str, Boolean prepend) 
    {
        StringBuffer sb = new StringBuffer(str.length()+10);
        if ( prepend )
        {
            sb.append("\\\"");
        }
        for(int ii=0; ii<str.length(); ++ii) {
            char c = str.charAt(ii);
            if(c == '\"' || c == '\\')
                sb.append('\\');
            sb.append(c);
        }
        if ( prepend )
        {
            sb.append("\\\"");
        }
        return sb.toString();
    }
}
