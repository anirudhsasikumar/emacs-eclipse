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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class BaseHandler {

	public static IDocument document;
	public static ISourceViewer sourceViewer;
	public static IEditorPart editor;
	
	public static Boolean resolveDocument()
	{
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IPartService partService=window.getPartService();
		IWorkbenchPart part=partService.getActivePart();

	   
		if (!(part instanceof IEditorPart) )
		{
			MessageDialog.openInformation(
					window.getShell(),
					"Emacs Eclipse Plug-in",
					"Unexpected part: " + part);
	      return false;
		}
		
		
	   BaseHandler.editor = (IEditorPart)part;
	   BaseHandler.sourceViewer = (ISourceViewer)editor.getAdapter(ITextOperationTarget.class);
	   BaseHandler.document = BaseHandler.sourceViewer.getDocument();
	   
	   return true;
	}
}
