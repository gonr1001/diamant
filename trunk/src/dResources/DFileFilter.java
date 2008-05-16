/** 
 * 
 * Title: DFileFilter $Revision: 1.3 $  $Date: 2005-04-19 20:37:52 $
 * Description: DFileFilter is a class used to show some files 
 * 	            ended with extensions in the filter.
 *              
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @version $Revision: 1.3 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dResources;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * DFileFilter is a used to filter the files in the Files dialogs
 * 
 */
public class DFileFilter extends FileFilter {

	String[] extensions;

	String description;

	public DFileFilter(String ext) {
		this(new String[] { ext }, null);
	}

	/**
	 * Default constructor.
	 * 
	 * @param exts
	 *            List of extensions to include in the file filter.
	 * @param descr
	 *            Description for the filter.
	 */
	public DFileFilter(String[] exts, String descr) {
		// clone and lowercase the extensions
		extensions = new String[exts.length];
		for (int i = exts.length - 1; i >= 0; i--) {
			extensions[i] = exts[i].toLowerCase();
		}
		// make sure we have a valid (if simplistic) description
		description = (descr == null ? exts[0] + " files " : descr);
	}

	// Accept all directories and all gif, jpg, or tiff files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		// ok, it's a regular file so check the extension
		String name = f.getName().toLowerCase();
		for (int i = extensions.length - 1; i >= 0; i--) {
			if (name.endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The description for this filter.
	 */
	public String getDescription() {
		return description;
	}
} /* end DFilter */
