/* Emacs Eclipse Plugin. Open active buffer in eclipse in emacs,
 * preseves Cursor position (only line position in case of .mxml and
 * .as files). */

/* Author: Anirudh Sasikumar http://anirudhs.chaosnet.org/ */
/* Migration to emacsclientw: Andreas Schmidt http://www.binpot.net */

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

/**
 * Translator for the high level commands used by {@link OpenEmacsHandler.java} to
 * to Elisp commands for emacsclientw.
 * 
 * The Elisp command sequence is wrapped in a progn block and put in quotes, such
 * that emacsclient[w] -e is able to execute it, eg.
 * "(progn (find-file \"foo.java\")(with-current-buffer (window-buffer)(if (fboundp 'eclipse-goto-offset)...)))"
 * 
 * Note: The whole composition of the Elisp progn string is depending on {@link OpenEmacsHandler.java}
 * executing the commands in the proper order and refactoring should be done with care.
 * 
 * @author Anirudh Sasikumar
 * @author Andreas Schmidt
 * @version 1.3.0
 */
public class EmacsHandler 
{
    /* Portions of this code are from "The Emacs Plug-In for Eclipse"
     * by Alan Donovan licensed under the LGPL */
    
	//wrap command in progn block and quote
    private final StringBuffer command = new StringBuffer("\"(progn ");
	
    /**
     *  Transmit the current set of elisp commands to Emacs, and reset.
     */
    public void emacsFlush() 
    {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        
        command.append(elispQuote(store.getString(Activator.POSTLISP_PREFERENCE), false));
        // close (progn of command string and end quote
        command.append(")\"");
        String elisp = command.toString();

        String[] cmdarray = {store.getString(Activator.GNUCLIENT_PREFERENCE), store.getString(Activator.SEXPR_PREFERENCE),   elisp};
        try {
            Runtime.getRuntime().exec(cmdarray); // fire and forget
        } catch(IOException e) {} // ignore errors
        
        command.setLength(0); // clear
    }
    
    public void emacsGotoChar(int start) 
    {
    	command.append("(with-current-buffer (window-buffer)");
        command.append("(if (fboundp 'eclipse-goto-offset)");
        command.append("(eclipse-goto-offset " + start + ")");
        command.append("(goto-char (+ 1 " + start + ")))");
        command.append("(recenter)");
        command.append(")");
    }
    
    public void emacsGotoLine(int line) 
    {
        line++;
        command.append("(with-current-buffer (window-buffer)");
        command.append("(goto-line "+line+")"+
                       "(recenter)");
        command.append(")");
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
