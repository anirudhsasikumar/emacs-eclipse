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

package com.anirudh.emacseclipse;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Anirudh Sasikumar
 * @author Andreas Schmidt
 * @version 1.3.0
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.anirudh.flexclipse";

	// The shared instance
	private static Activator plugin;
	
	//The identifiers for the preferences	
	public static final String GNUCLIENT_PREFERENCE = "gnuclientname";
	public static final String SEXPR_PREFERENCE = "sexprarg";
	public static final String POSTLISP_PREFERENCE = "postlisp";

	//The default values for the preferences
	public static final String DEFAULT_GNUCLIENT_PREFERENCE = "emacsclientw";
	public static final String DEFAULT_SEXPR_PREFERENCE = "-e";
	public static final String DEFAULT_POSTLISP_PREFERENCE = "(raise-frame)";

	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	protected void initializeDefaultPreferences(IPreferenceStore store)
	{
		store.setDefault(GNUCLIENT_PREFERENCE, DEFAULT_GNUCLIENT_PREFERENCE);
		store.setDefault(SEXPR_PREFERENCE, DEFAULT_SEXPR_PREFERENCE);
		store.setDefault(POSTLISP_PREFERENCE, DEFAULT_POSTLISP_PREFERENCE);
	}
		
}
