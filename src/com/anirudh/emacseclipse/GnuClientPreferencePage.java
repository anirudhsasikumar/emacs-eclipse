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

package com.anirudh.emacseclipse;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GnuClientPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
	Text gnuClientText;
	Text clientArgsText;
	Text elispText;

	@Override
	protected Control createContents(Composite parent) {
		Composite mainTable = new Composite(parent, SWT.NULL);
		
		GridData data = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);;
		data.grabExcessHorizontalSpace = true;
		mainTable.setLayoutData(data);

		GridLayout grid = new GridLayout();
		grid.numColumns = 2;
		mainTable.setLayout(grid);
		
		Label lbl = new Label(mainTable, SWT.NONE);
		lbl.setText("Gnuclient Executable Name:");
		

		gnuClientText = new Text(mainTable, SWT.BORDER);
		//Create a data that takes up the extra space in the dialog .
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		
		gnuClientText.setLayoutData(data);
		gnuClientText.setText(getPreferenceStore().getString(Activator.GNUCLIENT_PREFERENCE));
		
		lbl = new Label(mainTable, SWT.NONE);
		lbl.setText("Gnuclient Elisp Argument:");
		

		clientArgsText = new Text(mainTable, SWT.BORDER);
		//Create a data that takes up the extra space in the dialog .
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;		
		clientArgsText.setLayoutData(data);
		clientArgsText.setText(getPreferenceStore().getString(Activator.SEXPR_PREFERENCE));
		
		lbl = new Label(mainTable, SWT.NONE);
		lbl.setText("Elisp to execute after (find-file)(goto-char):");
		

		elispText = new Text(mainTable, SWT.BORDER);
		//Create a data that takes up the extra space in the dialog .
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;		
		elispText.setLayoutData(data);
		elispText.setText(getPreferenceStore().getString(Activator.POSTLISP_PREFERENCE));
		
		return mainTable;
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		setPreferenceStore(Activator.getDefault().getPreferenceStore());

	}
	

	protected void performDefaults() {
		gnuClientText.setText(Activator.DEFAULT_GNUCLIENT_PREFERENCE);
		clientArgsText.setText(Activator.DEFAULT_SEXPR_PREFERENCE);
		elispText.setText(Activator.DEFAULT_POSTLISP_PREFERENCE);
	}
	/** 
	 * Method declared on IPreferencePage. Save the
	 * author name to the preference store.
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(Activator.GNUCLIENT_PREFERENCE, gnuClientText.getText());
		store.setValue(Activator.SEXPR_PREFERENCE, clientArgsText.getText());
		store.setValue(Activator.POSTLISP_PREFERENCE, elispText.getText());
		return super.performOk();
	}

}
