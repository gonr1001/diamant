package dResources;

/* Title:        Diamant
 * Version:      0.0
 * Copyright:    Copyright (c) 1999, 2000
 * All Rights Reserved
 * Author:       Rgr
 * This file is part of the Diamant Project
 */



import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * DFileFilter is a used to filter the files
 * in the Files dialogs
 *
 * @author 	Ruben Gonzalez Rubio
 * @version 	%I%, %G%
 * @since       JDK1.2.1
 */
public class DFileFilter extends FileFilter {

  String [] extensions;
  String description;

  public DFileFilter ( String ext ) {
    this( new String[] { ext }, null );
  }

  /**
   * Default constructor.
   *
   * @param exts List of extensions to include in the file filter.
   * @param descr Description for the filter.
   */
  public DFileFilter ( String [] exts, String descr ) {
    // clone and lowercase the extensions
    extensions = new String [ exts.length ];
    for( int i = exts.length - 1; i >= 0; i--) {
      extensions [ i ] = exts [ i ].toLowerCase();
    }
    // make sure we have a valid (if simplistic) description
    description = ( descr == null ? exts [ 0 ] + " files " : descr );
  }

  // Accept all directories and all gif, jpg, or tiff files.
  public boolean accept (File f) {
    if ( f.isDirectory() ) {
      return true;
    }

    // ok, it's a regular file so check the extension
    String name = f.getName().toLowerCase();
    for(  int i = extensions.length - 1; i >= 0; i--) {
      if ( name.endsWith( extensions [ i ] ) ) {
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
}

