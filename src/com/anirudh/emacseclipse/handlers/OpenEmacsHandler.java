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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenEmacsHandler extends AbstractHandler {
    /**
     * The constructor.
     */
    public OpenEmacsHandler() {
    }
    
    /**
     * the command has been executed, so extract extract the needed information
     * from the application context.
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);		
        
        if ( BaseHandler.resolveDocument() == false )
        {
            return null;
        }
        
        
        ISourceViewer editor = BaseHandler.sourceViewer;	   
        
        try
        {
            ISelection cursorpos = editor.getSelectionProvider().getSelection();
            if ( cursorpos instanceof ITextSelection )
            {
                ITextSelection currSelection = (ITextSelection)cursorpos;
                int currOffset;
                EmacsHandler emacs = new EmacsHandler();
                
                if ( BaseHandler.editor.getEditorInput() instanceof IPathEditorInput )
                {
                    String path = ((IPathEditorInput)BaseHandler.editor.getEditorInput()).getPath().toOSString();
                    emacs.emacsFindFile(path);
                    if ( path.toLowerCase().endsWith(".mxml") || path.toLowerCase().endsWith(".as") )
                    {
                        currOffset = currSelection.getStartLine();
                        emacs.emacsGotoLine(currOffset);
                    }
                    else
                    {
                        currOffset = currSelection.getOffset();
                        emacs.emacsGotoChar(currOffset);
                    }
                    
                }
                else
                {
                    emacs.emacsShowMessage("Error: Not an IPathEditorInput");
                }
                emacs.emacsFlush();			 	   
            }
        }
        catch (Exception e)
        {
            MessageDialog.openInformation(
                window.getShell(),
                "Emacs Eclipse Plug-in",
                "Exception: " + e);
        }
        
        return null;
    }
}
